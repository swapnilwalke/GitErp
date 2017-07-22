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
import com.virtusa.akura.api.dto.StudentSport;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface StudentSportDao extends BaseDao<StudentSport> {
    
    /**
     * retrieving list of StudentSport for a given studentId and given year.
     * 
     * @param studentId the studentId to get Sports detail.
     * @param year of the StudentSport activity.
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentSport> getSportsListForStudent(int studentId, Date year) throws AkuraAppException;
    
    /**
     * retrieving list of StudentSport for a given studentId,sportsCategoryId and given year.
     * 
     * @param studentId the studentId to get Sports detail.
     * @param sportsCategoryId the sportsCategory Id.
     * @param year of the StudentSport activity.
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentSport> findStudentSport(int studentId, int sportsCategoryId, Date year) throws AkuraAppException;
    
    /**
     * get the list of student sports for given sportCategoryId and year.
     * 
     * @param sportCategoryId - integer
     * @param year - year
     * @return List of Student Sports objects
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentSport> getStudentListforSportsCategory(int sportCategoryId, Date year) throws AkuraAppException;
    
    /**
     * retrieving list of StudentSport for a given sportsCategoryId and given year.
     * 
     * @param sportsCategoryId the sportsCategory Id.
     * @param year of the StudentSport activity.
     * @return List {@link StudentSport}
     * @throws AkuraAppException - The detailed exception.
     */
    List<StudentSport> getStudentSportBySportCategoryId(int sportsCategoryId, Date year) throws AkuraAppException;
    
    /**
     * Get the list of StudentSports in the sports category where student state is not current.
     * 
     * @param sportsCategoryId - the sports category.
     * @param year - year of interest.
     * @param date - date of the event.
     * @return list of StudentSports.
     * @throws AkuraAppException - throws detailed exception when fails.
     */
    List<StudentSport> getNonCurrentStudentListforSportsCategory(int sportsCategoryId, Date year, Date date)
            throws AkuraAppException;
}
