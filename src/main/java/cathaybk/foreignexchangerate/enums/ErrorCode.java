package cathaybk.foreignexchangerate.enums;

public enum ErrorCode {
    SUCCESS("0000", "成功"),
    DATE_RANGE_ERROR("E001", "日期超出可查詢範圍"),
    INVALID_FORMAT("E002", "日期格式錯誤"),
    INTERNAL_SERVER_ERROR("E500", "發生異常，請聯繫IT人員");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

