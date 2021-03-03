package ru.parse;

import org.apache.commons.collections.map.HashedMap;

import javax.swing.plaf.PanelUI;
import java.time.LocalDateTime;
import java.util.Map;

public class ParseDate {
    private Map<String, Integer> months;

    public ParseDate() {
        init();
    }

    private void init() {
        months = new HashedMap();
        months.put("янв", 1);
        months.put("фев", 2);
        months.put("мар", 3);
        months.put("апр", 4);
        months.put("май", 5);
        months.put("июн", 6);
        months.put("июл", 7);
        months.put("авг", 8);
        months.put("сен", 9);
        months.put("окт", 10);
        months.put("ноя", 11);
        months.put("дек", 12);
    }

    public LocalDateTime parse(String dateLine) {
        dateLine = dateLine.split(" \\[")[0];
        String[] dateTimeArr = dateLine.split(", ");
        String[] timeArr = dateTimeArr[1].split(":");
        if (dateTimeArr[0].contains("сегодня")) {
            return  getLocalDateTime(LocalDateTime.now(),
                    timeArr[0],
                    timeArr[1]);
        } else if (dateTimeArr[0].contains("вчера")) {
            return  getLocalDateTime(LocalDateTime.now().minusDays(1),
                    timeArr[0],
                    timeArr[1]);
        }
        return getLocalDateTime(dateTimeArr);
    }

    private LocalDateTime getLocalDateTime(LocalDateTime localDate,
                                           String hours, String minutes ) {
       return LocalDateTime.of(localDate.getDayOfYear(),
               localDate.getMonthValue(),
               localDate.getDayOfMonth(),
               getInt(hours),
               getInt(minutes));
    }

    private LocalDateTime getLocalDateTime(String[] dateTime) {
        String[] dateArr = dateTime[0].split(" ");
        String[] timeArr = dateTime[1].split(":");
        return LocalDateTime.of(getInt("20" + dateArr[2]),
                months.get(dateArr[1]),
                getInt(dateArr[0]),
                getInt(timeArr[0]),
                getInt(timeArr[1]));
    }

    private int getInt(String number) {
        if (number.matches("//d+")) {
            throw new IllegalArgumentException("Something went wrong");
        }
        return Integer.parseInt(number);
    }
}
