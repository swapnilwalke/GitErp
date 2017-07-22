/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

/**
 * This class represents all the properties of a District domain object.
 *
 * @author Virtusa Corporation
 */
public class District extends BaseDomain implements Serializable {

    /** String attribute for to MODIFIED_DATE_TIME. */
    private static final String MODIFIED_DATE_TIME = " modified date/time";

    /** String attribute for to DESCRIPTION. */
    private static final String DESCRIPTION_VALUE = " description = ";

    /** String attribute for to DISTRICT_ID. */
    private static final String DISTRICT_ID = "district id = ";

    /**
     * Represents the id for a District.
     */
    private int districtId;

    /**
     * Represents the description for a District.
     */
    private String description;

    /**
     * Represents the province for a District.
     */
    private Province province;

    /**
     * Represents the cities for a District.
     */
    private Set<City> cities = new HashSet<City>();

    /**
     * Constructs a new District object.
     */
    public District() {

    }

    /**
     * Returns the id for this District object.
     *
     * @return the district id.
     */
    public final int getDistrictId() {

        return this.districtId;
    }

    /**
     * Sets the id of this District.
     *
     * @param intDistrictId - the id of the District.
     */
    public final void setDistrictId(final int intDistrictId) {

        this.districtId = intDistrictId;
    }

    /**
     * Returns the description for this District object.
     *
     * @return - the description of the District.
     */
    public final String getDescription() {

        return description;
    }

    /**
     * Sets the description for this District object with the given information.
     *
     * @param strDescription - the description of the District.
     */
    public final void setDescription(final String strDescription) {

        this.description = strDescription;
    }

    /**
     * Returns the Province for this District object.
     *
     * @return a Province instance of the District.
     */
    public final Province getProvince() {

        return province;
    }

    /**
     * Sets the province of the District.
     *
     * @param proProvince - the province of the District.
     */
    public final void setProvince(final Province proProvince) {

        this.province = proProvince;
    }

    /**
     * Returns the cities of the District.
     *
     * @return the cities of the District
     */
    public final Set<City> getCities() {

        return cities;
    }

    /**
     * Sets Cities of this District object.
     *
     * @param collCities - the cities of the District
     */
    public final void setCities(final Set<City> collCities) {

        this.cities = collCities;
    }

    /**
     * Returns the details for the District object.
     *
     * @return - the District object details.
     */
    public final String toString() {

        return DISTRICT_ID + this.districtId + DESCRIPTION_VALUE + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
