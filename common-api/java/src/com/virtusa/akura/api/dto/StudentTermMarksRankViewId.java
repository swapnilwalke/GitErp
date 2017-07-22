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
 * This class represents all the properties of StudentDiscipline.
 * 
 * @author Virtusa Corporation
 */
public class StudentTermMarksRankViewId implements Serializable {
    
    /** Holds the default serial id. */
    private static final long serialVersionUID = 1L;
    
    /** Holds the studentClassInfoID. */
    private int studentClassInfoID;
    
    /** Holds the termID. */
    private int termID;
    
    /**
     * Getter method for Student ClassInfo ID.
     * 
     * @return student class info id.
     */
    public int getStudentClassInfoID() {
    
        return studentClassInfoID;
    }
    
    /**
     * Setter method for Student ClassInfo ID.
     * 
     * @param vStudentClassInfoID - student class info id
     */
    public void setStudentClassInfoID(int vStudentClassInfoID) {
    
        this.studentClassInfoID = vStudentClassInfoID;
    }
    
    /**
     * Getter method for term id.
     * 
     * @return term id.
     */
    public int getTermID() {
    
        return termID;
    }
    
    /**
     * Setter method for term id.
     * 
     * @param vTermID term id.
     */
    public void setTermID(int vTermID) {
    
        this.termID = vTermID;
    }
}
