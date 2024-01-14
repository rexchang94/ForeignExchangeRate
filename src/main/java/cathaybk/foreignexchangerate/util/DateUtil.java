package cathaybk.foreignexchangerate.util;

import cathaybk.foreignexchangerate.enums.ErrorCode;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    public static String convertDateFormat(String dateStr, String fromFormat, String toFormat) {
        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(fromFormat));
            return date.format(DateTimeFormatter.ofPattern(toFormat));
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private static boolean isValidDateFormat(String dateStr, String format) {
        try {
            LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(format));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate parseAndValidateDate(String dateStr, LocalDate oneYearAgo, LocalDate yesterday) {
        if (!isValidDateFormat(dateStr, "yyyy/MM/dd")) {
            throw new DateTimeParseException(ErrorCode.DATE_RANGE_ERROR.getCode(), dateStr, 0);
        }

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        if (date.isBefore(oneYearAgo) || date.isAfter(yesterday)) {
            throw new DateTimeParseException("日期超出可查詢範圍", dateStr, 0);
        }

        return date;
    }
}

