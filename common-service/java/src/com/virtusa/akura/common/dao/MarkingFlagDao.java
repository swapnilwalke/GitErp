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

package com.virtusa.akura.common.dao;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.MarkingFlag;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides all the persistence related operations for MarkingFlag.
 * 
 * @author Virtusa Corporation
 */
public interface MarkingFlagDao extends BaseDao<MarkingFlag> {
    
    /**
     * This method returns true if marking is completed for this term. Otherwise returns false.
     * 
     * @param classGradeId class grade id
     * @param termId term id
     * @param year year value
     * @return boolean
     * @throws AkuraAppException - throws if exception occurs
     */
    Boolean isMarkingCompletedForTerm(int classGradeId, int termId, int year) throws AkuraAppException;
    
    /**
     * This method used to find marking flag.
     * 
     * @param classGradeId class grade id.
     * @param termId term id.
     * @param year year.
     * @return marking flag object.
     * @throws AkuraAppException - throws if exception occurs
     */
    MarkingFlag findMarkingFlag(int classGradeId, int termId, Date year) throws AkuraAppException;
    
    /**
     * Checks the marking completion status for the relevant class grade , year and the required status.
     * Completed or not.
     * 
     * @param clsGradeId - the relevant class grade key.
     * @param year - the relevant year
     * @param markCompletionStatus - the marking completion status true/false
     * @return - the list contains the relevant status objects.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Boolean> isMarkingCompleted(final int clsGradeId, final Date year, boolean markCompletionStatus)
            throws AkuraAppException;
    
    /**
     * Finds the marking completion object for the given grade key, term and the relevant year.
     * 
     * @param gradeId - the grade key
     * @param termId - the term
     * @param currentYear - the relevant year
     * @return - the status of the marking completion
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Boolean> findMarkingStatusForGrade(int gradeId, int termId, Date currentYear) throws AkuraAppException;

    /**
     * Retrieves the marking status for the class grade key and the year.
     * 
     * @param clsGradeId - the class grade key.
     * @param currentYear - the relevant year
     * @return - the status of the marking completion for the relevant class grade key and the year.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Boolean> isExistMarkingCompleted(int clsGradeId, Date currentYear)throws AkuraAppException;
}
