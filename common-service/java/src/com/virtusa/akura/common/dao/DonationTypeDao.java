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
import com.virtusa.akura.api.dto.DonationType;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the DonationType object.
 * 
 * @author Virtusa Corporation
 */
public interface DonationTypeDao extends BaseDao<DonationType> {
 
    /**
     *Retrieve the Donation Type by passing the DonationType Name.
     *This returns any donationTypeName with the name that passed.
     *
     *  @param donationTypeName -String 
     *  @return DonationType - object
     *  @throws AkuraAppException - Detailed Exceptions.
     */
    DonationType getDonationTypeByName(String donationTypeName) throws AkuraAppException;
    
}
