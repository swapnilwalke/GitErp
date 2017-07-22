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

package com.virtusa.akura.staff.dao;

import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.StaffProfessional;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Class StaffEducation object.
 *
 * @author Virtusa Corporation
 */
public interface StaffProfessionalDoa extends BaseDao<StaffProfessional> {

    /**
     * Search StaffProfessional with staffId.
     *
     * @param staffId staffId of staff member.
     * @return list of staffProfessional.
     * @throws AkuraAppException - The exception details that occurred when searching StaffEducation for a given
     *         criteria.
     */
    List<StaffProfessional> getStaffProfessionalListForStaff(int staffId) throws AkuraAppException;
}
