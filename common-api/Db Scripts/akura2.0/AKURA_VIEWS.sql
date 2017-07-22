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

use `akura`;

-- STUDENT_TERM_MARKS_GRADE_RANK_VIEW 
-- Purpose of this view is to show grade wise student's term marks rank . 


CREATE OR REPLACE VIEW  STUDENT_TERM_MARKS_GRADE_RANK_VIEW
AS
SELECT stmav.STUDENT_CLASS_INFO_ID, 
	stmav.CLASS_GRADE_ID,grade.GRADE_ID, stmav.YEAR, 
	stmav.TERM_ID, 
  	stmav.TOTAL,
	AVERAGE,
    FIND_IN_SET(
        AVERAGE,  
            (SELECT  GROUP_CONCAT(
                            DISTINCT stmav1.AVERAGE
                            ORDER BY stmav1.AVERAGE  DESC
                       )
                FROM    
                	STUDENT_TERM_MARK_AVG_VIEW stmav1
                  INNER JOIN CLASS_GRADE grade1
                  ON stmav1.CLASS_GRADE_ID = grade1.CLASS_GRADE_ID
                WHERE 
                	stmav.YEAR= stmav1.YEAR
                	AND stmav.TERM_ID= stmav1.TERM_ID
                	AND grade.GRADE_ID= grade1.GRADE_ID
                )
            ) AS RANK
FROM STUDENT_TERM_MARK_AVG_VIEW stmav    
INNER JOIN CLASS_GRADE grade
ON stmav.CLASS_GRADE_ID = grade.CLASS_GRADE_ID
order By GRADE_ID, YEAR, TERM_ID, RANK;





-- STUDENT_GRADE_SUBJECT_AVERAGE_VIEW 
-- Purpose of this view is to show average of a subject for year that student took. 

CREATE OR REPLACE VIEW  STUDENT_GRADE_SUBJECT_AVERAGE_VIEW
AS
SELECT stmv.IS_ABSENT, stmv.STUDENT_TERM_MARKS_ID, stmv.MARKS, 
	   stmv.SUBJECT_NAME, stmv.STUDENT_ID, stmv.YEAR, 
	   stmv.ADMISSION_NO, stmv.NAME_WT_INITIALS, stmv.CLASS_GRADE_ID, 
	   stmv.STUDENT_CLASS_INFO_ID, stmv.GRADE_SUBJECT_ID,
	   SUM(stmv.MARKS) AS TOTAL,
       AVG(stmv.MARKS) AS AVERAGE
       
FROM STUDENT_TERM_MARKS_VIEW stmv 
GROUP BY STUDENT_CLASS_INFO_ID, GRADE_SUBJECT_ID;

-- STUDENT_GRADE_SUBJECT_RANK_VIEW 
-- Purpose of this view is to show subject rank for grade.

CREATE OR REPLACE VIEW  STUDENT_GRADE_SUBJECT_RANK_VIEW
AS
SELECT sgsav.IS_ABSENT, sgsav.STUDENT_TERM_MARKS_ID, sgsav.MARKS,   
       sgsav.SUBJECT_NAME, sgsav.STUDENT_ID, sgsav.YEAR, sgsav.ADMISSION_NO, 
       sgsav.NAME_WT_INITIALS, sgsav.CLASS_GRADE_ID, sgsav.STUDENT_CLASS_INFO_ID, 
       sgsav.GRADE_SUBJECT_ID, 
       sgsav.TOTAL,
       sgsav.AVERAGE,
FIND_IN_SET(
        AVERAGE,  
            (SELECT  GROUP_CONCAT(
                            DISTINCT sgsav1.AVERAGE
                            ORDER BY sgsav1.AVERAGE  DESC
                       )
                FROM    
                	STUDENT_GRADE_SUBJECT_AVERAGE_VIEW sgsav1
                WHERE 
                	sgsav.YEAR= sgsav1.YEAR
                	AND sgsav.GRADE_SUBJECT_ID= sgsav1.GRADE_SUBJECT_ID
                )
            ) AS RANK
FROM STUDENT_GRADE_SUBJECT_AVERAGE_VIEW sgsav 
ORDER BY  sgsav.YEAR, sgsav.GRADE_SUBJECT_ID, RANK;


-- SUBJECT_STUDENT_COUNT 
-- Purpose of this view is to get the count of students in a class who are doing 
-- particlar subject.

CREATE OR REPLACE VIEW SUBJECT_STUDENT_COUNT
AS 
SELECT stmv.CLASS_GRADE_ID, stmv.STUDENT_CLASS_INFO_ID,stmv.YEAR,
	   stmv.TERM_ID, stmv.TERM, stmv.GRADE_SUBJECT_ID, 
       cg.DESCRIPTION, COUNT(stmv.STUDENT_CLASS_INFO_ID) AS STUDENT_COUNT
FROM STUDENT_TERM_MARKS_VIEW stmv JOIN CLASS_GRADE cg 
ON (stmv.CLASS_GRADE_ID = cg.CLASS_GRADE_ID) 
GROUP BY stmv.CLASS_GRADE_ID, stmv.GRADE_SUBJECT_ID, stmv.YEAR, stmv.TERM;

-- CLASS_SUBJECT_AVERAGE_VIEW
-- Purpose of this view is to get average marks of the particlar 
-- subject that students taken in class

CREATE OR REPLACE VIEW CLASS_SUBJECT_AVERAGE_VIEW
AS 
SELECT atm.GRADE AS GRADE,
	   atm.CLASS_DESCRIPTION AS CLASS_DESCRIPTION,
       atm.YEAR AS YEAR,
       atm.GRADE_SUBJECT_ID AS GRADE_SUBJECT_ID,
       atm.SUBJECT AS SUBJECT,
       atm.TERM AS TERM,
       atm.TOTAL_MARKS AS TOTAL_MARKS,
       ssc.STUDENT_COUNT AS STUDENT_COUNT,
       round((atm.TOTAL_MARKS / ssc.STUDENT_COUNT),2) AS AVERAGE 
FROM AVERAGE_TERM_MARKS atm 
LEFT JOIN SUBJECT_STUDENT_COUNT ssc ON (atm.CLASS_DESCRIPTION = ssc.DESCRIPTION 
&& atm.GRADE_SUBJECT_ID = ssc.GRADE_SUBJECT_ID 
&& atm.TERM = ssc.TERM && atm.YEAR = ssc.YEAR) ;

-- CLASS_WISE_STUDENTS_SUBJECTS_VIEW
-- Purpose of this view is to get student to enter assignment marks first time.
CREATE OR REPLACE VIEW  CLASS_WISE_STUDENTS_SUBJECTS_VIEW AS
SELECT s.FULL_NAME as FULLNAME,
    sci.STUDENT_ID as STUDENT_ID,
    sci.STUDENT_CLASS_INFO_ID as STUDENT_CLASS_INFO_ID, 
    sci.CLASS_GRADE_ID as CLASS_GRADE_ID,
    stm.GRADE_SUBJECT_ID as GRADE_SUBJECT_ID,
    sci.YEAR as YEAR
FROM STUDENT_CLASS_INFO sci,
     STUDENT_TERM_MARK stm,
     STUDENT s
WHERE sci.STUDENT_CLASS_INFO_ID = stm.STUDENT_CLASS_INFO_ID 
  AND sci.STUDENT_ID = s.STUDENT_ID
    GROUP BY 
	stm.STUDENT_CLASS_INFO_ID,
    sci.CLASS_GRADE_ID,
    stm.GRADE_SUBJECT_ID;
	
-- STUDENT_TERM_WISE_AVG_VIEW
-- Purpose of this view is to generate student's annual term wise average total marks 
CREATE OR REPLACE VIEW  STUDENT_TERM_WISE_AVG_VIEW
AS
SELECT stmv.IS_ABSENT, stmv.STUDENT_TERM_MARKS_ID, stmv.MARKS, 
	   stmv.SUBJECT_NAME, stmv.STUDENT_ID, stmv.YEAR, 
	   stmv.ADMISSION_NO, stmv.NAME_WT_INITIALS, stmv.CLASS_GRADE_ID, 
	   stmv.STUDENT_CLASS_INFO_ID, stmv.GRADE_SUBJECT_ID,stmv.TERM,
	   SUM(stmv.MARKS) AS TOTAL,
       AVG(stmv.MARKS) AS AVERAGE
       
FROM STUDENT_TERM_MARKS_VIEW stmv 
GROUP BY STUDENT_CLASS_INFO_ID, TERM;

	
-- TERM_MARKS__ANNUAL_AVG_VIEW
-- Purpose of this view is to generate student's annual term marks average
 
CREATE OR REPLACE VIEW TERM_MARKS_ANNUAL_AVG_VIEW
AS
SELECT stmv.STUDENT_ID,
	stmv.STUDENT_CLASS_INFO_ID,
	stmv.YEAR, 
	stmv.ADMISSION_NO, 
    stmv.NAME_WT_INITIALS, 
    stmv.CLASS_GRADE_ID, 
	SUM(stmv.TOTAL) AS TOTAL,
	ROUND(AVG(stmv.TOTAL),2) as AVERAGE 
FROM 
	STUDENT_TERM_WISE_AVG_VIEW stmv
GROUP BY 
	stmv.STUDENT_CLASS_INFO_ID
ORDER BY 
	AVERAGE DESC ;
	
	
-- TERM_MARKS_ANNUAL_GRADE_RANK_VIEW
-- Purpose of this view is to generate grade wise student annual rank

CREATE OR REPLACE VIEW TERM_MARKS_ANNUAL_GRADE_RANK_VIEW
AS
SELECT tmaav.STUDENT_CLASS_INFO_ID, 
	   tmaav.CLASS_GRADE_ID,
  	   grade.GRADE_ID, 
  	   tmaav.YEAR, 
       tmaav.TOTAL,
       tmaav.ADMISSION_NO, 
       tmaav.NAME_WT_INITIALS,
	   AVERAGE,
       FIND_IN_SET(
       AVERAGE,  
            (SELECT  GROUP_CONCAT(
                            DISTINCT tmaav1.AVERAGE
                            ORDER BY tmaav1.AVERAGE  DESC
                       )
                FROM    
                	TERM_MARKS_ANNUAL_AVG_VIEW tmaav1
                  INNER JOIN CLASS_GRADE grade1
                  ON tmaav1.CLASS_GRADE_ID = grade1.CLASS_GRADE_ID
                WHERE 
                	tmaav.YEAR= tmaav1.YEAR
                	AND grade.GRADE_ID= grade1.GRADE_ID
                )
            ) AS RANK
FROM TERM_MARKS_ANNUAL_AVG_VIEW tmaav    
INNER JOIN CLASS_GRADE grade
ON tmaav.CLASS_GRADE_ID = grade.CLASS_GRADE_ID
order By GRADE_ID,YEAR,RANK;





CREATE OR REPLACE VIEW `AVERAGE_TERM_MARKS`
AS 
SELECT 	`g`.`DESCRIPTION` AS `GRADE`,
	   	`cg`.`DESCRIPTION` AS `CLASS_DESCRIPTION`,
	   	`sci`.`YEAR` AS `YEAR`,
		`stm`.`GRADE_SUBJECT_ID` AS `GRADE_SUBJECT_ID`,
		`s`.`DESCRIPTION` AS `SUBJECT`,
		`gs`.`IS_OPTIONAL_SUBJECT` AS `IS_OPTIONAL`,
		`t`.`DESCRIPTION` AS `TERM`,
		SUM(`stm`.`MARKS`) AS `TOTAL_MARKS`, 
		COUNT(`stm`.`MARKS`) AS `TOTAL_CLASS_STUDENT` 
FROM ((((((`STUDENT_TERM_MARK` `stm`
LEFT JOIN `TERM` `t` ON((`stm`.`TERM_ID` = `t`.`TERM_ID`)))
LEFT JOIN `STUDENT_CLASS_INFO` `sci` ON((`stm`.`STUDENT_CLASS_INFO_ID` = `sci`.`STUDENT_CLASS_INFO_ID`)))
LEFT JOIN `GRADE_SUBJECT` `gs` ON((`stm`.`GRADE_SUBJECT_ID` = `gs`.`GRADE_SUBJECT_ID`))) 
LEFT JOIN `CLASS_GRADE` `cg`ON((`sci`.`CLASS_GRADE_ID` = `cg`.`CLASS_GRADE_ID`)))
LEFT JOIN `GRADE` `g` ON((`cg`.`GRADE_ID` = `g`.`GRADE_ID`)))
LEFT JOIN `SUBJECT` `s` ON((`gs`.`SUBJECT_ID` = `s`.`SUBJECT_ID`))) 
GROUP BY `g`.`DESCRIPTION`,`cg`.`DESCRIPTION`,`s`.`DESCRIPTION`,`t`.`DESCRIPTION`;

CREATE OR REPLACE VIEW `FINAL_AVERAGE_TERM_MARKS` 
AS 
SELECT  `atm`.`GRADE` AS `GRADE`,
		`atm`.`CLASS_DESCRIPTION` AS `CLASS_DESCRIPTION`,
		`atm`.`YEAR` AS `YEAR`,
		`atm`.`GRADE_SUBJECT_ID` AS `GRADE_SUBJECT_ID`,
		`atm`.`SUBJECT` AS `SUBJECT`,
		`atm`.`TERM` AS `TERM`,
		`atm`.`TOTAL_MARKS` AS `TOTAL_MARKS`,
		`atm`.`TOTAL_CLASS_STUDENT` AS `TOTAL_CLASS_STUDENT`,
		round((`atm`.`TOTAL_MARKS` / `atm`.`TOTAL_CLASS_STUDENT`),2) AS `AVERAGE` 
FROM (`AVERAGE_TERM_MARKS` `atm`) 
GROUP BY `atm`.`GRADE`,`atm`.`CLASS_DESCRIPTION`,`atm`.`YEAR`,`atm`.`GRADE_SUBJECT_ID`,`atm`.`TERM` 
ORDER BY `atm`.`GRADE_SUBJECT_ID`,`atm`.`YEAR`,`atm`.`TERM`;

CREATE OR REPLACE VIEW `FINAL_AVERAGE_TERM_MARKS_GRADE` 
AS 
SELECT	`fatm`.`GRADE` AS `GRADE`,
		`fatm`.`YEAR` AS `YEAR`,
		`fatm`.`GRADE_SUBJECT_ID` AS `GRADE_SUBJECT_ID`,
		`fatm`.`SUBJECT` AS `SUBJECT`,
		`fatm`.`TERM` AS `TERM`,
		SUM(`fatm`.`TOTAL_MARKS`) AS `TOTAL_MARKS`,
		SUM(`fatm`.`TOTAL_CLASS_STUDENT`) AS `STUDENT_COUNT` 
FROM `FINAL_AVERAGE_TERM_MARKS` `fatm` 
GROUP BY `fatm`.`GRADE`,`fatm`.`YEAR`,`fatm`.`GRADE_SUBJECT_ID`,`fatm`.`TERM` 
ORDER BY `fatm`.`GRADE_SUBJECT_ID`,`fatm`.`YEAR`,`fatm`.`TERM`;

CREATE OR REPLACE VIEW `GRADE_SUBJECT_AVERAGE` 
AS 
SELECT	`fatmg`.`GRADE` AS `grade`,
		`fatmg`.`YEAR` AS `year`,
		`fatmg`.`GRADE_SUBJECT_ID` AS `GRADE_SUBJECT_ID`,
		`fatmg`.`SUBJECT` AS `subject`,
		`fatmg`.`TERM` AS `term`,
		`fatmg`.`TOTAL_MARKS` AS `total_marks`,
		`fatmg`.`STUDENT_COUNT` AS `studentGradeCount`,
		round((`fatmg`.`TOTAL_MARKS` / `fatmg`.`STUDENT_COUNT`),2) AS `grade_Average`
FROM `FINAL_AVERAGE_TERM_MARKS_GRADE` `fatmg` 
GROUP BY `fatmg`.`GRADE`,`fatmg`.`YEAR`,`fatmg`.`GRADE_SUBJECT_ID`,`fatmg`.`TERM` 
ORDER BY `fatmg`.`GRADE_SUBJECT_ID`,`fatmg`.`YEAR`,`fatmg`.`TERM`;

CREATE OR REPLACE VIEW `STUDENT_TERM_SUBJECT_MARKS` 
AS 
SELECT 	`sci`.`STUDENT_ID` AS `student_id`,
		`sci`.`YEAR` AS `year`,
		`g`.`DESCRIPTION` AS `grade`,
		`cg`.`DESCRIPTION` AS `classGrade`,
		`t`.`DESCRIPTION` AS `term`,
		`stm`.`MARKS` AS `marks`,
		`stm`.`IS_ABSENT` AS `IS_ABSENT`,
		`s`.`DESCRIPTION` AS `subject`
FROM(((((((`STUDENT_TERM_MARK` `stm` 
LEFT JOIN `STUDENT_CLASS_INFO` `sci` ON((`stm`.`STUDENT_CLASS_INFO_ID` = `sci`.`STUDENT_CLASS_INFO_ID`)))
LEFT JOIN `CLASS_GRADE` `cg` ON((`cg`.`CLASS_GRADE_ID` = `sci`.`CLASS_GRADE_ID`)))
LEFT JOIN `GRADE_SUBJECT` `gs` ON((`gs`.`GRADE_SUBJECT_ID` = `stm`.`GRADE_SUBJECT_ID`)))
LEFT JOIN `SUBJECT` `s` ON((`s`.`SUBJECT_ID` = `gs`.`SUBJECT_ID`)))
LEFT JOIN `TERM` `t` ON((`t`.`TERM_ID` = `stm`.`TERM_ID`))) 
LEFT JOIN `CLASS` `c` ON((`c`.`CLASS_ID` = `cg`.`CLASS_ID`)))
LEFT JOIN `GRADE` `g` ON((`g`.`GRADE_ID` = `cg`.`GRADE_ID`))) 
GROUP BY `sci`.`YEAR`,`t`.`DESCRIPTION`,`sci`.`STUDENT_ID`,`s`.`DESCRIPTION`;

CREATE OR REPLACE VIEW `STUDENT_SUBJECT_AVERAGE` 
AS 
SELECT 	`stsm`.`student_id` AS `student_id`,
		`fatm`.`SUBJECT` AS `subject`,
		`fatm`.`YEAR` AS `year`,
		`fatm`.`AVERAGE` AS `average`,
		`fatm`.`GRADE` AS `grade`,
		`fatm`.`CLASS_DESCRIPTION` AS `class`,
		`stsm`.`marks` AS `marks`,
		`stsm`.`IS_ABSENT` AS `IS_ABSENT`,
		`fatm`.`TERM` AS `term` 
FROM (`STUDENT_TERM_SUBJECT_MARKS` `stsm` 
LEFT JOIN `FINAL_AVERAGE_TERM_MARKS` `fatm` ON(((`fatm`.`SUBJECT` = `stsm`.`subject`) 
AND (`fatm`.`TERM` = `stsm`.`term`) 
AND (`fatm`.`YEAR` = `stsm`.`year`) 
AND (`fatm`.`CLASS_DESCRIPTION` = `stsm`.`classGrade`)))) 
GROUP BY `stsm`.`student_id`,`fatm`.`YEAR`,`fatm`.`CLASS_DESCRIPTION`,`fatm`.`TERM`,`fatm`.`SUBJECT`,`fatm`.`CLASS_DESCRIPTION`;

CREATE OR REPLACE VIEW `CLASS_GRADE_AVERAGE` 
AS 
SELECT 	`ssa`.`student_id` AS `student_id`,
		`ssa`.`year` AS `year`,
		`ssa`.`grade` AS `grade`,
		`ssa`.`class` AS `class`,
		`ssa`.`term` AS `term`,
		`ssa`.`subject` AS `subject`,
		`ssa`.`marks` AS `marks`,
		`ssa`.`IS_ABSENT` AS `IS_ABSENT`,
		`ssa`.`average` AS `class_average`,
		`gsa`.`grade_Average` AS `grade_average` 
FROM (`STUDENT_SUBJECT_AVERAGE` `ssa` JOIN `GRADE_SUBJECT_AVERAGE` `gsa`) 
WHERE ((`ssa`.`year` = `gsa`.`year`) 
AND (`ssa`.`grade` = `gsa`.`grade`) 
AND (`ssa`.`term` = `gsa`.`term`) 
AND (`ssa`.`subject` = `gsa`.`subject`));


-- ASSIGNMENT_MARK_VIEW
-- Purpose of this view is to generate student's assignment marks 
CREATE OR REPLACE VIEW `ASSIGNMENT_MARK_VIEW` 
AS 
SELECT	`sci`.`STUDENT_ID` AS `STUDENT_ID`,
		`g`.`GRADE_ID` AS `GRADE_ID`,
		`sci`.`YEAR` AS `YEAR`,
		`a`.`ASSIGNMENT_ID` AS `ASSIGNMENT_ID`,
		`a`.`NAME` AS `NAME`,
		`a`.`GRADE_SUBJECT_ID` AS `GRADE_SUBJECT_ID`,
		`g`.`DESCRIPTION` AS `GRADE_DESCRIPTION`,
		`sub`.`SUBJECT_ID` AS `SUBJECT_ID`,
		`sub`.`DESCRIPTION` AS `SUBJECT_DESCRIPTION`,
		`sam`.`GRADING_ID` AS `GRADING_ID`,
		`gra`.`GRADE_ACRONYM` AS `GRADING`,
		`sam`.`MARKS` AS `MARKS`,
		`sam`.`IS_ABSENT` AS `IS_ABSENT`
FROM `STUDENT_ASSIGNMENT_MARK` `sam` 
LEFT JOIN `STUDENT_CLASS_INFO` `sci` ON `sam`.`student_class_info_id`= `sci`.`student_class_info_id`
LEFT JOIN `ASSIGNMENT` `a` ON `sam`.`assignment_id` = `a`.`assignment_id` 
LEFT JOIN `GRADE_SUBJECT` `gs` ON `sam`.`grade_subject_id` = `gs`.`grade_subject_id`
LEFT JOIN `GRADE` `g` ON `gs`.`grade_id` = `g`.`grade_id`
LEFT JOIN `SUBJECT` `sub` ON `gs`.`subject_id` =`sub`.`subject_id`
LEFT JOIN `GRADING` `gra` ON  `sam`.`grading_id` = `gra`.`grading_id`
WHERE (year(`sci`.`YEAR`) = year(`a`.`DATE`)) ;


-- EXAM_RESULTS_VIEW
-- Purpose of this view is to generate student's Exam Results
CREATE OR REPLACE VIEW  EXAM_RESULTS_VIEW
AS
select s.STUDENT_ID, s.FULL_NAME, sub.DESCRIPTION as SUB_DESCRIPTION, em.YEAR, em.MARKS, g.GRADE_ACRONYM,
 g.DESCRIPTION as GRADING_DESCRIPTION, e.EXAM_ID, e.DESCRIPTION as EXAM_DESCRIPTION,
 e.MARK_TYPE, es.IS_OPTIONAL_SUBJECT, ea.ADMISSION_NO,
 em.IS_ABSENT AS ABSENT,
 em.IS_OPTIONAL AS OPTIONAL
from EXAM_MARKS em 
LEFT JOIN EXAM_SUBJECT es ON em.EXAM_SUBJECT_ID=es.EXAM_SUBJECT_ID 
LEFT JOIN GRADING g on em.GRADING_ID=g.GRADING_ID 
LEFT JOIN STUDENT s ON em.STUDENT_ID=s.STUDENT_ID 
LEFT JOIN EXAM e ON es.EXAM_ID=e.EXAM_ID 
LEFT JOIN SUBJECT sub ON es.SUBJECT_ID=sub.SUBJECT_ID , EXAM_ADMISSIONS ea
WHERE ea.EXAM_ID=e.EXAM_ID AND em.YEAR=ea.YEAR AND em.STUDENT_ID=ea.STUDENT_ID;


-- STUDENT MARKS EVALUATION PERFORMANCE 
-- Purpose of this view is to generate student's marks Results
CREATE OR REPLACE view STUDENT_TERM_MARKS_PERFORMANCE_VIEW

AS
	SELECT stm.IS_ABSENT AS IS_ABSENT, 
	stm.STUDENT_TERM_MARKS_ID AS STUDENT_TERM_MARKS_ID,
	stm.MARKS AS MARKS,
	t.DESCRIPTION AS TERM,
	s.DESCRIPTION AS SUBJECT_NAME,
	sci.STUDENT_ID AS STUDENT_ID,
	sci.YEAR AS YEAR,
	stu.ADMISSION_NO AS ADMISSION_NO,
	stu.NAME_WT_INITIALS AS NAME_WT_INITIALS,
    stuM.STUDY_MEDIUM_NAME AS STUDY_MEDIAM,
	sci.CLASS_GRADE_ID AS CLASS_GRADE_ID,
	sci.STUDENT_CLASS_INFO_ID AS STUDENT_CLASS_INFO_ID,
	stm.TERM_ID AS TERM_ID,
	gs.GRADE_SUBJECT_ID AS GRADE_SUBJECT_ID,
        cg.DESCRIPTION AS CLASS_DESCRIPTION,
        g.DESCRIPTION AS GRADE_DESCRIPTION
	
FROM STUDENT_TERM_MARK stm, 
     STUDENT_CLASS_INFO sci, 
	   TERM t, 
     GRADE_SUBJECT gs, 
     SUBJECT as s, 
     STUDENT stu,
     STUDY_MEDIUM stuM,
     CLASS_GRADE cg,
     GRADE g,
     MARKING_FLAG mf
	
WHERE stm.STUDENT_CLASS_INFO_ID=sci.STUDENT_CLASS_INFO_ID
	    AND t.TERM_ID=stm.TERM_ID 
	    AND gs.GRADE_SUBJECT_ID=stm.GRADE_SUBJECT_ID 
	    AND s.SUBJECT_ID=gs.SUBJECT_ID 
	    AND stu.STUDENT_ID=sci.STUDENT_ID
      AND stu.LANGUAGE_ID=stuM.STUDY_MEDIUM_ID
      AND sci.CLASS_GRADE_ID=cg.CLASS_GRADE_ID
      AND cg.GRADE_ID=g.GRADE_ID
      AND sci.CLASS_GRADE_ID=mf.CLASS_GRADE_ID
      AND t.TERM_ID=mf.TERM_ID
      AND sci.YEAR=mf.YEAR
      AND g.GRADE_ID=mf.GRADE_ID
      AND mf.MARKING_COMPLETED=true;

-- Alteration of STUDENT_TERM_MARKS_VIEW 
-- purpose of this view is adding student language id --

ALTER view STUDENT_TERM_MARKS_VIEW 

AS
	SELECT stm.IS_ABSENT AS IS_ABSENT, 
	stm.STUDENT_TERM_MARKS_ID AS STUDENT_TERM_MARKS_ID,
	stm.MARKS AS MARKS,
	t.DESCRIPTION AS TERM,
	s.DESCRIPTION AS SUBJECT_NAME,
	sci.STUDENT_ID AS STUDENT_ID,
	sci.YEAR AS YEAR,
	stu.ADMISSION_NO AS ADMISSION_NO,
	stu.NAME_WT_INITIALS AS NAME_WT_INITIALS,
	sci.CLASS_GRADE_ID AS CLASS_GRADE_ID,
	sci.STUDENT_CLASS_INFO_ID AS STUDENT_CLASS_INFO_ID,
	stm.TERM_ID AS TERM_ID,
	gs.GRADE_SUBJECT_ID AS GRADE_SUBJECT_ID,
	stu.LANGUAGE_ID AS LANGUAGE_ID 
	
FROM STUDENT_TERM_MARK stm, STUDENT_CLASS_INFO sci, 
	TERM t, GRADE_SUBJECT gs, SUBJECT as s, STUDENT stu
	
WHERE stm.STUDENT_CLASS_INFO_ID=sci.STUDENT_CLASS_INFO_ID
	AND t.TERM_ID=stm.TERM_ID 
	AND gs.GRADE_SUBJECT_ID=stm.GRADE_SUBJECT_ID 
	AND s.SUBJECT_ID=gs.SUBJECT_ID 
	AND stu.STUDENT_ID=sci.STUDENT_ID;

-- MEDIUM_WISE_SUBJECT_STUDENT_COUNT view
-- Purpose of this view is to generate study medim wise student count in class
-- Used in class wise student marks sheet

CREATE OR REPLACE VIEW MEDIUM_WISE_SUBJECT_STUDENT_COUNT
AS 
SELECT stmv.CLASS_GRADE_ID, stmv.STUDENT_CLASS_INFO_ID,stmv.YEAR,
	   stmv.TERM_ID, stmv.TERM, stmv.GRADE_SUBJECT_ID, 
	   s.Language_ID AS Language_ID,
       cg.DESCRIPTION, COUNT(stmv.STUDENT_CLASS_INFO_ID) AS STUDENT_COUNT
FROM ((STUDENT_TERM_MARKS_VIEW stmv 
JOIN CLASS_GRADE cg 
	ON (stmv.CLASS_GRADE_ID = cg.CLASS_GRADE_ID)) 
LEFT JOIN STUDENT s
	ON (stmv.STUDENT_ID= s.STUDENT_ID))
GROUP BY stmv.CLASS_GRADE_ID, stmv.GRADE_SUBJECT_ID, stmv.YEAR, stmv.TERM, s.Language_ID;


-- MEDIUM_WISE_CLASS_SUBJECT_TOTAL view 
-- Purpose of this view is to generate study medim wise class subject total
-- Used in class wise student marks sheet
CREATE OR REPLACE VIEW MEDIUM_WISE_CLASS_SUBJECT_TOTAL
AS 
SELECT g.DESCRIPTION AS GRADE,
	cg.DESCRIPTION AS CLASS_DESCRIPTION,
	cg.CLASS_GRADE_ID AS CLASS_GRADE_ID,
	sci.YEAR AS YEAR,
	stm.GRADE_SUBJECT_ID AS GRADE_SUBJECT_ID,
	s.DESCRIPTION AS SUBJECT,
	gs.IS_OPTIONAL_SUBJECT AS IS_OPTIONAL,
	t.DESCRIPTION AS TERM,
	st.LANGUAGE_ID AS LANGUAGE_ID,
	sum(stm.MARKS) AS TOTAL_MARKS 
FROM (((((((STUDENT_TERM_MARK stm
LEFT JOIN TERM t ON ((stm.TERM_ID = t.TERM_ID))) 
LEFT JOIN STUDENT_CLASS_INFO sci ON ((stm.STUDENT_CLASS_INFO_ID = sci.STUDENT_CLASS_INFO_ID))) 
LEFT JOIN GRADE_SUBJECT gs ON ((stm.GRADE_SUBJECT_ID = gs.GRADE_SUBJECT_ID))) 
LEFT JOIN CLASS_GRADE cg ON ((sci.CLASS_GRADE_ID = cg.CLASS_GRADE_ID))) 
LEFT JOIN GRADE g ON ((cg.GRADE_ID = g.GRADE_ID))) 
LEFT JOIN SUBJECT s ON ((gs.SUBJECT_ID = s.SUBJECT_ID)))
LEFT JOIN STUDENT st ON ((st.STUDENT_ID=sci.STUDENT_ID)))
GROUP BY g.DESCRIPTION, cg.DESCRIPTION, s.DESCRIPTION, t.DESCRIPTION, st.LANGUAGE_ID;


-- MEDIUM_WISE_CLASS_SUBJECT_AVERAGE_VIEW view 
-- Purpose of this view is to generate study medim wise class subject average
-- Used in class wise student marks sheet
CREATE OR REPLACE VIEW MEDIUM_WISE_CLASS_SUBJECT_AVERAGE_VIEW
AS 
SELECT mwcst.GRADE AS GRADE,
	   mwcst.CLASS_DESCRIPTION AS CLASS_DESCRIPTION,
	   mwcst.CLASS_GRADE_ID AS CLASS_GRADE_ID,
       mwcst.YEAR AS YEAR,
       mwcst.GRADE_SUBJECT_ID AS GRADE_SUBJECT_ID,
       mwcst.SUBJECT AS SUBJECT,
       mwcst.TERM AS TERM,
	   mwcst.LANGUAGE_ID,
       mwcst.TOTAL_MARKS AS TOTAL_MARKS,
       mwssc.STUDENT_COUNT AS STUDENT_COUNT,
       round((mwcst.TOTAL_MARKS / mwssc.STUDENT_COUNT),2) AS AVERAGE 
FROM MEDIUM_WISE_CLASS_SUBJECT_TOTAL mwcst
LEFT JOIN MEDIUM_WISE_SUBJECT_STUDENT_COUNT mwssc ON (mwcst.CLASS_DESCRIPTION = mwssc.DESCRIPTION 
&& mwcst.GRADE_SUBJECT_ID = mwssc.GRADE_SUBJECT_ID 
&& mwcst.TERM = mwssc.TERM && mwcst.YEAR = mwssc.YEAR
&& mwcst.LANGUAGE_ID = mwssc.LANGUAGE_ID);


-- STUDENT_TERM_MARK_AVG_VIEW 
-- Purpose of this view is to show the students term wise rank .
-- removed distinct key word

CREATE OR REPLACE VIEW STUDENT_TERM_MARK_RANK_VIEW

AS

	SELECT STUDENT_CLASS_INFO_ID, 
	CLASS_GRADE_ID, stmav.YEAR, 
	stmav.TERM_ID, 
  	term.description AS TERM,
  	stmav.TOTAL,
	AVERAGE,
    FIND_IN_SET(
        AVERAGE,  
            (SELECT  GROUP_CONCAT(
                            stmav1.AVERAGE
                            ORDER BY stmav1.AVERAGE  DESC
                       )
                FROM    
                	STUDENT_TERM_MARK_AVG_VIEW stmav1 
                WHERE 
                	stmav.YEAR= stmav1.YEAR
                	AND stmav.TERM_ID= stmav1.TERM_ID
                	AND stmav.CLASS_GRADE_ID= stmav1.CLASS_GRADE_ID
                )
            ) AS rank
    FROM STUDENT_TERM_MARK_AVG_VIEW stmav
    INNER JOIN TERM term
    ON `stmav`.`TERM_ID` = term.TERM_ID;

    
-- STUDENT_TERM_MARKS_GRADE_RANK_VIEW 
-- Purpose of this view is to show grade wise student's term marks rank . 
-- removed distinct key word


CREATE OR REPLACE VIEW  STUDENT_TERM_MARKS_GRADE_RANK_VIEW
AS
SELECT stmav.STUDENT_CLASS_INFO_ID, 
	stmav.CLASS_GRADE_ID,grade.GRADE_ID, stmav.YEAR, 
	stmav.TERM_ID, 
  	stmav.TOTAL,
	AVERAGE,
    FIND_IN_SET(
        AVERAGE,  
            (SELECT  GROUP_CONCAT(
                            stmav1.AVERAGE
                            ORDER BY stmav1.AVERAGE  DESC
                       )
                FROM    
                	STUDENT_TERM_MARK_AVG_VIEW stmav1
                  INNER JOIN CLASS_GRADE grade1
                  ON stmav1.CLASS_GRADE_ID = grade1.CLASS_GRADE_ID
                WHERE 
                	stmav.YEAR= stmav1.YEAR
                	AND stmav.TERM_ID= stmav1.TERM_ID
                	AND grade.GRADE_ID= grade1.GRADE_ID
                )
            ) AS RANK
FROM STUDENT_TERM_MARK_AVG_VIEW stmav    
INNER JOIN CLASS_GRADE grade
ON stmav.CLASS_GRADE_ID = grade.CLASS_GRADE_ID
order By GRADE_ID, YEAR, TERM_ID, RANK;



-- STAFF_PROFILE_REPORT_VIEW 
-- Purpose of this view is to show the staff Details .

CREATE OR REPLACE VIEW STAFF_PROFILE_VIEW
AS 
select s.STAFF_ID, s.NATIONAL_ID_NO, s.FULL_NAME, s.NAME_WT_INITIALS, se.DESCRIPTION as SECTION, s.ADDRESS, c.DESCRIPTION as CITY, 
s.RESIDENCE_NO, s.MOBILE_NO, s.EMAIL, cn.COUNTRY_NAME as COUNTRY, r.DESCRIPTION as RACE, re.DESCRIPTION as RELIGION, 
cs.DESCRIPTION as CIVIL_STATUS, s.DOB, s.GENDER, s.DATE_FIRST_APPOINTMENT, s.DATE_OF_HIRE, 
an.DESCRIPTION as APPOINTMENT_NATURE, ac.DESCRIPTION as APPOINTMENT_CLASSIFICATION, 
sm.STUDY_MEDIUM_NAME as STUDY_MEDIUM, sc.DESCRIPTION as STAFF_CATEGORY, ssc.DESCRIPTION as STAFF_SERVICE,
s.STAFF_CLASS, s.STAFF_GRADE, s.BASIC_SALARY, s.PHOTO 
from STAFF s 
left join SECTION se on s.SECTION_ID=se.SECTION_ID 
left join CITY c on s.CITY_ID=c.CITY_ID 
left join COUNTRY cn on s.COUNTRY_ID = cn.COUNTRY_ID 
left join RACE r on s.RACE_ID=r.RACE_ID 
left join RELIGION re on s.RELIGION_ID=re.RELIGION_ID 
left join CIVIL_STATUS cs on s.CIVIL_STATUS_ID=cs.CIVIL_STATUS_ID 
left join APPOINTMENT_NATURE an on s.NATURE_ID=an.APPOINTMENT_NATURE_ID 
left join APPOINTMENT_CLASSIFICATION ac on s.CLASSIFICATION_ID=ac.APPOINTMENT_CLASSIFICATION_ID 
left join STUDY_MEDIUM sm on s.STUDY_MEDIUM_ID=sm.STUDY_MEDIUM_ID 
left join STAFF_CATEGORY sc on s.STAFF_CATEGORY_ID=sc.STAFF_CATEGORY_ID 
left join STAFF_SERVICE_CATEGORY ssc on s.STAFF_SERVICE_ID= ssc.STAFF_SERVICE_CATEGORY_ID ;
	