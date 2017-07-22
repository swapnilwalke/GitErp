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
import com.virtusa.akura.api.dto.CivilStatus;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This interface provides persistence layer functionality for the
 * civil status object.
 * 
 * @author Virtusa Corporation
 *
 */

public interface CivilStatusDao extends BaseDao<CivilStatus>{
    /**
     * Retrieve the civilstatus by passing the civilStatusName.
     * 
     * @param civilStatusName - String
     * @return CivilStatus object.
     * @throws AkuraAppException Detailed exception.
     */
    
    CivilStatus getAnyCivilStatusByName(String civilStatusName) throws AkuraAppException;

}
