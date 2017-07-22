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

import java.util.Date;
import java.util.List;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.StudentSpecialEventParticipationView;
import com.virtusa.akura.api.exception.AkuraAppException;

/**
 * This interface provides persistence layer functionality for the Parent object.
 * 
 * @author Virtusa Corporation
 */
public class StudentSpecialEventParticipationViewDaoImpl extends BaseDaoImpl<StudentSpecialEventParticipationView>
        implements StudentSpecialEventParticipationViewDao {
    
    /** Constant for query name getSpecialEventParticipationStudentInfo. */
    private static final String GET_SPECIAL_EVENT_PARTICIPATION_STUDENT_INFO =
            "getSpecialEventParticipationStudentInfo";
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentSpecialEventParticipationView> getSpecialEventParticipationStudentInfo(int participationId,
            Date year) throws AkuraAppException {
    
        return getHibernateTemplate().findByNamedQuery(GET_SPECIAL_EVENT_PARTICIPATION_STUDENT_INFO, participationId,
                year);
    }
    
}
