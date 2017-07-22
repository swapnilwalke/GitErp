
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
import com.virtusa.akura.api.dto.ExamResults;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * The Exam Results Dao class.
 *  
 * @author Virtusa Corporation
 *
 */

public interface ExamResultsDao extends BaseDao<ExamResults>{

    /**
     * Get Exam Results by Exam Admission No.
     *
     * @param examAdmissionNo - examAdmissionNo
     * @param examId - examId
     * @return list of exam results objects
     * @throws AkuraAppException - AkuraAppException
     */
    List<ExamResults> getExamResultsByExamAdmissionNo(String examAdmissionNo, int examId) throws AkuraAppException ;

    /**
     * get all examResults for a student.
     *
     * @param studentId student id
     * @return list of exam results objects
     * @throws AkuraAppException AkuraAppException
     */
    List<ExamResults> getExamResultsByStudentId(int studentId) throws AkuraAppException ;

    /**
     * Retrieves a list of exam marks results for a given key of the student and the date.
     *
     * @param studentId - the key of the student
     * @param date - the date
     * @return - a list of exam marks results
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<ExamResults> getExamResults(final int studentId, final Date date)throws AkuraAppException;
}
