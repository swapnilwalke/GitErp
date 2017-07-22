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
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This interface provides persistence layer functionality for the Sport
 * domain object.
 *
 * @author Virtusa Corporation
 *
 */

public interface SportDao extends BaseDao<Sport> {

    /**
     * Retrieve all the sport's with the name.
     * 
     * @param sportName The name of the sport.
     * @return sport - the sport with the sportName.
     * @throws AkuraAppException SMS Exceptions.
     */
    Sport getAnySportByName(String sportName) throws AkuraAppException;

}
