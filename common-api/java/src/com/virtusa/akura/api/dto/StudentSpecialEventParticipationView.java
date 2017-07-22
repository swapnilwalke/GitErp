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
 * The DTO class to holds the data for Student Marks view.
 * 
 * @author Virtusa Corporation
 */
public class StudentSpecialEventParticipationView extends BaseDomain implements Serializable {
    
    /** Generated serial id.*/
    private static final long serialVersionUID = 1L;

    /** Composite primary key. */
    private StudentSpecialEventParticipationViewId id;
    
    /** Student's full name. */
    private String fullName;
    
    /** Student's name with initials. */
    private String nameWithInitals;
    
	/** Holds only student allocated sport category id. */
    private Integer sportCategoryId;
    
    /** Holds only student allocated class grade id. */
    private Integer classGradeId;
    
    /** Holds only student allocated club society id. */
    private Integer clubSocietyId;
    
    /** Student's id. */
    private int studentId;
    
    /** Holds Year that student allocated to club,class or sport. */
    private Date year;
    
    /**
     * Getter method of the composite primary key.
     *     
     * @return StudentSpecialEventParticipationViewId object
     */
    public StudentSpecialEventParticipationViewId getId() {
    
        return id;
    }

    /**
     * Setter method of the composite primary key.
     * 
     * @param idValue StudentSpecialEventParticipationViewId object
     */
    public void setId(StudentSpecialEventParticipationViewId idValue) {
    
        this.id = idValue;
    }

    /**
     * Getter method for fullName.
     * 
     * @return full name.
     */
    public String getFullName() {
    
        return fullName;
    }
    
    /**
     * Setter method for fullName.
     * 
     * @param vFullName String
     */
    public void setFullName(String vFullName) {
    
        this.fullName = vFullName;
    }
    
    /**
     * Getter method for sportCategoryId.
     * 
     * @return sport category id - integer
     */
    public Integer getSportCategoryId() {
    
        return sportCategoryId;
    }
    
    /**
     * Setter method for sportCategoryId.
     * 
     * @param vSportCategoryId integer
     */
    public void setSportCategoryId(Integer vSportCategoryId) {
    
        this.sportCategoryId = vSportCategoryId;
    }
    
    /**
     * Getter method for classGradeId.
     * 
     * @return class grade id
     */
    public Integer getClassGradeId() {
    
        return classGradeId;
    }
    
    /**
     * Setter method for classGradeId.
     * 
     * @param vClassGradeId integer
     */
    public void setClassGradeId(Integer vClassGradeId) {
    
        this.classGradeId = vClassGradeId;
    }
    
    /**
     * Getter method for clubSocietyId.
     * 
     * @return club society id
     */
    public Integer getClubSocietyId() {
    
        return clubSocietyId;
    }
    
    /**
     * Setter method for clubSocietyId.
     * 
     * @param vClubSocietyId integer
     */
    public void setClubSocietyId(Integer vClubSocietyId) {
    
        this.clubSocietyId = vClubSocietyId;
    }
    
    /**
     * Getter method for student id.
     * 
     * @return student id
     */
    public int getStudentId() {
    
        return studentId;
    }
    
    /**
     * Setter method for student id.
     * 
     * @param vStudentId int
     */
    public void setStudentId(int vStudentId) {
    
        this.studentId = vStudentId;
    }
    
    /**
     * Getter method for year.
     * 
     * @return Date
     */
    public Date getYear() {
    
        if (year != null) {
            return (Date) year.clone();
        }
        return null;
    }
    
    /**
     * Getter method for Name with Initials.
     * 
     * @return Students name with initials.
     */
    public String getNameWithInitals() {
		return nameWithInitals;
	}

    /**
     * Setter method for student's name with initials.
     * 
     * @param nameWithInital - Student's name with initials.
     */
	public void setNameWithInitals(String nameWithInital) {
		this.nameWithInitals = nameWithInital;
	}

    /**
     * Setter method for yesr.
     * 
     * @param vYear Date
     */
    public void setYear(Date vYear) {
    
        if (vYear != null) {            
            this.year = (Date) vYear.clone();
        }
    }
}
