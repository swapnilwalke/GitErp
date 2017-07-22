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
import com.virtusa.akura.api.dto.MethodOfTravel;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This is the implementation class for MethodOfTravelDao interface. It handles
 * all the persistent related functionalities for the MethodOfTravel object.
 *
 * @author Virtusa Corporation
 *
 */
public class MethodOfTravelDaoImpl extends BaseDaoImpl<MethodOfTravel>
		implements MethodOfTravelDao {
    
    /** String constant for holding respective query name. */
    private static final String GET_METHOD_OF_TRAVEL_BY_NAME = "getMethodOfTravelByName";
    
    /**
     * Retrieve all the Methods Of Travels.
     * 
     * @param description The name of the MethodOfTravel.
     * @return MethodOfTravel.
     * @throws AkuraAppException Detailed exception.
     */
    @SuppressWarnings("unchecked")
    public MethodOfTravel getMethodOfTravelByName(String description) throws AkuraAppException {

        List<MethodOfTravel> methodOfTravelList = null;
        MethodOfTravel methodOfTravel = null;
        
        try {
            methodOfTravelList = getHibernateTemplate().findByNamedQuery(GET_METHOD_OF_TRAVEL_BY_NAME, description);
            if (methodOfTravelList != null && !methodOfTravelList.isEmpty()) {
                methodOfTravel = methodOfTravelList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return methodOfTravel;
    }
}
