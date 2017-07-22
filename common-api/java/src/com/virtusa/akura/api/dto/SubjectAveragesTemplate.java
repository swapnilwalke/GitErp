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

import java.util.List;

/**
 * This class represents all properties that used to show in class wise student marks sheet report study
 * medium wise average section.
 * 
 * @author Virtusa Corporation
 */
public class SubjectAveragesTemplate {
    
    /** Holds string value of study medium to show in report. */
    private String studyMedium;
    
    /** Holds subject averages in string list to show in report. */
    private List<String> subjectAverageList;
    
    /**
     * Getter method to get string value of study medium.
     * 
     * @return string value of study medium
     */
    public String getStudyMedium() {
    
        return studyMedium;
    }
    
    /**
     * Setter method to set study medium.
     * 
     * @param vStudyMedium string value
     */
    public void setStudyMedium(String vStudyMedium) {
    
        this.studyMedium = vStudyMedium;
    }
    
    /**
     * Getter method to get subject average list.
     * 
     * @return list of string values
     */
    public List<String> getSubjectAverageList() {
    
        return subjectAverageList;
    }
    
    /**
     * Setter method to set subject average list.
     * 
     * @param vSubjectAverageList String list
     */
    public void setSubjectAverageList(List<String> vSubjectAverageList) {
    
        this.subjectAverageList = vSubjectAverageList;
    }
}
