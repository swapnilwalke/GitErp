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

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.common.BaseCommonTest;
import com.virtusa.akura.api.dto.Position;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * PositionDaoImplTest is to test all the persistent related functionalities for the position object.
 * 
 * @author Virtusa Corporation
 */
public class PositionDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds positionDAO instance of {@link PositionDao}.
     */
    @Autowired
    private PositionDao positionDAO;
    
    /**
     * Holds position instance of {@link Position}.
     */
    private Position position;
    
    /**
     * Instantiate the set up method.
     * 
     * @throws java.lang.Exception {@link Exception}
     */
    @Before
    public final void setUp() throws Exception {

        position = new Position();
        position.setDescription("Member");
    }
    
    /**
     * Test method for save Position.
     */
    @Test
    public final void testAddPosition() {

        try {
            Position addPosition = this.positionDAO.save(position);
            assertNotNull(" Added Position object can not be null ", addPosition);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update Position. This test method covers test for get Position by id too.
     */
    @Test
    public final void testModifyPosition() {

        try {
            Position updatePosition = this.positionDAO.save(position);
            assertNotNull(" Added Position object can not be null ", updatePosition);
            
            updatePosition.setDescription("Senior Member");
            this.positionDAO.update(updatePosition);
            
            Position findPosition = (Position) positionDAO.findById(Position.class, updatePosition.getPositionId());
            assertEquals("Position object has been modified successfully", updatePosition.getDescription(),
                    findPosition.getDescription());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available Positions.
     */
    @Test
    public final void testGetPositions() {

        try {
            Position addPosition = this.positionDAO.save(position);
            assertNotNull(" Added Position object can not be null ", addPosition);
            
            List<Position> positions = this.positionDAO.findAll(Position.class);
            assertTrue("Received Position object list size cannot be zero", positions.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Tear down method for each test case. This handles delete Position.
     */
    @After
    public final void teardown() {

        try {
            this.positionDAO.delete(position);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
