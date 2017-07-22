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
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the working
 * country object.
 *
 * @author Virtusa Corporation
 */
public class CountryDaoImpl extends BaseDaoImpl<Country> implements CountryDao {
    
    /** The query name for searching a country by name.  */
    private static final String FIND_COUNTRY_BY_NAME = "findCountryByName";

    /** The description of the error in SMS error log. */
    private static final String SEARCHING_COUNTRIES_FOR_A_GIVEN_NAME = "Searching Countries for a given name";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(CountryDaoImpl.class);
    
    /** String constant for holding respective query name. */
    private static final String GET_ANY_COUNTRY_BY_NAME = "getAnyCountryByName";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")    
    public final List<Country> searchCountry(final String countryName)
    throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(
                    FIND_COUNTRY_BY_NAME, countryName);
        } catch (DataAccessException e) {
            LOG.debug(SEARCHING_COUNTRIES_FOR_A_GIVEN_NAME);
          throw new AkuraAppException(
                  AkuraConstant.DB_CONNECTION_ERROR, e);
      }
    }
    
    /**
     * Retrieve all the countries with the countryname.
     * 
     * @param countryName The country name of the country.
     * @return country the country with the country name "countryName".
     * @throws AkuraAppException throws the detailed exception.
     */
    @SuppressWarnings("unchecked")
    public Country getAnyCountryByName(String countryName) throws AkuraAppException {

        List<Country> countryList = null;
        Country country = null;
        
        try {
            countryList = getHibernateTemplate().findByNamedQuery(GET_ANY_COUNTRY_BY_NAME, countryName);
            if (countryList != null && !countryList.isEmpty()) {
                country = countryList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return country;
    }
    
    
    
}
