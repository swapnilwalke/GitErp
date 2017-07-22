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
import java.util.Date;

/**
 * This class represents all the properties of StudentPrefect.
 * 
 * @author Virtusa Corporation
 */
public class StudentPrefect extends BaseDomain implements Serializable {
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string yearDate. */
    private static final String YEAR_DATE = ", yearDate=";
    
    /** key to hold string prefectTypeId. */
    private static final String PREFECT_TYPE_ID = ", prefectTypeId=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = ", studentId=";
    
    /** key to hold string studentPrefectId. */
    private static final String STUDENT_PREFECT_ID = "studentPrefectId=";
    
    /**
     * Holds studentPrefectId.
     */
    private int studentPrefectId;
    
    /**
     * Holds student.
     */
    private Student student;
    
    /**
     * Holds prefectType.
     */
    private PrefectType prefectType;
    
    /**
     * Holds year.
     */
    private Date yearDate;
    
    /**
     * Default constructor.
     */
    public StudentPrefect() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStudentPrefectId {@link int}
     * @param objStudent {@link Student}
     * @param objPrefectType {@link PrefectType}
     * @param strYear type string
     */
    public StudentPrefect(int intStudentPrefectId, Student objStudent, PrefectType objPrefectType, Date strYear) {
    
        this.studentPrefectId = intStudentPrefectId;
        this.student = objStudent;
        this.prefectType = objPrefectType;
        this.yearDate = (Date) strYear.clone();
    }
    
    /**
     * @return the studentPrefectId
     */
    public int getStudentPrefectId() {
    
        return studentPrefectId;
    }
    
    /**
     * @param intStudentPrefectId the studentPrefectId to set
     */
    public void setStudentPrefectId(int intStudentPrefectId) {
    
        this.studentPrefectId = intStudentPrefectId;
    }
    
    /**
     * @return the student
     */
    public Student getStudent() {
    
        return student;
    }
    
    /**
     * @param objStudent the student to set
     */
    public void setStudent(Student objStudent) {
    
        this.student = objStudent;
    }
    
    /**
     * @return the prefectType
     */
    public PrefectType getPrefectType() {
    
        return prefectType;
    }
    
    /**
     * @param objPrefectType the prefectType to set
     */
    public void setPrefectType(PrefectType objPrefectType) {
    
        this.prefectType = objPrefectType;
    }
    
    /**
     * @return the year
     */
    public Date getYear() {
    
        if (yearDate != null) {
            
            return (Date) yearDate.clone();
        }
        return null;
    }
    
    /**
     * @param strYear the year to set
     */
    public void setYear(Date strYear) {
    
        if (strYear != null) {
            this.yearDate = (Date) strYear.clone();
        }
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STUDENT_PREFECT_ID + studentPrefectId + STUDENT_ID + student.getStudentId() + PREFECT_TYPE_ID
                + prefectType.getPrefectTypeId() + YEAR_DATE + yearDate;
    }
}
