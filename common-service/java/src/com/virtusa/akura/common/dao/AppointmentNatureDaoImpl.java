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
import com.virtusa.akura.api.dto.AppointmentNature;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */

public class AppointmentNatureDaoImpl extends BaseDaoImpl<AppointmentNature> implements AppointmentNatureDao {
    
    /** String constant for holding respective query name. */
    private static final String GET_ANY_APPOINTMENT_NATURE_BY_DESCRIPTION = "getAppointmentNatureByDescription";
    
    /**
     * Retrieve all the Appointment natures with the description.
     * 
     * @param description The description of the Appointment nature.
     * @return appointmentNature the Appointment nature with the description.
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    @Override
    public AppointmentNature getAppointmentNatureByDescription(String description) throws AkuraAppException {

        List<AppointmentNature> appointmentNatureList = null;
        AppointmentNature appointmentNature = null;
        
        try {
            appointmentNatureList =
                    getHibernateTemplate().findByNamedQuery(GET_ANY_APPOINTMENT_NATURE_BY_DESCRIPTION, description);
            if (appointmentNatureList != null && !appointmentNatureList.isEmpty()) {
                appointmentNature = appointmentNatureList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        return appointmentNature;
    }
}
