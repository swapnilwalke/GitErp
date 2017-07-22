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
import com.virtusa.akura.api.dto.ClubSociety;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * ClubSocietyDaoImplTest is to test all the persistent related functionalities for
 * the ClubSociety object.
 * 
 * @author Virtusa Corporation
 * 
 */
public class ClubSocietyDaoImplTest extends BaseCommonTest{
    
    /**
     * This instance will be dependency injected by type.
     *
     */
    @Autowired
    private ClubSocietyDao clubSocietyDaoTarget;
    
    /**
     * Instance of a ClubSociety entity.
     */
    private ClubSociety clubSociety;
    
    /**
     * Instantiate the setup method.
     *
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {
        //Instantiates a ClubSociety object.
        clubSociety = new ClubSociety(); 
        clubSociety.setName("NewClub");
        clubSociety.setDescription("NewDes");
        clubSociety.setModifiedTime(new Date());
    }
    
    /**
     * Test method to check if a ClubSociety object was successfully added 
     * to the db.    
     */
    @Test
    public final void testAddClubSociety() {
        try {
            ClubSociety newClubSociety = clubSocietyDaoTarget.save(clubSociety);
            assertNotNull("new ClubSociety should not be null " + newClubSociety);
        } catch (AkuraAppException e) {   
            e.printStackTrace();
        }
    }
     
    /**
     * Test method to check if a ClubSociety object was successfully updated in the db.  
     * Also covering test for find ClubSociety object by id too.    
     */
    @Test
    public final void testUpdateClubSociety() {
        try {
            
            String newDescription = "NewDescription";
            
            ClubSociety newClubSociety = clubSocietyDaoTarget.save(clubSociety);
            assertNotNull("new ClubSociety should not be null " + newClubSociety);
            
            newClubSociety.setDescription(newDescription);
            clubSocietyDaoTarget.update(newClubSociety);
            
            ClubSociety updatedClubSociety = (ClubSociety)
                    clubSocietyDaoTarget.findById(ClubSociety.class, newClubSociety.getClubSocietyId());
            assertEquals(newDescription, updatedClubSociety.getDescription());
            
        } catch (AkuraAppException e) {   
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a collection of ClubSociety objects was successfully
     * loaded from the database. 
     */
    @Test
    public final void testFindAllClubSocieties(){
        
        try {
            ClubSociety newClubSociety = clubSocietyDaoTarget.save(clubSociety);
            assertNotNull("new ClubSociety should not be null " + newClubSociety);
            
            List<ClubSociety> clubSocietyList = clubSocietyDaoTarget.findAll(ClubSociety.class);    
            assertNotNull(clubSocietyList.size());
            
        } catch (AkuraAppException e) {   
            e.printStackTrace();
        }
    }
    
    /**
     * Teardown method invoked at the end of each testcase automatically by Junit. 
     */
    @After
    public final void teardown() {
        try {
            clubSocietyDaoTarget.delete(clubSociety);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
