package org.eotpremnice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DespatchAdviceStatusResponse {

    private String id;
    private String status;
}