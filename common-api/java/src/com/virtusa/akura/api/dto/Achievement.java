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

import java.util.Date;

/**
 * This class is to associates all the properties of Achievement domain object.
 * 
 * @author Virtusa Corporation
 */
public class Achievement extends BaseDomain {
    
    /** String attribute for academic. */
    private static final String ACADEMIC = ", academic=";
    
    /** String attribute for year. */
    private static final String YEAR_VALUE = ", year=";
    
    /** String attribute for description. */
    private static final String DESCRIPTION_VALUE = ", description=";
    
    /** String attribute for student. */
    private static final String STUDENT_VALUE = ", student=";
    
    /** String attribute for achievement id. */
    private static final String ACHIEVEMENT_ID = "Achievement-->achievementId=";
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * property achievementId type integer.
     */
    private int achievementId;
    
    /**
     * student {@link Student}.
     */
    private Student student;
    
    /**
     * property description type String.
     */
    private String description;
    
    /**
     * property year {@link Date}.
     */
    private Date year;
    
    /**
     * property activity type String.
     */
    private String activity;
    
    /**
     * property clubSociety type ClubSociety.
     */
    private ClubSociety clubSociety;
    
    /**
     * property isAcademic type boolean.
     */
    private boolean academic;
    
    /**
     * Default constructor.
     */
    public Achievement() {
    
    }
    
    /**
     * Constructor with parameter achievementId.
     * 
     * @param intAchievementId type int
     */
    public Achievement(int intAchievementId) {
    
        this.achievementId = intAchievementId;
    }
    
    /**
     * @return the achievementId
     */
    public int getAchievementId() {
    
        return achievementId;
    }
    
    /**
     * @param intAchievementId the achievementId to set
     */
    public void setAchievementId(int intAchievementId) {
    
        this.achievementId = intAchievementId;
    }
    
    /**
     * @return the description
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {
    
        this.description = strDescription;
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
     * @return the activity
     */
    public String getActivity() {
    
        return activity;
    }
    
    /**
     * @param activityobj the activity to set
     */
    public void setActivity(String activityobj) {
    
        this.activity = activityobj;
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
     * @return the isAcademic
     */
    public boolean isAcademic() {
    
        return academic;
    }
    
    /**
     * @param isAcademicFlag the isAcademic to set
     */
    public void setAcademic(boolean isAcademicFlag) {
    
        this.academic = isAcademicFlag;
    }
    
    /**
     * Returns the details for the Achievement object.
     * 
     * @return - the Achievement object details.
     */
    @Override
    public String toString() {
    
        return ACHIEVEMENT_ID + achievementId + STUDENT_VALUE + student.getStudentId() + DESCRIPTION_VALUE
                + description + YEAR_VALUE + year + ACADEMIC + academic;
    }
    
}
