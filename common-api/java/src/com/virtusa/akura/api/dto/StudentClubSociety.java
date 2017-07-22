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
 * @author Virtusa Corporation
 */
public class StudentClubSociety extends BaseDomain implements Serializable {
    
    /**
     * serialVersionUID- final.
     */
    private static final long serialVersionUID = 1L;
    
    /** key to hold string year. */
    private static final String YEAR = ", year=";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = ", studentId=";
    
    /** key to hold string positionId. */
    private static final String POSITION_ID = ", positionId=";
    
    /** key to hold string clubSocietyId. */
    private static final String CLUB_SOCIETY_ID = ", clubSocietyId=";
    
    /** key to hold string studentClubSocietyId. */
    private static final String STUDENT_CLUB_SOCIETY_ID = "studentClubSocietyId=";
    
    /**
     * Holds studentClubSocietyId.
     */
    private int studentClubSocietyId;
    
    /**
     * Holds clubSociety.
     */
    private ClubSociety clubSociety;
    
    /**
     * Holds position.
     */
    private Position position;
    
    /**
     * Holds student.
     */
    private Student student;
    
    /**
     * Holds membershipNo.
     */
    private String membershipNo;
    
    /**
     * Holds status.
     */
    private Byte status;
    
    /**
     * Holds year.
     */
    private Date year;
    
    /**
     * default constructor.
     */
    public StudentClubSociety() {
    
    }
    
    /**
     * constructor with a int parameter.
     * 
     * @param intStudentClubSocietyId the studentClubSocietyId to pass.
     */
    public StudentClubSociety(int intStudentClubSocietyId) {
    
        studentClubSocietyId = intStudentClubSocietyId;
    }
    
    /**
     * @return the studentClubSocietyId
     */
    public int getStudentClubSocietyId() {
    
        return studentClubSocietyId;
    }
    
    /**
     * @param intStudentClubSocietyId the studentClubSocietyId to set
     */
    public void setStudentClubSocietyId(int intStudentClubSocietyId) {
    
        this.studentClubSocietyId = intStudentClubSocietyId;
    }
    
    /**
     * @return the clubSociety
     */
    public ClubSociety getClubSociety() {
    
        return clubSociety;
    }
    
    /**
     * @param objClubSociety the clubSociety to set
     */
    public void setClubSociety(ClubSociety objClubSociety) {
    
        this.clubSociety = objClubSociety;
    }
    
    /**
     * @return the position
     */
    public Position getPosition() {
    
        return position;
    }
    
    /**
     * @param objPosition the position to set
     */
    public void setPosition(Position objPosition) {
    
        this.position = objPosition;
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
     * @return the membershipNo
     */
    public String getMembershipNo() {
    
        return membershipNo;
    }
    
    /**
     * @param strMembershipNo the membershipNo to set
     */
    public void setMembershipNo(String strMembershipNo) {
    
        this.membershipNo = strMembershipNo;
    }
    
    /**
     * @return the status
     */
    public Byte getStatus() {
    
        return status;
    }
    
    /**
     * @param bytStatus the status to set
     */
    public void setStatus(Byte bytStatus) {
    
        this.status = bytStatus;
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
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return STUDENT_CLUB_SOCIETY_ID + this.studentClubSocietyId + CLUB_SOCIETY_ID
                + this.clubSociety.getClubSocietyId() + POSITION_ID + this.position.getPositionId() + STUDENT_ID
                + this.student.getStudentId() + YEAR + this.year;
    }

    /**
     * Override object class hashCode method.
     * 
     * @return int hashcode.
     */
    public int hashCode() {

        final int prime = 31;
        int result = 1;
        result = prime * result + studentClubSocietyId;
        return result;
    }

    /**
     * Override object class equals method.
     * 
     * @param obj object to compare.
     * @return true if both objects are equal.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentClubSociety other = (StudentClubSociety) obj;
        if (studentClubSocietyId != other.studentClubSocietyId)
            return false;
        return true;
    }
    
    
}
