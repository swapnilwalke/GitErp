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

package com.virtusa.akura.api.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.virtusa.akura.api.dto.BaseDomain;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for the BaseDao. It provides the main CRUD operations for all the domain
 * objects in the system.
 * 
 * @param <T> - the concrete class.
 * @author Virtusa Corporation
 */

public class BaseDaoImpl<T extends BaseDomain> extends HibernateDaoSupport implements BaseDao<T> {
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(BaseDaoImpl.class);
    
    /**
     * Construct a new BaseDao object.
     */
    public BaseDaoImpl() {
    
    }
    
    /**
     * Saves the given object to the database.
     * 
     * @param domainObj - The domain object to save.
     * @throws AkuraAppException - the exception.
     * @return - The newly saved object.
     */
    public T save(T domainObj) throws AkuraAppException {
    
        try {
            LOG.debug(" BaseDaoImpl->save-> object details" + domainObj.toString());
            
            getHibernateTemplate().save(domainObj);
            return domainObj;
            
        } catch (DataAccessException ex) {
            LOG.error("BaseDaoImpl - error occured while saving object " + domainObj + "-->" + ex.toString());
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, ex);
        }
    }
    
    /**
     * Method to update a record for the given domain object.
     * 
     * @param domainObj - The domain object to update.
     * @throws AkuraAppException - The exception.
     */
    public void update(T domainObj) throws AkuraAppException {
    
        try {
            getHibernateTemplate().update(domainObj);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, ex);
        }
    }
    
    /**
     * Method to delete the given domain object from the database.
     * 
     * @param domainObj - the domain object to be deleted from the db.
     * @throws AkuraAppException - the detailed exception.
     */
    public void delete(T domainObj) throws AkuraAppException {
    
        try {
            getHibernateTemplate().delete(domainObj);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, ex);
        }
    }
    
    /**
     * This method will create a collection of objects (if not already available in the db); else it will
     * update the existing records in the db for the given collection.
     * 
     * @param objList - The list of objects to be saved/updated.
     * @throws AkuraAppException - The detailed exception.
     */
    public void saveOrUpdateAll(List<T> objList) throws AkuraAppException {
    
        try {
            getHibernateTemplate().saveOrUpdateAll(objList);
        } catch (DataAccessException ex) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, ex);
        }
    }
    
    /**
     * Finds a db record for a specific domain object and its id.
     * 
     * @param domainObj - The domain object to be retrieved.
     * @param id - the specific id/key of the domain object to be retrieved.
     * @return Object - The specific domain object.
     * @throws AkuraAppException - The detailed exception.
     */
    public Object findById(Class<T> domainObj, int id) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().get(domainObj, id);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * Retrieves the entire list of records for a given domain object.
     * 
     * @param domainClass - the entity/domain object class.
     * @return List - The list of records for the given domain class.
     * @throws AkuraAppException - The detailed exception.
     */
    public List<T> findAll(Class<T> domainClass) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().loadAll(domainClass);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
}
