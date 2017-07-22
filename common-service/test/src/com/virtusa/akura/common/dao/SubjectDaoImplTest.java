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
import com.virtusa.akura.api.dto.Subject;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This test class, tests all the persistence related functionality for the
 * Subject domain object.
 * 
 * @author Virtusa Corporation
 */
public class SubjectDaoImplTest extends BaseCommonTest {
	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private SubjectDao subjectDao;

	/**
	 * Represents an instance of Subject.
	 */
	private Subject subject;

	/**
	 * @throws Exception
	 *             when an error has occurred during processing.
	 */
	@Before
	public final void setUp() throws Exception {
		// create a subject object.
		subject = new Subject();
		subject.setDescription("Ecology1");
		subject.setModifiedTime(new Date());
	}

	/**
	 * Test method for
	 * {@link com.virtusa.akura.common.dao.subjectDaoImpl# addSubject(com.virtusa.akura.api.dto.Subject)}
	 * .
	 * 
	 * @throws Exception
	 *             - the exception that occurred.
	 */
	@Test
	public final void testAddSubject() throws Exception {
		try {
			Subject newSubject = subjectDao.save(subject);
			assertNotNull("Subject Type object should not null ", newSubject);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.virtusa.akura.common.dao.StreamDaoImpl# updateSubject(com.virtusa.akura.api.dto.Subject)}
	 * .
	 * 
	 * @throws Exception
	 *             - the exception that occurred.
	 */
	@Test
	public final void testUpdateSubject() throws Exception {
		try {
			Subject newSubject = subjectDao.save(subject);
			assertNotNull(newSubject);
			newSubject.setDescription("Maths");
			subjectDao.update(newSubject);
			Subject findSubject = (Subject) subjectDao.findById(Subject.class,
					newSubject.getSubjectId());
			assertEquals(newSubject.getDescription(),
					findSubject.getDescription());
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.virtusa.akura.common.dao.StreamDaoImpl# getSubjectList()}.
	 * 
	 * @throws Exception
	 *             - the exception that occurred.
	 */
	@Test
	public final void testGetSubjectList() throws Exception {
		try {
			Subject newSubject = subjectDao.save(subject);
			assertNotNull(newSubject);
			List<Subject> subjectList = subjectDao.findAll(Subject.class);
			assertTrue(subjectList.size() > 0);
		} catch (AkuraAppException e) {
			// delete the object.
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.virtusa.akura.common.dao.subjectDaoImpl# findSubject(java.lang.Integer)}
	 * .
	 * 
	 * @throws Exception
	 *             - the exception that occurred.
	 */
	@Test
	public final void testFindSubject() throws Exception {
		try {
			Subject newSubject = subjectDao.save(subject);
			assertNotNull(newSubject);
			Subject findSubject = (Subject) subjectDao.findById(Subject.class,
					newSubject.getSubjectId());
			assertNotNull("Subject Type object should not null ", findSubject);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for(@link
	 * {@link com.virtusa.akura.common.dao.subjectDaoImpl# searchSubject(java.lang.String)}
	 * .
	 * 
	 * @throws AkuraAppException
	 *             - if failed.
	 */
	@Test
	public final void testSearchSubject() throws AkuraAppException {
		try {
			Subject newSubject = subjectDao.save(subject);
			assertNotNull(newSubject);

			List<Subject> subjectList = subjectDao.searchSubject("Ecology1");
			assertEquals("Ecology1", subjectList.get(0).getDescription());

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
	public final void teardown() throws AkuraAppException {

		try {
			subjectDao.delete(subject);
		} catch (AkuraAppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
