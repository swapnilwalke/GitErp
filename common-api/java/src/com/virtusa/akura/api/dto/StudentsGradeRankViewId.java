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
 * @author Virtusa Corporation
 */
public class StudentsGradeRankViewId implements Serializable {
    
    /** Holds serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** Property to hold studentClassinfoId. */
    private int studentClassInfoId;
    
    /** Property to hold termId. */
    private int termId;
    
    /**
     * Getter method for studentClassInfoId.
     * 
     * @return student class info id
     */
    public int getStudentClassInfoId() {
    
        return studentClassInfoId;
    }
    
    /**
     * Setter method for studentClassInfoId.
     * 
     * @param vStudentClassInfoId student class info id
     */
    public void setStudentClassInfoId(int vStudentClassInfoId) {
    
        this.studentClassInfoId = vStudentClassInfoId;
    }
    
    /**
     * Getter method for termId.
     * 
     * @return term id
     */
    public int getTermId() {
    
        return termId;
    }
    
    /**
     * Setter method for termId.
     * 
     * @param vTermId term id
     */
    public void setTermId(int vTermId) {
    
        this.termId = vTermId;
    }
}
