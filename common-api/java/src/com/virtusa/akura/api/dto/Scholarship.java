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
 * This class is to associates all the properties of Scholarship domain object.
 * 
 * @author Virtusa Corporation
 */
public class Scholarship extends BaseDomain {
    
    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "Scholarship scholarshipId=". */
    private static final String SCHOLARSHIP_SCHOLARSHIP_ID = "Scholarship scholarshipId=";
    
    /** The constant for String ", conditions=". */
    private static final String CONDITIONS = ", conditions=";
    
    /** The constant for String ", name=". */
    private static final String NAME = ", name=";
    
    /** The constant for String ", noOfStudents=". */
    private static final String NO_OF_STUDENTS = ", noOfStudents=";
    
    /** The constant for String ", sponsorship=". */
    private static final String SPONSORSHIP = ", sponsorship=";
    /**
     * Represents the unique id for Scholarship.
     */
    private int scholarshipId;
    
    /**
     * Holds no of students for Scholarship.
     */
    private int noOfStudents;
    
    /**
     * Holds name of the Scholarship.
     */
    private String name;
    
    /**
     * Holds conditions for a Scholarship.
     */
    private String conditions;
    
    /**
     * Holds sponsorship for a Scholarship.
     */
    private String sponsorship;

    /**
     * Default constructor.
     */
    public Scholarship() {
    
    }
    
    /**
     * Constructor with parameter scholarshipId.
     * 
     * @param intScholarshipId type int
     */
    public Scholarship(int intScholarshipId) {
    
        this.scholarshipId = intScholarshipId;
    }
    
    /**
     * Get the scholarship id for the scholarship.
     * 
     * @return the scholarshipId
     */
    public int getScholarshipId() {
    
        return scholarshipId;
    }
    
    /**
     * Set the scholarship id for the scholarship.
     * 
     * @param intScholarshipId the scholarshipId to set
     */
    public void setScholarshipId(int intScholarshipId) {
    
        this.scholarshipId = intScholarshipId;
    }
    
    /**
     * Get the number of students for the scholarship.
     * 
     * @return the noOfStudents
     */
    public int getNoOfStudents() {
    
        return noOfStudents;
    }
    
    /**
     * Set the number of students for the scholarship.
     * 
     * @param intNoOfStudents the noOfStudents to set
     */
    public void setNoOfStudents(int intNoOfStudents) {
    
        this.noOfStudents = intNoOfStudents;
    }
    
    /**
     * Get the name of the scholarship.
     * 
     * @return the name
     */
    public String getName() {
    
        return name;
    }
    
    /**
     * Set the name for the scholarship.
     * 
     * @param strName the name to set
     */
    public void setName(String strName) {
    
        this.name = strName;
    }
    
    /**
     * Get the conditions which applies for the scholarship.
     * 
     * @return the conditions
     */
    public String getConditions() {
    
        return conditions;
    }
    
    /**
     * Set the conditions which applies for the scholarship.
     * 
     * @param strConditions the conditions to set
     */
    public void setConditions(String strConditions) {
    
        this.conditions = strConditions;
    }
    
    /**
     * Get the sponsorship which applies for the scholarship.
     * 
     * @return the sponsorship
     */
    public String getSponsorship(){
        return sponsorship;
    }
    
    /**
     * Set the sponsorship which applies for the scholarship.
     * 
     * @param sponsorship the sponsorship to set
     */
    public void setSponsorship(String sponsorship){
        this.sponsorship = sponsorship;
        
    }
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return SCHOLARSHIP_SCHOLARSHIP_ID + scholarshipId + NO_OF_STUDENTS + noOfStudents + NAME + name + CONDITIONS
                + conditions + SPONSORSHIP + sponsorship;
    }
    
}
