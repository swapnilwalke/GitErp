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

package com.virtusa.akura.common.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.City;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the working
 * city object.
 *
 * @author Virtusa Corporation
 */
public class CityDaoImpl extends BaseDaoImpl<City> implements CityDao {

    /** The query name for searching a city by description.  */
    private static final String FIND_CITY_BY_DESCRIPTION = "findCityByDescription";

    /** The description of the error in SMS error log. */
    private static final String SEARCHING_CITIES_FOR_A_GIVEN_CRITERIA = "Searching Cities for a given criteria";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(CityDaoImpl.class);

    /**
     * {@inheritDoc}
     */
	@SuppressWarnings("unchecked")
    public final List<City> searchCity(final String description)
    throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(
            FIND_CITY_BY_DESCRIPTION, description);
        } catch (DataAccessException e) {
            LOG.debug(SEARCHING_CITIES_FOR_A_GIVEN_CRITERIA);
          throw new AkuraAppException(
                  AkuraConstant.DB_CONNECTION_ERROR, e);
      }
    }
}
