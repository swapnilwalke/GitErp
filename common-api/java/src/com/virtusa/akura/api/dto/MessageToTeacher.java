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

package com.virtusa.akura.api.dto;

import java.util.List;
import java.util.Map;

/**
 * to bind Message to teacher Form values.
 * 
 * @author Virtusa
 */
public class MessageToTeacher extends BaseDomain {
    
    /** default serial version id. */
    private static final long serialVersionUID = 1L;
    
    /** holds message subject. */
    private String subject;
    
    /** holds message. */
    private String message;
    
    /** holds staffId where Email sends to. */
    private Integer toStaffId;
    
    /** holds staffId where Email copies to. */
    private List<Integer> ccStaffId;
    
    /**
     * holds class/subject teacher.
     */
    private Map<Integer, String> teacherMap;
    
    /**
     * this method will clear(set to default) attribute values. but Teacher Map will not clear.
     */
    public void clearAttributes() {
    
        subject = "";
        message = "";
        toStaffId = 0;
        ccStaffId.clear();
    }
    
    /**
     * @return Email subject.
     */
    public String getSubject() {
    
        return subject;
    }
    
    /**
     * @param subjectArg object to set
     */
    public void setSubject(String subjectArg) {
    
        this.subject = subjectArg;
    }
    
    /**
     * @return Email body
     */
    public String getMessage() {
    
        return message;
    }
    
    /**
     * @param messageArg set Email body
     */
    public void setMessage(String messageArg) {
    
        this.message = messageArg;
    }
    
    /**
     * @return StaffId to send email
     */
    public Integer getToStaffId() {
    
        return toStaffId;
    }
    
    /**
     * @param toStaffIdArg staffId to copied
     */
    public void setToStaffId(Integer toStaffIdArg) {
    
        this.toStaffId = toStaffIdArg;
    }
    
    /**
     * @return Map with key -staffId and value -label
     */
    public Map<Integer, String> getTeacherMap() {
    
        return teacherMap;
    }
    
    /**
     * @param teacherMapArg teacher as Map( key -staffId and value -label)
     */
    public void setTeacherMap(Map<Integer, String> teacherMapArg) {
    
        this.teacherMap = teacherMapArg;
    }
    
    /**
     * @return ccStaffId to send email
     */
    public List<Integer> getCcStaffId() {
    
        return ccStaffId;
    }
    
    /**
     * @param ccStaffId ccStaffId to copied
     */
    public void setCcStaffId(List<Integer> ccStaffId) {
    
        this.ccStaffId = ccStaffId;
    }
    
}
