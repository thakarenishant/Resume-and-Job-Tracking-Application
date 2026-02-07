package org.web.resumeandjobtracking.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddingResponse {
    private EmbeddingData embedding;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmbeddingData {
        private List<Double> values;
    }
}
