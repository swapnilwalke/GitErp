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

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.BaseDomain;
import com.virtusa.akura.api.dto.CRDisciplinaryActionsGeneralTemplate;
import com.virtusa.akura.api.dto.CRExamAbsentTemplate;
import com.virtusa.akura.api.dto.CRExtraCurricularActivitiesTemplate;
import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;
import com.virtusa.akura.api.dto.ReportAbsenteeList;
import com.virtusa.akura.api.dto.ReportStudentClubSociety;
import com.virtusa.akura.api.dto.ReportStudentDiscipline;
import com.virtusa.akura.api.dto.StudentScholarshipTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Class to implement DAO methods specified in CommonReportingDao Interface.
 */

public class CommonReportingDaoImpl extends BaseDaoImpl<BaseDomain> implements CommonReportingDao {
    
    /** attribute for String boolean value for scholarship completed. */
    private static final String SCHOLARSHIP = "scholarship";
    
    /** attribute for holding boolean value for marking completed. */
    private static final String MARKING_COMPLETED = "markingCompleted";
    
    /** attribute for holding boolean value. */
    private static final boolean BOOLEAN_VALUE2 = Boolean.TRUE;
    
    /** attribute for holding boolean value. */
    private static final boolean BOOLEAN_VALUE1 = Boolean.FALSE;
    
    /** attribute for holding string marks. */
    private static final String MARKS = "marks";
    
    /** attribute for holding string absent. */
    private static final String ABSENT = "absent";
    
    /** attribute for holding string classDescription. */
    private static final String CLASS_DESCRIPTION = "classDescription";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(CommonReportingDaoImpl.class);
    
    /**
     * First item in drop down list.
     */
    private static final String LIST_FIRST_ITEM = "Please Select";
    
    /**
     * GRADE_DESCRIPTION for static variable , to be for description of the grade.
     */
    private static final String GRADE_DESCRIPTION = "gradeDescription";
    
    /**
     * Student Class wise Exam Absentee list report.
     * 
     * @param cRExamAbsentTemplate user input for report generation.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentClassWiseExamAbsenteeList(CRExamAbsentTemplate cRExamAbsentTemplate)
            throws AkuraAppException {
    
        // criteria based on term, class(grade and name year 7 -A ,
        // exam(physics, chemistry))
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportAbsenteeList.class);
        
        if (cRExamAbsentTemplate.getGradeDescription().trim().length() > 0) {
            detachedCriteria.add(Restrictions.eq(GRADE_DESCRIPTION, cRExamAbsentTemplate.getGradeDescription()));
        }
        if ((cRExamAbsentTemplate.getClassDescription().trim().length() > 0)) {
            detachedCriteria.add(Restrictions.eq(CLASS_DESCRIPTION, cRExamAbsentTemplate.getClassDescription()));
        }
        detachedCriteria.add(Restrictions.eq(ABSENT, BOOLEAN_VALUE2));
        detachedCriteria.add(Restrictions.eq(MARKING_COMPLETED, BOOLEAN_VALUE2));
        
        try {
            return findResults(detachedCriteria);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Student disciplinary actions reports.
     * 
     * @param classGradeDescription collect user input for report generation.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentDisciplinaryActionsTrends(String classGradeDescription)
            throws AkuraAppException {
    
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportStudentDiscipline.class);
        
        if (classGradeDescription != null) {
            detachedCriteria.add(Restrictions.eq("classDescription", classGradeDescription));
        }
        
        try {
            return findResults(detachedCriteria);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * Student disciplinary actions reports.
     * 
     * @param cRDisciplinaryActionsTemplate collect user input for report generation.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentDisciplinaryActionsTrends(
            CRDisciplinaryActionsGeneralTemplate cRDisciplinaryActionsTemplate) throws AkuraAppException {
    
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportStudentDiscipline.class);
        
        if (cRDisciplinaryActionsTemplate.getAdmissionNo() != null
                && cRDisciplinaryActionsTemplate.getAdmissionNo().length() > 0) {
            detachedCriteria.add(Restrictions.eq("admissionNo", cRDisciplinaryActionsTemplate.getAdmissionNo()));
        }
        if (cRDisciplinaryActionsTemplate.getFullName() != null
                && cRDisciplinaryActionsTemplate.getFullName().length() > 0) {
            detachedCriteria.add(Restrictions.eq("fullName", cRDisciplinaryActionsTemplate.getFullName()));
        }
        if (cRDisciplinaryActionsTemplate.getWarningLevel() != null
                && cRDisciplinaryActionsTemplate.getWarningLevel().length() > 0
                && !cRDisciplinaryActionsTemplate.getWarningLevel().contains(LIST_FIRST_ITEM)) {
            detachedCriteria.add(Restrictions.eq("warningLevel", cRDisciplinaryActionsTemplate.getWarningLevel()));
        }
        if (cRDisciplinaryActionsTemplate.getGradeDescription() != null
                && cRDisciplinaryActionsTemplate.getGradeDescription().length() > 0
                && !cRDisciplinaryActionsTemplate.getGradeDescription().contains(LIST_FIRST_ITEM)) {
            detachedCriteria
                    .add(Restrictions.eq(GRADE_DESCRIPTION, cRDisciplinaryActionsTemplate.getGradeDescription()));
        }
        if (cRDisciplinaryActionsTemplate.getClassDescription() != null
                && cRDisciplinaryActionsTemplate.getClassDescription().length() > 0
                && !cRDisciplinaryActionsTemplate.getClassDescription().contains(LIST_FIRST_ITEM)) {
            detachedCriteria
                    .add(Restrictions.eq(CLASS_DESCRIPTION, cRDisciplinaryActionsTemplate.getClassDescription()));
        }
        if (cRDisciplinaryActionsTemplate.getDisciplineCategory() != null
                && cRDisciplinaryActionsTemplate.getDisciplineCategory().length() > 0) {
            detachedCriteria.add(Restrictions.eq("disciplineCategory",
                    cRDisciplinaryActionsTemplate.getDisciplineCategory()));
        }
        
        try {
            return findResults(detachedCriteria);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * Finds the results using the given detachedCriteria.
     * 
     * @param detachedCriteria detachedCriteria
     * @return the results.
     */
    private JRBeanCollectionDataSource findResults(final DetachedCriteria detachedCriteria) {
    
        return new JRBeanCollectionDataSource(getHibernateTemplate().findByCriteria(detachedCriteria));
    }
    
    /**
     * Student Extra Curricular report.
     * 
     * @param cRExtraCurricularActivitiesTemplate collect user input for report generation.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentExtraCurricularActivities(
            CRExtraCurricularActivitiesTemplate cRExtraCurricularActivitiesTemplate) throws AkuraAppException {
    
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportStudentClubSociety.class);
        
        if (cRExtraCurricularActivitiesTemplate.getAdmissionNo() != null
                && cRExtraCurricularActivitiesTemplate.getAdmissionNo().length() > 0) {
            detachedCriteria.add(Restrictions.eq("admissionNo", cRExtraCurricularActivitiesTemplate.getAdmissionNo()));
            
        }
        if (cRExtraCurricularActivitiesTemplate.getFullName() != null
                && cRExtraCurricularActivitiesTemplate.getFullName().length() > 0) {
            detachedCriteria.add(Restrictions.eq("fullName", cRExtraCurricularActivitiesTemplate.getFullName()));
            
        }
        
        try {
            return findResults(detachedCriteria);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * Student student performance report.
     * 
     * @param cRStudentPerformanceTemplate collect user input for report generation.
     * @return {@link JRBeanCollectionDataSource}.
     * @throws AkuraAppException - The detailed exception.
     */
    public JRBeanCollectionDataSource getStudentPerformance(CRStudentPerformanceTemplate cRStudentPerformanceTemplate)
            throws AkuraAppException {
    
        LOG.info("performance report Dao set criteria = " + cRStudentPerformanceTemplate.getGradeDescription()
                + cRStudentPerformanceTemplate.getClassDescription() + cRStudentPerformanceTemplate.getLessThan());
        
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ReportAbsenteeList.class);
        
        if (cRStudentPerformanceTemplate.getGradeDescription() != null
                && cRStudentPerformanceTemplate.getGradeDescription().trim().length() > 0
                && !cRStudentPerformanceTemplate.getGradeDescription().contains(LIST_FIRST_ITEM)) {
            detachedCriteria
                    .add(Restrictions.eq(GRADE_DESCRIPTION, cRStudentPerformanceTemplate.getGradeDescription()));
            LOG.info("set Grade = " + cRStudentPerformanceTemplate.getGradeDescription());
        }
        if (cRStudentPerformanceTemplate.getClassDescription() != null
                && cRStudentPerformanceTemplate.getClassDescription().trim().length() > 0
                && !cRStudentPerformanceTemplate.getClassDescription().contains(LIST_FIRST_ITEM)) {
            detachedCriteria
                    .add(Restrictions.eq(CLASS_DESCRIPTION, cRStudentPerformanceTemplate.getClassDescription()));
            LOG.info("set class = " + cRStudentPerformanceTemplate.getClassDescription());
        }
        if (cRStudentPerformanceTemplate.getGradeDescriptionGreater() != null
                && cRStudentPerformanceTemplate.getGradeDescriptionGreater().trim().length() > 0
                && !cRStudentPerformanceTemplate.getGradeDescriptionGreater().contains(LIST_FIRST_ITEM)) {
            detachedCriteria.add(Restrictions.eq(GRADE_DESCRIPTION,
                    cRStudentPerformanceTemplate.getGradeDescriptionGreater()));
            LOG.info("set Grade = " + cRStudentPerformanceTemplate.getGradeDescriptionGreater());
        }
        if (cRStudentPerformanceTemplate.getClassDescriptionGreater() != null
                && cRStudentPerformanceTemplate.getClassDescriptionGreater().trim().length() > 0
                && !cRStudentPerformanceTemplate.getClassDescriptionGreater().contains(LIST_FIRST_ITEM)) {
            detachedCriteria.add(Restrictions.eq(CLASS_DESCRIPTION,
                    cRStudentPerformanceTemplate.getClassDescriptionGreater()));
            LOG.info("set class = " + cRStudentPerformanceTemplate.getClassDescriptionGreater());
        }
        if (cRStudentPerformanceTemplate.getGradeDescriptionLess() != null
                && cRStudentPerformanceTemplate.getGradeDescriptionLess().trim().length() > 0
                && !cRStudentPerformanceTemplate.getGradeDescriptionLess().contains(LIST_FIRST_ITEM)) {
            detachedCriteria.add(Restrictions.eq(GRADE_DESCRIPTION,
                    cRStudentPerformanceTemplate.getGradeDescriptionLess()));
            LOG.info("set Grade = " + cRStudentPerformanceTemplate.getGradeDescriptionLess());
        }
        if (cRStudentPerformanceTemplate.getClassDescriptionLess() != null
                && cRStudentPerformanceTemplate.getClassDescriptionLess().trim().length() > 0
                && !cRStudentPerformanceTemplate.getClassDescriptionLess().contains(LIST_FIRST_ITEM)) {
            detachedCriteria.add(Restrictions.eq(CLASS_DESCRIPTION,
                    cRStudentPerformanceTemplate.getClassDescriptionLess()));
            LOG.info("set class = " + cRStudentPerformanceTemplate.getClassDescriptionLess());
        }
        // less than
        if (cRStudentPerformanceTemplate.getLessThan() > 0) {
            detachedCriteria.add(Restrictions.lt(MARKS, cRStudentPerformanceTemplate.getLessThan()));
            LOG.info("set less than = " + cRStudentPerformanceTemplate.getLessThan());
        }
        // greater than
        if (cRStudentPerformanceTemplate.getGreaterThan() > 0) {
            detachedCriteria.add(Restrictions.gt(MARKS, cRStudentPerformanceTemplate.getGreaterThan()));
            LOG.info("set grater  than = " + cRStudentPerformanceTemplate.getGreaterThan());
        }
        // in between
        if (cRStudentPerformanceTemplate.getInBetweenLessValue() > 0
                && cRStudentPerformanceTemplate.getInBetweenGreaterValue() > 0) {
            detachedCriteria.add(Restrictions.gt(MARKS, cRStudentPerformanceTemplate.getInBetweenLessValue()));
            detachedCriteria.add(Restrictions.lt(MARKS, cRStudentPerformanceTemplate.getInBetweenGreaterValue()));
            LOG.info("set In between than = " + cRStudentPerformanceTemplate.getInBetweenLessValue() + " to  "
                    + cRStudentPerformanceTemplate.getInBetweenGreaterValue());
            
        }
        
        detachedCriteria.add(Restrictions.eq(ABSENT, BOOLEAN_VALUE1));
        
        LOG.info("detached  criteria = " + detachedCriteria.toString());
        
        try {
            return findResults(detachedCriteria);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public JRBeanCollectionDataSource getStudentScholarshipList(StudentScholarshipTemplate studentScholarshipTemplate)
            throws AkuraAppException {
    
        final DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StudentScholarshipTemplate.class);
        
        detachedCriteria.add(Restrictions.eq(SCHOLARSHIP, studentScholarshipTemplate.getScholarship()));
        
        try {
            return findResults(detachedCriteria);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }




    
   
    }
    


