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
 * This class is to associates all the properties of Section domain object.
 * 
 * @author Virtusa Corporation
 */

public class StudentStatus extends BaseDomain implements Serializable {

	/**
	 * Holds serial version id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * property sectionId type integer.
	 */
	private int studentStatusId;

	/**
	 * property description type String.
	 */
	private String description;

	/**
	 * Default constructor.
	 */
	public StudentStatus() {

	}

	/**
	 * @return the studentStatusId
	 */
	public int getStudentStatusId() {
		return studentStatusId;
	}

	/**
	 * @param newStudentStatusId
	 *            the studentStatusId to set
	 */
	public void setStudentStatusId(int newStudentStatusId) {
		this.studentStatusId = newStudentStatusId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param newDescription
	 *            the description to set
	 */
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

}
