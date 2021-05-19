package com.boilerplate.demo.helper.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    private static final String SMART_DATE_DETECTION = "^((0[1-9]|1[0-9]|3[01])[- \\/.])*((0[1-9]|1[012])[- \\/.])*(19|20)\\d\\d$";
    private static final String ZIPCODE_REGEX = "^(\\d{5})$";

    public static boolean looksLikeDateMonthOrYear(String input) {
        String suspect = StringUtils.trimToEmpty(input);

        if (StringUtils.isBlank(suspect)) {
            return false;
        }

        Pattern pattern = Pattern.compile(SMART_DATE_DETECTION, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(suspect);
        return matcher.find();
    }

    public static boolean isZipCodeValid(String input){
        String suspect = StringUtils.trimToEmpty(input);


        if (StringUtils.isBlank(suspect)) {
            return false;
        }

        String[] splitStr = suspect.split("\\s+");
        Pattern validCharacterPattern = Pattern.compile(ZIPCODE_REGEX);
        Matcher matcher = validCharacterPattern.matcher(splitStr[0]);
        return matcher.find();
    }
}
