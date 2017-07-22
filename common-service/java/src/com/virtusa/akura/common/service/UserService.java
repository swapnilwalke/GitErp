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

package com.virtusa.akura.common.service;

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dto.Module;
import com.virtusa.akura.api.dto.Privilege;
import com.virtusa.akura.api.dto.RolePrivilege;
import com.virtusa.akura.api.dto.SecurityQuestions;
import com.virtusa.akura.api.dto.Tab;
import com.virtusa.akura.api.dto.UserLogin;
import com.virtusa.akura.api.dto.UserRole;
import com.virtusa.akura.api.dto.UserSecurityQuestions;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.InvalidIdentificationNoException;
import com.virtusa.akura.api.exception.NonCurrentStudentUserLoginCreationException;
import com.virtusa.akura.api.exception.PastStaffException;
import com.virtusa.akura.api.exception.UniqueUserNameEmailException;

/**
 * Declare userService functionality.
 * 
 * @author Virtusa Corporation
 */
public interface UserService {
    
    /**
     * create a system user.
     * 
     * @param userLogin - userLogin
     * @param identificationNo - the identification number
     * @throws AkuraAppException AkuraAppException
     * @return flag indicates whether userLogin creation is succeded.
     */
    boolean createUser(UserLogin userLogin, final int identificationNo) throws AkuraAppException;
    
    /**
     * create a system user.
     * 
     * @param userLogin - userLogin
     * @throws AkuraAppException AkuraAppException
     * @throws UniqueUserNameEmailException UniqueUserNameEmailException
     * @throws PastStaffException -PastStaffException
     * @throws InvalidIdentificationNoException - InvalidIdentificationNoException
     * @throws NonCurrentStudentUserLoginCreationException - throws if student is non current.
     * @return flag indicates whether userLogin creation is succeeded.
     */
    boolean createSystemUser(UserLogin userLogin) throws AkuraAppException, UniqueUserNameEmailException,
    InvalidIdentificationNoException, PastStaffException, NonCurrentStudentUserLoginCreationException;
    
    /**
     * edit a system user.
     * 
     * @param userLogin - userLogin
     * @throws AkuraAppException AkuraAppException
     */
    void editUser(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * edit a system user.
     * 
     * @param userLogin - userLogin
     * @throws AkuraAppException AkuraAppException
     * @throws UniqueUserNameEmailException - UniqueUserNameEmailException
     * @throws PastStaffException - PastStaffException
     * @throws InvalidIdentificationNoException - InvalidIdentificationNoException
     */
    void editSystemUser(UserLogin userLogin) throws AkuraAppException, UniqueUserNameEmailException,
            PastStaffException, InvalidIdentificationNoException;
    
    /**
     * encode password.
     * 
     * @param userLogin - userLogin
     * @throws AkuraAppException AkuraAppException
     * @return encoded password.
     */
    String encodePassword(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * Update a system user.
     * 
     * @param userLogin - userLogin
     * @throws AkuraAppException AkuraAppException
     */
    void updateUser(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * Delete a system user.
     * 
     * @param userLogin - userLogin
     * @throws AkuraAppException AkuraAppException
     */
    void deleteUser(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * get availiable userRole list.
     * 
     * @throws AkuraAppException AkuraAppException
     * @return availiable userRoleList.
     */
    List<UserRole> getUserRoleList() throws AkuraAppException;
    
    /**
     * Retrieve all the available list of UserLogin.
     * 
     * @return list of UserLogin.
     * @throws AkuraAppException - throw detailed exception.
     */
    List<UserLogin> getUserLoginList() throws AkuraAppException;
    
    /**
     * Retrieve all the available list of UserLogin by user role.
     * 
     * @param userRoleIdList - list of role id
     * @return list of UserLogin.
     * @throws AkuraAppException - throw detailed exception.
     */
    List<UserLogin> getUserLoginListByUserRole(List<Integer> userRoleIdList) throws AkuraAppException;
    
    /**
     * Get the any users UserLogin object by passing the user's username.
     * 
     * @param userName - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the UserLogin object.
     */
    UserLogin getAnyUser(String userName) throws AkuraAppException;
    
    /**
     * Get the UserRole object by passing the userrole rolename.
     * 
     * @param role - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the UserRole object.
     */
    UserRole getUserRoleByRoleName(String role) throws AkuraAppException;
    
    /**
     * Get the any users UserLogin object by passing the user's email.
     * 
     * @param email - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the UserLogin object.
     */
    UserLogin getAnyUserByEmail(String email) throws AkuraAppException;
    
    /**
     * Get the any users UserLogin object by passing the user's role_id and IdentificationNo.
     * 
     * @param roleId - int
     * @param identificationNo - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the UserLogin object.
     */
    int getAnyUserByUserRoleIdAndIdentificationNo(int roleId, String identificationNo) throws AkuraAppException;
    
    /**
     * Get the users UserLogin object by passing the user's username.
     * 
     * @param userName - String
     * @throws AkuraAppException AkuraAppException
     * @return returns the UserLogin object.
     */
    UserLogin getUser(String userName) throws AkuraAppException;
    
    /**
     * Reset the user password.
     * 
     * @param userLogin - userLogin
     * @throws AkuraAppException AkuraAppException
     * @return flag indicates whether the password has changed for this user.
     */
    String resetPassword(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * Generate a random password string.
     * 
     * @throws AkuraAppException AkuraAppException
     * @return Random string which is used as a password.
     */
    String generateRandomPassword() throws AkuraAppException;
    
    /**
     * Retrieve logged in user by userName.
     * 
     * @param strName - String
     * @return UserLogin Object.
     * @throws AkuraAppException - throw detailed exception.
     */
    UserLogin getUserByName(String strName) throws AkuraAppException;
    
    /**
     * Retrieve userlogin by user identification no.
     * 
     * @param identificationNo - String
     * @return UserLogin object
     * @throws AkuraAppException - throw this
     */
    UserLogin getUserLoginByIdentificationNo(String identificationNo) throws AkuraAppException;
    
    /**
     * Get all the Security Questions available in the system.
     * 
     * @return UserSecurityQuestions object which has all the security questions.
     * @throws AkuraAppException -throws when failed.
     */
    List<SecurityQuestions> getAllSecurityQuestions() throws AkuraAppException;
    
    /**
     * Create a user Security Question.
     * 
     * @param userSecurityQuestions - a question to be asked form users.
     * @throws AkuraAppException throws when failed.
     */
    void createSystemUserSecurityQuestion(UserSecurityQuestions userSecurityQuestions) throws AkuraAppException;
    
    /**
     * Get all the UserSecurityQuestions.
     * 
     * @return returns true if retrieval is successful.
     * @throws AkuraAppException throws when fail.
     */
    List<UserSecurityQuestions> getAllUserSecurityQuestions() throws AkuraAppException;
    
    /**
     * Get all the Security questions of this user.
     * 
     * @param userLoginId Id of the User.
     * @return {@link List} list of security questions.(There should be two security questions.)
     * @throws AkuraAppException when fails.
     */
    List<UserSecurityQuestions> getUserSecurityQuestionsById(int userLoginId) throws AkuraAppException;
    
    /**
     * Check the availability of security questions for this user.
     * 
     * @param user -User's user login
     * @return returns true if security questions available for this user.
     * @throws AkuraAppException throws when fails.
     */
    boolean isSecurityQuestionsExist(UserLogin user) throws AkuraAppException;
    
    /**
     * Change the password of this user to the new password pass into the method as a parameter.
     * 
     * @return true if password has successfuly changed.
     * @param userLogin The details of user logged in.
     * @param newPassword the new password provide by the user.
     * @throws AkuraAppException when fails in operation.
     */
    boolean changePassword(UserLogin userLogin, String newPassword) throws AkuraAppException;
    
    /**
     * Validate the row password with the encoded password.
     * 
     * @param password the row password entered by the user.
     * @param userLogin the user login.
     * @return {@link Boolean} true if the passwords are matched.
     * @throws AkuraAppException when fails in operation.
     */
    boolean validatePassword(String password, UserLogin userLogin) throws AkuraAppException;
    
    /**
     * search system user by user name.
     * 
     * @param userLogin the user login.
     * @return {@link List} list of user login objects.
     * @throws AkuraAppException when fails.
     */
    List<UserLogin> searchUserLogin(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * search system user by user name and role.
     * 
     * @param userLogin the user login.
     * @return {@link List} list of user login objects.
     * @throws AkuraAppException when fails.
     */
    List<UserLogin> searchUserLoginByUserNameAndRole(UserLogin userLogin) throws AkuraAppException;
    
    /**
     * find system userLogin by userLgoinId.
     * 
     * @param userLgoinId the id of the user.
     * @return {@link List} list of user login objects.
     * @throws AkuraAppException when fails.
     */
    UserLogin findUserLogin(int userLgoinId) throws AkuraAppException;
    
    /**
     * find userRole by userRoleId.
     * 
     * @param userRoleId the id of the userRole.
     * @return userRole object.
     * @throws AkuraAppException when fails.
     */
    UserRole findUserRole(int userRoleId) throws AkuraAppException;
    
    /**
     * create a user role.
     * 
     * @param userRole - userRole
     * @throws AkuraAppException AkuraAppException
     * @return user role.
     */
    UserRole createUserRole(UserRole userRole) throws AkuraAppException;
    
    /**
     * update a system user.
     * 
     * @param userRole - userRole
     * @throws AkuraAppException AkuraAppException
     */
    void updateUserRole(UserRole userRole) throws AkuraAppException;
    
    /**
     * Delete a user role.
     * 
     * @param userRole - userRole
     * @throws AkuraAppException AkuraAppException
     */
    void deleteUserRole(UserRole userRole) throws AkuraAppException;
    
    /**
     * Grant or revoke privileges to user roles.
     * 
     * @param userRole - userRole
     * @param privileges - List of privileges
     * @param tabs - List of tabs
     * @throws AkuraAppException AkuraAppException
     */
    void grantOrRevokePrivileges(UserRole userRole, List<Privilege> privileges, List<Tab> tabs)
            throws AkuraAppException;
    
    /**
     * Get all the Modules as a list.
     * 
     * @return list of modules.
     * @throws AkuraAppException when fails.
     */
    List<Module> getAllModules() throws AkuraAppException;
    
    /**
     * Get Privileges by user role.
     * 
     * @param userRole - userRole
     * @return list of privileges.
     * @throws AkuraAppException when fails.
     */
    List<Privilege> getPrivilegesByUserRole(UserRole userRole) throws AkuraAppException;
    
    /**
     * Get Tabs by user role.
     * 
     * @param userRole - userRole
     * @return list of privileges.
     * @throws AkuraAppException when fails.
     */
    List<Tab> getTabsByUserRole(UserRole userRole) throws AkuraAppException;
    
    /**
     * Get RolePrivileges by user role.
     * 
     * @param userRole - userRole
     * @return list of privileges.
     * @throws AkuraAppException when fails.
     */
    List<RolePrivilege> getRolePrivilegesByUserRole(UserRole userRole) throws AkuraAppException;
    
    /**
     * get Dependencies privilege ID list.
     * 
     * @param privilegeIdList - privilege IDs list.
     * @return list of dependencies IDs.
     * @throws AkuraAppException when fails.
     */
    List<Privilege> getDependenciesList(List<Integer> privilegeIdList) throws AkuraAppException;
    
    /**
     * get Dependencies tab ID list.
     * 
     * @param privilegeIdList - privilege IDs list.
     * @return list of dependencies IDs.
     * @throws AkuraAppException when fails.
     */
    List<Tab> getDependenciesTabIdList(List<Integer> privilegeIdList) throws AkuraAppException;
    
    /**
     * check whether identification is valid or not.
     * 
     * @param userRoleId - userRoleId.
     * @param identificationNo - the identificationNo.
     * @return vaid registration no.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the user login of the
     *         staff member.
     */
    int isValidationIdentificationNo(int userRoleId, String identificationNo) throws AkuraAppException;
    
    /**
     * check whether identification is existing or not.
     * 
     * @param userRoleId - userRoleId.
     * @param identificationNo - the identificationNo.
     * @return UserLogin of the staff member.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the user login of the
     *         staff member.
     */
    boolean isExistingIdentificationNo(int userRoleId, String identificationNo) throws AkuraAppException;
    
    /**
     * check whether user is a past user or not.
     * 
     * @param userRoleId - userRoleId.
     * @param identificationNo - the identificationNo.
     * @return true for if user is a past user.
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the user login of the
     *         staff member.
     */
    boolean isPastUser(int userRoleId, String identificationNo) throws AkuraAppException;
    
    /**
     * get ClassTeacher List.
     * 
     * @param userLoginId - userLogin id.
     * @param year - assigned year.
     * @param classGradeId - classGradeId.
     * @return true for if the user is the class teacher for specific class in the specified year
     * @throws AkuraAppException - throws detailed exception when fails to retrieve the ClassTeacher of the
     *         staff member.
     */
    boolean isClassTeacher(int userLoginId, Date year, int classGradeId) throws AkuraAppException;
    
    /**
     * This method returns true if the student is not a current student.
     * 
     * @param userRoleId - the user role type id for student. 
     * @param identificationNo -  the admission number of the student.
     * @return true if this student is temporary leaved, suspended or past.
     * @throws AkuraAppException - throws detailed exception when fails to check if the student is active or
     *         not.
     */
    boolean isNonCurrentStudent(int userRoleId, String identificationNo) throws AkuraAppException;
}
