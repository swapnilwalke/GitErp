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

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Publication;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the publication object.
 *
 * @author Virtusa Corporation
 */
public class PublicationDaoImpl extends BaseDaoImpl<Publication> implements PublicationDao {

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(PublicationDaoImpl.class);

    /**
     * Represents the name for the query to retrieve the total count of publications.
     */
    private static final String GET_COUNT_OF_PUBLICATION = "getCountOfPublications";

    /**
     * Returns the latest publiations.
     */
    private static final String GET_LATEST_PUBLICATION = "getLatestPublications";

    /** Represents the query name of the exsist publications. */
    private static final String IS_EXIST_PUBLICATION = "isExistPublication";

    /**
     * {@inheritDoc}
     */
    public List<Publication> getPublicationsWithPagination(final int minSize, final int maxSize)
    throws AkuraAppException {
    	Session session = getHibernateTemplate().getSessionFactory().openSession();
        try {
            DateUtil.getFormatDate(new Date());
            
            Query q =
            		session.createQuery(
                            "FROM com.virtusa.akura.api.dto.Publication WHERE expiryDate >= '"
                                    + DateUtil.getFormatDate(new Date()) + "' ORDER BY expiryDate ASC");
            q.setFirstResult(minSize);
            q.setMaxResults(maxSize);
            return q.list();

        } catch (DataAccessException e) {
            LOG.error("error occured while processing  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        } 
        finally {
        	session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Long> getMaxNoOfPublications() throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_COUNT_OF_PUBLICATION,new Date(),  new Date());
        } catch (DataAccessException e) {
            LOG.error("error occured while processing  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<Publication> getLatestPublications() throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(GET_LATEST_PUBLICATION , new Date(),  new Date());
        } catch (DataAccessException e) {
            LOG.error("error occured while processing  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

    /** {@inheritDoc}*/
    public List<Publication> isExistPublication(final String header)
            throws AkuraAppException {
        try {
            return getHibernateTemplate().findByNamedQuery(IS_EXIST_PUBLICATION ,new Date(), new Date(), header);
        } catch (DataAccessException e) {
            LOG.error("error occured while processing  " + " the object-->" + e.toString());
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

}
