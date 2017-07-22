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
 * This class represents all the properties of WorkingSegment.
 * 
 * @author Virtusa Corporation
 */

public class WorkingSegment extends BaseDomain implements Serializable {
    
    /** string constant for holding `modified date/time`. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";
    
    /** string constant for holding ` description = `. */
    private static final String DESCRIPTION = " description = ";
    
    /** string constant for holding `working segment id = `. */
    private static final String WORKING_SEGMENT_ID = "working segment id = ";
    
    /**
     * Generated serial id.
     */
    private static final long serialVersionUID = 8325431996087080549L;
    
    /**
     * Represents the id for a working segment.
     */
    private int workingSegmentId;
    
    /**
     * The description for a working segment.
     */
    private String description;
    
    /**
     * Constructs a new working segment object.
     */
    public WorkingSegment() {
    
    }
    
    /**
     * Constructs a new working segment object with the specified segment id.
     * 
     * @param segmentId - the working segment id.
     */
    public WorkingSegment(int segmentId) {
    
        this.workingSegmentId = segmentId;
    }
    
    /**
     * Constructs a new working segment object with the specified segment id and description.
     * 
     * @param segmentId - the working segment id.
     * @param descr - Description of the working segment.
     */
    public WorkingSegment(int segmentId, String descr) {
    
        this.workingSegmentId = segmentId;
        this.description = descr;
    }
    
    /**
     * Returns the id for this working segment object.
     * 
     * @return - the working segment id.
     */
    public int getWorkingSegmentId() {
    
        return workingSegmentId;
    }
    
    /**
     * Sets the working segment id with the given key.
     * 
     * @param segmentId - The working segment id to be set for this object.
     */
    public void setWorkingSegmentId(int segmentId) {
    
        this.workingSegmentId = segmentId;
    }
    
    /**
     * Returns the description for this working segment object.
     * 
     * @return - The description of the working segment.
     */
    public String getDescription() {
    
        return description;
    }
    
    /**
     * Sets the description for this working segment object with the given information.
     * 
     * @param descr - The description of the working segment.
     */
    public void setDescription(String descr) {
    
        this.description = descr;
    }
    
    /**
     * Returns the details for the WorkingSegment object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return WORKING_SEGMENT_ID + this.workingSegmentId + DESCRIPTION + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
    
}
