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

package com.virtusa.akura.staff.delegate;

import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.delegate.SystemUserDelegate;
import com.virtusa.akura.staff.service.StaffService;

/**
 * Delegates the method to staff service.
 * 
 * @author Virtusa Corporation
 */
public class StaffSystemUserDelegate implements SystemUserDelegate {
    
    /**
     * Represents an instance of StaffService.
     */
    private StaffService staffService;
    
    /**
     * Sets an instance of StaffService.
     * 
     * @param staffServiceVal - an instance of StaffService.
     */
    public void setStaffService(StaffService staffServiceVal) {

        this.staffService = staffServiceVal;
    }
    
    /** {@inheritDoc} */
    public boolean isExistRegistrationNo(String registrationNo) throws AkuraAppException {

        return staffService.isValidRegistrationNo(registrationNo);
    }
    
    /** {@inheritDoc} */
    public int getKeyByIdentificationNo(final String identification) throws AkuraAppException {

        return staffService.getKeyByIdentificationNo(identification);
    }
    
    /** {@inheritDoc} */
    public String getIdentificationNoByKey(final int staffId) throws AkuraAppException {

        Staff staff = staffService.findStaff(staffId);
        return staff != null ? staff.getRegistrationNo() : "";
    }
    
    /** {@inheritDoc} */
    public boolean isPastUser(String userIdentificationNo) throws AkuraAppException {

        return staffService.hasDepartureDate(userIdentificationNo);
    }
    
}
