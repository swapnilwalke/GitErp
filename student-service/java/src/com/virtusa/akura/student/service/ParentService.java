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

package com.virtusa.akura.student.service;

import java.util.List;

import com.virtusa.akura.api.dto.Donation;
import com.virtusa.akura.api.dto.Parent;
import com.virtusa.akura.api.dto.ParentDonation;
import com.virtusa.akura.api.dto.Student;
import com.virtusa.akura.api.dto.StudentParent;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public interface ParentService {
    
    /**
     * Service to add a Parent object.
     * 
     * @param parent the Parent object to set
     * @return parent object.
     * @throws AkuraAppException - throw this
     */
    Parent addParent(Parent parent) throws AkuraAppException;
    
    /**
     * Service to modify Parent object.
     * 
     * @param parent the Parent object to modify
     * @throws AkuraAppException - throw this
     */
    void modifyParent(Parent parent) throws AkuraAppException;
    
    /**
     * Service to delete Parent object.
     * 
     * @param parent Parent object to delete
     * @throws AkuraAppException - throw this
     */
    void deleteParent(Parent parent) throws AkuraAppException;
    
    /**
     * Service to find the parent object.
     * 
     * @param parentId to find Parent
     * @return parent object
     * @throws AkuraAppException - throw this
     */
    Parent findParent(int parentId) throws AkuraAppException;
    
    /**
     * Service to view all parent types.
     * 
     * @return returns list of Parents.
     * @throws AkuraAppException - throw this
     */
    List<Parent> viewAllParents() throws AkuraAppException;
    
    /**
     * Service to add a ParentDonation object.
     * 
     * @param parentDonation ParentDonation object to set
     * @return parent donation object.
     * @throws AkuraAppException - throw this
     */
    ParentDonation addParentDonation(ParentDonation parentDonation) throws AkuraAppException;
    
    /**
     * Service to modify ParentDonation object.
     * 
     * @param parentDonation ParentDonation object to modify
     * @throws AkuraAppException - throw this
     */
    void modifyParentDonation(ParentDonation parentDonation) throws AkuraAppException;
    
    /**
     * Service to delete ParentDonation object.
     * 
     * @param parentDonation ParentDonation object to delete
     * @throws AkuraAppException - throw this
     */
    void deleteParentDonation(ParentDonation parentDonation) throws AkuraAppException;
    
    /**
     * Service to view all parents's donations.
     * 
     * @return returns list of Parent's Donations.
     * @throws AkuraAppException - throw this
     */
    List<ParentDonation> viewAllParentDonation() throws AkuraAppException;
    
    /**
     * Service to add Donation object.
     * 
     * @param donation Donation object to add
     * @return donation object
     * @throws AkuraAppException - throw this
     */
    Donation addDonation(Donation donation) throws AkuraAppException;
    
    /**
     * Service to modify Donation object.
     * 
     * @param donation Donation object to modify
     * @throws AkuraAppException - throw this
     */
    void modifyDonation(Donation donation) throws AkuraAppException;
    
    /**
     * Service to delete Donation object.
     * 
     * @param donation Donation object to delete
     * @throws AkuraAppException - throw this
     */
    void deleteDonation(Donation donation) throws AkuraAppException;
    
    /**
     * Service to view all donations.
     * 
     * @return returns list of Donations.
     * @throws AkuraAppException - throw this
     */
    List<Donation> viewAllDonation() throws AkuraAppException;
    
    /**
     * Service to find the donation object.
     * 
     * @param donationId to find Donation
     * @return donation object
     * @throws AkuraAppException - throw this
     */
    Donation findDonation(int donationId) throws AkuraAppException;
    
    /**
     * Service to add StudentParent object.
     * 
     * @param studentParent StudentParent object to add
     * @return studentParent object
     * @throws AkuraAppException - throw this
     */
    StudentParent addStudentParent(StudentParent studentParent) throws AkuraAppException;
    
    /**
     * Service to view all StudentParent objects by studentId.
     * 
     * @param studentId StudentParent objects to find
     * @return List of StudentParent returns list of StudentParents.
     * @throws AkuraAppException - throw this
     */
    List<StudentParent> getStudentParentListByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Service to view all donation objects by studentId.
     * 
     * @param studentId Donations objects to find
     * @return List of Donations returns list of Donations.
     * @throws AkuraAppException - throw this
     */
    List<Donation> getDonationListByStudentId(int studentId) throws AkuraAppException;
    
    /**
     * Service to view parent objects by NIC number.
     * 
     * @param nicNo parent objects to find
     * @return parent object returns of Parent.
     * @throws AkuraAppException - throw this
     */
    Parent getParentByNIC(String nicNo) throws AkuraAppException;
    
    /**
     * Gets the parent donations by donation id.
     * 
     * @param donationId the donation id
     * @return the parent donations by donation id
     * @throws AkuraAppException throw the exception
     */
    List<ParentDonation> getParentDonationsByDonationId(int donationId) throws AkuraAppException;
    
    /**
     * check whether NIC no exist in db.
     * 
     * @param nicNO NIC no to check
     * @return - true if NIC exist
     * @throws AkuraAppException when exception Occurs
     */
    boolean isNICnoExist(String nicNO) throws AkuraAppException;
    
    /**
     * To get children for the parent.
     * 
     * @param nicNo parent NIC no
     * @return List list of children for the parent
     * @throws AkuraAppException when exception Occurs
     */
    List<Student> getAllChilds(String nicNo) throws AkuraAppException;
    
    /**
     * delete the Given studentParent relation.
     * 
     * @param studentParet object to delete
     * @throws AkuraAppException when exception Occurs
     */
    void deleteStudentParent(StudentParent studentParet) throws AkuraAppException;
    
    /**
     * update the parent records.
     * 
     * @param parent object
     * @return updated object
     * @throws AkuraAppException when exception Occurs
     */
    Parent updateParent(Parent parent) throws AkuraAppException;
    
    /**
     * check whether the given parent is used by other student without this student(Id).
     * 
     * @param parent parent that contains NIC
     * @param studentID studentId
     * @return true if user by other student
     * @throws AkuraAppException when exception occurs
     */
    boolean isUsedInOtherStudent(Parent parent, int studentID) throws AkuraAppException;
    
    /**
     * check whether the given parent is used by the student(Id).
     * 
     * @param parent parent that contains NIC
     * @param studentID studentId
     * @return true if user by same student
     * @throws AkuraAppException when exception occurs
     */
    boolean isUsedInSameStudent(Parent parent, int studentID) throws AkuraAppException;
    
    /**
     * To get the StudentParent if exist with given student(id) and the relationship (f,m,g).
     * 
     * @param studentId studentId value
     * @param relationship relationship as character
     * @return StudentParent list
     * @throws AkuraAppException when exception occurs
     */
    List<StudentParent> getParentListBy(int studentId, Character relationship) throws AkuraAppException;
    
    /**
     * get all students if exist from StudentParent table that have the parent and the given relation in used.
     * 
     * @param nic NIC no
     * @param relationship relationship as character
     * @return student list
     * @throws AkuraAppException when exception occurs
     */
    List<Student> getStudentListBy(String nic, Character relationship) throws AkuraAppException;
    
    /**
     * check whether passed donation information is exists or not.
     * 
     * @param donation - specifies the donation object
     * @return List of Donations
     * @throws AkuraAppException - Akura Exception
     */
    boolean isExistsDonation(Donation donation) throws AkuraAppException;
}
