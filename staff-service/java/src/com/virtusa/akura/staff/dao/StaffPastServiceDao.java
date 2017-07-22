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

package com.virtusa.akura.staff.dao;

import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.StaffPastService;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the StaffPastService object.
 * 
 * @author Virtusa Corporation
 */
public interface StaffPastServiceDao extends BaseDao<StaffPastService> {
    
    /**
     * Returns all the staff past services for the given staff id.
     * 
     * @param staffId the id of the staff member.
     * @return - a list of staff past services for the given staff id.
     * @throws AkuraAppException when exception occurs.
     */
    List<StaffPastService> getPastStaffServiceList(int staffId) throws AkuraAppException;

}
