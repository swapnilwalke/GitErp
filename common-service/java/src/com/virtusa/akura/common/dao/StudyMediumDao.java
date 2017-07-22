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
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * StudyMediumDao interface provides persistent related functionalities for StudyMedium object.
 *
 * @author Virtusa Corporation
 */

public interface StudyMediumDao extends BaseDao<StudyMedium> {

    /**
     * Returns all the study mediums that starts with the given key string.
     *
     * @param key - name of the medium that needs to be searched.
     * @return - the relevant list of StudyMedium object.
     * @throws AkuraAppException - The exception details that occurred when searching Stream instance/s.
     */
    List<StudyMedium> findStudyMediums(String key) throws AkuraAppException;

    /**
     * Returns the study mediums that same as the given string (case insensitive) .
     *
     * @param name - name of the medium that needs to be searched.
     * @return - the relevant list of StudyMedium object.
     * @throws AkuraAppException - The exception details that occurred when searching Stream instance/s.
     */
    List<StudyMedium> findSameStudyMedium(String name) throws AkuraAppException;

}
