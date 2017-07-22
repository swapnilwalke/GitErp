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

package com.virtusa.akura.api.dto;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

/**
 * Admin class to hold dynamic session attributes of a staff member.
 * 
 * @author Virtusa Corporation
 */
public class AdminDetails extends UserInfo {
    
    /** String attribute for registration no. */
    private static final String REGISTRATION_NO = ", registrationNo=";
    
    /** String attribute for staff member id. */
    private static final String STAFF_MEMBER_ID = ", searchStaffMemberId=";
    
    /** String attribute for student id. */
    private static final String STUDENT_ID = "AdminDetails-->searchStudentId=";
    
    /** long attribute for holding serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** holds studentId return from student search. */
    private int searchStudentId;
    
    /** holds staffMemberId return from staff search. */
    private int searchStaffMemberId;
    
    /** holds the registrationNo of the staff member. */
    private String registrationNo;
    
    /**
     * Instantiates a new admin details.
     * 
     * @param userName the user name
     * @param password the password
     * @param userRole the user role
     * @param userRoleIdval - user userRoleId.
     * @param registrationNoVal the registration no val
     * @param isActive the is active
     * @param accountNonLocked the account non locked
     * @param grantedAuthority the granted authority
     */
    public AdminDetails(String userName, String password, String userRole, int userRoleIdval, String registrationNoVal,
            boolean isActive, boolean accountNonLocked, List<GrantedAuthority> grantedAuthority) {

        super(userName, password, userRole, userRoleIdval, isActive, accountNonLocked, grantedAuthority);
        
        registrationNo = registrationNoVal;
    }
    
    /**
     * Returns the details for the AdminDetails object.
     * 
     * @return - the AdminDetails object details.
     */
    @Override
    public String toString() {

        return STUDENT_ID + searchStudentId + STAFF_MEMBER_ID + searchStaffMemberId + REGISTRATION_NO + registrationNo;
    }
    
    /**
     * return searchStudentId of the Student.
     * 
     * @return the searchStudentId
     */
    public int getSearchStudentId() {

        return searchStudentId;
    }
    
    /**
     * Setter method for searchStudentId.
     * 
     * @param searchStudentIdVal the searchStudentId to set
     */
    public void setSearchStudentId(int searchStudentIdVal) {

        this.searchStudentId = searchStudentIdVal;
    }
    
    /**
     * return searchStaffMemberId of the Student.
     * 
     * @return the searchStaffMemberId
     */
    public int getSearchStaffMemberId() {

        return searchStaffMemberId;
    }
    
    /**
     * Setter method for searchStaffMemberId.
     * 
     * @param searchStaffMemberIdVal the searchStaffMemberId to set
     */
    public void setSearchStaffMemberId(int searchStaffMemberIdVal) {

        this.searchStaffMemberId = searchStaffMemberIdVal;
    }
    
    /**
     * return registrationNo of the Student.
     * 
     * @return the registrationNo
     */
    public String getRegistrationNo() {

        return registrationNo;
    }
    
    /**
     * Setter method for registrationNo.
     * 
     * @param registrationNoVal the registrationNo to set
     */
    public void setRegistrationNo(String registrationNoVal) {

        this.registrationNo = registrationNoVal;
    }
    
    /**
     * method is to clear Student related dynamic attributes.
     */
    @Override
    public void clear() {

        searchStaffMemberId = 0;
        searchStudentId = 0;
        
    }
    
    /**
     * registrationNo for the admin user.
     * 
     * @return registrationNo - registrationNo.
     */
    @Override
    public String getUserLevelIdentifier() {

        return registrationNo;
    }
    
}
