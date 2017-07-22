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
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;
/**
 * This interface provides persistence layer functionality for the
 * subject domain object.
 *
 * @author Virtusa Corporation
 */
public interface SubjectDao extends BaseDao<Subject> {

    /**
     * Returns all the subjects that starts with the given information.
     *
     * @param searchDescription - name of the Subject that needs to be searched.
     * @return - the relevant Subject object.
     * @throws AkuraAppException - The exception details that occurred
     * when searching Subject instance/s.
     */
    List<Subject> searchSubject(String searchDescription) throws AkuraAppException;

    /**
     * Gets the available subjects list.
     *
     * @param selectedSubjectsIdList the selected subjects id list
     * @return the available subjects list
     * @throws AkuraAppException the akura app exception
     */
    List<Subject> getAvailableSubjectsList(List<Integer> selectedSubjectsIdList) throws AkuraAppException;

    /**
     * Retrieves the subjects list by the exam.
     *
     * @param selectedExamId - the key of the exam
     * @return - a list of subjects for a given exam
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<?> getSubjectListByExam(final int selectedExamId)throws AkuraAppException;

    /**
     * Retrieves the optional subject list for a given exam key.
     *
     * @param examId - the key of the exam
     * @return - a list of optional subjects for a exam
     * @throws AkuraAppException - The exception details that occurred when retrieving.
     */
    List<?> getOptionalSubjectList(final int examId)throws AkuraAppException;
}