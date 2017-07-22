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
 * This class represents all properties of a StudentsGradeRankView domain object.
 * 
 * @author Virtusa Corporation
 */
public class StudentsGradeRankView extends BaseDomain implements Serializable {
    
    /** serialVersionUID - final. */
    private static final long serialVersionUID = 1L;
    
    /** Holds composite id. */
    private StudentsGradeRankViewId id;
    
    /** Holds classGradeID. */
    private int classGradeId;
    
    /** Holds grade id. */
    private int gradeId;
    
    /** Holds year. */
    private Date year;
    
    /** Holds value of total. */
    private int total;
    
    /** Holds average. */
    private double average;
    
    /** Holds rank.*/
    private int rank;
    
    /**
     * Getter method for id.
     * 
     * @return id composite id
     */
    public StudentsGradeRankViewId getId() {
    
        return id;
    }
    
    /**
     * Setter method for id.
     * 
     * @param vId composite id
     */
    public void setId(StudentsGradeRankViewId vId) {
    
        this.id = vId;
    }
    
    /**
     * Getter method for class grade id.
     * 
     * @return class grade id
     */
    public int getClassGradeId() {
    
        return classGradeId;
    }
    
    /**
     * Setter method for class grade id.
     * 
     * @param vClassGradeId class grade id
     */
    public void setClassGradeId(int vClassGradeId) {
    
        this.classGradeId = vClassGradeId;
    }
    
    /**
     * Getter method for grade id.
     * 
     * @return grade id
     */
    public int getGradeId() {
    
        return gradeId;
    }
    
    /**
     * Setter method for grade id.
     * 
     * @param vGradeId grade id
     */
    public void setGradeId(int vGradeId) {
    
        this.gradeId = vGradeId;
    }
    
    /**
     * Getter method for year.
     * 
     * @return year
     */
    public Date getYear() {
    
        return year;
    }
    
    /**
     * Setter method for year.
     * 
     * @param vYear year
     */
    public void setYear(Date vYear) {
    
        this.year = vYear;
    }
    
       
    /**
     * Getter method for total.
     * 
     * @return total
     */
    public int getTotal() {
    
        return total;
    }
    
    /**
     * Setter method for total.
     * 
     * @param vTotal total
     */
    public void setTotal(int vTotal) {
    
        this.total = vTotal;
    }
    
    /**
     * Getter method for average.
     * 
     * @return average average
     */
    public double getAverage() {
    
        return average;
    }
    
    /**
     * Setter method for average.
     * 
     * @param vAverage average
     */
    public void setAverage(double vAverage) {
    
        this.average = vAverage;
    }
    
    /**
     * Getter method for rank.
     * 
     * @return rank
     */
    public int getRank() {
    
        return rank;
    }
    
    /**
     * Setter method for rank.
     * 
     * @param vRank rank
     */
    public void setRank(int vRank) {
    
        this.rank = vRank;
    }
    
}
