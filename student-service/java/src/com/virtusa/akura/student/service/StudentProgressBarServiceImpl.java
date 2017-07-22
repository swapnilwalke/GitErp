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

package com.virtusa.akura.student.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.virtusa.akura.api.dto.FaithLifeRating;
import com.virtusa.akura.api.dto.StudentTermMarkDTO;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.api.exception.AkuraConstant;
import com.virtusa.akura.common.dao.TermDao;
import com.virtusa.akura.student.dao.FaithLifeRatingDao;
import com.virtusa.akura.student.dao.StudentTermMarkViewDao;
import com.virtusa.akura.util.DateUtil;

/**
 * Class to declare the student progress bar related services.
 * 
 * @author Virtusa Corporation
 */
public class StudentProgressBarServiceImpl implements StudentProgressBarService {
    
    /** Constant to hold Term 3. */
    private static final String TERM_3 = "Term 3";
    
    /** Constant to hold Term 2. */
    private static final String TERM_2 = "Term 2";
    
    /** Constant to hold Term 1. */
    private static final String TERM_1 = "Term 1";

    /**
     * faithLifeRatingDaoTarget to hold a single instance of student FaithLifeRating.
     */
    private FaithLifeRatingDao faithLifeRatingDaoTarget;
    
    /** termDao to hold a single instance of TermDao. */
    private TermDao termDao;
    
    /**
     * @param termDaoObj the TermDao to set
     */
    public void setTermDao(TermDao termDaoObj) {
    
        this.termDao = termDaoObj;
    }
    
    /**
     * @param faithLifeRatingDaoTargetObj the faithLifeRatingDaoTarget to set
     */
    public void setFaithLifeRatingDaoTarget(FaithLifeRatingDao faithLifeRatingDaoTargetObj) {
    
        this.faithLifeRatingDaoTarget = faithLifeRatingDaoTargetObj;
    }
    
    /** This instance will be dependency injected by type. */
    private StudentTermMarkViewDao studentTermMarkViewDao;
    
    /**
     * Sets the Student term mark view dao.
     * 
     * @param dao studentTermMarkViewDao.
     */
    public void setStudentTermMarkViewDao(StudentTermMarkViewDao dao) {
    
        this.studentTermMarkViewDao = dao;
    }
    
    /**
     * Returns an average value of faithLifeRating for a selected student for selected year.
     * 
     * @param studentId - Id of the student
     * @param year - Date
     * @return - an average of the rating
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @Override
    public double calculateFaithLifeRatingAverage(int studentId, Date year) throws AkuraAppException {
    
        double highestGraging = 0.0;
        int countA = 0;
        int countB = 0;
        int countC = 0;
        int countD = 0;
        int countS = 0;
        int countF = 0;
        int total =0;
        
        List<FaithLifeRating> faithLifeObj =
                faithLifeRatingDaoTarget.getFaithLifeRatingsListForStudent(studentId, year);
        for (FaithLifeRating flr : faithLifeObj) {
            String valueStr = flr.getFaithLifeGrading().getDescription().trim();
            
            if (valueStr.equals(com.virtusa.akura.api.enums.FaithLifeRating.A.toString())) {
                ++countA;
                total++;
            }
            if (valueStr.equals(com.virtusa.akura.api.enums.FaithLifeRating.B.toString())) {
                ++countB;
                total++;
            }
            if (valueStr.equals(com.virtusa.akura.api.enums.FaithLifeRating.C.toString())) {
                ++countC;
                total++;
            }
            if (valueStr.equals(com.virtusa.akura.api.enums.FaithLifeRating.D.toString())) {
                ++countD;
                total++;
            }
            if (valueStr.equals(com.virtusa.akura.api.enums.FaithLifeRating.S.toString())) {
                ++countS;
                total++;
            }
            if (valueStr.equals(com.virtusa.akura.api.enums.FaithLifeRating.F.toString())) {
                ++countF;
                total++;
            }
        }
        
        int obatinGrading =
                (countA * AkuraConstant.PARAM_INDEX_FIVE) + (countB * AkuraConstant.PARAM_INDEX_FOUR)
                        + (countC * AkuraConstant.PARAM_INDEX_THREE) + (countD * AkuraConstant.PARAM_INDEX_TWO)
                        + (countS * AkuraConstant.PARAM_INDEX_ONE) + (countF * AkuraConstant.PARAM_INDEX_ZERO);
        int maximumGrading = total * AkuraConstant.PARAM_INDEX_FIVE;
        highestGraging = (double) obatinGrading / maximumGrading;
        return highestGraging * AkuraConstant.MAXIMUM_MARK;
        
    }
    
    /**
     * Returns an average value of AcademicLifeRating for a selected student for selected year.
     * 
     * @param studentId - Id of the student
     * @param year - Date
     * @return - an average of the rating
     * @throws AkuraAppException - The exception details that occurred when processing
     */
    @Override
    public double calculateAcademicLifeRatingAverage(int studentId, Date year) throws AkuraAppException {
    
        int intYear = DateUtil.getYearFromDate(year);
        double averageMarksFinal = 0.0;
        int termOneSubjectCount = 0;
        int termTwoSubjectCount = 0;
        int termThreeSubjectCount = 0;
        
        int termCount = 0;
        
        float termOneAvg = 0.0f;
        float termTwoAvg = 0.0f;
        float termThreeAvg = 0.0f;
        
        float termOneMarks = 0.0f;
        float termTwoMarks = 0.0f;
        float termThreeMarks = 0.0f;
        List<StudentTermMarkDTO> myList = studentTermMarkViewDao.getTermMarksForStudent(studentId, intYear);
        
        Map<String, Float> termMarkMap = new TreeMap<String, Float>();
        
        for (StudentTermMarkDTO dto : myList) {
            if (dto.getTerm().equals(TERM_1) && !dto.isAbsent()) {
                termOneMarks = termOneMarks + dto.getMarks();
                termOneSubjectCount++;
                termMarkMap.put(TERM_1, termOneMarks);
            } else if (dto.getTerm().equals(TERM_2) && !dto.isAbsent()) {
                termTwoMarks = termTwoMarks + dto.getMarks();
                termTwoSubjectCount++;
                termMarkMap.put(TERM_2, termTwoMarks);
            } else if (dto.getTerm().equals(TERM_3) && !dto.isAbsent()) {
                termThreeMarks = termThreeMarks + dto.getMarks();
                termThreeSubjectCount++;
                termMarkMap.put(TERM_3, termThreeMarks);
                
            }
        }
        
        if (!termMarkMap.isEmpty()) {
            if (termMarkMap.containsKey(TERM_1)) {
                termCount++;
                termOneAvg = (termMarkMap.get(TERM_1)) / termOneSubjectCount;
            }
            if (termMarkMap.containsKey(TERM_2)) {
                termCount++;
                termTwoAvg = (termMarkMap.get(TERM_2)) / termTwoSubjectCount;
            }
            if (termMarkMap.containsKey(TERM_3)) {
                termCount++;
                termThreeAvg = (termMarkMap.get(TERM_3)) / termThreeSubjectCount;
            }
            
        }
        if (termCount != 0) {
            averageMarksFinal = (termOneAvg + termTwoAvg + termThreeAvg) / termCount;
        }
        return averageMarksFinal;

    }
}
