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
package com.virtusa.akura.common.dao;

import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This interface provides all the persistence related operations for
 * SportCateogry.
 * @author Virtusa Corporation
 *
 */

public interface SportCategoryDao extends BaseDao<SportCategory> {

    /**
     * Retrieves the list of SportCategories for given sportId and SportSubId.
     * @param sportId - the id of sport object.
     * @param sportSubId - the id of sportSub object.
     * @return List - The list of SportCategory.
     * @throws AkuraAppException - The detailed exception.
     */
    List<SportCategory> findSportCategory(int sportId, int sportSubId) throws AkuraAppException;

    /**
     * Retrieves the list of SportCategories for given sportId.
     * @param sportId - the id of sport object.
     * @return List - The list of SportCategory.
     * @throws AkuraAppException - The detailed exception.
     */
    List<SportCategory> findSportCategoryBySportId(int sportId) throws AkuraAppException;
    
    /**
     * Retrieve the SportCategory by passing the Sport ID and Sport Sub ID. This returns Sport Category.
     *
     *  @param intSport -integer
     *  @param intSportSub - integer 
     *  @return SportCategory - object
     *  @throws AkuraAppException - Detailed Exceptions.
     */
    SportCategory getSportCategoryById(int intSport, int intSportSub) throws AkuraAppException;
}
