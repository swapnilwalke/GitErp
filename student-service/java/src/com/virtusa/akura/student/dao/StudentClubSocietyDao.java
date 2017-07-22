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
import com.virtusa.akura.api.dto.StudentClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface StudentClubSocietyDao extends BaseDao<StudentClubSociety> {
    
    /**
     * retrieving list of StudentClubSociety for a given studentId and year.
     * 
     * @param studentId the studentId to get ClubSociety details.
     * @param year the Date to get ClubSociety details.
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentClubSociety> getClubSocietyListForStudent(int studentId, Date year) throws AkuraAppException;
    
    /**
     * retrieving list of StudentClubSociety for a given studentId.
     * 
     * @param studentId the studentId to get ClubSociety details.
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentClubSociety> getClubSocietiesListForStudent(int studentId) throws AkuraAppException;
    
    /**
     * retrieving list of StudentClubSociety for a given studentId,sportsCategoryId and given year.
     * 
     * @param studentId the studentId to get ClubSociety details.
     * @param clubSocietyId the club or society Id.
     * @param year the Date.
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentClubSociety> findStudentClubSociety(int studentId, int clubSocietyId, Date year)
            throws AkuraAppException;
    
    /**
     * retrieving list of StudentClubSociety for a given clubSocietyId and given year.
     * 
     * @param clubSocietyId - integer
     * @param year - Year
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentClubSociety> getStudentListforClubSociety(int clubSocietyId, Date year) throws AkuraAppException;
    
    /**
     * retrieving list of StudentClubSociety for a given sportsCategoryId and given year.
     * 
     * @param clubSocietyId the club or society Id.
     * @param year the Date.
     * @return List {@link StudentClubSociety}
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentClubSociety> findStudentClubSocietyByClubSocietyId(int clubSocietyId, Date year)
            throws AkuraAppException;
    
    /**
     * Get the list of non current student club societies.
     * 
     * @param clubSOcietyId - the club or society id.
     * @param year - year of interest
     * @param date - date of interest.
     * @return list of StudentClubSociety.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentClubSociety> getNonCurrentStudentListByClubSociety(int clubSOcietyId, Date year, Date date)
            throws AkuraAppException;

    /**
     * Returns the club and societies of the student for the current year.
     * 
     * @param studentKey - the key of the student.
     * @param currentYear - the current year
     * @return - the club and societies of the student for the current year.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<List<String>> viewClubAndSocieties(int studentKey, Date currentYear)throws AkuraAppException;
}
