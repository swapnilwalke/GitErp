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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides all the persistence related operations for SportCateogry.
 *
 * @author Virtusa Corporation
 */

public class SportCategoryDaoImpl extends BaseDaoImpl<SportCategory> implements SportCategoryDao {

    /**Logger to log the exceptions.*/
    private static final Logger LOG = Logger.getLogger(SportCategoryDaoImpl.class);
    
    /** String that holds the error message to logged.*/
    private static final String ERROR_WHILE_SEARCHING_SPORT_CATEGORY_TYPE =
                                                      "Error While Searching Sport Category --->";
    /**
     * String attribute for query name by sport id.
     */
    private static final String QUERY_NAME_BY_SPORT_ID = "getSportCategoryListBySportId";

    /**
     * String attribute for query name sport category.
     */
    private static final String QUERY_NAME_SPORT_CATEGORY = "getSportCategoryObj";
    

    /**
     * Retrieves the list of SportCategories for given sportId and SportSubId.
     *
     * @param sportId - the id of sport object.
     * @param sportSubId - the id of sportSub object.
     * @return List - The list of SportCategory.
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    public List<SportCategory> findSportCategory(int sportId, int sportSubId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(QUERY_NAME_SPORT_CATEGORY, sportId, sportSubId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }

    }

    /**
     * Retrieves the list of SportCategories for given sportId.
     * @param sportId - the id of sport object.
     * @return List - The list of SportCategory.
     * @throws AkuraAppException - The detailed exception.
     */
    @SuppressWarnings("unchecked")
    public List<SportCategory> findSportCategoryBySportId(int sportId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(QUERY_NAME_BY_SPORT_ID, sportId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Retrieve the SportCategory by passing the Sport ID and Sport Sub ID. This returns Sport Category.
     * 
     * @param intSport -integer
     * @param intSportSub - integer
     * @return SportCategory - object
     * @throws AkuraAppException - Detailed Exceptions.
     */
    @SuppressWarnings("unchecked")
    public  SportCategory getSportCategoryById(int intSport, int intSportSub) throws AkuraAppException {

        List<SportCategory> sportCategoryList = null;
        SportCategory sportCategory = null;
        
        try {
            sportCategoryList =
                    getHibernateTemplate().findByNamedQuery(QUERY_NAME_SPORT_CATEGORY, intSport, intSportSub);
            if (sportCategoryList != null && !sportCategoryList.isEmpty()) {
                sportCategory = sportCategoryList.get(0);
            }
        } catch (DataAccessException ex) {
            
            LOG.error(ERROR_WHILE_SEARCHING_SPORT_CATEGORY_TYPE + ex.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, ex);
        }
        return sportCategory;
    }      
}
