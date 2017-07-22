/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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

package com.virtusa.akura.reporting.service;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.virtusa.akura.api.dto.CRDisciplinaryActionsGeneralTemplate;
import com.virtusa.akura.api.dto.CRExamAbsentTemplate;
import com.virtusa.akura.api.dto.CRExtraCurricularActivitiesTemplate;
import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;
import com.virtusa.akura.api.dto.StudentScholarshipTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.reporting.dao.CommonReportingDao;

/**
 * Common Reporting Service Implementation.
 * 
 * @author Virtusa Corporation
 */

public class CommonReportingServiceImpl implements CommonReportingService {
    
    /** CommonReportingDao attribute for holding CommonReportingDao. */
    private CommonReportingDao commonReportingDao;
    
    /**
     * Adds a public setter to create an instance of type CommonReportingDao.
     * 
     * @param setCommonReportingDao {@link CommonReportingDao}.
     */
    public void setCommonReportingDao(final CommonReportingDao setCommonReportingDao) {
    
        this.commonReportingDao = setCommonReportingDao;
    }
    
    /**
     * Student Class wise Exam Absentee list report.
     * 
     * @param cRExamAbsentTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentClassWiseExamAbsenteeList(CRExamAbsentTemplate cRExamAbsentTemplate)
            throws AkuraAppException {
    
        return this.commonReportingDao.getStudentClassWiseExamAbsenteeList(cRExamAbsentTemplate);
    }
    
    /**
     * Student disciplinary actions reports.
     * 
     * @param classGradeDescription collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentDisciplinaryActionsTrends(String classGradeDescription)
            throws AkuraAppException {
    
        return this.commonReportingDao.getStudentDisciplinaryActionsTrends(classGradeDescription);
    }
    
    /**
     * Student disciplinary actions reports.
     * 
     * @param cRDisciplinaryActionsGeneralTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentDisciplinaryActionsTrends(
            CRDisciplinaryActionsGeneralTemplate cRDisciplinaryActionsGeneralTemplate) throws AkuraAppException {
    
        return this.commonReportingDao.getStudentDisciplinaryActionsTrends(cRDisciplinaryActionsGeneralTemplate);
    }
    
    /**
     * Student Extra Curricular report.
     * 
     * @param cRExtraCurricularActivitiesTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentExtraCurricularActivities(
            CRExtraCurricularActivitiesTemplate cRExtraCurricularActivitiesTemplate) throws AkuraAppException {
    
        return this.commonReportingDao.getStudentExtraCurricularActivities(cRExtraCurricularActivitiesTemplate);
    }
    
    /**
     * Student Performance report.
     * 
     * @param cRStudentPerformanceTemplate collect user input.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentPerformance(CRStudentPerformanceTemplate cRStudentPerformanceTemplate)
            throws AkuraAppException {
    
        return this.commonReportingDao.getStudentPerformance(cRStudentPerformanceTemplate);
    }
    
    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getStudentScholarshipList(StudentScholarshipTemplate studentTemplate)
            throws AkuraAppException {
    
        return this.commonReportingDao.getStudentScholarshipList(studentTemplate);
    }
    
}
