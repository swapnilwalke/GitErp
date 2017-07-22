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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.StaffLeaveType;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * StaffLeaveTypeDaoImplTest is to test all the persistent related
 * functionalities for the StaffLeaveType object.
 * 
 * @author Virtusa Corporation
 */
public class StaffLeaveTypeDaoImplTest extends BaseCommonTest {

	/**
	 * Holds staffLeaveTypeDAO instance of {@link StaffLeaveTypeDao}.
	 */
	@Autowired
	private StaffLeaveTypeDao staffLeaveTypeDAO;

	/**
	 * Holds staffLeaveType instance of {@link StaffLeaveType}.
	 */
	private StaffLeaveType staffLeaveType;

	/**
	 * Instantiate the set up method.
	 * 
	 * @throws java.lang.Exception
	 *             {@link Exception}
	 */
	@Before
	public final void setUp() throws Exception {

		staffLeaveType = new StaffLeaveType();
		staffLeaveType.setDescription("Annual");
		staffLeaveType.setMaxStaffLeaves(0);
	}

	/**
	 * Test method for save StaffLeaveType.
	 */
	@Test
	public final void testAddStaffLeaveType() {

		try {
			StaffLeaveType addStaffLeaveType = this.staffLeaveTypeDAO
					.save(staffLeaveType);
			assertNotNull("Added StaffLeaveType object can not be null ",
					addStaffLeaveType);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Test method for update StaffLeaveType. This test method covers test for
	 * get StaffLeaveType by id too.
	 */
	@Test
	public final void testModifyStaffLeaveType() {

		try {
			StaffLeaveType updateStaffLeaveType = this.staffLeaveTypeDAO
					.save(staffLeaveType);
			assertNotNull("Added StaffLeaveType object can not be null ",
					updateStaffLeaveType);

			updateStaffLeaveType.setDescription("Annual");
			updateStaffLeaveType.setMaxStaffLeaves(0);
			this.staffLeaveTypeDAO.update(updateStaffLeaveType);

			StaffLeaveType findStaffLeaveType = (StaffLeaveType) staffLeaveTypeDAO
					.findById(StaffLeaveType.class,
							updateStaffLeaveType.getStaffLeaveTypeId());
			assertEquals(
					"StaffLeaveType object has been modified successfully",
					updateStaffLeaveType.getDescription(),
					findStaffLeaveType.getDescription());
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Test method for get all the available StaffLeaveTypes.
	 */
	@Test
	public final void testGetStaffLeaveTypes() {

		try {
			StaffLeaveType addStaffLeaveType = this.staffLeaveTypeDAO
					.save(staffLeaveType);
			assertNotNull(addStaffLeaveType);

			List<StaffLeaveType> staffLeaveTypes = this.staffLeaveTypeDAO
					.findAll(StaffLeaveType.class);
			assertTrue(
					"Received StaffLeaveType object list size cannot be zero",
					staffLeaveTypes.size() > 0);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Tear down method for each test case. This handles delete StaffLeaveType.
	 */
	@After
	public final void teardown() {

		try {
			this.staffLeaveTypeDAO.delete(staffLeaveType);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
}
