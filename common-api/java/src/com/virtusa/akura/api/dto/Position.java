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
 * This class is to associates properties of Position.
 *
 * @author Virtusa Corporation
 */
public class Position extends BaseDomain {

    /** attribute for holding string modified date/time. */
    private static final String MODIFIED_DATE_TIME = " modified date/time = ";

    /** attribute for holding string description. */
    private static final String DESCRIPTION = " description ";

    /** attribute for holding string position id. */
    private static final String POSITION_ID = " position id = ";

    /**
     * Holds serial version id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Represents the unique id for Position.
     */
    private int positionId;

    /**
     * Holds description for Position.
     */
    private String description;

    /**
     * Default constructor.
     */
    public Position() {

    }

    /**
     * Constructor with parameter positionId.
     *
     * @param intPositionId type int
     */
    public Position(int intPositionId) {

        this.positionId = intPositionId;
    }

    /**
     * Get the position id for the position.
     *
     * @return the positionId
     */
    public int getPositionId() {

        return positionId;
    }

    /**
     * Set the position id for the position.
     *
     * @param intPositionId the positionId to set
     */
    public void setPositionId(int intPositionId) {

        this.positionId = intPositionId;
    }

    /**
     * Get the description for the position.
     *
     * @return the description
     */
    public String getDescription() {

        return description;
    }

    /**
     * Set the description for the position.
     *
     * @param strDescription the description to set
     */
    public void setDescription(String strDescription) {

        this.description = strDescription;
    }

    /**
     * Returns a string representation of the object.
     *
     * @return - the current object details.
     */
    public String toString() {

        return POSITION_ID + this.positionId + DESCRIPTION + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
