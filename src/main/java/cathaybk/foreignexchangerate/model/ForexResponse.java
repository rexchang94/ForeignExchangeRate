package cathaybk.foreignexchangerate.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForexResponse {
    private ErrorInfo error;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<CurrencyData> currency;

    public ForexResponse(ErrorInfo errorInfo) {
        this.error = errorInfo;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CurrencyData {
        private String date;
        private Double usd;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ErrorInfo {
        private String code;
        private String message;

    }
}
