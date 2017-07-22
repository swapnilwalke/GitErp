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

package com.virtusa.akura.student.dao;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Parent object.
 * 
 * @author Virtusa Corporation
 */
public interface ParentDao extends BaseDao<Parent> {
    
    /**
     * Retrieve available Parent information given by NIC number.
     * 
     * @param nicNo specifies the NIC Number, defined by a String
     * @return Parent object.
     * @throws AkuraAppException SMS Exception
     */
    Parent getParentInfoByNIC(String nicNo) throws AkuraAppException;
}
