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

import java.io.Serializable;

/**
 * This class is to associates properties of PrefectType.
 * 
 * @author Virtusa Corporation
 */
public class StaffLeaveType extends BaseDomain implements Serializable {

	/** attribute for holding string. */
	private static final String STAFF_LEAVE_TYPE_DESCRIPTION = ", description=";

	/** attribute for holding string. */
	private static final String STAFF_LEAVE_TYPE_ID = "staffLeaveTypeId=";

	/** attribute for holding string. */
	private static final String MAX_STAFF_LEAVES = ", maxStaffLeaves=";

	/**
	 * Generated serial id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Holds staff leave type id.
	 */
	private int staffLeaveTypeId;

	/**
	 * Holds description.
	 */
	private String description;

	/**
	 * Holds MaxStaffLeaves.
	 */
	private int maxStaffLeaves;
	
	/** gender of the staff. */
    private char gender;
    
     
    
	/**
	 * Default constructor.
	 */
	public StaffLeaveType() {

	}

	/**
	 * Overloaded constructor.
	 * 
	 * @param intStaffLeaveTypeId
	 *            type int
	 * @param strDescription
	 *            type string
	 */
	public StaffLeaveType(int intStaffLeaveTypeId, String strDescription) {

		this.staffLeaveTypeId = intStaffLeaveTypeId;
		this.description = strDescription;
	}

	/**
	 * @return the staffLeaveTypeId
	 */
	public int getStaffLeaveTypeId() {
		return staffLeaveTypeId;
	}

	/**
	 * @param intstaffLeaveTypeId
	 *            the staffLeaveTypeId to set
	 */
	public void setStaffLeaveTypeId(int intstaffLeaveTypeId) {
		this.staffLeaveTypeId = intstaffLeaveTypeId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
     * @return the gender
     */
    public char getGender() {
    
        return gender;
    }

    /**
     * @param gender - set the gender
     * 
     */
    public void setGender(char gender) {
    
        this.gender = gender;
    }

    /**
	 * Returns a string representation of the object.
	 * 
	 * @param strdescription - the description to set
	 * 
	 */
	public void setDescription(String strdescription){
		this.description = strdescription;
	}

	/**
	 * Returns a string representation of the object.
	 * 
	 * @return - the current object details.
	 */
	@Override
	public String toString() {

		return STAFF_LEAVE_TYPE_ID + staffLeaveTypeId
				+ STAFF_LEAVE_TYPE_DESCRIPTION + description + MAX_STAFF_LEAVES
				+ maxStaffLeaves;
	}

	/**
	 * @param intmaxStaffLeaves
	 *            the maxStaffLeaves to set
	 */
	public void setMaxStaffLeaves(int intmaxStaffLeaves) {
		this.maxStaffLeaves = intmaxStaffLeaves;
	}

	/**
	 * @return the maxStaffLeaves
	 */
	public int getMaxStaffLeaves() {
		return maxStaffLeaves;
	}
}
