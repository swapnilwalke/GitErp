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

package com.virtusa.akura.api.dao;

import java.util.List;

import com.virtusa.akura.api.dto.BaseDomain;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Generic base interface for DAOs. This Helper class simplifies Hibernate data access by allowing a common
 * access point for all the entities in the application to execute various persistence related operations.
 * 
 * @param <T> - the concrete class.
 */
public interface BaseDao<T extends BaseDomain> {
    
    /**
     * Method to save a new object to the database.
     * 
     * @param domainObj - The entity to be saved.
     * @return - The new entity that has been added to the system.
     * @throws AkuraAppException - the exception.
     */
    T save(T domainObj) throws AkuraAppException;
    
    /**
     * Updates and existing record for the given domain object.
     * 
     * @param domainObj - The domain object to be updated.
     * @throws AkuraAppException - the exception.
     */
    void update(T domainObj) throws AkuraAppException;
    
    /**
     * Deletes the given domain object from the database.
     * 
     * @param domainObj - the domain object to be deleted
     * @throws AkuraAppException - the exception.
     */
    void delete(T domainObj) throws AkuraAppException;
    
    /**
     * This method will create a collection of objects (if not already available in the db); else it will
     * update the existing records for the given collection in the db.
     * 
     * @param objList - The list of objects to be saved/updated.
     * @throws AkuraAppException - The detailed exception.
     */
    void saveOrUpdateAll(List<T> objList) throws AkuraAppException;
    
    /**
     * Finds a db record for a specific domain object and its id.
     * 
     * @param domainObj - The domain object to be retrieved.
     * @param id - the specific id/key of the domain object to be retrieved.
     * @return Object - The specific domain object.
     * @throws AkuraAppException - The detailed exception
     */
    Object findById(Class<T> domainObj, int id) throws AkuraAppException;
    
    /**
     * Retrieves the entire list of records for a given domain object.
     * 
     * @param domainClass - the entity/domain object class.
     * @return List - The list of records for the given domain class.
     * @throws AkuraAppException - The detailed exception.
     */
    List<T> findAll(Class<T> domainClass) throws AkuraAppException;
    
}
