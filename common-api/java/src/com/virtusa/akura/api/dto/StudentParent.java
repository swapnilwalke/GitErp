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

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * This class represents all properties of StudentParent domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentParent extends BaseDomain implements Serializable {
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = -7601191048715337609L;
    
    /** key to hold string modified date/time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** key to hold string parentId. */
    private static final String PARENT_ID = " parentId = ";
    
    /** key to hold string studentId. */
    private static final String STUDENT_ID = " studentId = ";
    
    /** key to hold string student parent id. */
    private static final String STUDENT_PARENT_ID = "student parent id = ";
    
    /** Represents the id for a student parent. */
    private int studentParentId;
    
    /** Represents relationship for a parent donation. */
    private char relationship;
    
    /** Represents the student for a parent donation. */
    @JsonIgnore
    private Student student;
    
    /** Represents the parent for a parent donation. */
    private Parent parent;
    
    /**
     * holds the current status of the student, whether he departures or not.
     */
    private boolean deleted;
    
    /**
     * Constructs a new StudentParetn object.
     */
    public StudentParent() {
    
    }
    
    /**
     * Constructs a new studentParent object with the specified studentParent id.
     * 
     * @param id - the studentParentId.
     */
    public StudentParent(int id) {
    
        this.studentParentId = id;
    }
    
    /**
     * Returns the id for this StudentParent object.
     * 
     * @return the id of the StudentParent object
     */
    public int getStudentParentId() {
    
        return studentParentId;
    }
    
    /**
     * Sets the id for this StudentParent object with the given key.
     * 
     * @param intStudentParentId - id of the parentDonation.
     */
    public void setStudentParentId(int intStudentParentId) {
    
        this.studentParentId = intStudentParentId;
    }
    
    /**
     * Returns the Student for this StudentParent object.
     * 
     * @return - the Student of the StudentParent.
     */
    @JsonIgnore
    public Student getStudent() {
    
        return student;
    }
    
    /**
     * Sets the Student for this StudentParent object.
     * 
     * @param objStudent - the Student of the StudentParent.
     */
    @JsonIgnore
    public void setStudent(Student objStudent) {
    
        this.student = objStudent;
    }
    
    /**
     * Returns the Parent for this StudentParent object.
     * 
     * @return - the Parent of the StudentParent.
     */
    public Parent getParent() {
    
        return parent;
    }
    
    /**
     * Sets the Parent for this StudentParent object.
     * 
     * @param objParent - the Parent of the StudentParent.
     */
    public void setParent(Parent objParent) {
    
        this.parent = objParent;
    }
    
    /**
     * Returns the relationship for this StudentParent object.
     * 
     * @return - the relationship of the StudentParent.
     */
    public char getRelationship() {
    
        return relationship;
    }
    
    /**
     * Sets the relationship for this StudentParent object.
     * 
     * @param chaRelationship - the relationship of the StudentParent.
     */
    public void setRelationship(char chaRelationship) {
    
        this.relationship = chaRelationship;
    }
    
    /**
     * return the deleted status of the student.
     * 
     * @return the deleted status .
     */
    public boolean getDeleted() {
    
        return deleted;
    }
    
    /**
     * set the the deleted status of the student.
     * 
     * @param deletedVal the deleted status to set
     */
    public void setDeleted(boolean deletedVal) {
    
        this.deleted = deletedVal;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return STUDENT_PARENT_ID + this.studentParentId + STUDENT_ID + this.student.getStudentId() + PARENT_ID
                + this.parent.getParentId() + MODIFIED_DATE_TIME + this.getModifiedTime();
    }
}
