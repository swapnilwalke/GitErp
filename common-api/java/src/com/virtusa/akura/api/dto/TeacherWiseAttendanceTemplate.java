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
public class TeacherWiseAttendanceTemplate {

    /**
     * used to track category type of staff.
     */
    private int catogaryID;

    /**
     * property teacherNo type java.lang.String. used to track a particular teacher.
     */
    private String teacherNo;

    /**
     * used to track attendance starting from this day.
     */
    private String dateFrom;

    /**
     * @return the catogaryID
     */
    public int getCatogaryID() {

        return catogaryID;
    }

    /**
     * @param catogaryIDRef the catogaryID to set
     */
    public void setCatogaryID(int catogaryIDRef) {

        this.catogaryID = catogaryIDRef;
    }

    /**
     * used to track attendance ending from this day.
     */
    private String dateTo;

    /**
     * The Default constructor.
     */
    public TeacherWiseAttendanceTemplate() {

    }

    /**
     * @return the teacherNo
     */
    public String getTeacherNo() {

        return teacherNo;
    }

    /**
     * @param teacherNoRef the teacherNo to set
     */
    public void setTeacherNo(String teacherNoRef) {

        this.teacherNo = teacherNoRef;
    }

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
     * @param dateToRef the dateTo to set
     */
    public void setDateTo(String dateToRef) {

        this.dateTo = dateToRef;
    }

}
