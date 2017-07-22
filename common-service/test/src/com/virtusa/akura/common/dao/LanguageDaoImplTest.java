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
import com.virtusa.akura.api.dto.Language;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * LanguageDaoImplTest is to test all the persistent related functionalities for the Language object.
 * 
 * @author Virtusa Corporation
 */

public class LanguageDaoImplTest extends BaseCommonTest {
    
    /**
     * Holds languageDao instance of {@link LanguageDao}.
     */
    @Autowired
    private LanguageDao languageDao;
    
    /**
     * Holds language instance of {@link Language}.
     */
    private Language language;
    
    /**
     * Instantiate the setup method.
     * 
     * @throws Exception when an error has occurred during processing.
     */
    @Before
    public final void setUp() throws Exception {

        language = new Language();
        language.setLanguage("Sinhala");
    }
    
    /**
     * Test method for save Language.
     */
    @Test
    public final void testAddLanguage() {

        try {
            Language languageObj = this.languageDao.save(language);
            assertNotNull("Added Language object can not be null ", languageObj);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for update Language. This test method covers test for get Language by id too.
     */
    @Test
    public final void testModifyLanguage() {

        try {
            Language updateLanguage = this.languageDao.save(language);
            assertNotNull("Updated Grading object can not be null ", updateLanguage);
            
            updateLanguage.setLanguage("English");
            this.languageDao.update(updateLanguage);
            
            Language findLanguage = (Language) languageDao.findById(Language.class, updateLanguage.getLanguageId());
            assertEquals("Language object has been modified successfully", updateLanguage.getLanguage(), findLanguage
                    .getLanguage());
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * Test method for get all the available Language.
     */
    @Test
    public final void testGetLanguages() {

        try {
            Language addLanguage = this.languageDao.save(language);
            assertNotNull("Added Grading object can not be null ", addLanguage);
            
            List<Language> languages = this.languageDao.findAll(Language.class);
            assertTrue("Received Language object list size cannot be zero",languages.size() > 0);
        } catch (AkuraAppException e) {
            e.getStackTrace();
        }
    }
    
    /**
     * teardown method for each test case.
     * 
     * @throws Exception - the exception.
     */
    @After
    public final void teardown() throws Exception {

        try {
            this.languageDao.delete(language);
        } catch (AkuraAppException e) {
            e.printStackTrace();
        }
    }
}
