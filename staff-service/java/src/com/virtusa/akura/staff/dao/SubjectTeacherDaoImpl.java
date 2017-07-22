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

package com.virtusa.akura.staff.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.SchoolClass;
import com.virtusa.akura.api.dto.SubjectTeacher;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the SubjectTeacher object.
 * 
 * @author Virtusa Corporation
 */
public class SubjectTeacherDaoImpl extends BaseDaoImpl<SubjectTeacher> implements SubjectTeacherDao {
    
    /** query name for markAsDeleteAllSubjectTeacherByStaffId. */
    private static final String MARK_AS_DELETED_ALL_BY_STAFF_ID = "markAsDeleteAllSubjectTeacherByStaffId";
    
    /** query name for removeAsDeleteAllSubjectTeacherByStaffId. */
    private static final String REMOVE_AS_DELETE_ALL_SUBJECT_TEACHER_BY_STAFF_ID =
            "removeAsDeleteAllSubjectTeacherByStaffId";
    
    /** query name for getSubjectTeacherListByStaffId. */
    private static final String GET_SUBJECT_TEACHER_LIST_BY_STAFF_ID = "getSubjectTeacherListByStaffId";
    
    /** query name for getting subject Teachers for given class grade and year. */
    private static final String GET_SUBJECT_TEACHERS_BY_CLASS = "getSubjectTeachersByClass";
    
    /** The constant for String "findsubjectTeachers". */
    private static final String FINDSUBJECT_TEACHERS = "findsubjectTeachers";
    
    /**
     * key to hold the named query deleteSubjectTeacherRocordsByDepartureDate.
     */
    private static final String DELETE_SUBJECT_TEACHER_ROCORDS_BY_DEPARTURE_DATE =
            "deletedSubjectTeacherRecordsByDepartureDate";
    
    /** The constant for String "schoolclassList". */
    private static final String SCHOOLCLASS_LIST = "schoolclassList";
    
    /** The constant for String "subjectTeacherList". */
    private static final String SUBJECT_TEACHER_LIST = "subjectTeacherList";
    
    /** The constant for String "getSubjectTeacherList". */
    private static final String GET_SUBJECT_TEACHER_LIST = "getSubjectTeacherList";
    
    /** The constant for String "%". */
    private static final String MODULO_STRING = "%";
    
    /**
     * String attribute for subject teacher info.
     */
    private static final String ERROR_SUBJECT_TEACHER_INFO = "Error while retrieving subject teacher info ---> ";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(SubjectTeacherDaoImpl.class);
    
    /**
     * Service method is to get list of subject teachers by giving last name and registration number.
     * 
     * @param lname - last name.
     * @param regNo - registrationNo.
     * @return list of subject teachers.
     * @throws AkuraAppException - The exception details that occurred when searching SubjectTeacher for a
     *         given criteria.
     */
    @SuppressWarnings(AkuraConstant.UNCHECKED)
    public List<SubjectTeacher> getSubjectTeacherList(String lname, String regNo) throws AkuraAppException {

        List<SubjectTeacher> subjectTeacher = null;
        lname = MODULO_STRING + lname + MODULO_STRING;
        
        try {
            
            subjectTeacher =
                    getHibernateTemplate().findByNamedQuery(GET_SUBJECT_TEACHER_LIST, lname, lname, regNo, regNo);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        return subjectTeacher;
    }
    
    /**
     * Service to delete a SubjectTeacher object.
     * 
     * @param staffId staff id.
     * @param gradeSubjectId grade subject Id.
     * @param year check year.
     * @throws AkuraAppException - The exception details that occurred when deleting SubjectTeacher with given
     *         criteria.
     */
    @SuppressWarnings(AkuraConstant.UNCHECKED)
    public void deleteSubTeacher(int staffId, int gradeSubjectId, String year) throws AkuraAppException {

        List<SubjectTeacher> teacherList = null;
        
        try {
            teacherList = getHibernateTemplate().findByNamedQuery(SUBJECT_TEACHER_LIST, staffId, gradeSubjectId, year);
            
            getHibernateTemplate().deleteAll(teacherList);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * Service method is to get list of classes for a particular staff for a particular grade subject.
     * 
     * @param staffId - staff id.
     * @param gradeSubjectId - grade subject ID.
     * @param year assigned year.
     * @return list of classes for a particular staff for a particular grade subject.
     * @throws AkuraAppException - The exception details that occurred when retrieving a Class list.
     */
    @SuppressWarnings(AkuraConstant.UNCHECKED)
    public List<Integer> getClassList(int staffId, int gradeSubjectId, String year) throws AkuraAppException {

        List<Integer> classList = null;
        
        try {
            classList = getHibernateTemplate().findByNamedQuery(SCHOOLCLASS_LIST, staffId, gradeSubjectId, year);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        return classList;
        
    }
    
    /**
     * Service method is to delete Subject Teacher.
     * 
     * @param subTeacher - subject Teacher.
     * @throws AkuraAppException - The exception details that occurred when deleting SubjectTeacher.
     */
    @SuppressWarnings(AkuraConstant.UNCHECKED)
    public void deleteSubTeacher(SubjectTeacher subTeacher) throws AkuraAppException {

        List<SubjectTeacher> teacherList = null;
        
        try {
            teacherList =
                    getHibernateTemplate().findByNamedQuery(FINDSUBJECT_TEACHERS, subTeacher.getStaff(),
                            subTeacher.getGradeSubject(), subTeacher.getSchoolClass());
            
            getHibernateTemplate().deleteAll(teacherList);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(AkuraConstant.UNCHECKED)
    public List<SubjectTeacher> getSubjectTeachersList(int staffId, int gradeSubjectId, String year)
            throws AkuraAppException {

        try {
            
            return getHibernateTemplate().findByNamedQuery(SUBJECT_TEACHER_LIST, staffId, gradeSubjectId, year);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(AkuraConstant.UNCHECKED)
    public List<SubjectTeacher> getSubjectTeachers(ClassGrade classGrade, int year) throws AkuraAppException {

        try {
            return (List<SubjectTeacher>) getHibernateTemplate().findByNamedQuery(GET_SUBJECT_TEACHERS_BY_CLASS,
                    classGrade.getSchoolClass(), classGrade.getGrade(), String.valueOf(year));
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * Get Subjects taught at present by staff id and current year.
     * 
     * @param staffId staff id
     * @param year current year
     * @return list of subjects
     * @throws AkuraAppException AkuraAppException
     */
    @SuppressWarnings(AkuraConstant.UNCHECKED)
    public List<String> getCurrentSubjectsByStaffId(int staffId, String year) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_SUBJECT_TEACHER_LIST_BY_STAFF_ID, staffId, year);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings(AkuraConstant.UNCHECKED)
    public List<SchoolClass> getSchooClassObjectList(int staffId, int gradeSubjectId, String year)
            throws AkuraAppException {

        try {
            return (List<SchoolClass>) getHibernateTemplate().findByNamedQuery("schoolclassObjectList", staffId,
                    gradeSubjectId, year);
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void markAsDeletedAllSubjectTeacherRecords(int staffId) throws AkuraAppException {

        Session session = null;
        try {
            
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(MARK_AS_DELETED_ALL_BY_STAFF_ID);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void deletedSubjectTeacherRecordsByDepartureDate(int staffId, Date departureDate) throws AkuraAppException {

        Session session = null;
        try {
            
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(DELETE_SUBJECT_TEACHER_ROCORDS_BY_DEPARTURE_DATE);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId, departureDate);
        } catch (DataAccessException e) {
            LOG.error(ERROR_SUBJECT_TEACHER_INFO + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public void removeAsDeletedAllSubjectTeacherRecords(int staffId) throws AkuraAppException {

        Session session = null;
        try {
            
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(REMOVE_AS_DELETE_ALL_SUBJECT_TEACHER_BY_STAFF_ID);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
        
    }
}
