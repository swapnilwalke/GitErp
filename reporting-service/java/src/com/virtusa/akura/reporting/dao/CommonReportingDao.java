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

package com.virtusa.akura.reporting.dao;

import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.BaseDomain;
import com.virtusa.akura.api.dto.CRDisciplinaryActionsGeneralTemplate;
import com.virtusa.akura.api.dto.CRExamAbsentTemplate;
import com.virtusa.akura.api.dto.CRExtraCurricularActivitiesTemplate;
import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;
import com.virtusa.akura.api.dto.StudentScholarshipTemplate;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Interface to define DAO methods related to common reporting.
 */

public interface CommonReportingDao extends BaseDao<BaseDomain> {
    
    /**
     * Student Class wise Exam Absentee list report.
     * 
     * @param cRExamAbsentTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    JRBeanCollectionDataSource getStudentClassWiseExamAbsenteeList(CRExamAbsentTemplate cRExamAbsentTemplate)
            throws AkuraAppException;
    
    /**
     * Student disciplinary actions reports.
     * 
     * @param classGradeDescription - string.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    JRBeanCollectionDataSource getStudentDisciplinaryActionsTrends(String classGradeDescription)
            throws AkuraAppException;
    
    /**
     * Student disciplinary actions reports.
     * 
     * @param cRDisciplinaryActionsGeneralTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    JRBeanCollectionDataSource getStudentDisciplinaryActionsTrends(
            CRDisciplinaryActionsGeneralTemplate cRDisciplinaryActionsGeneralTemplate) throws AkuraAppException;
    
    /**
     * Student Extra Curricular report.
     * 
     * @param cRExtraCurricularActivitiesTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    JRBeanCollectionDataSource getStudentExtraCurricularActivities(
            CRExtraCurricularActivitiesTemplate cRExtraCurricularActivitiesTemplate) throws AkuraAppException;
    
    /**
     * Student student performance report.
     * 
     * @param cRStudentPerformanceTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    JRBeanCollectionDataSource getStudentPerformance(CRStudentPerformanceTemplate cRStudentPerformanceTemplate)
            throws AkuraAppException;
    
    /**
     * Student Scholarship report.
     * 
     * @param studentScholarshipTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    JRBeanCollectionDataSource getStudentScholarshipList(StudentScholarshipTemplate studentScholarshipTemplate)
            throws AkuraAppException;


    
}
