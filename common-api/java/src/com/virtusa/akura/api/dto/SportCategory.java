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
 * Domain object to map data for SportCategory.
 *
 * @author Virtusa Corporation
 */
public class SportCategory extends BaseDomain implements Serializable {

    /**
     * serialVersionUID -final.
     */

    private static final long serialVersionUID = 1L;

    /** The constant for String "SportCategory sportCategoryId=". */
    private static final String SPORT_CATEGORY_SPORT_CATEGORY_ID = "SportCategory sportCategoryId=";

    /** The constant for String ", sport=". */
    private static final String SPORT = ", sport=";

    /** The constant for String ", sportSubCategory=". */
    private static final String SPORT_SUB_CATEGORY = ", sportSubCategory=";

    /**
     * Unique id of the sportCategory.
     */

    private int sportCategoryId;

    /**
     * foreign key for {@link}Sport.
     */
    private Sport sport;

    /**
     * foreign key for {@link SportSub}.
     */
    private SportSub sportSubCategory;

    /**
     * default constructor to create sportCategory object.
     */

    public SportCategory() {

    }

    /**
     * Constructor with an integer parameter to create SportCategory type object.
     *
     * @param intSportCategoryId to set id.
     */
    public SportCategory(int intSportCategoryId) {

        this.sportCategoryId = intSportCategoryId;
    }

    /**
     * Constructs a SportCategory object using the Sport Id and Sport Sub Id as keys.
     *
     * @param sportObj - the sport object set.
     * @param sportSubObj - the sport sub-category to set.
     */
    public SportCategory(Sport sportObj, SportSub sportSubObj) {

        this.sport = sportObj;
        this.sportSubCategory = sportSubObj;
    }

    /**
     * gets the Id of the sportCategory object.
     *
     * @return id of the sportCategory in integer.
     */
    public int getSportCategoryId() {

        return sportCategoryId;
    }

    /**
     * sets the Id of the sportCategory.
     *
     * @param intSportCategoryId the sportCategoryId in integer.
     */
    public void setSportCategoryId(int intSportCategoryId) {

        this.sportCategoryId = intSportCategoryId;
    }

    /**
     * Returns the Sport object being referred in this object.
     *
     * @return The Sport reference.
     */
    public Sport getSport() {

        return sport;
    }

    /**
     * Sets the Sport object for this instance.
     *
     * @param sportObj - The Sport reference.
     */
    public void setSport(Sport sportObj) {

        this.sport = sportObj;
    }

    /**
     * Returns the Sport Sub object being referred in this object.
     *
     * @return The Sport Sub reference.
     */
    public SportSub getSportSubCategory() {

        return sportSubCategory;
    }

    /**
     * Sets the Sport Sub object for this instance.
     *
     * @param sportSubCategoryObj - The Sport Sub reference.
     */
    public void setSportSubCategory(SportSub sportSubCategoryObj) {

        this.sportSubCategory = sportSubCategoryObj;
    }

    /**
     * Returns the details for the current object.
     *
     * @return - the current object details.
     */
    public String toString() {

        return SPORT_CATEGORY_SPORT_CATEGORY_ID + sportCategoryId + SPORT + sport + SPORT_SUB_CATEGORY
                + sportSubCategory;
    }

}
