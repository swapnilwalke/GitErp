/*
 * © 2011 Virtusa(Pvt)Ltd. All rights reserved. CONFIDENTIAL AND

 * PROPRIETARY INFORMATION The information contained herein (the

 * 'Proprietary Information') is highly confidential and proprietary to and

 * constitutes trade secrets of Virtusa(Pvt)Ltd. The Proprietary Information

 * for Virtusa(Pvt)Ltd internal use only and shall not be published,

 * communicated, disclosed or divulged to any person, firm, corporation or

 * other legal entity, directly or indirectly, without the prior written

 * consent of Virtusa(Pvt)Ltd Information Management.
 */

package com.virtusa.akura.reporting.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.virtusa.akura.api.dto.CRAverageTestMarkTemplate;
import com.virtusa.akura.api.dto.CRDisciplinaryActionsTemplate;
import com.virtusa.akura.api.dto.CRExamAbsentTemplate;
import com.virtusa.akura.api.dto.CRExtraCurricularActivitiesTemplate;
import com.virtusa.akura.api.dto.CRStudentPerformanceTemplate;
import com.virtusa.akura.api.dto.ClassGrade;
import com.virtusa.akura.api.dto.Term;
import com.virtusa.akura.api.exception.AkuraAppException;
import com.virtusa.akura.reporting.BaseReportingTest;

/**
 * This test class, tests all the persistence related functionality for the CommonReportingServiceImpl domain
 * object.
 *
 * @author Virtusa Corporation
 */

public class CommonReportingServiceImplTest extends BaseReportingTest {

    /**
     * This static instance for Grade Id.
     */
    private static final int GRADE_ID = 1;

    /**
     * This static instance for Class Grade Id.
     */
    private static final int CLASS_GRADE_ID = 100;

    /**
     * This static instance for Discipline Id.
     */
    private static final int DISCIPLINE_ID = 123;

    /**
     * This static instance for Student Id.
     */
    private static final int STUDENT_ID = 1;

    /**
     * This static instance for state.
     */
    private static final byte STATE = 1;

    /**
     * This static instance for Cube Society Id of the Student.
     */
    private static final int STUDENT_CLUB_SOCIETY_ID = 1;

    /**
     * This static instance for date-year.
     */
    private static final Date YEAR = null;

    /**
     * This instance will be dependency injected by type.
     */
    @Autowired
    private CommonReportingService commonReportingService;

    /**
     * Represents an instance of Term.
     */
    private Term term;

    /**
     * Represents an instance of classGrade.
     */
    private ClassGrade classGrade;

    /**
     * Represents an instance of CRExamAbsentTemplate.
     */
    private CRExamAbsentTemplate cRExamAbsentTemplate;

    /**
     * Represents an instance of CRAverageTestMarkTemplate.
     */
    private CRAverageTestMarkTemplate cRAverageTestMarkTemplate;

    /**
     * Represents an instance of CRDisciplinaryActionsTemplate.
     */
    private CRDisciplinaryActionsTemplate cRDisciplinaryActionsTemplate;

    /**
     * Represents an instance of CRExtraCurricularActivitiesTemplate.
     */
    private CRExtraCurricularActivitiesTemplate cRExtraCurricularActivitiesTemplate;

    /**
     * Represents an instance of CRStudentPerformanceTemplate.
     */
    private CRStudentPerformanceTemplate cRStudentPerformanceTemplate;

    /**
     * @throws AkuraAppException - the exception that occurred.
     */
    @Before
    public final void setUp() throws AkuraAppException {

        cRExamAbsentTemplate = new CRExamAbsentTemplate();
        cRExamAbsentTemplate.setAbsent(false);
        cRExamAbsentTemplate.setClassDescription("xxxxxxxxxx");
        cRExamAbsentTemplate.setGradeDescription("grade 123");
        cRExamAbsentTemplate.setSubjectDescription("subject1");
        cRExamAbsentTemplate.setTermDescription("term123");

        List<String> subjectList = new ArrayList<String>();
        subjectList.add("Science");
        subjectList.add("Maths");
        subjectList.add("Art");

        classGrade = new ClassGrade();
        classGrade.setClassGradeId(CLASS_GRADE_ID);

        Set<ClassGrade> classGrades = new HashSet<ClassGrade>();
        classGrades.add(classGrade);

        term = new Term();
        term.setDescription("term 12345");
        // term.setChapters(cha);

        List<Term> termList = new ArrayList<Term>();
        termList.add(term);

        cRAverageTestMarkTemplate = new CRAverageTestMarkTemplate();
        cRAverageTestMarkTemplate.setClassDescription("class1");
        cRAverageTestMarkTemplate.setGradeDescription("grade1");
        cRAverageTestMarkTemplate.setGradeId(GRADE_ID);
        cRAverageTestMarkTemplate.setSubjectDescription("subject1");
        // cRAverageTestMarkTemplate.setSubjectList(subjectList);
        cRAverageTestMarkTemplate.setTermDescription("term Description 123");
        cRAverageTestMarkTemplate.setTermList(termList);

        cRDisciplinaryActionsTemplate = new CRDisciplinaryActionsTemplate();
        cRDisciplinaryActionsTemplate.setAdmissionNo("123456");
        cRDisciplinaryActionsTemplate.setClassDescription("class 1234");
        cRDisciplinaryActionsTemplate.setDisciplineCategory("category 1");
        cRDisciplinaryActionsTemplate.setFullName("Name 1");
        cRDisciplinaryActionsTemplate.setGradeDescription("grage 123");
        cRDisciplinaryActionsTemplate.setStudentDisciplineId(DISCIPLINE_ID);
        cRDisciplinaryActionsTemplate.setStudentId(STUDENT_ID);
        cRDisciplinaryActionsTemplate.setWarningLevel("bad");

        cRExtraCurricularActivitiesTemplate = new CRExtraCurricularActivitiesTemplate();
        cRExtraCurricularActivitiesTemplate.setAdmissionNo("1234");
        cRExtraCurricularActivitiesTemplate.setClubSocietyName("club 1");
        cRExtraCurricularActivitiesTemplate.setFullName("name 1");
        cRExtraCurricularActivitiesTemplate.setMembershipNo("number 1");
        cRExtraCurricularActivitiesTemplate.setPosition("position 1");
        cRExtraCurricularActivitiesTemplate.setStatus(STATE);
        cRExtraCurricularActivitiesTemplate.setStudentclubSocietyId(STUDENT_CLUB_SOCIETY_ID);
        cRExtraCurricularActivitiesTemplate.setStudentId(STUDENT_ID);
        cRExtraCurricularActivitiesTemplate.setYear(YEAR);

        cRStudentPerformanceTemplate = new CRStudentPerformanceTemplate();
        cRStudentPerformanceTemplate.setClassDescription("class 1");

    }

    /**
     * Test method to check if, cRExamAbsentTemplate object was successfully reterieved from the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testStudentClassWiseExamAbsenteeList() throws AkuraAppException {

        JRBeanCollectionDataSource rd =
                commonReportingService.getStudentClassWiseExamAbsenteeList(cRExamAbsentTemplate);
        assertNotNull("Object :" + rd);
    }

    /**
     * Test method to check if, cRExtraCurricularActivitiesTemplate object was successfully reterieved from
     * the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testStudentExtraCurricularActivities() throws AkuraAppException {

        JRBeanCollectionDataSource rd =
                commonReportingService.getStudentExtraCurricularActivities(cRExtraCurricularActivitiesTemplate);
        assertNotNull("Object :" + rd);
    }

    /**
     * Test method to check if, cRStudentPerformanceTemplate object was successfully reterieved from the db.
     *
     * @throws AkuraAppException - the exception that occurred.
     */
    @Test
    public final void testStudentPerformance() throws AkuraAppException {

        JRBeanCollectionDataSource rd = commonReportingService.getStudentPerformance(cRStudentPerformanceTemplate);
        assertNotNull("Object :" + rd);
    }

}
