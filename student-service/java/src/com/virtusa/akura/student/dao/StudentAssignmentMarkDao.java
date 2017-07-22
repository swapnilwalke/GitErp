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

import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.StudentAssignmentMark;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * The Interface StudentAssignmentMarkDao.
 */
public interface StudentAssignmentMarkDao extends BaseDao<StudentAssignmentMark> {

    /**
     * Gets the student assignment marks list.
     *
     * @param gradeSubjectId the grade subject id
     * @param assignmentId the assignment id
     * @param year the year
     * @param classGradeId the class grade id
     * @return the student assignment marks list
     * @throws AkuraAppException the akura app exception
     */
    List<Object[]> getStudentAssignmentMarksList(int gradeSubjectId, int assignmentId, int year, int classGradeId)
            throws AkuraAppException;

    /**
     * Checks the existence of the Assignment marks is already assigned for selected assignment.
     *
     * @param assignmentId - the key of the assignment idt.
     * @return - a list of existing Assignment Marks for given assignmet id
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<StudentAssignmentMark> isExistAssignmentMarks(final int assignmentId) throws AkuraAppException;

    /**
     * Gets the student assignment marks by student id.
     *
     * @param studentId the student id
     * @return the student assignment marks by student id
     * @throws AkuraAppException the akura app exception
     */
    List<Object[]> getStudentAssignmentMarksByStudentId(int studentId) throws AkuraAppException;

    /**
     * Gets the student assignment marks by student id for report.
     *
     * @param studentId the student id
     * @return the student assignment marks by student id
     * @throws AkuraAppException the akura app exception
     */
    List<Object[]> getStudentAssignmentMarksByStudentIdForReport(int studentId) throws AkuraAppException;

    /**
     * Gets the student class info id list but not in assignment.
     *
     * @param year - year.
     * @return the student class info id Integer list
     * @throws AkuraAppException the akura app exception
     */
    List<Integer> getStudentClassInfoIdsNotInAssignmentMarks(int year) throws AkuraAppException;
}
