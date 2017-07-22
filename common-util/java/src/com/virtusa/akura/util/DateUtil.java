/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
 *
 * Copyright (C) 2012 Virtusa Corporation.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

package com.virtusa.akura.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.util.reporting.ReportUtil;

/**
 * This util method is to manipulate date type functions.
 *
 * @author Virtusa Corporation
 */

public final class DateUtil {

    /** Constant for string comma. */
    private static final String STRING_COMMA = ",";

    /** Constant for string underscore. */
    private static final String STRING_UNDERSCORE = "_";

    /** Constant for DATE. for report */
    private static final String DATE = "Report_date";

    /** Constant for EEEE d MMMM yyyy. */
    private static final String EEEE_D_MMMM_YYYY = "EEEE d MMMM yyyy";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(DateUtil.class);

    /** key to hold Date format. */
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /** The constant for year and month format. */
    private static final String YEAR_FORMAT = "-01-01";

    /** Represent error message when parsing the date. */
    private static final String DATE_CONVERSION_ERROR = "Date Conversion Error";

    /** Date format year. */
    private static final String DATE_FORMAT_YYYY = "yyyy";

    /** key to hold error message while parsing date. */
    private static final String ERROR_PARSING_THE_DATE = "Error while copying the file to parsing the date-->";

    /** key to hold code of parse exception. */
    private static final String PARSE_EXCEPTION = "Parse Exception";

    /**
     * The default constructor.
     */
    private DateUtil() {

    }

    /**
     * Method to retrieve Date type year value given by String year.
     *
     * @param year - year defined by String type variable.
     * @return Date type object.
     * @throws AkuraAppException throws when exception occurs
     */
    public static Date getDateTypeYearValue(String year) throws AkuraAppException {

        DateFormat formatter;
        try {
            formatter = new SimpleDateFormat(DATE_FORMAT_YYYY);
            return formatter.parse(year);
        } catch (ParseException e) {
            LOG.error(ERROR_PARSING_THE_DATE + e.toString());
            throw new AkuraAppException(PARSE_EXCEPTION, e);
        }
    }

    /**
     * Returns a list that contains the year.
     *
     * @param maxYear - the number that needs of the past year.
     * @return - returns the year.
     */
    public static List<String> getPastYears(final int maxYear) {

        List<String> yearList = new ArrayList<String>();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        yearList.add(String.valueOf(year));
        for (int index = 1; index < maxYear; index++) {
            int newYear = year - index;
            yearList.add(String.valueOf(newYear));
        }
        return yearList;
    }

    /**
     * Returns a list that contains the year.
     *
     * @param maxYear - the number that needs of the future year.
     * @return - returns the year.
     */
    public static List<String> getFutureYears(final int maxYear) {

        List<String> yearList = new ArrayList<String>();

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        yearList.add(String.valueOf(year));
        for (int index = 1; index < maxYear; index++) {
            int newYear = year + index;
            yearList.add(String.valueOf(newYear));
        }
        return yearList;
    }

    /**
     * Method to retrieve integer type year value given by Date type value.
     *
     * @param date - date defined by Date type variable.
     * @return integer type object which holds a year.
     */
    public static int getYearFromDate(Date date) {

        int result = -1;
        if (date != null) {

            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            result = cal.get(Calendar.YEAR);
        }
        return result;
    }

    /**
     * get current year.
     *
     * @return Date object
     * @throws AkuraAppException - throws when exception occurs.
     */
    public static Date currentYear() throws AkuraAppException {

        Calendar cal = Calendar.getInstance();
        Date date = null;
        int year = cal.get(Calendar.YEAR);
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_YYYY);
        try {
            date = (Date) formatter.parse(String.valueOf(year));
        } catch (ParseException e) {
            LOG.error(ERROR_PARSING_THE_DATE + e.toString());
            throw new AkuraAppException(PARSE_EXCEPTION, e);
        }
        return date;
    }

    /**
     * get current year.
     *
     * @return int object
     * @throws AkuraAppException - throws when exception occurs.
     */
    public static int currentYearOnly() throws AkuraAppException {

        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * @param min min date.
     * @param max max date.
     * @param d date.
     * @return the value.
     */
    public static boolean isDateBetween(Date min, Date max, Date d) {

        return (min.equals(d) || max.equals(d) || (d.after(min) && d.before(max)));
    }

    /**
     * Get the parse date using SimpleDateFormat.
     *
     * @param date - date to be parse.
     * @return parsed date.
     * @throws AkuraAppException - SMSAppException
     */
    public static Date getParseDate(String date) throws AkuraAppException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        Date returnDate = null;
        try {
            returnDate = dateFormat.parse(date);
        } catch (ParseException e) {
            LOG.error(ERROR_PARSING_THE_DATE + e.toString());
            throw new AkuraAppException(PARSE_EXCEPTION, e);
        }
        return returnDate;
    }

    /**
     * Get the format date using SimpleDateFormat.
     *
     * @param date - the date to be format.
     * @return the formatted date.
     * @throws AkuraAppException - SMSAppException
     */
    public static String getFormatDate(Date date) throws AkuraAppException {

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(date);
    }

    /**
     * Get the format date using SimpleDateFormat.
     *
     * @param date - the date to be format.
     * @return the formatted date.
     * @throws AkuraAppException - SMSAppException
     */
    public static String getStringYear(Date date) throws AkuraAppException {

        SimpleDateFormat simpleDateformat = new SimpleDateFormat(DATE_FORMAT_YYYY);
        return simpleDateformat.format(date);
    }

    /**
     * Converts the date of type String into Date format.
     *
     * @param stringDate - the date.
     * @return - the date in type Date.
     * @throws ParseException - The exception details that occurred when processing.
     */
    public static Date convertStringToDate(String stringDate) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.parse(stringDate);
    }

    /**
     * Check the given date is a holiday or not.
     *
     * @param holidayList - list consist of Holidays for the given time period.
     * @param currentDate - currentDate
     * @return boolean
     */
    public static boolean isHoliday(List<Holiday> holidayList, Date currentDate) {

        boolean flag = false;

        for (Holiday tempHoliday : holidayList) {
            if ((currentDate.after(tempHoliday.getStartDate()) && currentDate.before(tempHoliday.getEndDate()))
                    || currentDate.equals(tempHoliday.getStartDate()) || currentDate.equals(tempHoliday.getEndDate())) {

                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Convert Date into format which is used in reports.
     *
     * @param date the date to be format.
     * @return formatted date
     */
    public static String getLongDate(Date date) {

        DateFormat d = new SimpleDateFormat(EEEE_D_MMMM_YYYY);
        return d.format(date);
    }

    /**
     * Returns the date as '2012-01-01'.
     *
     * @param year - the year.
     * @return - the year as a '2012-01-01' date.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public static Date getDate(final int year) throws AkuraAppException {

        String stringYear = year + YEAR_FORMAT;
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);

        try {
            return (Date) formatter.parse(stringYear);
        } catch (ParseException parseException) {
            throw new AkuraAppException(DATE_CONVERSION_ERROR, parseException);
        }
    }

    /**
     * Gets the age by passing date of birth.
     *
     * @param dateOfBirth the date of birth
     * @return the age
     */
    public static int getAge(Date dateOfBirth) {

        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age;
    }

    /**
     * get the first day of the current month as Date object.
     *
     * @return Date
     */
    public static Date getFistDayOfMonth() {

        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);

        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        return cal.getTime();
    }

    /**
     * get the day, month, year for report date column.
     *
     * @param locales of the local date
     * @return Date
     */
    public static String getReportGeneratedDate(String locales) {

        Locale locale = new Locale(locales);
        return new SimpleDateFormat(PropertyReader.getPropertyValue(ReportUtil.REPORT_TEMPLATE, DATE), locale)
                .format(new Date());
    }

    /**
     * Checks the relevant date equals or before the given date.
     *
     * @param relvantDate - the date to be need to compare.
     * @param givenDate - the given date.
     * @return - the status of the comparison.
     */
    public static boolean isDateBeforeGivenDate(final Date givenDate, final Date relvantDate) {

        return givenDate.equals(relvantDate) || relvantDate.before(givenDate);
    }

    /**
     * this methode is for getting all the months ,but before current month.
     *
     * @param selectedYear - the selected Year.
     * @param yearMap - map of month of year.
     * @return - the year months up to previous current month.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    public static String getMonthsBySelectedYear(int selectedYear, Map<Integer, String> yearMap)
            throws AkuraAppException {

        int currentYear = DateUtil.currentYearOnly();
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int month = cal.get(Calendar.MONTH);

        StringBuilder allYearMonths = new StringBuilder();

        Map<Integer, String> yearTempMap = new TreeMap<Integer, String>();

        if (selectedYear == currentYear) {

            yearTempMap.putAll(yearMap);

            for (Map.Entry<Integer, String> tempEntry : yearTempMap.entrySet()) {

                if (tempEntry.getKey() > month) {
                    yearMap.remove(tempEntry.getKey());
                }
            }
        }

        StringBuilder months = new StringBuilder();
        boolean isFirst = true;

        for (Map.Entry<Integer, String> entry : yearMap.entrySet()) {

            months.append(entry.getValue());
            months.append(STRING_UNDERSCORE);
            months.append(entry.getKey());

            if (isFirst) {
                allYearMonths.append(months.toString());// no comma
                isFirst = false;
            } else {
                allYearMonths.append(STRING_COMMA); // comma
                allYearMonths.append(months.toString());
            }

            months.delete(0, months.length());
        }

        return allYearMonths.toString();
    }

    /**
     * get the first date , by giving year , month.
     *
     * @param year - year of user selected year.
     * @param monthIndex - month id , of user selected month.
     * @return - the date.
     */
    public static Date getFistDayOfSelectedYearMonth(int year, int monthIndex) {

        Calendar cal = Calendar.getInstance();
        cal.set(year, monthIndex - 1, 1);

        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        return cal.getTime();
    }

    /**
     * get the last date , by giving year , month.
     *
     * @param year - year of user selected year.
     * @param monthIndex - month id , of user selected month.
     * @return - the date.
     */
    public static Date getLastDayOfSelectedYearMonth(int year, int monthIndex) {

        Calendar cal = Calendar.getInstance();

        int day = 1;
        cal.set(year, monthIndex - 1, day);
        int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        cal.set(year, monthIndex - 1, days);

        cal.clear(Calendar.HOUR);
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        return cal.getTime();
    }

}
