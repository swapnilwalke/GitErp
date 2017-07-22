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
 * @author Virtusa Corporation
 */
public class StudentAchievementTemplate {

    /** The year. */
    private Date year;

    /** The achievement. */
    private String achievement;

    /** The achievement category. */
    private String achievementCategory;

    /** The type. */
    private String type;

    /**
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

    /**
     * Gets the achievement.
     *
     * @return the achievement
     */
    public String getAchievement() {

        return achievement;
    }

    /**
     * Sets the achievement.
     *
     * @param strAchievement the achievement to set
     */
    public void setAchievement(String strAchievement) {

        this.achievement = strAchievement;
    }

    /**
     * Gets the achievement category.
     *
     * @return the achievementCategory
     */
    public String getAchievementCategory() {

        return achievementCategory;
    }

    /**
     * Sets the achievement category.
     *
     * @param strAchievementCategory the achievementCategory to set
     */
    public void setAchievementCategory(String strAchievementCategory) {

        this.achievementCategory = strAchievementCategory;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    public String getType() {

        return type;
    }

    /**
     * Sets the type.
     *
     * @param strType the type to set
     */
    public void setType(String strType) {

        this.type = strType;
    }

}
