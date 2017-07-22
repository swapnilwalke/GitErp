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

import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.delegate.SystemUserDelegate;
import com.virtusa.akura.student.service.StudentService;

/**
 * Delegates the method to student service.
 * 
 * @author Virtusa Corporation
 */
public class StudentSystemUserDelegate implements SystemUserDelegate {
    
    /**
     * Represents an instance of StudentService.
     */
    private StudentService studentService;
    
    /**
     * Sets an instance of StudentService.
     * 
     * @param studentServiceVal - an instance of StudentService.
     */
    public void setStudentService(StudentService studentServiceVal) {

        this.studentService = studentServiceVal;
    }
    
    /** {@inheritDoc} */
    public boolean isExistRegistrationNo(String registrationNo) throws AkuraAppException {

        return studentService.isAdmissionNoExist(registrationNo);
    }
    
    /** {@inheritDoc} */
    public int getKeyByIdentificationNo(final String identification) throws AkuraAppException {

        return studentService.getKeyByIdentificationNo(identification);
    }
    
    /** {@inheritDoc} */
    public boolean isPastUser(String userIdentificationNo) throws AkuraAppException {

        return false;
    }
    
    /** {@inheritDoc} */
    public String getIdentificationNoByKey(int key) throws AkuraAppException {

        Student student = studentService.findStudent(key);
        return student != null ? student.getAdmissionNo() : "";
    }
}
