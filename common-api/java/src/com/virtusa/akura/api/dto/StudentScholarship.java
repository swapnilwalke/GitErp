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
 * @author Virtusa Corporation
 */
public class StudentScholarship extends BaseDomain implements Serializable {
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string comments. */
    private static final String COMMENTS = ", comments=";
    
    /** key to hold string year. */
    private static final String YEAR = ", year=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = ", studentId=";
    
    /** key to hold string scholarshipId. */
    private static final String SCHOLARSHIP_ID = ", scholarshipId=";
    
    /** key to hold string studentScholarshipId. */
    private static final String STUDENT_SCHOLARSHIP_ID = "studentScholarshipId=";
    
    /**
     * Holds studentScholarshipId.
     */
    private int studentScholarshipId;
    
    /**
     * Holds scholarship.
     */
    private Scholarship scholarship;
    
    /**
     * Holds student.
     */
    private Student student;
    
    /**
     * Holds year.
     */
    private Date year;
    
    /**
     * Holds comments.
     */
    private String comments;
    
    /**
     * Default constructor.
     */
    public StudentScholarship() {
    
    }
    
    /**
     * Overloaded constructor.
     * 
     * @param intStudentScholarshipId {@link int}
     * @param objScholarship {@link Scholarship}
     * @param objStudent {@link Student}
     * @param dtYear type year
     */
    public StudentScholarship(int intStudentScholarshipId, Scholarship objScholarship, Student objStudent, Date dtYear) {
    
        this.studentScholarshipId = intStudentScholarshipId;
        this.scholarship = objScholarship;
        this.student = objStudent;
        
        if (dtYear != null) {
            this.year = (Date) dtYear.clone();
        }
    }
    
    /**
     * @return the studentScholarshipId
     */
    public int getStudentScholarshipId() {
    
        return studentScholarshipId;
    }
    
    /**
     * @param intStudentScholarshipId the studentScholarshipId to set
     */
    public void setStudentScholarshipId(int intStudentScholarshipId) {
    
        this.studentScholarshipId = intStudentScholarshipId;
    }
    
    /**
     * @return the scholarship
     */
    public Scholarship getScholarship() {
    
        return scholarship;
    }
    
    /**
     * @param objScholarship the scholarship to set
     */
    public void setScholarship(Scholarship objScholarship) {
    
        this.scholarship = objScholarship;
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
     * @return the year
     */
    public Date getYear() {
    
        if (year != null) {
            return (Date) year.clone();
        }
        return null;
    }
    
    /**
     * @param dtYear the year to set
     */
    public void setYear(Date dtYear) {
    
        if (dtYear != null) {
            this.year = (Date) dtYear.clone();
        }
    }
    
    /**
     * @return the comments
     */
    public String getComments() {
    
        return comments;
    }
    
    /**
     * @param strComments the comments to set
     */
    public void setComments(String strComments) {
    
        this.comments = strComments;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STUDENT_SCHOLARSHIP_ID + studentScholarshipId + SCHOLARSHIP_ID + scholarship.getScholarshipId()
                + STUDENT_ID + student.getStudentId() + YEAR + year.toString() + COMMENTS + comments;
    }
}
