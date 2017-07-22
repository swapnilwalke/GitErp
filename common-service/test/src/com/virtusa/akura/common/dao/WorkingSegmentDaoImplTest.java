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
import com.virtusa.akura.api.dto.WorkingSegment;
import com.virtusa.akura.api.exception.AkuraAppException;


/**
 * This test class, tests all the persistence related functionality for the
 * working segment domain object.
 * 
 * @author Virtusa Corporation
 */

public class WorkingSegmentDaoImplTest extends BaseCommonTest{


    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private WorkingSegmentDao workingSegmentDaoTarget;

    /**
     * Instance of a working segment entity.
     */
    private WorkingSegment segment;

    /**
     * Instantiate the setup method.
     *
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {
        //Instantiates WorkingSegment object.
        segment = new WorkingSegment();
        segment.setDescription("Telecommunications");
        segment.setModifiedTime(new Date());
    }

    /**
     * Test method to check if a working segment object was successfully added
     * to the db.
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testAddWorkingSegment() throws AkuraAppException {
        try {
            
            WorkingSegment newSegment = workingSegmentDaoTarget.save(segment);
            assertNotNull("new working segment should not be null " + newSegment);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }


    /**
     * Test method to check if a working segment object was successfully updated
     * to the db.
     * @throws Exception - the exception that occurred.
     */
    @Test
    public final void testUpdateWorkingSegment() throws Exception{
        try {
            String newDescription = "Telecom";
            WorkingSegment newSegment = workingSegmentDaoTarget.save(segment);
            assertNotNull("new working segment should not be null " + newSegment);

            newSegment.setDescription(newDescription);
            workingSegmentDaoTarget.update(newSegment);
            WorkingSegment updatedValue = (WorkingSegment)workingSegmentDaoTarget.
            findById(WorkingSegment.class, newSegment.getWorkingSegmentId());
            assertEquals("Telecom", updatedValue.getDescription());

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }

    /**
     * Test method to check load all the working segment records from the db.
     * @throws Exception - the exception that occurred.
     */
    @Test
    public final void testFindAllRecords() throws Exception{
        try {
            WorkingSegment newSegment = workingSegmentDaoTarget.save(segment);
            assertNotNull("new working segment should not be null " + newSegment);

            List<WorkingSegment> workingSegmentList = workingSegmentDaoTarget.findAll
            (WorkingSegment.class);
            assertNotNull(workingSegmentList.size());

        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }


    /**
     * Test method to check if a collection of objects are saved / updated
     * to the db.
     * @throws Exception - the exception that occurred.
     */
    @Test
    public final void testSaveWorkingSegments() throws Exception{
        try {
            WorkingSegment newSegment = workingSegmentDaoTarget.save(segment);
            assertNotNull("new working segment should not be null " + newSegment);
            
            List<WorkingSegment> segmentList = new ArrayList<WorkingSegment>();
            segmentList.add(newSegment);
            
            workingSegmentDaoTarget.saveOrUpdateAll(segmentList);
            List<WorkingSegment> newSegmentList = workingSegmentDaoTarget.findAll(WorkingSegment.class);
            assertTrue(newSegmentList.size() > 0);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * teardown method for each test case.
     * @throws Exception - the exception.
     */
    @After
    public final void teardown() throws Exception {
        try {
            workingSegmentDaoTarget.delete(segment);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
