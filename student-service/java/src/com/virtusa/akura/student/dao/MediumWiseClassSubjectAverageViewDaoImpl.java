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

package com.virtusa.akura.student.dao;

import java.util.List;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.MediumWiseClassSubjectAverageView;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * @author Virtusa Corporation
 */
public class MediumWiseClassSubjectAverageViewDaoImpl extends BaseDaoImpl<MediumWiseClassSubjectAverageView> implements
        MediumWiseClassSubjectAverageViewDao {
    
    /** Constant to hold named query findStudyMediumsInClass. */
    private static final String FIND_STUDY_MEDIUMS_IN_CLASS = "findStudyMediumsInClass";
    
    /** Constant to hold named query findMediumWiseClassSubjectAverage. */
    private static final String FIND_MEDIUM_WISE_CLASS_SUBJECT_AVERAGE = "findMediumWiseClassSubjectAverage";
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public MediumWiseClassSubjectAverageView findMediumWiseClassSubjectAverage(int classGradeid, int year,
            int gradeSubjectId, String term, int languageId) throws AkuraAppException {
    
        MediumWiseClassSubjectAverageView mediumWiseClassSubjectAverageView = null;
        
        List<MediumWiseClassSubjectAverageView> list =
                getHibernateTemplate().findByNamedQuery(FIND_MEDIUM_WISE_CLASS_SUBJECT_AVERAGE,
                        classGradeid, year, gradeSubjectId, term, languageId);
        
        if (list.size() > 0) {
            mediumWiseClassSubjectAverageView = list.get(0);
        }
        return mediumWiseClassSubjectAverageView;
    }
    
    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Integer> findStudyMediumsInClass(int classGradeid, int year, String term)
            throws AkuraAppException {
    
        return getHibernateTemplate().findByNamedQuery(FIND_STUDY_MEDIUMS_IN_CLASS, classGradeid, year, term);
                
    }
    
}
