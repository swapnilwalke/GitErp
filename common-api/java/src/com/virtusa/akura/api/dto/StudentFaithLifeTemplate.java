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
 * The Class StudentFaithLifeTemplate.
 *
 * @author Virtusa Corporation
 */
public class StudentFaithLifeTemplate {

    /** The grading value. */
    private String gradingValue;

    /** The category. */
    private String category;

    /** The comment. */
    private String comment;

    /** The year. */
    private Date year;

    /**
     * Gets the grading value.
     *
     * @return the gradingValue
     */
    public String getGradingValue() {

        return gradingValue;
    }

    /**
     * Sets the grading value.
     *
     * @param strGradingValue the gradingValue to set
     */
    public void setGradingValue(String strGradingValue) {

        this.gradingValue = strGradingValue;
    }

    /**
     * Gets the category.
     *
     * @return the category
     */
    public String getCategory() {

        return category;
    }

    /**
     * Sets the category.
     *
     * @param strCategory the category to set
     */
    public void setCategory(String strCategory) {

        this.category = strCategory;
    }

    /**
     * Gets the comment.
     *
     * @return the comment
     */
    public String getComment() {

        return comment;
    }

    /**
     * Sets the comment.
     *
     * @param strComment the comment to set
     */
    public void setComment(String strComment) {

        this.comment = strComment;
    }

    /**
     * Gets the year.
     *
     * @return the year
     */
    public Date getYear() {

        if (year != null) {
            return (Date) year.clone();
        }

        return null;
    }

    /**
     * Sets the year.
     *
     * @param dtYear the year to set
     */
    public void setYear(Date dtYear) {

        if (dtYear != null) {

            this.year = (Date) dtYear.clone();
        }
    }

}
