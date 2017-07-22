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

package com.virtusa.akura.school.service;

import java.util.List;

import com.virtusa.akura.api.dto.School;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.school.dao.SchoolDao;

/**
 * The Class SchoolServiceImpl.
 *
 * @author Virtusa Corporation
 */
public class SchoolServiceImpl implements SchoolService {

    /** SchoolDao attribute for holding schoolDao. */
    private SchoolDao schoolDao;

    /**
     * Setter method for school Dao.
     *
     * @param schoolDaoValue - SchoolDao
     */
    public void setSchoolDao(SchoolDao schoolDaoValue) {

        this.schoolDao = schoolDaoValue;
    }

    /**
     * {@inheritDoc}
     *
     * @throws AkuraAppException
     */
    public final School addSchool(final School school) throws AkuraAppException {

        return schoolDao.save(school);
    }

    /**
     * {@inheritDoc}
     */
    public final School findSchool(final Integer schoolId) throws AkuraAppException {

        return (School) schoolDao.findById(School.class, schoolId);
    }

    /**
     * {@inheritDoc}
     */
    public final List<School> getSchoolList() throws AkuraAppException {

        return schoolDao.findAll(School.class);
    }

    /**
     * {@inheritDoc}
     *
     * @throws AkuraAppException
     */
    public final void updateSchool(final School school) throws AkuraAppException {

        schoolDao.update(school);
    }

    /**
     * {@inheritDoc}
     */
    public final void deleteSchool(final School school) throws AkuraAppException {

        schoolDao.delete(school);
    }

}
