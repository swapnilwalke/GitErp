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
import com.virtusa.akura.api.dto.Province;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the province object.
 *
 * @author Virtusa Corporation
 */
public class ProvinceDaoImpl extends BaseDaoImpl<Province> implements ProvinceDao {

    /** The constant for String object "findProvinceByDescription". */
    private static final String FIND_PROVINCE_BY_DESCRIPTION = "findProvinceByDescription";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(ProvinceDaoImpl.class);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public final List<Province> searchProvince(final String provinceName) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_PROVINCE_BY_DESCRIPTION, provinceName);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
