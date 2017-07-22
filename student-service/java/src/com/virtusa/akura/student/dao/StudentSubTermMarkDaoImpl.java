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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentSubTermMark;
import com.virtusa.akura.api.dto.StudentTermMark;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * @author Virtusa Corporation
 */

public class StudentSubTermMarkDaoImpl extends BaseDaoImpl<StudentSubTermMark> implements StudentSubTermMarkDao {
    
    /** key to hold the named query getSubTermMarks. */
    private static final String GET_SUB_TERM_MARKS = "getSubTermMarks";
    
    /** key to hold the named query getSubTermMarksId. */
    private static final String GET_SUB_TERM_MARKS_ID = "getSubTermMarksId";
    
    /** key to hold the string message to log. */
    private static final String MESSAGE_LOG_FLUSH_MODE_IS = "Session Flush mode is  ";
    
    /** key to hold the named query viewSubTermInfoByTermMarkId. */
    private static final String VIEW_SUB_TERM_INFO_BY_TERM_MARK_ID = "viewSubTermInfoByTermMarkId";
    
    /** key to hold the named parameter termmarkidlist. */
    private static final String TERMMARKIDLIST = "termmarkidlist";
    
    /** key to hold the named query selectFromStudentSubTermMark. */
    private static final String SELECT_FROM_STUDENT_SUB_TERM_MARK = "selectFromStudentSubTermMark";
    
    /** int value to store number of records in batch update. */
    private static final int NUMBER_OF_RECORDS_AT_A_TIME = 30;
    
    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StudentSubTermMarkDaoImpl.class);
    
    /**
     * Method is to delete list of StudentSubTermMark objects.
     * 
     * @param deletedTermMarks - TermMark objects that already deleted.
     * @throws AkuraAppException when exception occurs.
     */
    @SuppressWarnings("unchecked")
    public void deleteSubTrmMarks(List<StudentTermMark> deletedTermMarks) throws AkuraAppException {
    
        List<Integer> termmarkIds = new ArrayList<Integer>();
        
        Session session = null;
        
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            
            for (StudentTermMark temmark : deletedTermMarks) {
                termmarkIds.add(temmark.getStudentTermMarkId());
            }
            List<StudentSubTermMark> subtermmarks =
                    getHibernateTemplate().findByNamedQueryAndNamedParam(SELECT_FROM_STUDENT_SUB_TERM_MARK,
                            new String[] { TERMMARKIDLIST }, new Object[] { termmarkIds });
            
            Transaction tx = session.beginTransaction();
            // log.debug("Session Flush mode is  "+session1.getFlushMode());
            int count = 0;
            for (Iterator<StudentSubTermMark> itrList = subtermmarks.iterator(); itrList.hasNext();) {
                StudentSubTermMark record = itrList.next();
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
            
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }
    
    /**
     * Method is to save list of StudentSubTermMark objects.
     * 
     * @param changedTermMarkList - TermMark objects that need to save.
     * @throws AkuraAppException when exception occurs.
     */
    
    public void saveAll(List<StudentSubTermMark> changedTermMarkList) throws AkuraAppException {
    
        Session session = null;
        try {
            
            session = getHibernateTemplate().getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            // log.debug("Session Flush mode is  "+session1.getFlushMode());
            int count = 0;
            for (Iterator<StudentSubTermMark> itrList = changedTermMarkList.iterator(); itrList.hasNext();) {
                StudentSubTermMark record = itrList.next();
                session.save(record);
                
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
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
        
    }
    
    // -------- Student sub term mark pop up screen related methods -------//
    /**
     * Method is to view list of StudentSubTermMark objects given by student term mark id.
     * 
     * @param termMarkId - Student term mark id specified by an integer type variable
     * @throws AkuraAppException when exception occurs
     * @return list of StudentSubTermMark objects
     */
    @SuppressWarnings("unchecked")
    public List<StudentSubTermMark> viewSubTermInfoByTermMarkId(int termMarkId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(VIEW_SUB_TERM_INFO_BY_TERM_MARK_ID, termMarkId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    // ---- End of student sub term mark pop up screen related methods ----//
    
    /**
     * Method is to get sub term mark objects by proving a term mark id list.
     * 
     * @param termMarkIdList - studentTermMarkID list .
     * @return List of StudentSubTermMark- studentSubTermMark list returns.
     * @throws AkuraAppException when exception occurs.
     */
    @SuppressWarnings("unchecked")
    public List<StudentSubTermMark> getSelectedSubTermMarksByGrade(List<Integer> termMarkIdList)
            throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQueryAndNamedParam(SELECT_FROM_STUDENT_SUB_TERM_MARK,
                    new String[] { TERMMARKIDLIST }, new Object[] { termMarkIdList });
            
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Method is to update StudentSubTermMark List.
     * 
     * @param studentSubTermMarkList - studentSubTermMark list want to update.
     * @throws AkuraAppException when exception occurs.
     */
    @SuppressWarnings("rawtypes")
    public void updateStudentSubTermMark(List<StudentSubTermMark> studentSubTermMarkList) throws AkuraAppException {
    
        Session session = null;
        try {
            
            session = getHibernateTemplate().getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            LOG.debug(MESSAGE_LOG_FLUSH_MODE_IS + session.getFlushMode());
            int count = 0;
            for (Iterator itrList = studentSubTermMarkList.iterator(); itrList.hasNext();) {
                StudentSubTermMark record = (StudentSubTermMark) itrList.next();
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
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }
    
    /**
     * Returns the id of the subTerms relevant to a given id of a studentTermMark.
     * 
     * @param studentTermMarkId - id of a studentTermMark
     * @return - id of the subTerms
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getSubTerms(int studentTermMarkId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_SUB_TERM_MARKS_ID, studentTermMarkId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Returns a list of StudentSubTermMarks for a given termId and a sub term.
     * 
     * @param subTermId - id of a subTerm
     * @param termId - id of a relevant studentTermMarks
     * @return - a list of StudentSubTermMarks
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @SuppressWarnings("unchecked")
    public List<StudentSubTermMark> getSubTermMarks(int subTermId, int termId) throws AkuraAppException {
    
        try {
            return getHibernateTemplate().findByNamedQuery(GET_SUB_TERM_MARKS, subTermId, termId);
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
}
