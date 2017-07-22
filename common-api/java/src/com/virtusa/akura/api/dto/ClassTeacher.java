/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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
 * This class is to associates all the properties of ClassTeacher domain object.
 * 
 * @author Virtusa Corporation
 */
public class ClassTeacher extends BaseDomain implements Serializable {
    
    /** String attribute for year. */
    private static final String YEAR_VALUE = ", year=";
    
    /** String attribute for class grade. */
    private static final String CLASS_GRADE = ", classGrade=";
    
    /** String attribute for staff. */
    private static final String STAFF_VALUE = ", staff=";
    
    /** String attribute for class teacher id. */
    private static final String CLASS_TEACHER_ID = "ClassTeacher [classTeacherId=";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Holds the classTeacherId of ClassTeacher.
     */
    private int classTeacherId;
    
    /**
     * Holds the staff.
     */
    private Staff staff;
    
    /**
     * Holds the classGrade of the ClassTeacher.
     */
    private ClassGrade classGrade;
    
    /**
     * Holds the year of the ClassTeacher.
     */
    private Date year;
    
    /**
     * Holds the current departure status of the staff member.
     */
    private boolean deleted;
    
    
    /**
     * @return the deleted.
     */
    public boolean getDeleted() {

        return deleted;
    }
    
    /**
     * @param deletedFlag is to set the deleted flag.
     */
    public void setDeleted(boolean deletedFlag) {

        this.deleted = deletedFlag;
    }
    
    /**
     * @return the classTeacherId
     */
    public int getClassTeacherId() {

        return classTeacherId;
    }
    
    /**
     * @param intClassTeacherId the classTeacherCompositeId to set
     */
    public void setClassTeacherId(int intClassTeacherId) {

        this.classTeacherId = intClassTeacherId;
    }
    
    /**
     * @return the staff
     */
    public Staff getStaff() {

        return staff;
    }
    
    /**
     * @param objStaff the staff to set
     */
    public void setStaff(Staff objStaff) {

        this.staff = objStaff;
    }
    
    /**
     * @return the classGrade
     */
    public ClassGrade getClassGrade() {

        return classGrade;
    }
    
    /**
     * @param objClassGrade the classGrade to set
     */
    public void setClassGrade(ClassGrade objClassGrade) {

        this.classGrade = objClassGrade;
    }
    
    /**
     * @return the year
     */
    public Date getYear() {

        if (year != null) {
            return (Date) year.clone();
        }
        return null;
    }
    
    /**
     * @param dateYear the year to set
     */
    public void setYear(Date dateYear) {

        if (dateYear != null) {
            this.year = (Date) dateYear.clone();
        }
    }
    
    /**
     * Returns the details for the ClassTeacher object.
     * 
     * @return - the ClassTeacher object details.
     */
    @Override
    public String toString() {

        return CLASS_TEACHER_ID + classTeacherId + YEAR_VALUE + year;
    }
    
   
    
}
