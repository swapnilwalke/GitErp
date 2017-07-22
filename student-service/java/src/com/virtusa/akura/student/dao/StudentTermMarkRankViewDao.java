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
import com.virtusa.akura.api.dto.StudentTermMarksRank;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This class provides persistence layer functionality for the student discipline domain object.
 * 
 * @author Virtusa Corporation
 */

public interface StudentTermMarkRankViewDao extends BaseDao<StudentTermMarksRank> {
    
    /**
     * Get student terms rank object.
     * 
     * @param studentClassInfo student class info id
     * @param termId term id
     * @return StudentTermMarksRank object
     * @throws AkuraAppException the akura app exception
     */
    StudentTermMarksRank getStudentTermMarksRank(int studentClassInfo, int termId) throws AkuraAppException;
    
    /**
     * Returns a list of StudentTermMarksRank for a given classInfo id.
     * 
     * @param studentClassInfoId - the classinfo id for a student.
     * @return - a list of StudentTermMarksRank for a given classInfo id.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<StudentTermMarksRank> getStudentRank(int studentClassInfoId) throws AkuraAppException;
    
    /**
     * Gets the best student average and class average.
     *
     * @param rank the rank
     * @param classGradeId the class grade id
     * @param term the term
     * @param year the year
     * @return the best student average
     * @throws AkuraAppException the akura app exception
     */
    Double getBestStudentAverage(int classGradeId, String term, Date year) throws AkuraAppException;
    
    /**
     * Gets the class average.
     *
     * @param classGradeId the class grade id
     * @param term the term
     * @param year the year
     * @return the class average
     * @throws AkuraAppException the akura app exception
     */
    Object[] getClassAverage(int classGradeId, String term, Date year) throws AkuraAppException;
}
