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

package com.virtusa.akura.student.delegate;

import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.delegate.SystemUserDelegate;
import com.virtusa.akura.student.service.ParentService;

/**
 * Delegates the method to parent service.
 * 
 * @author Virtusa Corporation
 */
public class ParentSystemUserDelegate implements SystemUserDelegate {
    
    /**
     * Represents an instance of ParentService.
     */
    private ParentService parentService;
    
    /**
     * Sets an instance of ParentService.
     * 
     * @param parentServiceArg - an instance of ParentService.
     */
    public void setParentService(ParentService parentServiceArg) {

        this.parentService = parentServiceArg;
    }
    
    /**
     * Checks whether given number is in the database (parent NIC no for any student's parent info).
     * 
     * @param nicNo - identification number (NIC).
     * @return - the status of existing of the NIC number for the parent
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @Override
    public boolean isExistRegistrationNo(String nicNo) throws AkuraAppException {

        return parentService.isNICnoExist(nicNo);
    }
    
    /** {@inheritDoc} */
    public int getKeyByIdentificationNo(String identification) throws AkuraAppException {
        Parent parent = parentService.getParentByNIC(identification);
        return parent != null ? parent.getParentId() : 0;
    }
    
    /** {@inheritDoc} */
    public boolean isPastUser(String userIdentificationNo) throws AkuraAppException {

        return false;
    }
    
    /** {@inheritDoc} */
    public String getIdentificationNoByKey(int key) throws AkuraAppException {

        Parent parent = parentService.findParent(key);
        return parent != null ? parent.getNationalIdNo() : "";
    }
}
