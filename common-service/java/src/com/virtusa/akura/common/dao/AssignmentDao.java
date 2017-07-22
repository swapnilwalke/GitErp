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

import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Assignment;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the assignment object.
 *
 * @author Virtusa Corporation
 */
public interface AssignmentDao extends BaseDao<Assignment> {

    /**
     * Checks the existence of the Assignment for grade - subject for a given keys of the grade-subject id and
     * the assignment name.
     *
     * @param gradeSubjectId - the key of the grade subject.
     * @param assignmentName - the key of the assignment name.
     * @param year - the key of the year of the submitted date.
     * @param marksType - the key of the the marks type of the assignment.
     * @return - a list of existing Assignment for selected grade-subject
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<Assignment> isExistAssignmentGradeSubject(final int gradeSubjectId, final String assignmentName,
            final int year, final boolean marksType) throws AkuraAppException;

    /**
     * Gets the assignments by grade.
     *
     * @param classGradeId the class grade id
     * @return the assignments by grade
     * @throws AkuraAppException the akura app exception
     */
    List<Object[]> getAssignmentsByGrade(int classGradeId) throws AkuraAppException;

}
