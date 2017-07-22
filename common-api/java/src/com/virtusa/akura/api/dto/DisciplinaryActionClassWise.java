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
 * Domain object to map data for DisciplinaryActionClassWise.
 * 
 * @author Virtusa Corporation
 */
public class DisciplinaryActionClassWise {
    
    /**
     * descriptive text for gradeDescription.
     */
    private String gradeDescription;
    
    /**
     * descriptive text for classDescription.
     */
    private String classDescription;
    
    /**
     * gets the gradeDescription.
     * 
     * @return the gradeDescription
     */
    public String getGradeDescription() {
    
        return gradeDescription;
    }
    
    /**
     * sets the gradeDescription.
     * 
     * @param strGradeDescription the gradeDescription to set
     */
    public void setGradeDescription(String strGradeDescription) {
    
        this.gradeDescription = strGradeDescription;
    }
    
    /**
     * gets the classDescription.
     * 
     * @return the classDescription
     */
    public String getClassDescription() {
    
        return classDescription;
    }
    
    /**
     * sets the classDescription.
     * 
     * @param strClassDescription the classDescription to set
     */
    public void setClassDescription(String strClassDescription) {
    
        this.classDescription = strClassDescription;
    }
    
}
