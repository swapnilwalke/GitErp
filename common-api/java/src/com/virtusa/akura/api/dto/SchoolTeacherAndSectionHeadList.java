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
 * SchoolTeacherAndSectionHeadList class works as a model for data transfer.
 * 
 * @author VIRTUSA Corporation
 */
public class SchoolTeacherAndSectionHeadList {
    
    /** The constant for String "SchoolTeacherAndSectionHeadList listType=". */
    private static final String SCHOOL_TEACHER_AND_SECTION_HEAD_LIST_LIST_TYPE =
            "SchoolTeacherAndSectionHeadList listType=";
    
    /** The attribute to hold the list type. */
    private int listType;
    
    /** The attribute to hold catogaryID. */
    private int catogaryID;
    
    /** The attribute to hold category. */
    private String category;
    
    /** The attribute to hold grade id. */
    private int gradeId;
    
    /** The attribute to hold gradeDescription. */
    private String gradeDescription;
    
    /**
     * Get the grade id.
     * 
     * @return a grade Id.
     */
    public int getGradeId() {
    
        return gradeId;
    }
    
    /**
     * Set the garde id.
     * 
     * @param gradeIds a Grade id.
     */
    public void setGradeId(int gradeIds) {
    
        this.gradeId = gradeIds;
    }
    
    /**
     * Get the list type.
     * 
     * @return a list type.
     */
    public int getListType() {
    
        return listType;
    }
    
    /**
     * Set the list type.
     * 
     * @param listTypes the type of list.
     */
    public void setListType(int listTypes) {
    
        this.listType = listTypes;
    }
    
    /**
     * Get the categoryID.
     * 
     * @return integer category id.
     */
    public int getCatogaryID() {
    
        return catogaryID;
    }
    
    /**
     * Set the categoryID.
     * 
     * @param catogaryIDs to set.
     */
    public void setCatogaryID(int catogaryIDs) {
    
        this.catogaryID = catogaryIDs;
    }
    
    /**
     * Get the category.
     * 
     * @return a category description.
     */
    public String getCategory() {
    
        return category;
    }
    
    /**
     * Set the category.
     * 
     * @param categoryDes to set.
     */
    public void setCategory(String categoryDes) {
    
        this.category = categoryDes;
    }
    
    /**
     * Get the grade description.
     * 
     * @return grade description.
     */
    public String getGradeDescription() {
    
        return gradeDescription;
    }
    
    /**
     * Set the grade description.
     * 
     * @param gradeDescriptions to set.
     */
    public void setGradeDescription(String gradeDescriptions) {
    
        this.gradeDescription = gradeDescriptions;
    }
    
    /**
     * Returns a string representation of the object.
     * 
     * @return - the current object details.
     */
    @Override
    public String toString() {
    
        return SCHOOL_TEACHER_AND_SECTION_HEAD_LIST_LIST_TYPE + listType;
    }
}
