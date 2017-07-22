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

package com.virtusa.akura.student.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.util.DateUtil;

/**
 * Class which provides implementation to StudentTermMarkDao.
 */
public class StudentTermMarkDaoImpl extends BaseDaoImpl<StudentTermMark> implements StudentTermMarkDao {
    
    /**
     * Represents the name for named query.
     */
    private static final String FIND_MARKS = "findStuTermMarks";
    
    /** key to hold the named query viewStudentTermMarkByStudentIdYear. */
    private static final String VIEW_STUDENT_TERM_MARK_BY_STUDENT_ID_YEAR = "viewStudentTermMarkByStudentIdYear";
    
    /** key to hold the named query viewStudentTermMarkObj. */
    private static final String VIEW_STUDENT_TERM_MARK_OBJ = "viewStudentTermMarkObj";
    
    /** key to hold log message. */
    private static final String MESSAGE_LOG_FLUSH_MODE = "Session Flush mode is  ";
    
    /** key to hold parameter termMarkIdList. */
    private static final String TERM_MARK_ID_LIST = "termMarkIdList";
    
    /** key to hold the named query getDeletableTermMarks. */
    private static final String GET_DELETABLE_TERM_MARKS = "getDeletableTermMarks";
    
    /** key to hold the named query studenttermmarksofclasssubjects. */
    private static final String STUDENT_TERMMARKS_OF_CLASSSUBJECTS = "studenttermmarksofclasssubjects";
    
    /** key to hold the named query getSelectedSubjectMarksByClass. */
    private static final String GET_SELECTED_SUBJECT_MARKS_BY_CLASS = "getSelectedSubjectMarksByClass";
    
    /** key to hold the named query getSelectedSubjectMarks. */
    private static final String GET_SELECTED_SUBJECT_MARKS = "getSelectedSubjectMarks";
    
    /** key to hold the named query getSelectedSubjectMarks. */
    private static final String TERMMARKS_BY_STUDENTID_YEAR_TERMID = "viewStudentTermMarkByStudentIdYearAndTermId";
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentTermMarkDaoImpl.class);
    
    /** int value to store number of records in batch update. */
    private static final int NUMBER_OF_RECORDS_AT_A_TIME = 30;
    
    /** Represents the query name for the checking the student mark. */
    private static final String IS_EXIST_MARKS = "isExistMark";
    
    /**
     * retrieves a list of student subject marks on search criteria.
     * 
     * @param termId to hold term Id.
     * @return List of subject marks for all students matching search criteria.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @SuppressWarnings("unchecked")
    public List<StudentTermMark> getSelectedSubjectMarks(int termId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_SELECTED_SUBJECT_MARKS, termId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * retrieves a list of student subject marks on search criteria.
     * 
     * @param termId to hold term Id.
     * @param classGradeId to class grade Id.
     * @return List of subject marks for all students matching search criteria.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    @SuppressWarnings("unchecked")
    public List<StudentTermMark> getSelectedSubjectMarksByGrade(int termId, int classGradeId) throws AkuraAppException {
    
        try {
            Calendar currentDate = Calendar.getInstance();
            
            int year = currentDate.get(Calendar.YEAR);
            
            return getHibernateTemplate().findByNamedQuery(GET_SELECTED_SUBJECT_MARKS_BY_CLASS, classGradeId, termId,
                    year);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Method is to return list of StudentTermMark objects of a class grade.
     * 
     * @param classGrade - ClassGrade type object.
     * @return list of StudentTermMark Objects of that ClassGrade.
     * @throws AkuraAppException when exception occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentTermMark> getStudentTermMarkInfoOfClassSubjects(ClassGrade classGrade) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(STUDENT_TERMMARKS_OF_CLASSSUBJECTS,
                    classGrade.getGrade().getGradeId(), classGrade.getClassGradeId());
        } catch (DataAccessException e) {
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Method is to delete list of StudentTermMark objects.
     * 
     * @param deletedTermMarkIdList - StudentTermMarkId s to delete.
     * @return list of StudentTermMark Objects that get deleted.
     * @throws AkuraAppException when exception occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentTermMark> deleteStudentTermMarkInfoOfClassSubjects(List<Integer> deletedTermMarkIdList)
            throws AkuraAppException {
    
        List<StudentTermMark> markList = null;
        
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            markList =
                    getHibernateTemplate().findByNamedQueryAndNamedParam(GET_DELETABLE_TERM_MARKS,
                            new String[] { TERM_MARK_ID_LIST }, new Object[] { deletedTermMarkIdList });
            
            LOG.debug(MESSAGE_LOG_FLUSH_MODE + session.getFlushMode());
            int count = 0;
            for (Iterator<StudentTermMark> itrList = markList.iterator(); itrList.hasNext();) {
                StudentTermMark record = (StudentTermMark) itrList.next();
                session.delete(record);
                
                count++;
                if (count % NUMBER_OF_RECORDS_AT_A_TIME == 0) { // 20, same as
                                                                // the JDBC
                                                                // batch size
                    // flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                
            }
            
            tx.commit();
            return markList;
        } catch (DataAccessException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
    }
    
    /**
     * Method is to get list of StudentTermMark objects that have saved.
     * 
     * @param termMarkList - StudentTermMark that wants to be saved.
     * @return list of StudentTermMark Objects that get saved.
     * @throws AkuraAppException when exception occurs.
     */
    public List<StudentTermMark> getSavedStudentSubjectList(List<StudentTermMark> termMarkList)
            throws AkuraAppException {
    
        List<StudentTermMark> markList = new ArrayList<StudentTermMark>();
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        try {
            LOG.debug(MESSAGE_LOG_FLUSH_MODE + session.getFlushMode());
            
            int count = 0;
            for (Iterator<StudentTermMark> itrList = termMarkList.iterator(); itrList.hasNext();) {
                StudentTermMark record = (StudentTermMark) itrList.next();
                int studenttermmarkID = (Integer) session.save(record);
                record.setStudentTermMarkId(studenttermmarkID);
                markList.add(record);
                count++;
                if (count % NUMBER_OF_RECORDS_AT_A_TIME == 0) { // 20, same as
                                                                // the JDBC
                                                                // batch size
                    // flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                
            }
            
            tx.commit();
            return markList;
            
        } catch (DataAccessException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
    }
    
    /**
     * Method is to get list of StudentTermMark objects that have saved.
     * 
     * @param termMarkList - StudentTermMark that wants to be saved.
     * @throws AkuraAppException when exception occurs.
     */
    public void updateStudentTermMark(List<StudentTermMark> termMarkList) throws AkuraAppException {
    
        Session session = getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        try {
            LOG.debug(MESSAGE_LOG_FLUSH_MODE + session.getFlushMode());
            int count = 0;
            for (Iterator<StudentTermMark> itrList = termMarkList.iterator(); itrList.hasNext();) {
                StudentTermMark record = (StudentTermMark) itrList.next();
                session.update(record);
                count++;
                if (count % NUMBER_OF_RECORDS_AT_A_TIME == 0) { // 20, same as
                                                                // the JDBC
                                                                // batch size
                    // flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
                
            }
            
            tx.commit();
        } catch (DataAccessException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
    }
    
    /**
     * {@inheritDoc}
     */
    public List<Float> getStuTermMarks(int classInfoId, int gradeSubjectId, int termId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(FIND_MARKS, classInfoId, gradeSubjectId, termId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<Integer> isStudentTermMarkExist(int studentClassInfoId, int gradeSubjectId, int termId)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(IS_EXIST_MARKS, studentClassInfoId, gradeSubjectId, termId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<StudentTermMark> getTermMarkByStudentIdAndYear(int studentId, Date year) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(VIEW_STUDENT_TERM_MARK_BY_STUDENT_ID_YEAR, studentId, year);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public void deleteTrmMarks(int studentId, int termId) throws AkuraAppException {
    
        Session session = null;
        session = getHibernateTemplate().getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        
        try {
            
            List<StudentTermMark> termmarks =
                    getHibernateTemplate().findByNamedQuery(TERMMARKS_BY_STUDENTID_YEAR_TERMID, studentId,
                            DateUtil.currentYearOnly(), termId);
            LOG.debug(MESSAGE_LOG_FLUSH_MODE + session.getFlushMode());
            // log.debug("Session Flush mode is  "+session1.getFlushMode());
            int count = 0;
            for (Iterator<StudentTermMark> itrList = termmarks.iterator(); itrList.hasNext();) {
                StudentTermMark record = itrList.next();
                session.delete(record);
                
                count++;
                if (count % NUMBER_OF_RECORDS_AT_A_TIME == 0) {
                    // 20, same as the JDBC batch size
                    // flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }
            tx.commit();
            
        } catch (DataAccessException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_LOADING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }finally {
            if(session!=null && session.isOpen()){
                session.close();
            }
        }
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    @Override
    public List<StudentTermMark> getStudentTermMarkObject(int studentClassInfoId, int gradeSubjectId, int termId)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(VIEW_STUDENT_TERM_MARK_OBJ, studentClassInfoId,
                    gradeSubjectId, termId);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }
}
