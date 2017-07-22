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

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudyMedium;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;


/**
 * This is the implementation class for StudyMediumDao interface. It handles all the persistent related
 * functionalities for the StudyMedium object.
 *
 * @author Virtusa Corporation
 *
 */
public class StudyMediumDaoImpl extends BaseDaoImpl<StudyMedium> implements StudyMediumDao {

	/** Query name for find partially matching mediums. */
    private static final String FIND_MEDIUM_BY_KEY_NAME = "findMediumByKeyName";
    /** Query name for similar mediums. */
	private static final String FIND_SAME_MEDIUMS_BY_NAME = "findSameMediumsByName";

	/**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudyMedium> findStudyMediums(String key) throws AkuraAppException {

        try {
            return (List<StudyMedium>) getHibernateTemplate().findByNamedQuery(FIND_MEDIUM_BY_KEY_NAME, key);
        } catch (DataAccessException e) {

            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public List<StudyMedium> findSameStudyMedium(String name) throws AkuraAppException {

        try {
            return (List<StudyMedium>) getHibernateTemplate().findByNamedQuery(FIND_SAME_MEDIUMS_BY_NAME, name);
        } catch (DataAccessException e) {

            throw new AkuraAppException(AkuraConstant.DB_CONNECTION_ERROR, e);
        }
    }
}
