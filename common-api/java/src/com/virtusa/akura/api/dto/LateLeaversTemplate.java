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

package com.virtusa.akura.api.dto;

/**
 * DTO class to collect inputs for Student early leavers & late leavers.
 * 
 * @author Virtusa Corporation
 */
public class LateLeaversTemplate {
    
    /** attribute for holding string. */
    private static final String TIME_OUT_TO = ", timeOutTo=";
    
    /** attribute for holding string. */
    private static final String TIME_OUT_FROM = ", timeOutFrom=";
    
    /** attribute for holding string. */
    private static final String DATE_TO = ", dateTo=";
    
    /** attribute for holding string. */
    private static final String DATE_FROM = "dateFrom=";
    
    /**
     * property dateFrom type java.lang.String. used to track start date of a date range.
     */
    private String dateFrom;
    
    /**
     * property dateTo type java.lang.String. used to track end date of a date range.
     */
    private String dateTo;
    
    /**
     * property timeOutFrom type java.lang.String. used to track start time of a time slot.
     */
    private String timeOutFrom;
    
    /**
     * property timeOutTo type java.lang.String. used to track end time of a time slot.
     */
    private String timeOutTo;
    
    /**
     * @return the dateFrom
     */
    public String getDateFrom() {
    
        return dateFrom;
    }
    
    /**
     * @param dateFromRef the dateFrom to set
     */
    public void setDateFrom(String dateFromRef) {
    
        this.dateFrom = dateFromRef;
    }
    
    /**
     * @return the dateTo
     */
    public String getDateTo() {
    
        return dateTo;
    }
    
    /**
     * @param dateToRef the dateToRef to set
     */
    
    public void setDateTo(String dateToRef) {
    
        this.dateTo = dateToRef;
    }
    
    /**
     * @return the timeOutFrom
     */
    public String getTimeOutFrom() {
    
        return timeOutFrom;
    }
    
    /**
     * @param timeOutFromRef the timeOutFrom to set
     */
    public void setTimeOutFrom(String timeOutFromRef) {
    
        this.timeOutFrom = timeOutFromRef;
    }
    
    /**
     * @return the timeOutTo
     */
    public String getTimeOutTo() {
    
        return timeOutTo;
    }
    
    /**
     * @param timeOutToRef the timeInTo to set
     */
    public void setTimeOutTo(String timeOutToRef) {
    
        this.timeOutTo = timeOutToRef;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return DATE_FROM + dateFrom + DATE_TO + dateTo + TIME_OUT_FROM + timeOutFrom + TIME_OUT_TO + timeOutTo;
    }
    
}
