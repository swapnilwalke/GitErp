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
 * AttendeesWrapperTemplate class.
 * 
 * @author Virtusa Corporation
 */
public class AttendeesWrapperTemplate {
    
    /**
     * used to hold TeacherLateAttendiesTemplate object.
     */
    private TeacherLateAttendiesTemplate teacherLateAttendiesTemplate;
    
    /**
     * used to hold TeacherEarlyComersTemplate object.
     */
    private TeacherEarlyComersTemplate teacherEarlyComersTemplate;
    
    /**
     * property dateFrom used to track attendance starting from this day.
     */
    private String dateFrom;
    
    /**
     * property dateFrom used to track attendance ending from this day.
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
    
    /**
     * The default constructor.
     */
    public AttendeesWrapperTemplate() {
    
    }
    
    /**
     * used to get TeacherLateAttendiesTemplate object.
     * 
     * @return teacherLateAttendiesTemplate
     */
    public TeacherLateAttendiesTemplate getTeacherLateAttendiesTemplate() {
    
        return teacherLateAttendiesTemplate;
    }
    
    /**
     * used to set TeacherLateAttendiesTemplate object.
     * 
     * @param objTeacherLateAttendiesTemplate - TeacherLateAttendiesTemplate
     */
    public void setTeacherLateAttendiesTemplate(TeacherLateAttendiesTemplate objTeacherLateAttendiesTemplate) {
    
        this.teacherLateAttendiesTemplate = objTeacherLateAttendiesTemplate;
    }
    
    /**
     * used get TeacherEarlyComersTemplate object.
     * 
     * @return teacherEarlyComersTemplate
     */
    public TeacherEarlyComersTemplate getTeacherEarlyComersTemplate() {
    
        return teacherEarlyComersTemplate;
    }
    
    /**
     * used to set TeacherEarlyComersTemplate object.
     * 
     * @param objTeacherEarlyComersTemplate - TeacherEarlyComersTemplate
     */
    public void setTeacherEarlyComersTemplate(TeacherEarlyComersTemplate objTeacherEarlyComersTemplate) {
    
        this.teacherEarlyComersTemplate = objTeacherEarlyComersTemplate;
    }
    
}
