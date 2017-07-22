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
 * DTO class to hold inputs specified by the user via a JSP.
 */
public class StudentWiseSwipInOutTemplate {
    
    /**
     * property dateFrom is used to track attendance starting from this day.
     */
    private String dateFrom;
    
    /**
     * property dateFrom is used to track attendance ending from this day.
     */
    private String dateTo;
    
    /**
     * @return the dateFrom
     */
    
    public String getDateFrom() {
    
        return dateFrom;
    }
    
    /**
     * @param fromDate the dateFrom to set
     */
    
    public void setDateFrom(String fromDate) {
    
        this.dateFrom = fromDate;
    }
    
    /**
     * @return the dateTo
     */
    
    public String getDateTo() {
    
        return dateTo;
    }
    
    /**
     * @param toDate the dateTo to set
     */
    
    public void setDateTo(String toDate) {
    
        this.dateTo = toDate;
    }
    
}
