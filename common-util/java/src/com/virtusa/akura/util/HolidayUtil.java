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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dto.Holiday;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Provides static methods for the functionalities of holiday.
 *
 * @author Virtusa Corporation
 */
public final class HolidayUtil {

    /**
     * The default constructor.
     */
    private HolidayUtil() {

    }

    /** String attribute for holding TO_DAYS. */
    private static final int TO_DAYS = 24;

    /** String attribute for holding TO_HOURS. */
    private static final int TO_HOURS = 60;

    /** String attribute for holding TO_MINIUTES. */
    private static final int TO_MINIUTES = 60;

    /** String attribute for holding TO_SECONDS. */
    private static final int TO_SECONDS = 1000;

    /**
     * Check the given date is a holiday or not.
     *
     * @param holidayList - list consits of Holidays for the given time period.
     * @param currentDate - currentDate
     * @param start - Calender start date
     * @return boolean
     */
    public static boolean isHoliday(List<Holiday> holidayList, Date currentDate, Calendar start) {

        boolean flag = false;
        int dayOfWeek = start.get(Calendar.DAY_OF_WEEK);

        if (holidayList.isEmpty()) {
            if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                flag = true;
            }
        } else {

            for (Holiday tempHoliday : holidayList) {
                if ((currentDate.after(tempHoliday.getStartDate()) && currentDate.before(tempHoliday.getEndDate()))
                        || currentDate.equals(tempHoliday.getStartDate())
                        || currentDate.equals(tempHoliday.getEndDate()) || dayOfWeek == Calendar.SATURDAY
                        || dayOfWeek == Calendar.SUNDAY) {

                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * Returns the number of leave days.
     *
     * @param startDate - started date.
     * @param endDate - end date.
     * @return - the number of days.
     */
    public static int daysBetween(Date startDate, Date endDate) {

        return (int) (((endDate.getTime() - startDate.getTime()) /
                (TO_SECONDS * TO_MINIUTES * TO_HOURS * TO_DAYS)) + 1);
    }

    /**
     * Count the number of holidays in given time period.
     *
     * @param fromDate - start date
     * @param toDate - end date
     * @param holidayList - list of holiday for the year.
     * @return holidayCount
     */
    public static int countHolidays(Calendar fromDate, Calendar toDate, List<Holiday> holidayList) {

        int holidayCount = 0;

        while (!fromDate.after(toDate)) {

            if (isHoliday(holidayList, fromDate.getTime(), fromDate)) {
                holidayCount++;
            }
            fromDate.add(Calendar.DATE, 1);

        }
        return holidayCount;
    }

    /**
     * Count the working days in given time period.
     *
     * @param fromDate -start date.
     * @param endDate - end date.
     * @param holidayList - list of holiday for the year.
     * @return workingDays.
     * @throws AkuraAppException - Detailed exception
     */
    public static int countWorkingDays(Calendar fromDate, Calendar endDate, List<Holiday> holidayList)
            throws AkuraAppException {

        int workingDays = 0;

        String fDate = DateUtil.getFormatDate(fromDate.getTime());

        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getParseDate(fDate));

        if (fromDate.before(endDate)) {
            // Get the day count and reduce holidays.
            workingDays =
                    (daysBetween(fromDate.getTime(), endDate.getTime()) - (countHolidays(cal, endDate, holidayList)));
        }

        return workingDays;
    }
}
