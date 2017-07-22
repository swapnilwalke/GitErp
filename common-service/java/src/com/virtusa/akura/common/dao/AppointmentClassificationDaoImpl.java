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
import com.virtusa.akura.api.dto.AppointmentClassification;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */

public class AppointmentClassificationDaoImpl extends BaseDaoImpl<AppointmentClassification> implements
        AppointmentClassificationDao {
    
    /** String constant for holding respective query name. */
    private static final String GET_ANY_APPOINTMENTCLASSIFICATION_BY_NAME = "getAnyAppointmentClassificationByName";
    
    /**
     * Retrieve all the appointmentClassifications with its name.
     * 
     * @param classification - String.
     * @return appointmentClassification - AppointmentClassification.
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public AppointmentClassification getAnyAppointmentClassificationByName(String classification)
            throws AkuraAppException {

        List<AppointmentClassification> appointmentClassificationList = null;
        AppointmentClassification appointmentClassification = null;
        
        try {
            appointmentClassificationList =
                    getHibernateTemplate().findByNamedQuery(GET_ANY_APPOINTMENTCLASSIFICATION_BY_NAME, classification);
            if (appointmentClassificationList != null && !appointmentClassificationList.isEmpty()) {
                appointmentClassification = appointmentClassificationList.get(0);
                
                String name = appointmentClassification.getDescription();
                String trimedName = name.trim();
                
                
                boolean check = trimedName.equalsIgnoreCase(classification);
                
                if(!check){
                    
                    appointmentClassification = null;
                }
                
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return appointmentClassification;
    }
    
}
