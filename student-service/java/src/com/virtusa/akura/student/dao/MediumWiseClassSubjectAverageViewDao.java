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
import com.virtusa.akura.api.dto.MediumWiseClassSubjectAverageView;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface MediumWiseClassSubjectAverageViewDao extends BaseDao<MediumWiseClassSubjectAverageView> {
    
    /**
     * This method is used to find list of averages of subjects per class.
     * 
     * @param classGradeid class grade id
     * @param year int value of year
     * @param gradeSubjectId grade subject id
     * @param term term description
     * @param languageId represents study medium id
     * @return list of MediumWiseClassSubjectAverageView objects
     * @throws AkuraAppException throws if exception occurs
     */
    MediumWiseClassSubjectAverageView findMediumWiseClassSubjectAverage(int classGradeid,
            int year, int gradeSubjectId, String term, int languageId) throws AkuraAppException;
    
    /**
     * This method is used to find list of study medium id available for class.
     * 
     * @param classGradeid class grade id
     * @param year int value of year
     * @param term term description
     * @return list of study medium id available for class
     * @throws AkuraAppException throws if exception occurs
     */
    List<Integer> findStudyMediumsInClass(int classGradeid, int year, String term) throws AkuraAppException;
}
