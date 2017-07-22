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

package com.virtusa.akura.student.dao;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Achievement;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */

public interface AchievementDao extends BaseDao<Achievement> {
    
    /**
     * Retrieve all the available Achievement information of a given year for a student Id .
     * 
     * @param studentId specifies the student id, defined by an integer
     * @param year the student Date.
     * @return List of Achievement
     * @throws AkuraAppException - The detailed exception.
     */
    List<Achievement> getAchievementsListForStudent(int studentId, Date year) throws AkuraAppException;
    
    /**
     * Retrieve all the available Achievement information for a student using given parameters.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @param strDescription -String
     * @param strActivity - String
     * @param year the student Date.
     * @return List of Achievement
     * @throws AkuraAppException - The detailed exception.
     */
    List<Achievement> findStudentAchievement(int studentId, String strDescription, String strActivity, Date year)
            throws AkuraAppException;
    
    /**
     * Retrieve all the available Achievement information for a student using given parameters.
     * 
     * @param studentId specifies the student id, defined by an integer
     * @param strDescription -String
     * @param clubSocietyId - int
     * @param year the student Date.
     * @return List of Achievement
     * @throws AkuraAppException - The detailed exception.
     */
    List<Achievement> findStudentClubAchievement(int studentId, String strDescription, int clubSocietyId, Date year)
            throws AkuraAppException;
    
    /**
     * Returns the academic achievements of the student, current year.
     * 
     * @param studentKey - the key of the student
     * @param currentYearOnly - current year
     * @param acheivementStatus - achievements status, academic status
     * @return - the academic achievements of the student, current year.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<String> viewAchievements(int studentKey, Date currentYearOnly, boolean acheivementStatus)
            throws AkuraAppException;
    
}
