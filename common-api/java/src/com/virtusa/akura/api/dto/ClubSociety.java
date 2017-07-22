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
 * This class is to associates all the properties of ClubSociety domain object.
 *
 * @author Virtusa Corporation
 */
public class ClubSociety extends BaseDomain implements Serializable {

    /** String attribute for description. */
    private static final String DESCRIPTION_VALUE = ", description=";

    /** String attribute for name. */
    private static final String NAME_VALUE = ", name=";

    /** String attribute for club society id. */
    private static final String CLUB_SOCIETY_ID = "ClubSociety [clubSocietyId=";

    /**
     * serialVersionUID - final.
     */
    private static final long serialVersionUID = 1L;

    /**
     * clubSociety Unique id of the clubSociety object.
     */
    private int clubSocietyId;

    /**
     * hold the name of the clubSociety.
     */
    private String name;

    /**
     * descriptive text for clubSociety.
     */
    private String description;

    /**
     * default constructor to create ClubSociety object.
     */
    public ClubSociety() {

    }

    /**
     * Constructor with an integer parameter to create ClubSociety type object.
     *
     * @param intId the clubSocietyId to pass.
     */
    public ClubSociety(int intId) {

        clubSocietyId = intId;
    }

    /**
     * gets the Id of the clubSociety object.
     *
     * @return the clubSocietyId.
     */
    public int getClubSocietyId() {

        return clubSocietyId;
    }

    /**
     * sets the Id of the clubSociety.
     *
     * @param intId the clubSocietyId to set.
     */
    public void setClubSocietyId(int intId) {

        clubSocietyId = intId;
    }

    /**
     * gets the name of the clubSociety.
     *
     * @return the name.
     */
    public String getName() {

        return name;
    }

    /**
     * sets the name of the clubSociety object.
     *
     * @param strName the name to set.
     */
    public void setName(String strName) {

        name = strName;
    }

    /**
     * gets the description of the clubSociety.
     *
     * @return the description.
     */
    public String getDescription() {

        return description;
    }

    /**
     * sets the description of the clubSociety.
     *
     * @param strDescript the description to set.
     */
    public void setDescription(String strDescript) {

        description = strDescript;
    }

    /**
     * Returns the details for the ClubSociety object.
     *
     * @return - the ClubSociety object details.
     */
    @Override
    public String toString() {

        return CLUB_SOCIETY_ID + clubSocietyId + NAME_VALUE + name + DESCRIPTION_VALUE + description ;
    }





}
