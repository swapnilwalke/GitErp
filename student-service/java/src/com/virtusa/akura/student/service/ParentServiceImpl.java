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
import com.virtusa.akura.student.dao.DonationDao;
import com.virtusa.akura.student.dao.ParentDao;
import com.virtusa.akura.student.dao.ParentDonationDao;
import com.virtusa.akura.student.dao.StudentParentDao;

/**
 * @author Virtusa Corporation
 */
public class ParentServiceImpl implements ParentService {
    
    /** ParentDao attribute for holding parentDao. */
    private ParentDao parentDao;
    
    /** ParentDonationDao attribute for holding parentDonationDao. */
    private ParentDonationDao parentDonationDao;
    
    /** DonationDao attribute for holding DonationDao. */
    private DonationDao donationDao;
    
    /** StudentParentDao attribute for holding studentParentDao. */
    private StudentParentDao studentParentDao;
    
    /**
     * Adds a public setter to create an instance of type ParentDao.
     * 
     * @param pDao set the property of type ParentDao.
     */
    public void setParentDao(final ParentDao pDao) {

        this.parentDao = pDao;
    }
    
    /**
     * Adds a public setter to create an instance of type ParentDonationDao.
     * 
     * @param pDonationDao set the property of type ParentDonationDao.
     */
    public void setParentDonationDao(final ParentDonationDao pDonationDao) {

        this.parentDonationDao = pDonationDao;
    }
    
    /**
     * Adds a public setter to create an instance of type DonationDao.
     * 
     * @param dDao set the property of type DonationDao.
     */
    public void setDonationDao(final DonationDao dDao) {

        this.donationDao = dDao;
    }
    
    /**
     * Adds a public setter to create an instance of type StudentParentDao.
     * 
     * @param studentParentDaoValue set the property of type StudentParentDao.
     */
    public void setStudentParentDao(StudentParentDao studentParentDaoValue) {

        this.studentParentDao = studentParentDaoValue;
    }
    
    /**
     * Service to add a parent object.
     * 
     * @param parent the Parent object to add.
     * @return parent object
     * @throws AkuraAppException - throw this
     */
    public Parent addParent(final Parent parent) throws AkuraAppException {

        return this.parentDao.save(parent);
        
    }
    
    /**
     * Service to delete a parent object.
     * 
     * @param parent the Parent object to delete.
     * @throws AkuraAppException - throw this
     */
    public void deleteParent(final Parent parent) throws AkuraAppException {

        this.parentDao.delete(parent);
    }
    
    /**
     * Service to find a parent object.
     * 
     * @param parentId the Parent object to find
     * @return parent object
     * @throws AkuraAppException - throw this
     */
    public Parent findParent(final int parentId) throws AkuraAppException {

        return (Parent) this.parentDao.findById(Parent.class, parentId);
    }
    
    /**
     * Service to modify a parent object.
     * 
     * @param parent Parent object to update
     * @throws AkuraAppException - throw this
     */
    public void modifyParent(final Parent parent) throws AkuraAppException {

        this.parentDao.update(parent);
        
    }
    
    /**
     * Service to view all parent object.
     * 
     * @return returns list of parents.
     * @throws AkuraAppException - throw this
     */
    public List<Parent> viewAllParents() throws AkuraAppException {

        return this.parentDao.findAll(Parent.class);
    }
    
    /**
     * Service to add a donation object.
     * 
     * @param donation Donation object to add.
     * @return donation object
     * @throws AkuraAppException - throw this
     */
    public Donation addDonation(final Donation donation) throws AkuraAppException {

        return (Donation) this.donationDao.save(donation);
    }
    
    /**
     * Service to add a parentDonation object.
     * 
     * @param parentDonation ParentDonation object to add
     * @return donationType object
     * @throws AkuraAppException - throw this
     */
    public ParentDonation addParentDonation(final ParentDonation parentDonation) throws AkuraAppException {

        return (ParentDonation) this.parentDonationDao.save(parentDonation);
    }
    
    /**
     * Service to delete a donation object.
     * 
     * @param donation Donation object to delete.
     * @throws AkuraAppException - throw this
     */
    public void deleteDonation(final Donation donation) throws AkuraAppException {

        this.donationDao.delete(donation);
    }
    
    /**
     * Service to delete a parentDonation object.
     * 
     * @param parentDonation ParentDonation object to delete
     * @throws AkuraAppException - throw this
     */
    public void deleteParentDonation(final ParentDonation parentDonation) throws AkuraAppException {

        this.parentDonationDao.delete(parentDonation);
    }
    
    /**
     * Service to find a donation object.
     * 
     * @param donationId Donation object to find
     * @return donation object
     * @throws AkuraAppException - throw this
     */
    public Donation findDonation(final int donationId) throws AkuraAppException {

        return (Donation) this.donationDao.findById(Donation.class, donationId);
    }
    
    /**
     * Service to modify a donation object.
     * 
     * @param donation Donation object to update
     * @throws AkuraAppException - throw this
     */
    public void modifyDonation(final Donation donation) throws AkuraAppException {

        this.donationDao.update(donation);
    }
    
    /**
     * Service to modify a parentDonation object.
     * 
     * @param parentDonation ParentDonation object to update
     * @throws AkuraAppException - throw this
     */
    public void modifyParentDonation(final ParentDonation parentDonation) throws AkuraAppException {

        this.parentDonationDao.update(parentDonation);
    }
    
    /**
     * Service to view all donation objects.
     * 
     * @return Collection of Donation returns list of Donations.
     * @throws AkuraAppException - throw this
     */
    public List<Donation> viewAllDonation() throws AkuraAppException {

        return this.donationDao.findAll(Donation.class);
    }
    
    /**
     * Service to view all parentDonation objects.
     * 
     * @return returns list of Donations.
     * @throws AkuraAppException - throw this
     */
    public List<ParentDonation> viewAllParentDonation() throws AkuraAppException {

        return this.parentDonationDao.findAll(ParentDonation.class);
    }
    
    /**
     * Service to add a StudentParent object.
     * 
     * @param studentParent StudentParent object to add
     * @return studentParent object
     * @throws AkuraAppException - throw this
     */
    public StudentParent addStudentParent(final StudentParent studentParent) throws AkuraAppException {

        return (StudentParent) this.studentParentDao.save(studentParent);
    }
    
    /**
     * Service to view all StudentParent objects by studentId.
     * 
     * @param studentId StudentParent objects to find
     * @return List of StudentParent returns list of StudentParents.
     * @throws AkuraAppException - throw this
     */
    public List<StudentParent> getStudentParentListByStudentId(int studentId) throws AkuraAppException {

        return this.studentParentDao.getStudentParentsByStudentId(studentId);
        
    }
    
    /**
     * Service to view all Donation objects by studentId.
     * 
     * @param studentId Donation objects to find
     * @return List of Donation returns list of Donations.
     * @throws AkuraAppException - throw this
     */
    public List<Donation> getDonationListByStudentId(int studentId) throws AkuraAppException {

        return this.donationDao.getDonationsByStudentId(studentId);
        
    }
    
    /**
     * Service to view parent objects by NIC number.
     * 
     * @param nicNo parent objects to find
     * @return parent object returns of Parent.
     * @throws AkuraAppException - throw this
     */
    public Parent getParentByNIC(String nicNo) throws AkuraAppException {

        return this.parentDao.getParentInfoByNIC(nicNo);
    }
    
    /**
     * Gets the parent donations by donation id.
     * 
     * @param donationId the donation id
     * @return the parent donations by donation id
     * @throws AkuraAppException throw the exception
     */
    public List<ParentDonation> getParentDonationsByDonationId(int donationId) throws AkuraAppException {

        return parentDonationDao.getParentDonationsByDonationId(donationId);
    }
    
    /** {@inheritDoc} */
    public boolean isNICnoExist(String nicNO) throws AkuraAppException {

        boolean isNICnoExist = false;
        Parent parent = parentDao.getParentInfoByNIC(nicNO);
        if (parent != null) {
            isNICnoExist = true;
        }
        return isNICnoExist;
        
    }
    
    /** {@inheritDoc} */
    public List<Student> getAllChilds(String nicNo) throws AkuraAppException {

        Parent parent = parentDao.getParentInfoByNIC(nicNo);
        List<Student> studentList = null;
        if (parent != null) {
            studentList = studentParentDao.getStudentByParnetId(parent.getParentId());
            
        }
        
        return studentList;
    }
    
    /** {@inheritDoc} */
    public Parent updateParent(Parent parent) throws AkuraAppException {

        parentDao.update(parent);
        
        return parent;
    }
    
    /** {@inheritDoc} */
    public void deleteStudentParent(StudentParent studentParet) throws AkuraAppException {

        studentParentDao.delete(studentParet);
    }
    
    /** {@inheritDoc} */
    public boolean isUsedInOtherStudent(Parent parent, int studentID) throws AkuraAppException {

        return studentParentDao.isUsedInOtherStudent(parent.getNationalIdNo(), studentID);
    }
    
    /** {@inheritDoc} */
    public boolean isUsedInSameStudent(Parent parent, int studentID) throws AkuraAppException {

        return studentParentDao.isUsedInSameStudent(parent.getNationalIdNo(), studentID);
    }
    
    /** {@inheritDoc} */
    public List<StudentParent> getParentListBy(int studentId, Character relationship) throws AkuraAppException {

        return studentParentDao.getParentListBy(studentId, relationship);
        
    }
    
    /** {@inheritDoc} */
    public List<Student> getStudentListBy(String nic, Character relationship) throws AkuraAppException {

        return studentParentDao.getStudentListBy(nic, relationship);
    }
    
    /** {@inheritDoc} */
    public boolean isExistsDonation(Donation donation) throws AkuraAppException {

        boolean isExistsDonation =
                !donationDao.getDonations(donation.getPurpose(), donation.getAmount(), donation.getDate(),
                        donation.getDonationType().getDonationTypeId()).isEmpty();
        
        if (donation.getDonationId() != 0 && isExistsDonation) {
            
            Donation oldDonationObj = (Donation) donationDao.findById(Donation.class, donation.getDonationId());
            if (oldDonationObj.getPurpose().equals(donation.getPurpose())
                    && oldDonationObj.getAmount().equals(donation.getAmount())
                    && oldDonationObj.getDate().equals(donation.getDate())
                    && oldDonationObj.getDonationType().getDonationTypeId() == donation.getDonationType()
                            .getDonationTypeId()) {
                isExistsDonation = false;
            }
        }
        return isExistsDonation;
    }
}
