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
import com.virtusa.akura.api.dto.Race;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * RaceDao interface provides persistent related functionalities for race object.
 * 
 * @author Virtusa Corporation
 */

public interface RaceDao extends BaseDao<Race> {
    
    /**
     * Retrieve the race by passing the race's racename.This returns any race with the race passed.
     * 
     * @param raceName - String
     * @return Race object.
     * @throws AkuraAppException SMS Exceptions.
     */
    Race getRaceByName(String raceName) throws AkuraAppException;
    
}
