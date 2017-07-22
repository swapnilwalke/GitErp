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
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the working segment
 * domain object.
 *
 * @author Virtusa Corporation
 *
 */

public class WorkingSegmentDaoImpl extends BaseDaoImpl<WorkingSegment>
		implements WorkingSegmentDao {
    
    /** String constant for holding respective query name. */
    private static final String GET_WORKING_SEGMENT_BY_NAME = "getWorkingSegmentByName";
    
    /**
     * Retrieve all the Methods Of Travels.
     * 
     * @param description The name of the WorkingSegment.
     * @return WorkingSegment.
     * @throws AkuraAppException Detailed exception.
     */
   @SuppressWarnings("unchecked")
    public WorkingSegment getWorkingSegmentByName(String description) throws AkuraAppException {

        List<WorkingSegment> workingSegmentList = null;
        WorkingSegment workingSegment = null;
        
        try {
            workingSegmentList = getHibernateTemplate().findByNamedQuery(GET_WORKING_SEGMENT_BY_NAME, description);
            if (workingSegmentList != null && !workingSegmentList.isEmpty()) {
                workingSegment = workingSegmentList.get(0);
            }
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
        return workingSegment;
    }

}
