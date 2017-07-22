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

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.EducationalQualification;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 * 
 */
public class EducationalQualificationDaoImplTest extends BaseCommonTest {

	/**
	 * Holds educationalQualificationDAO instance of {@link EducationalQualificationDao}.
	 */
	@Autowired
	private EducationalQualificationDao educationalQualificationDAO;

	/**
	 * Holds educationalQualification instance of {@link EducationalQualification}.
	 */
	private EducationalQualification educationalQualification;

	/**
	 * Current date of {@link Date}.
	 */
	private Date date;

	/**
	 * Instantiate the set up method.
	 * 
	 * @throws java.lang.Exception {@link Exception}
	 */
	@Before
	public final void setUp() throws Exception {
		date = new Date();

		educationalQualification = new EducationalQualification();
		educationalQualification.setDescription("BSc in ITDDD");
		educationalQualification.setModifiedTime(date);
	}

	/**
	 * Test method for save EducationalQualification.
	 */
	@Test
	public final void testAddEducationalQualification() {
		try {
			EducationalQualification addEducationalQualification = this.educationalQualificationDAO
					.save(educationalQualification);
			assertNotNull(" Added EducationalQualification object can not be null ",
					addEducationalQualification);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Test method for update EducationalQualification. This test method covers test for
	 * get EducationalQualification by id too.
	 */
	@Test
	public final void testModifyEducationalQualification() {
		try {
			EducationalQualification updateEducationalQualification = this.educationalQualificationDAO
					.save(educationalQualification);
			assertNotNull(updateEducationalQualification);

			updateEducationalQualification.setDescription("MBA");
			this.educationalQualificationDAO.update(updateEducationalQualification);

			EducationalQualification findEducationalQualification = (EducationalQualification) 
			educationalQualificationDAO.findById(EducationalQualification.class,
							updateEducationalQualification.getEducationalQualificationId());
			assertEquals(updateEducationalQualification.getDescription(),
					findEducationalQualification.getDescription());
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Test method for get all the available EducationalQualifications.
	 */
	@Test
	public final void testGetEducationalQualifications() {
		try {
			EducationalQualification addEducationalQualification = this.educationalQualificationDAO
					.save(educationalQualification);
			assertNotNull(addEducationalQualification);

			List<EducationalQualification> educationalQualifications = this.educationalQualificationDAO
					.findAll(EducationalQualification.class);
			assertTrue(educationalQualifications.size() > 0);
		} catch (AkuraAppException e) {
			e.getStackTrace();
		}
	}

	/**
	 * Tear down method for each test case. This handles delete EducationalQualification.
	 */
	@After
	public final void teardown() {
		try {
			this.educationalQualificationDAO.delete(educationalQualification);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
}
