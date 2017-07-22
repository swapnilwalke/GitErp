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

package com.virtusa.akura.common.delegate;

import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * Delegates the method to relevant service.
 *
 * @author Virtusa Corporation
 */
public interface SystemUserDelegate {

    /**
     * Checks wether given number is in the database.
     *
     * @param userIdentificationNo - admission number or the registration number.
     * @return - the status of exsisting of the admission or the registration number for the
     * student and the staff respectively.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    boolean isExistRegistrationNo(String userIdentificationNo)throws AkuraAppException;

    /**
     * Returns the key of the staff or the student by the identification number.
     * 
     * @param identification - the identification number.
     * @return - the key for the relevant identification number.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    int getKeyByIdentificationNo(String identification)throws AkuraAppException;

    /**
     * Returns the identification number of the staff or the student by the StudentID or StaffID.
     * 
     * @param key - StudentID or StaffID.
     * @return - the key for the relevant identification number.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    String getIdentificationNoByKey(int key)throws AkuraAppException;

    /**
     * Checks whether given user is past user.
     *
     * @param userIdentificationNo - admission number or the registration number.
     * @return - the status of the admission or the registration number for the
     * student and the staff is a past user or not.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    boolean isPastUser(String userIdentificationNo)throws AkuraAppException;
}
