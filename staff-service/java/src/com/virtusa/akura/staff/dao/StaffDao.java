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

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Class Staff object.
 * 
 * @author Virtusa Corporation
 */
public interface StaffDao extends BaseDao<Staff> {
    
    /**
     * Search staff members.
     * 
     * @param staff member to be searched.
     * @throws AkuraAppException - The exception details that occurred when searching Staff member. for a
     *         given criteria.
     * @return list of Staff members.
     */
    List<Staff> staffSearch(Staff staff) throws AkuraAppException;
    
    /**
     * Get staffId of a staff member with the given registrationNo.
     * 
     * @param registrationNo -The registrationNo of the staff member.
     * @return flag states whether admission no exist or not.
     * @throws AkuraAppException - The exception details that occurred when searching Staff for a given
     *         criteria.
     */
    int findStaffIdForRegistrationNo(String registrationNo) throws AkuraAppException;
    
    /**
     * Service method is to get list of academic staff.
     * 
     * @return list of staff.
     * @throws AkuraAppException - The exception details that occurred when searching Staff for a given
     *         criteria.
     */
    List<Staff> getacademicStaff() throws AkuraAppException;
    
    /**
     * Service method is to get list of staff by Type.
     * 
     * @param staffType - boolean
     * @param selectedDate - Date
     * @return list of staff.
     * @throws AkuraAppException - The exception details that occurred when searching Staff for a given
     *         criteria.
     */
    List<Staff> getStaffByType(boolean staffType, Date selectedDate) throws AkuraAppException;
    
    /**
     * Get the max staffId number.
     * 
     * @return - the max staffId number.
     * @throws AkuraAppException - The exception details that occurred when retrieving the max staffId.
     */
    List<Integer> getMaxStaffId() throws AkuraAppException;
    
    /**
     * Get staff members by category.
     * 
     * @param staffCategoryId - staff category type id.
     * @return - list of staff members in the category.
     * @throws AkuraAppException - The exception details that occurred when retrieving staff members.
     */
    List<Staff> getStaffByCategory(int staffCategoryId) throws AkuraAppException;
    
    /**
     * Get staff members by category.
     * 
     * @param staffCategoryId - staff category type id.
     * @return - list of staff members in the category.
     * @param date - date.
     * @param hDate - hDate
     * @throws AkuraAppException - The exception details that occurred when retrieving staff members.
     */
    List<Staff> getStaffByCategoryByDate(int staffCategoryId, Date date, Date hDate) throws AkuraAppException;
    
    /**
     * Get all staff members by category.
     * 
     * @param staffCategoryId - staff category type id.
     * @return - list of all staff members in the category.
     * @throws AkuraAppException - The exception details that occurred when retrieving staff members.
     */
    List<Staff> getAllStaffByCategory(int staffCategoryId) throws AkuraAppException;
    
    /**
     * Get departure staff members by category.
     * 
     * @param staffCategoryId - staff category type id.
     * @return - list of departure staff members in the category.
     * @throws AkuraAppException - The exception details that occurred when retrieving staff members.
     */
    List<Staff> getDepatureStaffByCategory(int staffCategoryId) throws AkuraAppException;
    
    /**
     * Get all available staff members.
     * 
     * @return - list of staff members
     * @throws AkuraAppException - The exception details that occurred when retrieving staff members.
     */
    List<Staff> findAllAvailableStaff() throws AkuraAppException;
    
    /**
     * Get all available staff members.
     * 
     * @return - list of staff members - without departure.
     * @throws AkuraAppException - The exception details that occurred when retrieving staff members.
     */
    List<Staff> findAllAvailableStaffWithOutDeparture() throws AkuraAppException;
    
    /**
     * Get all available staff members.
     * 
     * @return - list of staff members - without departure.
     * @param date - date.
     * @param hDate - hDate.
     * @throws AkuraAppException - The exception details that occurred when retrieving staff members.
     */
    List<Staff> findAllAvailableStaffWithOutDepartureDate(Date date, Date hDate) throws AkuraAppException;
    
    /**
     * Returns a list of primary keys of the staff.
     * 
     * @param registrationNo - registration number of the staff.
     * @return - a list of primary keys of the staff.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Integer> getStaffId(String registrationNo) throws AkuraAppException;
    
    /**
     * Check if this email exist or not.
     * 
     * @param email to be checked for availability.
     * @return the existing email address.
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<String> checkEmailAvailability(String email) throws AkuraAppException;
    
    /**
     * gets the email addresses if any, for given staff id list.
     * 
     * @param staffIdList staff id list
     * @return email address list
     * @throws AkuraAppException when exception occuers
     */
    List<String> getStaffEmails(List<Integer> staffIdList) throws AkuraAppException;
    
    /**
     * gets the staff member by registration no.
     * 
     * @param registrationNo - registration no of staff member
     * @return staff member object
     * @throws AkuraAppException when exception occurs
     */
    Staff getStaffMemberByRegistrationNo(String registrationNo) throws AkuraAppException;
    
    /**
     * Delete all the Attendance records that have date after the departureDate.
     * 
     * @param staffId staff id.
     * @param departureDate departure date.
     * @throws AkuraAppException - The exception details that occurred when deleting StaffAttedance records
     *         with given staffId.
     */
    void deletedStaffAttendanceRecordsByDepartureDate(int staffId, Date departureDate) throws AkuraAppException;
    
    /**
     * Get the number of staff members in the school.
     * 
     * @return the number of staff members.
     * @throws AkuraAppException - The exception details that occurred when getting the number of staff 
     * members in the school. 
     */
    int getNumberOfStaffMembersInSchool() throws AkuraAppException;
}
