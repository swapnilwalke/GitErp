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
import com.virtusa.akura.api.dto.Country;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the
 * city object.
 *
 * @author Virtusa Corporation
 */
public interface CountryDao extends BaseDao<Country> {
    
    /**
     * Get the list of countries in the world.
     * @param countryName name of the Country.
     * @return list of countries.
     * @throws AkuraAppException throws the detailed exception.
     */
    List<Country> searchCountry(String countryName) throws AkuraAppException;
    
    /**
     * Retrieve the country by passing the countryname.This returns any country with the country name passed.
     * 
     * @param countryName - String
     * @return Country object.
     * @throws AkuraAppException throws the detailed exception.
     */
    Country getAnyCountryByName(String countryName) throws AkuraAppException;

}
