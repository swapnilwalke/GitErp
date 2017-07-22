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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Religion;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for ReligionDao interface. It handles all
 * the persistent related functionalities for the religion object.
 *
 * @author Virtusa Corporation
 *
 */
public class ReligionDaoImpl extends BaseDaoImpl<Religion> implements
		ReligionDao {
    
    /** String constant for holding respective query name. */
    private static final String GET_ANY_RELIGION_BY_DESCRIPTION = "getReligionByDescription";
    
    /**
     * Retrieve all the Religions with the description.
     * 
     * @param description The description of the Religion.
     * @return religion the Religion with the description.
     * @throws AkuraAppException Exceptions.
     */
    @SuppressWarnings("unchecked")
    @Override
    public Religion getReligionByDescription(String description) throws AkuraAppException {
        
        List<Religion> religionList = null;
        Religion religion = null;
        
        try{
            religionList = getHibernateTemplate().findByNamedQuery(GET_ANY_RELIGION_BY_DESCRIPTION, description);
            
            if(religionList != null && !religionList.isEmpty() ){
                religion = religionList.get(0);
            }
        }catch(DataAccessException e){
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return religion;
    }
}
