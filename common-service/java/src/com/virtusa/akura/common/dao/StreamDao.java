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
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the
 * stream domain object.
 *
 * @author Virtusa Corporation
 */
public interface StreamDao extends BaseDao<Stream> {

    /**
     * Returns all the streams that starts with the given information.
     *
     * @param searchDescription - name of the Stream that needs to be searched.
     * @return - the relevant Stream object.
     * @throws AkuraAppException - The exception details that occurred
     * when searching Stream instance/s.
     */
    List<Stream> searchStream(String searchDescription) throws AkuraAppException;

}
