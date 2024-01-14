package cathaybk.foreignexchangerate.enums;

import lombok.Data;


public enum ApiUrl {

    FOREX("https://openapi.taifex.com.tw/v1/DailyForeignExchangeRates");


    private final String url;

    ApiUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

}
