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

package com.virtusa.akura.common.dao;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDao;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the UserLogin object.
 * 
 * @author Virtusa Corporation
 */
public interface UserLoginDao extends BaseDao<UserLogin> {
    
    /**
     * Retrieve the user login by passing the user's username.This returns any user with the user name passed.
     * 
     * @param userName - String
     * @return UserLogin object.
     * @throws AkuraAppException SMS Exceptions.
     */
    UserLogin getAnyUserByName(String userName) throws AkuraAppException;
    
    /**
     * Retrieve the user login by passing the user's email. This returns any user with the email passed.
     * 
     * @param email - String
     * @return UserLogin object.
     * @throws AkuraAppException SMS Exceptions.
     */
    UserLogin getAnyUserByEmail(String email) throws AkuraAppException;
    
    /**
     * Retrieve the user login by passing the user's role_id. This returns any user with the role_id passed.
     * 
     * @param roleId - int
     * @param identificationNo - String
     * @return UserLogin object.
     * @throws AkuraAppException SMS Exceptions.
     */
    List<Integer> getStudentByUserRoleIdAndIdentificationNo(int roleId, String identificationNo)
            throws AkuraAppException;
    
    /**
     * Retrieve the user login by passing the user's username.
     * 
     * @param userName - String
     * @return UserLogin object.
     * @throws AkuraAppException SMS Exceptions.
     */
    UserLogin getUserLoginByName(String userName) throws AkuraAppException;
    
    /**
     * Retrieve the user login by using user identification no.
     * 
     * @param identificationNo - String
     * @return UserLogin object
     * @throws AkuraAppException - throw this
     */
    UserLogin getUserLoginByIdentificationNo(String identificationNo) throws AkuraAppException;
    
    /**
     * search system user by user name.
     * 
     * @param userLogin - UserLogin
     * @return {@link List} list of user login objects.
     * @throws AkuraAppException when fails.
     */
    List<UserLogin> searchSystemUserByUserName(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * search system user by user name and role.
     * 
     * @param userLogin - UserLogin
     * @return {@link List} list of user login objects.
     * @throws AkuraAppException when fails.
     */
    List<UserLogin> searchSystemUserByUserNameAndRole(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * Retrieve all the available list of UserLogin by user role.
     * 
     * @param userRoleIdList - list of role id
     * @return list of UserLogin.
     * @throws AkuraAppException - throw detailed exception.
     */
    List<UserLogin> getUserLoginListByUserRole(List<Integer> userRoleIdList) throws AkuraAppException;
    
    /**
     * Returns the staff key by the identification number.
     * 
     * @param roleId - the key of the role
     * @param identificationNo - the identification number.
     * @return - the list of staff key
     * @throws AkuraAppException - The exception details that occurred when processing.
     */
    List<Integer> getStaffByUserRoleIdAndIdentificationNo(int roleId, String identificationNo) throws AkuraAppException;
    
    /**
     * get any user by user role and identification no.
     * 
     * @param userRoleId - user role id.
     * @param identificationNo - user identification no.
     * @return list of user Login objects.
     * @throws AkuraAppException when fails.
     */
    UserLogin getUserLoginByUserRoleAndIdetificationNo(int userRoleId, String identificationNo)
            throws AkuraAppException;
    
    /**
     * Get the user login of staff members by passing the registration number.
     * 
     * @param registrationNo - the registration number of the staff member.
     * @return UserLogin of the staff mamber.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the user login of the
     *         staff member.
     */
    List<UserLogin> getUserLoginOfAnyStatusByRegistrationNo(String registrationNo) throws AkuraAppException;
    
    /**
     * check whether identification is valid or not.
     * 
     * @param query - query.
     * @param identificationNo - the identificationNo.
     * @return valid registration no.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the user login of the
     *         staff member.
     */
    int isValidationIdentificationNo(String query, String identificationNo) throws AkuraAppException;
    
    /**
     * check whether identification is existing or not.
     * 
     * @param query - query.
     * @param identificationNo - the identificationNo.
     * @param roleId - the roleId.
     * @return UserLogin of the staff member.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the user login of the
     *         staff member.
     */
    boolean isExistingIdentificationNo(String query, String identificationNo, int roleId) throws AkuraAppException;
    
    /**
     * check whether identification is existing or not.
     * 
     * @param query - query.
     * @param identificationNo - the identificationNo.
     * @return UserLogin of the staff member.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the user login of the
     *         staff member.
     */
    boolean isPastUser(String query, String identificationNo) throws AkuraAppException;

    /**
     * get ClassTeacher List.
     * 
     * @param userLoginId - userLogin id.
     * @param year - assigned year.
     * @param classGradeId - classGradeId.
     * @return ClassTeacherId - list of ClassTeacher ids belongs to specific staff member.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the ClassTeacher of the
     *         staff member.
     */
    List<Integer> getClassTeacherIdList(int userLoginId, Date year, int classGradeId) throws AkuraAppException;
    
    /**
     * Returns true if this student is not a current student with status id of 1.
     * 
     * @param query - the query to get the student status.
     * @param identificationNo - the identification number of the student.
     * @return true if this student is non-current.
     * @throws AkuraAppException - throws detailed exception if fails.
     */
    boolean isNonCurrentStudent(String query, String identificationNo) throws AkuraAppException;
    
}
