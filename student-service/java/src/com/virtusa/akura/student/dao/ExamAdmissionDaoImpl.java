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

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ExamAdmission;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * This class provides persistence layer functionality for the Exam Admission object.
 *
 * @author Virtusa Corporation
 */
public class ExamAdmissionDaoImpl extends BaseDaoImpl<ExamAdmission> implements ExamAdmissionDao {

    /** Logger to log the exceptions. */
    private static final Logger LOG = Logger.getLogger(ExamAdmissionDaoImpl.class);

    /** Represents the query name for finding the Exam Admission by the admission. */
    private static final String FIND_BY_EXAM_ADMISSION = "findByExamAdmission";

    /** Represents the query name for finding the Exam Admission by the admission. */
    private static final String FIND_BY_ADMISSION = "findByAdmission";

    /** Represents the query name for retrieving the exam admission for a given key of the student. */
    private static final String FIND_EXAM_ADMISSION_BY_STUDENT_ID = "findExamAdmissionByStudentId";

    /** {@inheritDoc} */
    public List<ExamAdmission> findExamAdmissionByAdmission(final String admissionNo, final int year, final int examId)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_BY_ADMISSION, admissionNo, DateUtil.getDate(year),
                    examId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /** {@inheritDoc} */
    public List<ExamAdmission> findExamAdmissionByStudentId(String admission, int year, int studentId, int examId)
            throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_EXAM_ADMISSION_BY_STUDENT_ID, admission,
                    DateUtil.getDate(year), studentId, examId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /**
     * Get the exam admission by the exam admission no.
     *
     * @param examAdmissionNo - the admission of the exam admission
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     * @return examAdmissionNo
     */
    public String getExamAdmissionNo(final String examAdmissionNo) throws AkuraAppException {

        String tempExamAdmissionNo = "";
        try {

            List<String> checkList = getHibernateTemplate().findByNamedQuery(FIND_BY_EXAM_ADMISSION, examAdmissionNo);

            if (checkList.size() > 0) {
                tempExamAdmissionNo = checkList.get(0).toString();
            }

        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }

        return tempExamAdmissionNo;
    }
}
