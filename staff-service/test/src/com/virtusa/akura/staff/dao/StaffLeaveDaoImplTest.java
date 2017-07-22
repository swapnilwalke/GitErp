/*
 * < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>
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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.staff.BaseStaffTest;
import com.virtusa.akura.api.dto.Staff;
import com.virtusa.akura.api.dto.StaffCategory;
import com.virtusa.akura.api.dto.StaffLeave;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.common.dao.StaffCategoryDao;

/**
 * This test class, tests all the functionalities for the StaffLeaveDaoImpl.
 * 
 * @author Virtusa Corporation
 * 
 */

public class StaffLeaveDaoImplTest extends BaseStaffTest {

	/** Date attribute for holding currentDate. */
	private Date currentDate = new Date();

	/** Holds StaffLeaveDao instance. */
	@Autowired
	private StaffLeaveDao staffLeaveDao;

	/** Holds StaffDao instance. */
	@Autowired
	private StaffDao staffDao;

	/** Holds StaffCategoryDao instance. */
	@Autowired
	private StaffCategoryDao staffCategoryDao;

	/** Holds StaffLeave instance. */
	private StaffLeave staffLeave;

	/** Holds Staff instance. */
	private Staff staff;

	/** Holds StaffCategory instance. **/
	private StaffCategory staffCategory;

	/**
	 * setting up resources.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Before
	public final void setUp() throws AkuraAppException {

		// Instantiate a staffCategory object.
		StaffCategory newStaffCategory = new StaffCategory();
		newStaffCategory.setAcademic(true);
		newStaffCategory.setDescription("StaffDaoImplTest Description*--*");
		staffCategory = staffCategoryDao.save(newStaffCategory);
		assertNotNull(" StaffCategory saved to db " + staffCategory);

		// Instantiate a staff object.
		Staff newStaff = new Staff();
		newStaff.setStaffCategory(staffCategory);
		newStaff.setRegistrationNo("StaffDaoImplTest RegNo*--*");
		newStaff.setDateOfHire(new Date());
		newStaff.setFullName("StaffDaoImplTest FullName");
		newStaff.setLastName("StaffDaoImplTest LastName");
		newStaff.setNationalID("StaffDaoImplTest ID*--*");
		newStaff.setDateOfBirth(new Date());
		newStaff.setAddress("StaffDaoImplTest Address");
		newStaff.setGender('M');

		staff = staffDao.save(newStaff);

		staffLeave = new StaffLeave();
		staffLeave.setStaffId(staff.getStaffId());
		staffLeave.setFromDate(currentDate);
		staffLeave.setToDate(currentDate);
		staffLeave.setReason("reason");
		staffLeave.setModifiedTime(currentDate);
		staffLeave.setNoOfDays(0.0f);

	}

	/**
	 * Test method to check if a student Staff Leave object was successfully
	 * added to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testAddStaffLeave() throws AkuraAppException {
		try {
			StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
			assertNotNull("newStaffLeave should not be null " + newStaffLeave);

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check if a student Staff Leave object was successfully
	 * updated to the db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@Test
	public void testUpdateStaffLeave() throws AkuraAppException {
		try {
			StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
			assertNotNull("newStaffLeave should not be null " + newStaffLeave);

			newStaffLeave.setReason("updatedReason");
			staffLeaveDao.update(newStaffLeave);
			StaffLeave updatedStaffLeave = (StaffLeave) staffLeaveDao.findById(
					StaffLeave.class, newStaffLeave.getStaffLeaveId());
			assertEquals("updatedReason", updatedStaffLeave.getReason());

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check load all the Staff newStaffLeave records from the
	 * db.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testViewAllStaffLeave() throws AkuraAppException {

		try {
			StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
			assertNotNull("newStaffLeave should not be null " + newStaffLeave);

			List<StaffLeave> newStaffLeaveList = staffLeaveDao
					.findAll(StaffLeave.class);
			assertNotNull(newStaffLeaveList.size());

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check Staff leave records by staff id from the db for
	 * given student ID and year.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testFindStaffLeaveByStaffId() throws AkuraAppException {
		try {
			StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
			assertNotNull("newStaffLeave should not be null " + newStaffLeave);

			List<StaffLeave> newStaffLeaveList = staffLeaveDao
					.findStaffLeaveByStaffId(newStaffLeave.getStaffId());
			assertNotNull(newStaffLeaveList.size());
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check Staff leave records by staff id from the db for
	 * given staff ID and year.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testFindFindStaffLeaveByDatePeriodAndStaffId()
			throws AkuraAppException {
		try {
			StaffLeave newStaffLeave = staffLeaveDao.save(staffLeave);
			assertNotNull("newStaffLeave should not be null " + newStaffLeave);

			List<StaffLeave> newStaffLeaveList = staffLeaveDao
					.findStaffLeaveByDatePeriodAndStaffId(
							newStaffLeave.getFromDate(),
							newStaffLeave.getToDate(),
							newStaffLeave.getStaffId());
			assertNotNull(newStaffLeaveList.size());
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for getAllowableStaffLeaveListByStaffTypeAndDate() to check
	 * results count.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testGetAllowableStaffLeaveListByStaffTypeAndDate()
			throws AkuraAppException {
		try {

			staffLeave.setStaffLeaveStatusId(Integer.valueOf(1));
			staffLeaveDao.save(staffLeave);
			List<StaffLeave> staffLeaveList = staffLeaveDao
					.getAllowableStaffLeaveListByStaffTypeAndDate(true,
							currentDate);

			assertTrue(staffLeaveList.size() > 0);

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Tear down method for each test case.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */

	@After
	public final void tearDown() throws AkuraAppException {

		try {
			staffLeaveDao.delete(staffLeave);
			staffDao.delete(staff);
			staffCategoryDao.delete(staffCategory);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

}
