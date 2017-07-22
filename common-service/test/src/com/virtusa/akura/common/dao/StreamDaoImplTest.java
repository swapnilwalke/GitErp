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
import com.virtusa.akura.api.dto.Stream;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This test class, tests all the persistence related functionality for the
 * Stream domain object.
 * 
 * @author Virtusa Corporation
 */
public class StreamDaoImplTest extends BaseCommonTest {

	/**
	 * This instance will be dependency injected by type.
	 */
	@Autowired
	private StreamDao streamDao;

	/**
	 * Represents an instance of Stream.
	 */
	private Stream stream;

	/**
	 * Instantiate the setup method.
	 * 
	 * @throws Exception
	 *             when an error has occurred during processing.
	 */
	@Before
	public final void setUp() throws Exception {

		// Instantiates Stream object.
		stream = new Stream();
		stream.setDescription("Maths");
		stream.setModifiedTime(new Date());
	}

	/**
	 * Test method for
	 * {@link com.virtusa.akura.common.dao.streamDaoImpl# addStream(com.virtusa.akura.api.dto.Stream)}
	 * .
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testAddStream() throws AkuraAppException {

		try {
			Stream addStream = streamDao.save(stream);
			assertNotNull("Stream Type Object should not null ", addStream);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.virtusa.akura.common.dao.streamDaoImpl# updateStream(com.virtusa.akura.api.dto.Stream)}
	 * .
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testUpdateStream() throws AkuraAppException {

		try {
			Stream newStream = streamDao.save(stream);
			assertNotNull(newStream);
			newStream.setDescription("Science");
			streamDao.update(newStream);
			Stream findStream = (Stream) streamDao.findById(Stream.class,
					newStream.getStreamId());
			assertEquals(newStream.getDescription(),
					findStream.getDescription());
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.virtusa.akura.common.dao.streamDaoImpl# findStream(java.lang.Integer)}
	 * .
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testFindStream() throws AkuraAppException {

		try {
			Stream newStream = streamDao.save(stream);
			assertNotNull(newStream);
			Stream findStream = (Stream) streamDao.findById(Stream.class,
					newStream.getStreamId());
			assertNotNull("Stream Type Object should not null ", findStream);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test method for
	 * {@link com.virtusa.akura.common.dao.streamDaoImpl# getStreamList()}.
	 * 
	 * @throws AkuraAppException
	 *             - the exception that occurred.
	 */
	@Test
	public final void testFindAllStream() throws AkuraAppException {

		try {
			Stream newStream = streamDao.save(stream);
			assertNotNull(newStream);
			List<Stream> streamList = streamDao.findAll(Stream.class);
			assertTrue(streamList.size() > 0);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test the method searchStream(String streamDescription).
	 * 
	 * @throws AkuraAppException
	 *             - Throws when fails.
	 */
	@Test
	public final void testSearchStream() throws AkuraAppException {

		try {
			Stream newStream = streamDao.save(stream);
			assertNotNull(newStream);

			List<Stream> searchResults = streamDao.searchStream("Maths");
			assertNotNull(searchResults.get(0));
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
			streamDao.delete(stream);
		} catch (AkuraAppException e) {
			e.printStackTrace();
		}
	}
}
