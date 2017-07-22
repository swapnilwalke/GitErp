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
 */
public class EarlyLateLeaversTemplate {
    
    /** String attribute for to TIME_OUT_TO. */
    private static final String TIME_OUT_TO = ", timeOutTo=";
    
    /** String attribute for to TIME_OUT_FROM. */
    private static final String TIME_OUT_FROM = ", timeOutFrom=";
    
    /** String attribute for to DATE_TO. */
    private static final String DATE_TO = ", dateTo=";
    
    /** String attribute for to DATE_FROM. */
    private static final String DATE_FROM = "EarlyLateLeaversTemplate [dateFrom=";
    
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
     * property earlyLeaversTemplate type EarlyLeaversTemplate.
     */
    
    private EarlyLeaversTemplate earlyLeaversTemplate;
    
    /**
     * @return the earlyLeaversTemplate
     */
    public EarlyLeaversTemplate getEarlyLeaversTemplate() {
    
        return earlyLeaversTemplate;
    }
    
    /**
     * @param earlyLeaversTemplateRef the EarlyLeaversTemplate to set
     */
    public void setEarlyLeaversTemplate(EarlyLeaversTemplate earlyLeaversTemplateRef) {
    
        this.earlyLeaversTemplate = earlyLeaversTemplateRef;
    }
    
    /**
     * @return the lateLeaversTemplate
     */
    public LateLeaversTemplate getLateLeaversTemplate() {
    
        return lateLeaversTemplate;
    }
    
    /**
     * @param lateLeaversTemplateRef the LateLeaversTemplate to set
     */
    public void setLateLeaversTemplate(LateLeaversTemplate lateLeaversTemplateRef) {
    
        this.lateLeaversTemplate = lateLeaversTemplateRef;
    }
    
    /**
     * property lateLeaversTemplate type LateLeaversTemplate.
     */
    
    private LateLeaversTemplate lateLeaversTemplate;
    
    /**
     * Returns the details for the EarlyLateLeaversTemplate object.
     * 
     * @return - the EarlyLateLeaversTemplate object details.
     */
    @Override
    public String toString() {
    
        return DATE_FROM + dateFrom + DATE_TO + dateTo + TIME_OUT_FROM + timeOutFrom + TIME_OUT_TO + timeOutTo;
    }
    
}
