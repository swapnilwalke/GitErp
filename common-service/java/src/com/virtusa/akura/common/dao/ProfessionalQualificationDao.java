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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.ProfessionalQualification;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * ProfessionalQualificationDao interface provides persistent related
 * functionalities for ProfessionalQualification object.
 *
 * @author Virtusa Corporation
 */
public interface ProfessionalQualificationDao extends
		BaseDao<ProfessionalQualification> {
    
    /**
     * Retrieve all the ProfessionalQualification's with the description.
     * 
     * @param description The ProfessionalQualification description of the ProfessionalQualification.
     * @return professionalQualification the ProfessionalQualification with the description "description".
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public ProfessionalQualification getProfessionalQualificationByName(String description) throws AkuraAppException;
}
