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
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the stream object.
 *
 * @author Virtusa Corporation
 */
public class StreamDaoImpl extends BaseDaoImpl<Stream> implements StreamDao {

    /**
     * String attribute for query name by description.
     */
    private static final String QUERY_NAME_BY_DESCRIPTION = "findStreamByDescription";
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StreamDaoImpl.class);

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
	public List<Stream> searchStream(String searchDescription) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(QUERY_NAME_BY_DESCRIPTION, searchDescription);
        } catch (DataAccessException e) {
            LOG.error("error occured while searching  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
