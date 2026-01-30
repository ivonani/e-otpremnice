package org.eotpremnice.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.eotpremnice.model.DespatchAdviceStatusResponse;
import org.eotpremnice.model.SupplierChangesResponse;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class SefClient {

    private final RestTemplate restTemplate;

    public ResponseEntity<String> sendUblXml(
            Path xmlFile, String url, String apiKey, String requestId) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.set("Api-key", apiKey);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("RequestId", requestId);
        body.add("File", new FileSystemResource(xmlFile.toFile()));

        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);

        String fullUrl = UriComponentsBuilder
                .fromHttpUrl(url)
                .path("/requests")
                .toUriString();

        return restTemplate.exchange(fullUrl, HttpMethod.POST, entity, String.class);
    }

    public ResponseEntity<String> getSupplierChangesRaw(String urlBase, String file, LocalDate date, String requestId) {
        String url = UriComponentsBuilder
                .fromHttpUrl(urlBase) // npr https://api.... (Parametri.URL)
                .path("/requests/changes")
                .queryParam("date", date.toString())        // YYYY-MM-DD
                .queryParam("requestId", requestId.trim())  // RTRIM
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-key", file);
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    public ResponseEntity<String> getDespatchAdviceStatus(
            String urlBase,
            String apiKey,
            String sefId
    ) {
        String url = UriComponentsBuilder
                .fromHttpUrl(urlBase)
                .path("/suppliers/despatch-advices/")
                .path(sefId.trim())
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Api-key", apiKey);
        headers.setAccept(Collections.singletonList(MediaType.TEXT_PLAIN));

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }


    public SupplierChangesResponse parseChanges(ObjectMapper om, String json) throws Exception {
        return om.readValue(json, SupplierChangesResponse.class);
    }

    public DespatchAdviceStatusResponse parseChangesStatus(ObjectMapper om, String json) throws Exception {
        return om.readValue(json, DespatchAdviceStatusResponse.class);
    }
}