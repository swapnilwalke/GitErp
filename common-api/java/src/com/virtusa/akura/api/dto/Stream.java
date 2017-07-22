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
import java.util.HashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Domain object to map data for Stream.
 * 
 * @author Virtusa Corporation
 */
public class Stream extends BaseDomain implements Serializable {
    
    /**
     * Represents the default serial id.
     */
    private static final long serialVersionUID = 1L;
    
    /** The constant for String "Stream streamId=". */
    private static final String STREAM_STREAM_ID = "Stream streamId=";
    
    /** The constant for String ", description=". */
    private static final String DESCRIPTION = ", description=";
    
    /**
     * Represents the id for a Stream.
     */
    private int streamId;
    
    /**
     * Represents the description for a Stream.
     */
    private String description;
    
    /**
     * Represents a set of the Subjects that relates to a Stream object.
     */
    private Set<Subject> subjects = new HashSet<Subject>();
    
    /**
     * Constructs a new Stream object.
     */
    public Stream() {
    
    }
    
    /**
     * Returns a set of Subjects of a stream.
     * 
     * @return - a set of subjects.
     */
    @JsonIgnore
    public final Set<Subject> getSubjects() {
    
        return subjects;
    }
    
    /**
     * Sets the Subjects of a Stream.
     * 
     * @param subjectsVal - Subjects of the Stream.
     */
    @JsonIgnore
    public final void setSubjects(final Set<Subject> subjectsVal) {
    
        this.subjects = subjectsVal;
    }
    
    /**
     * Returns the id for this Stream object.
     * 
     * @return - The id of the Stream.
     */
    public final int getStreamId() {
    
        return streamId;
    }
    
    /**
     * Sets the id of this Stream with the given key.
     * 
     * @param intStreamId - id for the stream.
     */
    public final void setStreamId(final int intStreamId) {
    
        this.streamId = intStreamId;
    }
    
    /**
     * Returns the description for this Stream object.
     * 
     * @return - the description of the Stream.
     */
    public final String getDescription() {
    
        return description;
    }
    
    /**
     * Sets the description for this Stream object with the given information.
     * 
     * @param strDescription - the description of the Stream.
     */
    public final void setDescription(final String strDescription) {
    
        this.description = strDescription;
    }
    
    /**
     * Returns the details for the current object.
     * 
     * @return - the current object details.
     */
    public String toString() {
    
        return STREAM_STREAM_ID + streamId + DESCRIPTION + description;
    }
}
