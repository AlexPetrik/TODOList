package ru.alexpetrik.todolist.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

    private static final String DATE_PATTERN = "dd.MM.yyyy";

    private static final DateTimeFormatter DATE_FORMATER =
            DateTimeFormatter.ofPattern(DATE_PATTERN);

    public static String formate(LocalDate localDate){
        if (localDate == null)
            return null;
        return DATE_FORMATER.format(localDate);
    }

    public static LocalDate parse(String localDate){
        try {
            return DATE_FORMATER.parse(localDate, LocalDate::from);
        } catch (DateTimeParseException e){
            return null;
        }

    }

    public static boolean validDate(String localDate){

        return DateUtil.parse(localDate) != null;

    }

}
