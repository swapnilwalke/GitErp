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
 * This class represents all the properties of StudentDiscipline.
 *
 * @author Virtusa Corporation
 */
public class StudentTermMarksRank extends BaseDomain implements Serializable {

    /** Holds the default serial id. */
    private static final long serialVersionUID = 1L;

    /** Holds the composite primary key. */
    private StudentTermMarksRankViewId id;

    /** Holds the average. */
    private double average;

    /** Holds the year. */
    private Date year;

    /** Holds the  rank. */
    private int rank;

    /** Holds the classGradeId. */
    private int classGradeId;

    /** Holds the description of the term. */
    private String term;
    
    /** Holds the total term marks of the student. */
    private int total;

    /**
     * Getter method for id.
     *
     * @return StudentTermMarksRankViewId object.
     */
    public StudentTermMarksRankViewId getId() {

        return id;
    }

    /**
     * Setter method for id.
     *
     * @param vId StudentTermMarksRankViewId object.
     */
    public void setId(StudentTermMarksRankViewId vId) {

        this.id = vId;
    }

    /**
     * Getter method for average.
     *
     * @return average.
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

    /**
     * Getter method for class grade id.
     *
     * @return class grade id.
     */
    public int getClassGradeId() {

        return classGradeId;
    }

    /**
     * Setter method for class grade id.
     *
     * @param vClassGradeId class grade id.
     */
    public void setClassGradeId(int vClassGradeId) {

        this.classGradeId = vClassGradeId;
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
     * Getter method for term.
     *
     * @return - the description of the term.
     */
    public String getTerm() {

        return term;
    }

    /**
     * Setter method for the term.
     *
     * @param strTerm - description of the term.
     */
    public void setTerm(String strTerm) {

        this.term = strTerm;
    }

    /**
     * Getter method for total.
     *
     * @return - total.
     */
    public int getTotal() {
    
        return total;
    }

    /**
     * Setter method for the total.
     *
     * @param vTotal - total.
     */
    public void setTotal(int vTotal) {
    
        this.total = vTotal;
    }

    
}
