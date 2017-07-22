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
import com.virtusa.akura.api.dto.Sport;
import com.virtusa.akura.api.dto.SportCategory;
import com.virtusa.akura.api.dto.SportSub;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * Test class for SportCategoryDaoImpl.
 * 
 * @author Virtusa Corporation
 */

public class SportCategoryDaoImplTest extends BaseCommonTest {
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SportDao sportDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SportCategoryDao sportCategoryDaoTarget;
    
    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private SportSubDao sportSubDaoTarget;
    
    /**
     * Instance of a Sport category entity.
     */
    private SportCategory sportCategory;
    
    /**
     * Instance of a Sport entity.
     */
    private Sport sport;
    
    /**
     * Instance of a SportSub entity.
     */
    private SportSub sportSub;
    
    /**
     * Instance of a Sport entity.
     */
    private Sport sport2;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        sport = new Sport();
        sport.setDescription("Karate");
        sport.setModifiedTime(new Date());
        
        sportSub = new SportSub();
        sportSub.setDescription("First 12-Team");
        sportSub.setModifiedTime(new Date());
        
        sport2 = new Sport();
        sport2.setDescription("Swimming");
        sport2.setModifiedTime(new Date());
        
    }
    
    /**
     * Test method to check if a Sport Category object was successfully added to the db.
     */
    @Test
    public final void testAddSportCategory() {

        try {
            // first add the sport
            Sport newSport = sportDaoTarget.save(sport);
            assertNotNull("new Sport should not be null " + newSport);
            
            // next add the sport sub category - example - Under-18
            SportSub newSportSub = sportSubDaoTarget.save(sportSub);
            assertNotNull("new Sport Sub should not be null " + newSportSub);
            
            sportCategory = new SportCategory(newSport, newSportSub);
            sportCategory.setModifiedTime(new Date());
            
            SportCategory newSportCategory = sportCategoryDaoTarget.save(sportCategory);
            assertNotNull("new Sport Category should not be null " + newSportCategory);
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a SportCategory object was successfully updated in the db.
     */
    @Test
    public final void testUpdateSportCategory() {

        try {
            Sport newSport = sportDaoTarget.save(sport);
            assertNotNull("new Sport should not be null " + newSport);
            
            // next add the sport sub category - example - Under-18
            SportSub newSportSub = sportSubDaoTarget.save(sportSub);
            assertNotNull("new Sport Sub should not be null " + newSportSub);
            
            sportCategory = new SportCategory(newSport, newSportSub);
            sportCategory.setModifiedTime(new Date());
            
            SportCategory newSportCategory = sportCategoryDaoTarget.save(sportCategory);
            assertNotNull("new Sport Category should not be null " + newSportCategory);
            
            Sport updatedSport = sportDaoTarget.save(sport2);
            assertNotNull("new Sport should not be null " + updatedSport);
            
            newSportCategory.setSport(updatedSport);
            sportCategoryDaoTarget.update(newSportCategory);
            
            SportCategory updatedSportCategory =
                    (SportCategory) sportCategoryDaoTarget.findById(SportCategory.class,
                            newSportCategory.getSportCategoryId());
            assertEquals(updatedSport.getSportId(), updatedSportCategory.getSport().getSportId());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a collection of SportCategory objects was successfully loaded from the
     * database.
     */
    @Test
    public final void testFindAllSportCategories() {

        try {
            Sport newSport = sportDaoTarget.save(sport);
            assertNotNull("new Sport should not be null " + newSport);
            
            SportSub newSportSub = sportSubDaoTarget.save(sportSub);
            assertNotNull("new Sport Sub should not be null " + newSportSub);
            
            sportCategory = new SportCategory(newSport, newSportSub);
            sportCategory.setModifiedTime(new Date());
            
            SportCategory newSportCategory = sportCategoryDaoTarget.save(sportCategory);
            assertNotNull("new Sport Category should not be null " + newSportCategory);
            
            List<SportCategory> sportCategoryList = sportCategoryDaoTarget.findAll(SportCategory.class);
            assertNotNull(sportCategoryList.size());
            
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * Test the find sport category by sportId.
	 */
	@Test
	public final void testFindSportCategory() {
		try {
			Sport addSport = sportDaoTarget.save(sport);
			assertNotNull("this sport should not be null ", addSport);

			SportSub addSportSub = sportSubDaoTarget.save(sportSub);
			assertNotNull("this sub sport should not be null ", addSportSub);

			sportCategory = new SportCategory(addSport, addSportSub);
			sportCategory.setModifiedTime(new Date());

			SportCategory newSportCategory = sportCategoryDaoTarget
					.save(sportCategory);
			assertNotNull("Sport Category should not be null ",
					newSportCategory);

			List<SportCategory> sportCategoryList = sportCategoryDaoTarget
					.findSportCategory(sport.getSportId(),
							sportSub.getSportSubId());
			assertNotNull(sportCategoryList.size());

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
            sportCategoryDaoTarget.delete(sportCategory);
            sportSubDaoTarget.delete(sportSub);
            sportDaoTarget.delete(sport);
            sportDaoTarget.delete(sport2);
        } catch (AkuraAppException e) {
            
            e.printStackTrace();
        }
    }
}
