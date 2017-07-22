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
package com.virtusa.akura.common.dao;

import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Scholarship;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * ScholarshipDao interface provides persistent related functionalities for
 * scholarship object.
 *
 * @author Virtusa Corporation
 *
 */
public interface ScholarshipDao extends BaseDao<Scholarship> {

    /**
     *
     * @return the scholarship list.
     * @throws AkuraAppException AkuraAppException
     */
    List<Scholarship> findScholarshiList() throws AkuraAppException;
    
    /**
    *
    * @param scholarship String
    * @return the sponsorship
    * @throws AkuraAppException AkuraAppException
    */
    List<String> findSponsorship(String scholarship) throws AkuraAppException;

}
