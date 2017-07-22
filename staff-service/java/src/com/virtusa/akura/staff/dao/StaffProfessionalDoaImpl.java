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

package com.virtusa.akura.staff.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StaffProfessional;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the StaffProfessional object.
 *
 * @author Virtusa Corporation
 */
public class StaffProfessionalDoaImpl extends BaseDaoImpl<StaffProfessional> implements StaffProfessionalDoa {

    /** The constant for String "staffProfessionalSearchWithStaffId". */
    private static final String STAFF_PROFESSIONAL_SEARCH_WITH_STAFF_ID = "staffProfessionalSearchWithStaffId";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffProfessionalDoaImpl.class);

    /**
     * Search StaffProfessional with staffId.
     *
     * @param staffId staffId of staff member.
     * @return list of staffProfessional.
     * @throws AkuraAppException - The exception details that occurred when searching StaffProfessional for a
     *         given criteria.
     */
    public List<StaffProfessional> getStaffProfessionalListForStaff(int staffId) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(STAFF_PROFESSIONAL_SEARCH_WITH_STAFF_ID, staffId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
