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

import java.io.Serializable;

/**
 * @author Virtusa Corporation
 */
public class AttendanceDashboardDto extends BaseDomain implements Serializable {

    /**
	 *
	 */
    private static final long serialVersionUID = 5139923345637727010L;

    /** attribute of the min number. */
    private int minNo;

    /** attribute of the grade id. */
    private Integer gradeId;

    /** attribute of the year. */
    private int year;

    /** attribute of the month. */
    private int month;

    /** attribute of the class grade id. */
    private int classGradeId;

    /** attribute of the academic days. */
    private int academicDays;

    /** attribute of the selected class. */
    private int selectedClassId;

    /** attribute of the flag. */
    private boolean flag;

    /** attribute of the class description. */
    private String classDescription;

    /**
     * @return the class description.
     */
    public String getClassDescription() {

        return classDescription;
    }

    /**
     * @param classDescriptionRef to set
     */
    public void setClassDescription(String classDescriptionRef) {

        this.classDescription = classDescriptionRef;
    }

    /**
     * @return the gradeId
     */
    public Integer getGradeId() {

        return gradeId;
    }

    /**
     * @param gradeIdValue the gradeId to set
     */
    public void setGradeId(Integer gradeIdValue) {

        this.gradeId = gradeIdValue;
    }

    /**
     * @return the academicDays
     */
    public int getAcademicDays() {

        return academicDays;
    }

    /**
     * @param academicDaysVal the academicDays to set
     */
    public void setAcademicDays(int academicDaysVal) {

        this.academicDays = academicDaysVal;
    }

    /**
     * @return the month
     */
    public int getMonth() {

        return month;
    }

    /**
     * @param monthVal the month to set
     */
    public void setMonth(int monthVal) {

        this.month = monthVal;
    }

    /**
     * @return the classGradeId
     */
    public int getClassGradeId() {

        return classGradeId;
    }

    /**
     * @param classGradeIdVal the classGradeId to set
     */
    public void setClassGradeId(int classGradeIdVal) {

        this.classGradeId = classGradeIdVal;
    }

    /**
     * @return the year
     */
    public int getYear() {

        return year;
    }

    /**
     * @param yearVal the year to set
     */
    public void setYear(int yearVal) {

        this.year = yearVal;
    }

    /**
     * @return the selectedClass
     */
    public int getSelectedClassId() {

        return selectedClassId;
    }

    /**
     * @param selectedClassIdVal the selectedClass to set
     */
    public void setSelectedClassId(int selectedClassIdVal) {

        this.selectedClassId = selectedClassIdVal;
    }

    /**
     * @return the minimum number.
     */
    public int getMinNo() {

        return minNo;
    }

    /**
     * @param minNoRef the minimum number to set
     */
    public void setMinNo(int minNoRef) {

        this.minNo = minNoRef;
    }

    /**
     * @return the flag.
     */
    public boolean isFlag() {

        return flag;
    }

    /**
     * @param flagRef to set
     */
    public void setFlag(boolean flagRef) {

        this.flag = flagRef;
    }

}
