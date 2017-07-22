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
 * Domain object to map data for ReportStudentClubSociety.
 * 
 * @author Virtusa Corporation
 */

public class ReportStudentClubSociety implements Serializable {
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = -3198043563595540303L;
    
    /** The constant for String ", status=". */
    private static final String STATUS = ", status=";
    
    /** The constant for String ", membershipNo=". */
    private static final String MEMBERSHIP_NO = ", membershipNo=";
    
    /** The constant for String ", position=". */
    private static final String POSITION = ", position=";
    
    /** The constant for String ", year=". */
    private static final String YEAR = ", year=";
    
    /** The constant for String ", clubSocietyName=". */
    private static final String CLUB_SOCIETY_NAME = ", clubSocietyName=";
    
    /** The constant for String ", fullName=". */
    private static final String FULL_NAME = ", fullName=";
    
    /** The constant for String ", admissionNo=". */
    private static final String ADMISSION_NO = ", admissionNo=";
    
    /** The constant for String ", studentId=". */
    private static final String STUDENT_ID = ", studentId=";
    
    /** The constant for String "ReportStudentClubSociety studentclubSocietyId=". */
    private static final String REPORT_STUDENT_CLUB_SOCIETY_STUDENTCLUB_SOCIETY_ID =
            "ReportStudentClubSociety studentclubSocietyId=";
    
    /**
     * Holds the studentclubSocietyId for the Student.
     */
    private int studentclubSocietyId;
    
    /**
     * Holds the student id of the Student.
     */
    private int studentId;
    
    /**
     * Holds the admission number of the Student.
     */
    private String admissionNo;
    
    /**
     * Holds the fullName of the Student.
     */
    private String fullName;
    
    /**
     * Holds the name of the clubSociety.
     */
    private String clubSocietyName;
    
    /**
     * Holds year.
     */
    private Date year;
    
    /**
     * Holds position.
     */
    private String position;
    
    /**
     * Holds membershipNo.
     */
    private String membershipNo;
    
    /**
     * Holds status.
     */
    private Byte status;
    
    /**
     * @return studentclubSocietyId Returns the studentclubSocietyId
     */
    public int getStudentclubSocietyId() {
    
        return studentclubSocietyId;
    }
    
    /**
     * @param stuclubSociId Sets the value for the studentclubSocietyId.
     */
    public void setStudentclubSocietyId(int stuclubSociId) {
    
        this.studentclubSocietyId = stuclubSociId;
    }
    
    /**
     * @return studentId Returns the id of the student.
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * @param stuId Sets the value for the studentId.
     */
    public void setStudentId(int stuId) {
    
        this.studentId = stuId;
    }
    
    /**
     * @return admissionNo Returns the admission number.
     */
    public String getAdmissionNo() {
    
        return admissionNo;
    }
    
    /**
     * @param admiNo Sets the value for the admission number.
     */
    public void setAdmissionNo(String admiNo) {
    
        this.admissionNo = admiNo;
    }
    
    /**
     * @return fullName Returns the full name of student.
     */
    public String getFullName() {
    
        return fullName;
    }
    
    /**
     * @param fName Sets the value for the full name.
     */
    public void setFullName(String fName) {
    
        this.fullName = fName;
    }
    
    /**
     * @return clubSocietyName Returns the name of the club or society.
     */
    public String getClubSocietyName() {
    
        return clubSocietyName;
    }
    
    /**
     * @param clubSociName Sets the value for the the name of the club or society.
     */
    public void setClubSocietyName(String clubSociName) {
    
        this.clubSocietyName = clubSociName;
    }
    
    /**
     * @return year Returns the year.
     */
    public Date getYear() {
    
        if (year != null) {
            
            return (Date) year.clone();
        }
        return null;
    }
    
    /**
     * @param activeYear Sets the value for the year.
     */
    public void setYear(Date activeYear) {
    
        if (activeYear != null) {
            this.year = (Date) activeYear.clone();
        }
    }
    
    /**
     * @return position Returns the position.
     */
    public String getPosition() {
    
        return position;
    }
    
    /**
     * @param posi Sets the value for the position.
     */
    public void setPosition(String posi) {
    
        this.position = posi;
    }
    
    /**
     * @return clubSocietyName Returns the name of the club or society.
     */
    public String getMembershipNo() {
    
        return membershipNo;
    }
    
    /**
     * @param memNo Sets the value for the membership number.
     */
    public void setMembershipNo(String memNo) {
    
        this.membershipNo = memNo;
    }
    
    /**
     * @return status Returns the status.
     */
    public Byte getStatus() {
    
        return status;
    }
    
    /**
     * @param stat Sets the value for the status.
     */
    public void setStatus(Byte stat) {
    
        this.status = stat;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return REPORT_STUDENT_CLUB_SOCIETY_STUDENTCLUB_SOCIETY_ID + studentclubSocietyId + STUDENT_ID + studentId
                + ADMISSION_NO + admissionNo + FULL_NAME + fullName + CLUB_SOCIETY_NAME + clubSocietyName + YEAR + year
                + POSITION + position + MEMBERSHIP_NO + membershipNo + STATUS + status;
    }
    
}
