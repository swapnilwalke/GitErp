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

/**
 * This class represents all properties of a Province domain object.
 *
 * @author Virtusa Corporation
 */
public class Province extends BaseDomain implements Serializable {

    /**
     * Default serial id.
     */
    private static final long serialVersionUID = 1L;

    /** The constant for String "province id = ". */
    private static final String PROVINCE_ID = "province id = ";

    /** The constant for String " description = ". */
    private static final String DESCRIPTION = " description = ";

    /** The constant for String " modified date/time". */
    private static final String MODIFIED_DATE_TIME = " modified date/time";

    /**
     * Represents the id for a Province.
     */
    private int provinceId;

    /**
     * Represents the description for a Province.
     */
    private String description;

    /**
     * Represents a set of Districts for a Province.
     */
    private Set<District> districts = new HashSet<District>();

    /**
     * Constructs a new Province object.
     */
    public Province() {

    }

    /**
     * Returns the id for this Province object.
     *
     * @return - the id of the Province object
     */
    public final int getProvinceId() {

        return provinceId;
    }

    /**
     * Sets the id for this Province object with the given key.
     *
     * @param intProvinceId - id of the Province.
     */
    public final void setProvinceId(final int intProvinceId) {

        this.provinceId = intProvinceId;
    }

    /**
     * Returns the description for this Province object.
     *
     * @return - the description of this Province object.
     */
    public final String getDescription() {

        return description;
    }

    /**
     * Sets the description for this Province object with the given information.
     *
     * @param strDescription - the description of the Province.
     */
    public final void setDescription(final String strDescription) {

        this.description = strDescription;
    }

    /**
     * Returns a Set of Districts for this Province object.
     *
     * @return the districts of the Province
     */
    public final Set<District> getDistricts() {

        return districts;
    }

    /**
     * Sets a Set of Districts of the Province.
     *
     * @param collDistricts - The Districts of the Province.
     */
    public final void setDistricts(final Set<District> collDistricts) {

        this.districts = collDistricts;
    }

    /**
     * Returns the details for the current object.
     *
     * @return - the current object details.
     */
    public final String toString() {

        return PROVINCE_ID + this.provinceId + DESCRIPTION + this.description + MODIFIED_DATE_TIME
                + this.getModifiedTime();
    }
}
