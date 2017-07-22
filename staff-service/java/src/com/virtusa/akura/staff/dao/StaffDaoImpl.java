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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * This class provides persistence layer functionality for the Staff object.
 * 
 * @author Virtusa Corporation
 */
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements StaffDao {
    
    /** query parameter name. */
    private static final String STAFF_ID_LIST = "staffIdList";
    
    /** query name. */
    private static final String GET_EMAILS_FOR_STAFF = "getEmailsForStaff";
    
    /** The constant for String "getAllAvailableStaff". */
    private static final String GET_ALL_AVAILABLE_STAFF = "getAllAvailableStaff";
    
    /** The constant for String "getAllAvailableStaffWithOutDeparture". */
    private static final String GET_ALL_AVAILABLE_STAFF_WITHOUT_DEPARTURE = "getAllAvailableStaffWithOutDeparture";
    
    /** The constant for String "getAllAvailableStaffWithOutDeparture". */
    private static final String GET_ALL_AVAILABLE_STAFF_WITHOUT_DEPARTURE_DATE =
            "getAllAvailableStaffWithOutDepartureDate";
    
    /** The constant for String "getStaffByType". */
    private static final String GET_STAFF_BY_TYPE = "getStaffByType";
    
    /** The constant for String "getStaffByCategory". */
    private static final String GET_STAFF_BY_CATEGORY = "getStaffByCategory";
    
    /** The constant for String "getStaffByCategory". */
    private static final String GET_STAFF_BY_CATEGORY_BY_DATE = "getSelectedDateStaffByCategory";
    
    /** The constant for String "getAllStaffByCategory". */
    private static final String GET_ALL_STAFF_BY_CATEGORY = "getAllStaffByCategory";
    
    /** The constant for String "getDepatureStaffByCategory". */
    private static final String GET_DEPATURE_STAFF_BY_CATEGORY = "getDepatureStaffByCategory";
    
    /** The constant for String "getMaxStaffId". */
    private static final String GET_MAX_STAFF_ID = "getMaxStaffId";
    
    /** The constant for String "getAcademicStaff". */
    private static final String GET_ACADEMIC_STAFF = "getAcademicStaff";
    
    /** The constant for String "checkRegistrationNoIsExist". */
    private static final String CHECK_REGISTRATION_NO_IS_EXIST = "checkRegistrationNoIsExist";
    
    /** The constant for String "staffSearchWithoutAcademicPast". */
    private static final String STAFF_SEARCH_WITHOUT_ACADEMIC_PAST_STAFF = "staffSearchWithoutAcademicPastStaff";
    
    /** The constant for String "staffSearchWithAcademicPast". */
    private static final String STAFF_SEARCH_WITH_ACADEMIC_PAST_STAFF = "staffSearchWithAcademicPastStaff";
    
    /** The constant for String "staffSearchWithoutAcademicPresent". */
    private static final String STAFF_SEARCH_WITHOUT_ACADEMIC_PRESENT_STAFF = "staffSearchWithoutAcademicPresentStaff";
    
    /** The constant for String "staffSearchWithAcademicPresent". */
    private static final String STAFF_SEARCH_WITH_ACADEMIC_PRESENT_STAFF = "staffSearchWithAcademicPresentStaff";
    
    /** The constant for "%" character. */
    private static final String MODULO_STRING = "%";
    
    /** Represents the name of the query to find the primary key of the staff. */
    private static final String FIND_TEACHER_ID = "getTeacherIdByRegistrationNo";
    
    /** The constant for String "checkEmailAvailability". */
    private static final String CHECK_EMAIL_AVAILABILITY = "checkEmailAvailability";
    
    /** The constant for String "staffSearchWithoutAcademic". */
    private static final String STAFF_SEARCH_WITHOUT_ACADEMIC = "staffSearchWithoutAcademic";
    
    /** The constant for String "staffSearchWithAcademic". */
    private static final String STAFF_SEARCH_WITH_ACADEMIC = "staffSearchWithAcademic";
    
    /** The constant for String "checkEmailAvailability". */
    private static final String GET_STAFF_MEMBER_BY_REGISTRATION_NO = "getStaffMemberByRegistrationNo";
    
    /** The constant for String "deleteStaffAttendenceByDepartureDate". */
    private static final String DELETE_STAFF_ATTENDENCE_BY_DEPARTURE_DATE = "deleteStaffAttendenceByDepartureDate";

    /** The constant for String query name "getStaffCount". */
    private static final String GET_STAFF_COUNT = "getStaffCount";

    /**
     * Logger to log the exceptions.
     */
    private static final Logger LOG = Logger.getLogger(StaffDaoImpl.class);
    
    /**
     * Search for Staff member.
     * 
     * @param staff - Staff member to be searched.
     * @return List of Staff members.
     * @throws AkuraAppException - The exception details that occurred when searching Staff for a given
     *         criteria.
     */
    public List<Staff> staffSearch(Staff staff) throws AkuraAppException {

        try {
            List<Staff> searchReults = null;
            String lastName = MODULO_STRING + staff.getLastName() + MODULO_STRING;
            String empNo = staff.getRegistrationNo();
            int staffState = staff.getStaffStatus();
            
            if (staffState == 2) {
                
                if (staff.getStaffCategory() != null) {
                    
                    searchReults =
                            getSearchWithAcademicStatus(staff, STAFF_SEARCH_WITH_ACADEMIC_PAST_STAFF, lastName,
                                    lastName, empNo, empNo);
                    
                } else {
                    
                    searchReults =
                            getSearchWithOutAcademicStatus(staff, STAFF_SEARCH_WITHOUT_ACADEMIC_PAST_STAFF, lastName,
                                    lastName, empNo, empNo);
                    
                }
                
            } else if (staffState == 1) {
                
                if (staff.getStaffCategory() != null) {
                    
                    searchReults =
                            getSearchWithAcademicStatus(staff, STAFF_SEARCH_WITH_ACADEMIC_PRESENT_STAFF, lastName,
                                    lastName, empNo, empNo);
                    
                } else {
                    
                    searchReults =
                            getSearchWithOutAcademicStatus(staff, STAFF_SEARCH_WITHOUT_ACADEMIC_PRESENT_STAFF,
                                    lastName, lastName, empNo, empNo);
                    
                }
                
            } else {
                
                if (staff.getStaffCategory() != null) {
                    
                    searchReults =
                            getSearchWithAcademicStatus(staff, STAFF_SEARCH_WITH_ACADEMIC, lastName, lastName, empNo,
                                    empNo);
                } else {
                    
                    searchReults =
                            getSearchWithOutAcademicStatus(staff, STAFF_SEARCH_WITHOUT_ACADEMIC, lastName, lastName,
                                    empNo, empNo);
                    
                }
            }
            return searchReults;
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get staffId of a staff member with the given registrationNo.
     * 
     * @param registrationNo - registrationNo of the Staff member.
     * @return flag states whether admission exist or not.
     * @throws AkuraAppException - The exception details that occurred when searching StaffId for a given
     *         criteria.
     */
    public int findStaffIdForRegistrationNo(String registrationNo) throws AkuraAppException {

        int staffId = 0;
        
        try {
            @SuppressWarnings("rawtypes")
            List checkList = getHibernateTemplate().findByNamedQuery(CHECK_REGISTRATION_NO_IS_EXIST, registrationNo);
            
            if (checkList.size() > 0) {
                staffId = (Integer) checkList.get(0);
            }
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffId;
    }
    
    /**
     * Get the list of academic staff.
     * 
     * @return list of staff members.
     * @throws AkuraAppException - The exception details that occurred when searching academic Staff for a
     *         given criteria.
     */
    @SuppressWarnings("unchecked")
    public List<Staff> getacademicStaff() throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_ACADEMIC_STAFF);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Service method is to get list of staff by Type.
     * 
     * @param staffType - boolean
     * @param selectedDate - Date
     * @return list of staff.
     * @throws AkuraAppException - The exception details that occurred when searching Staff for a given
     *         criteria.
     */
    @SuppressWarnings("unchecked")
    public List<Staff> getStaffByType(boolean staffType, Date selectedDate) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_STAFF_BY_TYPE, staffType, selectedDate, selectedDate,
                    selectedDate);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Integer> getMaxStaffId() throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(GET_MAX_STAFF_ID);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Get the list of Staff by category.
     * 
     * @param staffCategoryId staffCategorytypeId.
     * @return list of Staff.
     * @throws AkuraAppException throws when retrieving the list of staff fails.
     */
    @SuppressWarnings("unchecked")
    public List<Staff> getStaffByCategory(int staffCategoryId) throws AkuraAppException {

        List<Staff> staffList;
        
        try {
            staffList = getHibernateTemplate().findByNamedQuery(GET_STAFF_BY_CATEGORY, staffCategoryId);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffList;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Staff> getStaffByCategoryByDate(int staffCategoryId, Date date, Date hDate) throws AkuraAppException {

        List<Staff> staffList;
        
        try {
            staffList =
                    getHibernateTemplate()
                            .findByNamedQuery(GET_STAFF_BY_CATEGORY_BY_DATE, staffCategoryId, date, hDate);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffList;
    }
    
    /**
     * Get departure staff members by category.
     * 
     * @param staffCategoryId - staff category type id.
     * @return - list of departure staff members in the category.
     * @throws AkuraAppException - The exception details that occurred when retrieving staff members.
     */
    @SuppressWarnings("unchecked")
    public List<Staff> getDepatureStaffByCategory(int staffCategoryId) throws AkuraAppException {

        List<Staff> staffList;
        
        try {
            staffList = getHibernateTemplate().findByNamedQuery(GET_DEPATURE_STAFF_BY_CATEGORY, staffCategoryId);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffList;
    }
    
    /**
     * Get the list of All Staff by category.
     * 
     * @param staffCategoryId staffCategorytypeId.
     * @return list of All Staff.
     * @throws AkuraAppException throws when retrieving the list of staff fails.
     */
    @SuppressWarnings("unchecked")
    public List<Staff> getAllStaffByCategory(int staffCategoryId) throws AkuraAppException {

        List<Staff> staffList;
        
        try {
            staffList = getHibernateTemplate().findByNamedQuery(GET_ALL_STAFF_BY_CATEGORY, staffCategoryId);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffList;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Staff> findAllAvailableStaff() throws AkuraAppException {

        List<Staff> staffList;
        
        try {
            staffList = getHibernateTemplate().findByNamedQuery(GET_ALL_AVAILABLE_STAFF);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffList;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Staff> findAllAvailableStaffWithOutDeparture() throws AkuraAppException {

        List<Staff> staffList;
        
        try {
            staffList = getHibernateTemplate().findByNamedQuery(GET_ALL_AVAILABLE_STAFF_WITHOUT_DEPARTURE);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffList;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<Staff> findAllAvailableStaffWithOutDepartureDate(Date date, Date hDate) throws AkuraAppException {

        List<Staff> staffList;
        
        try {
            staffList =
                    getHibernateTemplate()
                            .findByNamedQuery(GET_ALL_AVAILABLE_STAFF_WITHOUT_DEPARTURE_DATE, date, hDate);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
        return staffList;
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<Integer> getStaffId(String registrationNo) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(FIND_TEACHER_ID, registrationNo);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_WHILE_PROCESSING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<String> checkEmailAvailability(String email) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQuery(CHECK_EMAIL_AVAILABILITY, email);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_WHILE_PROCESSING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /** {@inheritDoc} */
    @SuppressWarnings("unchecked")
    public List<String> getStaffEmails(List<Integer> staffIdList) throws AkuraAppException {

        try {
            return getHibernateTemplate().findByNamedQueryAndNamedParam(GET_EMAILS_FOR_STAFF,
                    new String[] { STAFF_ID_LIST }, new Object[] { staffIdList });
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_WHILE_PROCESSING + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
        
    }
    
    /**
     * Search for Staff member considering Academic status.
     * 
     * @param staff - Staff.
     * @param query - String.
     * @param lastName - String.
     * @param lName - String.
     * @param empNo - String.
     * @param eNo - String.
     * @return List of Staff.
     * @throws AkuraAppException - The exception details that occurred when searching Staff for a given
     *         criteria.
     */
    @SuppressWarnings("unchecked")
    public List<Staff> getSearchWithAcademicStatus(Staff staff, String query, String lastName, String lName,
            String empNo, String eNo) throws AkuraAppException {

        try {
            
            List<Staff> result = null;
            boolean isAcademic = staff.getStaffCategory().isAcademic();
            result =
                    (List<Staff>) getHibernateTemplate().findByNamedQuery(query, lastName, lName, empNo, eNo,
                            isAcademic);
            return result;
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_SEARCHING_THE_OBJECT + e.toString());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
    
    /**
     * Search for Staff member considering Academic status.
     * 
     * @param staff - Staff.
     * @param query - String.
     * @param lastName - String.
     * @param lName - String.
     * @param empNo - String.
     * @param eNo - String.
     * @return List of Staff.
     * @throws AkuraAppException - The exception details that occurred when searching Staff for a given
     *         criteria.
     */
    @SuppressWarnings("unchecked")
    public List<Staff> getSearchWithOutAcademicStatus(Staff staff, String query, String lastName, String lName,
            String empNo, String eNo) throws AkuraAppException {

        List<Staff> result = null;
        
        result = (List<Staff>) getHibernateTemplate().findByNamedQuery(query, lastName, lName, empNo, eNo);
        return result;
    }
    
    /**
     * gets the staff member by registration no.
     * 
     * @param registrationNo - registration no of staff member
     * @return staff member object
     * @throws AkuraAppException when exception occurs
     */
    @SuppressWarnings("unchecked")
    public Staff getStaffMemberByRegistrationNo(String registrationNo) throws AkuraAppException {

        return ((ArrayList<Staff>) getHibernateTemplate().findByNamedQuery(GET_STAFF_MEMBER_BY_REGISTRATION_NO,
                registrationNo)).get(0);
        
    }
    
    /**
     * {@inheritDoc}
     */
    public void deletedStaffAttendanceRecordsByDepartureDate(int staffId, Date departureDate) throws AkuraAppException {

        Session session = null;
        try {
            session = getHibernateTemplate().getSessionFactory().openSession();
            Query query = session.getNamedQuery(DELETE_STAFF_ATTENDENCE_BY_DEPARTURE_DATE);
            getHibernateTemplate().bulkUpdate(query.getQueryString(), staffId, departureDate);
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_DELETING_THE_OBJECT + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        } finally {
            session.close();
        }
    }

    /**
     * {@inheritDoc}
     */
    public int getNumberOfStaffMembersInSchool() throws AkuraAppException {

        List<?> staffCount;
        
        try{
            staffCount = getHibernateTemplate().findByNamedQuery(GET_STAFF_COUNT);
            
        } catch (DataAccessException e) {
            LOG.error(AkuraConstant.ERROR_OCCURED_WHILE_RETRIEVING_THE_OBJECT + e.getMessage());
            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e); 
        }
        
        return staffCount != null ? ((Long)staffCount.get(0)).intValue() : 0;
    }

    
}
