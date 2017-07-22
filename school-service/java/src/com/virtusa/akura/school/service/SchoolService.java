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

package com.virtusa.akura.school.service;

import java.util.List;

import com.virtusa.akura.api.dto.School;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * SchoolService contains all the school related functions.
 * 
 * @author Virtusa Corporation
 */
public interface SchoolService {
    
    /**
     * Adds a new School object.
     * 
     * @param school - an instance of School.
     * @return the saved instance of School.
     * @throws AkuraAppException - The exception details that occurred when saving a School instance.
     */
    School addSchool(School school) throws AkuraAppException;
    
    /**
     * Finds the School that relevant to the School id.
     * 
     * @param schoolId - id of the School.
     * @return a School instance for the relevant id.
     * @throws AkuraAppException - The exception details that occurred when finding a School instance.
     */
    School findSchool(Integer schoolId) throws AkuraAppException;
    
    /**
     * Updates the relevant School object..
     * 
     * @param school - instance of School.
     * @throws AkuraAppException - The exception details that occurred when updating a School instance.
     */
    void updateSchool(School school) throws AkuraAppException;
    
    /**
     * Returns a list of School instances.
     * 
     * @return - a list of School instances.
     * @throws AkuraAppException - The exception details that occurred when retrieving a list of School
     *         instances.
     */
    List<School> getSchoolList() throws AkuraAppException;
    
    /**
     * Delete school.
     *
     * @param school the school
     * @throws AkuraAppException the sMS app exception
     */
    void deleteSchool(final School school) throws AkuraAppException;
}
