package cathaybk.foreignexchangerate.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ForexDataDTO {

    @JsonProperty("Date")
    private String date;

    @JsonProperty("USD/NTD")
    private Double usdNtd;

    @JsonProperty("RMB/NTD")
    private Double rmbNtd;

    @JsonProperty("EUR/USD")
    private Double eurUsd;

    @JsonProperty("USD/JPY")
    private Double usdJpy;

    @JsonProperty("GBP/USD")
    private Double gbpUsd;

    @JsonProperty("AUD/USD")
    private Double audUsd;

    @JsonProperty("USD/HKD")
    private Double usdHkd;

    @JsonProperty("USD/RMB")
    private Double usdRmb;

    @JsonProperty("USD/ZAR")
    private Double usdZar;

    @JsonProperty("NZD/USD")
    private Double nzdUsd;

}
