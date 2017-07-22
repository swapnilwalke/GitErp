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

package com.virtusa.akura.student.service;

import java.util.Date;

import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * interface to declare the student progress bar related services.
 * 
 * @author Virtusa Corporation
 */
public interface StudentProgressBarService {
    
    /**
     * Returns an average value of faithLifeRating for a selected student for selected year.
     * 
     * @param studentId - Id of the student
     * @param year - Date
     * @return - an average of the rating
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    double calculateFaithLifeRatingAverage(int studentId, Date year) throws AkuraAppException;
    
    /**
     * Returns an average value of academicLifeRating for a selected student for selected year.
     * 
     * @param studentId - Id of the student
     * @param year - Date
     * @return - an average of the rating
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    double calculateAcademicLifeRatingAverage(int studentId, Date year) throws AkuraAppException;
    
}
