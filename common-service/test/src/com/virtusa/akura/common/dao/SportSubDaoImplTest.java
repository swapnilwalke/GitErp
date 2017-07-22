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

package com.virtusa.akura.common.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This is the test class for the Sport Sub entity.
 * 
 * @author Virtusa Corporation
 */

public class SportSubDaoImplTest extends BaseCommonTest {

	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private SportSubDao sportSubDao;

	/**
	 * Instance of Sport Sub Dao.
	 */
	private SportSub sportSub;

	/**
	 * Instance of a Sportsub.
	 */
	private SportSub newSportSub;

	/**
	 * Instantiate the setup method.
	 * 
	 * @throws Exception
	 *             when an error has occurred during processing.
	 */
	@Before
	public final void setUp() throws Exception {

		// Instantiates a SportSub object.
		sportSub = new SportSub();
		sportSub.setDescription("Under 20");
		sportSub.setModifiedTime(new Date());
	}

	/**
	 * Test method to check if a SportSub object was successfully added to the
	 * db.
	 */
	@Test
	public final void testAddSport() {

		try {
			newSportSub = sportSubDao.save(sportSub);
			assertNotNull("new Sport subcategory should not be null "
					+ newSportSub);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check if a Sport Sub object was successfully updated in
	 * the db.
	 */
	@Test
	public final void testUpdateSport() {

		try {

			String newDescription = "Under-21";

			newSportSub = sportSubDao.save(sportSub);
			assertNotNull("new Sport sub category should not be null "
					+ newSportSub);

			newSportSub.setDescription(newDescription);
			sportSubDao.update(newSportSub);

			SportSub updatedSportSub = (SportSub) sportSubDao.findById(
					SportSub.class, newSportSub.getSportSubId());
			assertEquals(newDescription, updatedSportSub.getDescription());

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check if a collection of SportSub objects was successfully
	 * loaded from the database.
	 */
	@Test
	public final void testFindAllSportSubs() {

		try {
			newSportSub = sportSubDao.save(sportSub);
			assertNotNull("new SportSub should not be null " + newSportSub);

			List<SportSub> sportSubList = sportSubDao.findAll(SportSub.class);
			assertNotNull(sportSubList.size());

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Test the Save or update all sportSub.
	 */
	@Test
	public final void testSaveOrUpdateAll() {
		try {
			List<SportSub> sportList = new ArrayList<SportSub>();
			sportList.add(sportSub);
			
			sportSubDao.saveOrUpdateAll(sportList);
			List<SportSub> sportLists = sportSubDao.findAll(SportSub.class);
			
			assertTrue(sportLists.size() > 0);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Teardown method invoked at the end of each testcase automatically by
	 * Junit.
	 */
	@After
	public final void teardown() {

		try {
			sportSubDao.delete(sportSub);
		} catch (AkuraAppException e) {

			e.printStackTrace();
		}
	}
}
