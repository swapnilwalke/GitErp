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
import com.virtusa.akura.api.dto.StudentSubTermMark;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */

public interface StudentSubTermMarkDao extends BaseDao<StudentSubTermMark> {

    /**
     * Method is to delete list of StudentSubTermMark objects.
     *
     * @param deletedTermMarks - TermMark objects that already deleted.
     * @throws AkuraAppException when exception occurs.
     */
    void deleteSubTrmMarks(List<StudentTermMark> deletedTermMarks) throws AkuraAppException;

    /**
     * Method is to save list of StudentSubTermMark objects.
     *
     * @param changedTermMarkList - SubTermMark objects that needs to save.
     * @throws AkuraAppException when exception occurs.
     */
    void saveAll(List<StudentSubTermMark> changedTermMarkList)throws AkuraAppException;

    /**
     * Method is to view list of StudentSubTermMark objects given by student term mark id.
     *
     * @param termMarkId - Student term mark id specified by an integer type variable
     * @throws AkuraAppException when exception occurs
     * @return list of StudentSubTermMark objects
     */
    List<StudentSubTermMark> viewSubTermInfoByTermMarkId(int termMarkId) throws AkuraAppException;

    /**
     * Method is to get sub term mark objects by proving a term mark id list.
     *
     * @param termMarkIdList - studentTermMarkID list .
     * @return List of StudentSubTermMark- studentSubTermMark list returns.
     * @throws AkuraAppException when exception occurs.
     */
    List<StudentSubTermMark> getSelectedSubTermMarksByGrade(List<Integer> termMarkIdList)throws AkuraAppException;;

    /**
     * Method is to update StudentSubTermMark List.
     *
     * @param studentSubTermMarkList - studentSubTermMark list want to update.
     * @throws AkuraAppException when exception occurs.
     */
    void updateStudentSubTermMark(List<StudentSubTermMark> studentSubTermMarkList)throws AkuraAppException;

    /**
     * Returns the id of the subTerms relevant to a given id of a studentTermMark.
     *
     * @param studentTermMarkId - id of a studentTermMark
     * @return - id of the subTerms
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    List<Integer> getSubTerms(int studentTermMarkId)throws AkuraAppException;

    /**
     * Returns a list of StudentSubTermMarks for a given termId and a sub term.
     *
     * @param subTermId - id of a subTerm
     * @param termId - id of a relevant studentTermMarks
     * @return - a list of StudentSubTermMarks
     * @throws AkuraAppException - The exception details that occurred when processing
     */
	List<StudentSubTermMark> getSubTermMarks(int subTermId, int termId)
			throws AkuraAppException;

}
