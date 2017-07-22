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
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides the persistence level implementation for the Section
 * domain object.
 * 
 * @author Virtusa Corporation
 *
 */

public class SectionDaoImpl extends BaseDaoImpl<Section> implements SectionDao{
    
    /** String constant for holding respective query name. */
    private static final String GET_ANY_SECTION_BY_NAME = "getSectionByName";
    
    /**
     * Retrieve all the section's with the description.
     * 
     * @param description The section description of the section.
     * @return section the section with the description "description".
     * @throws AkuraAppException SMS Exceptions.
     */
    @SuppressWarnings("unchecked")
    public Section getSectionByName(String description) throws AkuraAppException {

        List<Section> sectionList = null;
        Section section = null;
        
        try {
            sectionList = getHibernateTemplate().findByNamedQuery(GET_ANY_SECTION_BY_NAME, description);
            if (sectionList != null && !sectionList.isEmpty()) {
                section = sectionList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return section;
    }
}
