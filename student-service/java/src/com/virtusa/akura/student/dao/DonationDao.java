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

package com.virtusa.akura.student.dao;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Donation object.
 * 
 * @author Virtusa Corporation
 */
public interface DonationDao extends BaseDao<Donation> {
    
    /**
     * Retrieve all the available donation information given by student Id.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @return List of Donations
     * @throws AkuraAppException SMS Exception
     */
    List<Donation> getDonationsByStudentId(int studentId) throws AkuraAppException;

    /**
     * Retrieve all the available donation information given by purpose, amount, date and donationTypeId.
     * 
     * @param purpose - specifies the purpose of donation
     * @param amount  - specifies the amount of donation
     * @param date  - specifies the date of donation
     * @param donationTypeId  - specifies the date of donationTypeId
     * @return List of Donations
     * @throws AkuraAppException SMS Exception
     */
    List<Donation> getDonations(String  purpose, String amount, Date date, int donationTypeId) throws AkuraAppException;
}
