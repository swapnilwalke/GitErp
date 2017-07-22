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

package com.virtusa.akura.attendance.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.virtusa.akura.api.dao.BaseDaoImpl;
import com.virtusa.akura.api.dto.AttendanceDashboardDto;
import com.virtusa.akura.api.dto.BestStudentAttendanceTemplate;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;

/**
 * Daily Attendance service implementation.
 *
 * @author Virtusa Corporation
 */
public class BestStudentAttendanceDaoImpl extends BaseDaoImpl<BestStudentAttendanceTemplate> implements
        BestStudentAttendanceDao {

    /** Hoding string of sp call name. */
    private static final String CALL_STUDENT_GAMIFICATION_SEARCH = "callStudentGamificationSearch";

    /**
     * Get the best attendance information.
     *
     * @param attendanceDashboardDto of AttendanceDashboardDto
     * @return list of BestStudentAttendanceTemplate
     * @throws AkuraAppException of AkuraAppException
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> getTopAttendanceList(AttendanceDashboardDto attendanceDashboardDto) throws AkuraAppException {

        int gradeId = attendanceDashboardDto.getGradeId();
        int classId = attendanceDashboardDto.getClassGradeId();
        int year = attendanceDashboardDto.getYear();
        int monthId = attendanceDashboardDto.getMonth();

        int minNo = attendanceDashboardDto.getMinNo();

        try {

            List<Object[]> bestStudentAttendanceList = null;

            bestStudentAttendanceList =
                    getHibernateTemplate().findByNamedQuery(CALL_STUDENT_GAMIFICATION_SEARCH, minNo,
                            AkuraConstant.PARAM_INDEX_SIX, gradeId, classId, year, monthId);

            return bestStudentAttendanceList;
        } catch (DataAccessException e) {
            throw new AkuraAppException(AkuraConstant.HIBERNATE_INVALID_ADD_OPERATION, e);
        }
    }

}
