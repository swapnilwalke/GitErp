/*
 * < �KURA, This application manages the daily activities of a Teacher and a Student of a School>
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
import com.virtusa.akura.api.dto.Section;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * ClubSocietyDaoImplTest is to test all the persistent related functionalities for
 * the ClubSociety object.
 * 
 * @author Virtusa Corporation
 * 
 */
public class SectionDaoImplTest extends BaseCommonTest{
    
    /**
     * This instance will be dependency injected by type.
     *
     */
    @Autowired
    private SectionDao sectionDaoTarget;
    
    /**
     * Instance of a Section entity.
     */
    private Section section;
    
    /**
     * Instantiate the setup method.
     *
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {
        //Instantiates a ClubSociety object.
        section = new Section(); 
        section.setDescription("NewDes");
        section.setModifiedTime(new Date());
    }
    
    /**
     * Test method to check if a Section object was successfully added 
     * to the db.    
     */
    @Test
    public final void testAddSection() {
        try {
            Section newSection = sectionDaoTarget.save(section);
            assertNotNull("new Section should not be null " + newSection);
        } catch (AkuraAppException e) {   
            e.printStackTrace();
        }
    }
     
    /**
     * Test method to check if a Section object was successfully updated in the db.  
     * Also covering test for find Section object by id too.    
     */
    @Test
    public final void testUpdateSection() {
        try {
            
            String newDescription = "NewDescription";
            
            Section newSection = sectionDaoTarget.save(section);
            assertNotNull("new Section should not be null " + newSection);
            
            newSection.setDescription(newDescription);
            sectionDaoTarget.update(newSection);
            
            Section updatedSection = (Section)
                    sectionDaoTarget.findById(Section.class, newSection.getSectionId());
            assertEquals(newDescription, updatedSection.getDescription());
            
        } catch (AkuraAppException e) {   
            e.printStackTrace();
        }
    }
    
    /**
     * Test method to check if a collection of Section objects was successfully
     * loaded from the database. 
     */
    @Test
    public final void testFindAllSection(){
        
        try {
            Section newSection = sectionDaoTarget.save(section);
            assertNotNull("new Section should not be null " + newSection);
            
            List<Section> sectionList = sectionDaoTarget.findAll(Section.class);    
            assertNotNull(sectionList.size());
            
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
            sectionDaoTarget.delete(section);
        } catch (AkuraAppException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
