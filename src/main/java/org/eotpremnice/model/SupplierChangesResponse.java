package org.eotpremnice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SupplierChangesResponse {

    private List<Item> items;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Item {
        private String id;
        private String type;
        private String date;
        private String requestId;
        private DataBlock data;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DataBlock {
        private String documentId;       // <-- SEF_ID (data.despatchAdvice.id)
        private String status;
    }
}