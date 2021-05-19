package com.boilerplate.demo.helper.date;

import com.boilerplate.demo.helper.utils.RegexUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static final ZoneId CET = ZoneId.of("CET");
    public static final String DDMMYYYY = "dd/MM/yyyy";
    public static final String ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static long ONE_DAY_IN_MILLISECOND = 86400000l;
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);

    public static Date dateOrFarPast(Date date) {
        return date != null ? date : farPast();
    }

    public static Date dateOrFarFuture(Date date) {
        return date != null ? date : farFuture();
    }

    public static Date farPast() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, Calendar.JANUARY, 1);
        return calendar.getTime();
    }
    public static Date farFuture() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2999, Calendar.DECEMBER, 31);
        return calendar.getTime();
    }

    /**
     * "2021" → then from 01/01/2021 - 31/12/2021
     * "09/2020" → then from 01/09/2020 - 30/09/2020
     * "01/08/2020" → then from 01/08/2020 - 01/08/2020
     * @param input dd/MM/yyyy, or MM/yyyy, or yyyy
     * @return the range
     */
    public static Pair<Date, Date> parseDateRange(String input) {

        if (RegexUtils.looksLikeDateMonthOrYear(input)) {
            try {
                String[] parts = StringUtils.split(input, "/");
                ArrayUtils.reverse(parts);

                String year = parts[0];
                int y = Integer.parseInt(year);

                String monthFrom = "01";
                String monthTo = "12";

                String dayFrom = "01";
                String dayTo = "31";

                if (parts.length >= 2) {
                    monthFrom = parts[1];
                    monthTo = parts[1];

                    int m = Integer.parseInt(monthFrom);
                    if (m == 2) {
                        GregorianCalendar calendar = new GregorianCalendar();
                        if (calendar.isLeapYear(y)) {
                            dayTo = "29";
                        } else {
                            dayTo = "28";
                        }
                    } else if (Arrays.asList(4, 6, 9, 11).contains(m)) {
                        dayTo = "30";
                    }
                }
                if (parts.length == 3) {
                    dayFrom = parts[2];
                    dayTo = parts[2];
                }
                String from = dayFrom + "/" + monthFrom + "/" + year;
                String to = dayTo + "/" + monthTo + "/" + year;
                String dateFormat = DDMMYYYY;

                Date dateFrom = Date.from(convertStringToLocalDate(from, dateFormat).atStartOfDay(
                        ZoneId.systemDefault()).toInstant());
                Date dateTo = Date.from(convertStringToLocalDate(to, dateFormat).atStartOfDay(
                        ZoneId.systemDefault()).toInstant());

                return Pair.create(dateFrom, dateTo);

            } catch (Exception e) {
                logger.warn("could not parse to date range: " + input, e);
            }
        }
        return null;
    }

    public static String formatDateToString(Long date, String pattern) {
        return DateTimeFormatter.ofPattern(pattern).format(LocalDateTime.ofInstant(Instant.ofEpochMilli(date), ZoneOffset.UTC));
    }

    public static String convertLocalDateToString(LocalDate date) {
        return convertLocalDateToString(date, "dd/MM/yyyy");
    }

    public static String convertLocalDateToString(LocalDate date, String dateFormat){
        if(!(date instanceof LocalDate)){
            throw new IllegalArgumentException("Invalid LocalDate Object.");
        }
        if(StringUtils.isBlank(dateFormat)){
            throw new IllegalArgumentException("Invalid Date Format.");
        }
        return date.format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static LocalDate convertStringToLocalDate(String date, String dateFormat){
        if(StringUtils.isBlank(date)){
            throw new IllegalArgumentException("Invalid Date String.");
        }
        if(StringUtils.isBlank(dateFormat)){
            throw new IllegalArgumentException("Invalid Date Format.");
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
    }

    public static LocalDate convertStringToLocalDateSafely(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return convertStringToLocalDate(date, DDMMYYYY);
    }

    public static LocalDate convertDateToLocalDate(Date date, String dateFormat){
        if(!(date instanceof Date)){
            throw new IllegalArgumentException("Invalid Date Object.");
        }
        if(StringUtils.isBlank(dateFormat)){
            throw new IllegalArgumentException("Invalid Date Format.");
        }
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String convertDateToString(Date date, String dateFormat){
        if(!(date instanceof Date)){
            throw new IllegalArgumentException("Invalid Date Object.");
        }
        if(StringUtils.isBlank(dateFormat)){
            throw new IllegalArgumentException("Invalid Date Format.");
        }
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate().format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String formatDate(String date) {
        if(StringUtils.isBlank(date)) {
            return date;
        }
        String[] dateComponents = date.split("-");
        date = dateComponents[2] + "/" + dateComponents[1] + "/" + dateComponents[0];
        return date;
    }

    public static ZonedDateTime nowAtCET() {
        return ZonedDateTime.now(CET);
    }

    public static long timestamp(ZonedDateTime zdt) {
        return zdt.toInstant().toEpochMilli();
    }

    public static Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date getEndOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }


    /**
     *
     * @param localIsoDateTimeCET e.g. 2021-01-23T17:50:46
     * @return
     */
    public static Date parseLocalIsoDateTimeCET(String localIsoDateTimeCET) {
        if (StringUtils.isBlank(localIsoDateTimeCET)) {
            return null;
        }
        TemporalAccessor ta = DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(localIsoDateTimeCET);
        LocalDateTime localDateTime = LocalDateTime.from(ta);
        return Date.from(localDateTime.atZone(ZoneId.of("CET")).toInstant());
    }

    /**
     *
     * @param isoDateTime e.g. 2021-01-22T12:40:28.970+01:00
     * @return
     */
    public static Date parseIsoDateTime(String isoDateTime) {
        if (StringUtils.isBlank(isoDateTime)) {
            return null;
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ISO_DATETIME);
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(isoDateTime, dtf);
        return Date.from(zonedDateTime.toInstant());
    }


    public static String convertEpochMilliToDateTime(Long timestamp, String format){
        if(timestamp == null) {
            return null;
        }
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault());
        //LocalDateTime dateTime = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

	public static String convertEpochMilliToDateTime(Long timestamp, String format, ZoneId zoneId) {
		
		if (zoneId == null) {
			zoneId = ZoneOffset.UTC;
		}
		if (timestamp == null) {
			return null;
		}
		LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		return dateTime.format(formatter);
	}

    public static String epochMilliToString(Long timestamp, String format){
        if(timestamp == null) {
            return null;
        }
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return dateTime.format(formatter);
    }

    public static Long stringToEpochMilli(String s, String format){
        if(StringUtils.isBlank(s)) {
            return null;
        }
        LocalDate localDate = LocalDate.parse(s, DateTimeFormatter.ofPattern(format));
        long epochMilli = localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
        return epochMilli;
    }
}
