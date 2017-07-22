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
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * SportDaoImplTest is to test all the persistent related functionalities for
 * the sport object.
 * 
 * @author Virtusa Corporation
 * 
 */
public class SportDaoImplTest extends BaseCommonTest {

	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private SportDao sportDaoTarget;

	/**
	 * Instance of a Sport entity.
	 */
	private Sport sport;


	/**
	 * A new sport.
	 */
	private Sport newSport;

	/**
	 * Instantiate the setup method.
	 * 
	 * @throws Exception
	 *             when an error has occurred during processing.
	 */
	@Before
	public final void setUp() throws Exception {

		// Instantiates a Sport object.
		sport = new Sport();
		sport.setDescription("Volleyball");
		sport.setModifiedTime(new Date());

	}

	/**
	 * Test method to check if a Sport object was successfully added to the db.
	 * 
	 */
	@Test
	public final void testAddSport() {

		try {
			newSport = sportDaoTarget.save(sport);
			assertNotNull("new Sport should not be null " + newSport);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}

	}

	/**
	 * Test method to check if a Sport object was successfully updated in to the
	 * db, Also covering test for find Sport object by id too.
	 * 
	 */
	@Test
	public final void testUpdateSport() {

		try {

			String newDescription = "Netball";

			newSport = sportDaoTarget.save(sport);
			assertNotNull("new Sport should not be null " + newSport);

			newSport.setDescription(newDescription);
			sportDaoTarget.update(sport);

			Sport updatedSport = (Sport) sportDaoTarget.findById(Sport.class,
					newSport.getSportId());
			assertEquals(newDescription, updatedSport.getDescription());

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method to check if a collection of Sport objects was successfully
	 * loaded from the database.
	 * 
	 */
	@Test
	public final void testFindAllSports() {

		try {
			newSport = sportDaoTarget.save(sport);
			assertNotNull("new Sport should not be null " + newSport);

			List<Sport> sportList = sportDaoTarget.findAll(Sport.class);
			assertNotNull(sportList.size());

		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test the Save or update all sports.
	 */
	@Test
	public final void testSaveOrUpdateAll() {
		try {
			List<Sport> sportList = new ArrayList<Sport>();
			sportList.add(sport);
			
			sportDaoTarget.saveOrUpdateAll(sportList);
			List<Sport> sportLists = sportDaoTarget.findAll(Sport.class);
			
			assertTrue(sportLists.size() > 0);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Teardown method invoked at the end of each testcase automatically by
	 * Junit.
	 * 
	 */
	@After
	public final void teardown() {

		try {
			sportDaoTarget.delete(sport);
			
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
}
