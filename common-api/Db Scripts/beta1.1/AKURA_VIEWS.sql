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

-- STUDENT_SPECIAL_EVENT_PARTICIPATION_CATEGORY
-- purpose of this view to show only club, class grade, sport id 
-- which have allocated students
use `akura`;

CREATE OR REPLACE VIEW STUDENT_SPECIAL_EVENT_PARTICIPATION_CATEGORY

AS

SELECT STUDENT_ID, CLASS_GRADE_ID, null AS CLUB_SOCIETY_ID, 
null AS SPORT_CATEGORY_ID, YEAR FROM STUDENT_CLASS_INFO 

UNION


SELECT STUDENT_ID, null AS CLASS_GRADE_ID, null AS CLUB_SOCIETY_ID, 
SPORT_CATEGORY_ID, YEAR FROM STUDENT_SPORT 

UNION

SELECT STUDENT_ID, null AS CLASS_GRADE_ID, CLUB_SOCIETY_ID, 
null AS SPORT_CATEGORY_ID, YEAR FROM STUDENT_CLUB_SOCIETY;

-- STUDENT_SPECIAL_EVENT_PARTICIPATION
-- purpose of this view to show the stdent special event participation id
-- ,student details with their allocated sport,class grade, club and society.

CREATE OR REPLACE VIEW STUDENT_SPECIAL_EVENT_PARTICIPATION

AS

SELECT s.ADMISSION_NO, s.FULL_NAME, sep.SPECIAL_EVENT_PARTICIPATION_ID, 
category.SPORT_CATEGORY_ID, category.CLASS_GRADE_ID, category.CLUB_SOCIETY_ID,
category.STUDENT_ID, category.YEAR FROM SPECIAL_EVENT_PARTICIPATION sep

INNER JOIN 

STUDENT_SPECIAL_EVENT_PARTICIPATION_CATEGORY category 

ON (sep.SPORT_CATEGORY_ID = category.SPORT_CATEGORY_ID || 
sep.CLASS_GRADE_ID = category.CLASS_GRADE_ID || 
sep.CLUB_SOCIETY_ID = category.CLUB_SOCIETY_ID) 

LEFT JOIN 

STUDENT s on s.STUDENT_ID = category.STUDENT_ID ;

-- STUDENT_SUB_TERM_MARKS_VIEW 
-- purpose of this view is to show the students sub term marks in student summary report.

CREATE OR REPLACE VIEW STUDENT_SUB_TERM_MARKS_VIEW
AS
SELECT sstm.student_sub_term_mark_id as STUDENT_SUB_TERM_MARK_ID,
    sstm.IS_ABSENT as IS_ABSENT,
    sstm.grading_id as GRADING_ID,
    sstm.marks as MARKS,
	t.DESCRIPTION as TERM,
	st.DESCRIPTION as SUB_TERM,
	s.DESCRIPTION as SUBJECT_NAME,
	sci.STUDENT_ID as STUDENT_ID,
	sci.YEAR as YEAR
from STUDENT_SUB_TERM_MARK sstm,
	STUDENT_TERM_MARK stm, 
	STUDENT_CLASS_INFO sci, 
	TERM t, 
	GRADE_SUBJECT gs, 
	SUBJECT s,
	SUB_TERM st
where sstm.STUDENT_TERM_MARKS_ID = stm.STUDENT_TERM_MARKS_ID
  and stm.STUDENT_CLASS_INFO_ID=sci.STUDENT_CLASS_INFO_ID 
	and t.TERM_ID=stm.TERM_ID 
	and gs.GRADE_SUBJECT_ID=stm.GRADE_SUBJECT_ID 
	and s.SUBJECT_ID=gs.SUBJECT_ID
	and st.sub_term_id = sstm.sub_term_id;
	
	
-- Alteration of STUDENT_TERM_MARKS_VIEW 
-- purpose of this view to show the student marks in accedemic details page and progress bar --

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
	gs.GRADE_SUBJECT_ID AS GRADE_SUBJECT_ID
	
FROM STUDENT_TERM_MARK stm, STUDENT_CLASS_INFO sci, 
	TERM t, GRADE_SUBJECT gs, SUBJECT as s, STUDENT stu
	
WHERE stm.STUDENT_CLASS_INFO_ID=sci.STUDENT_CLASS_INFO_ID
	AND t.TERM_ID=stm.TERM_ID 
	AND gs.GRADE_SUBJECT_ID=stm.GRADE_SUBJECT_ID 
	AND s.SUBJECT_ID=gs.SUBJECT_ID 
	AND stu.STUDENT_ID=sci.STUDENT_ID;



-- STUDENT_TERM_MARK_AVG_VIEW 
-- Purpose of this view is to show the students term marks average .

CREATE OR REPLACE VIEW STUDENT_TERM_MARK_AVG_VIEW

AS

	SELECT sci.STUDENT_ID,
	stm.STUDENT_CLASS_INFO_ID,
	sci.CLASS_GRADE_ID,sci.YEAR, 
	stm.TERM_ID, SUM(stm.MARKS) AS TOTAL,
	ROUND(AVG(stm.MARKS),2) as AVERAGE 
	
FROM 
	STUDENT_TERM_MARK stm
	
INNER JOIN 
	STUDENT_CLASS_INFO sci
ON

	stm.STUDENT_CLASS_INFO_ID = sci.STUDENT_CLASS_INFO_ID
GROUP BY 
	stm.STUDENT_CLASS_INFO_ID, stm.TERM_ID
	
ORDER BY 
	AVERAGE DESC ;


-- STUDENT_TERM_MARK_AVG_VIEW 
-- Purpose of this view is to show the students term wise rank .

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
                            DISTINCT stmav1.AVERAGE
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

-- STUDENT_DISCIPLINE_VIEW 
-- Purpose of this view is to show the student discipline .    
    
CREATE OR REPLACE VIEW STUDENT_DISCIPLINE_VIEW AS
Select
sd.STUDENT_DISCIPLINE_ID AS STUDENT_DISCIPLINE_ID,
sd.STUDENT_ID AS STUDENT_ID,
s.ADMISSION_NO AS ADMISSION_NO, 
s.FULL_NAME AS FULL_NAME, 
sd.date AS WARN_DATE , 
sd.COMMENT AS WARNING,
wl.DESCRIPTION  AS WARNING_LEVEL, 
ul.FIRST_NAME AS TEACHER_NAME,
sd.IS_INFORMED_TO_PARENT AS INFORMED_TO_PARENT,
cg.DESCRIPTION AS CLASS_DESCRIPTION

from STUDENT_DISCIPLINE sd
LEFT JOIN WARNING_LEVEL wl
ON  sd.WARNING_LEVEL_ID  = wl.WARNING_LEVEL_ID

LEFT JOIN STUDENT s
ON  sd.STUDENT_ID =s.STUDENT_ID

LEFT JOIN USER_LOGIN ul
ON sd.USER_LOGIN_ID = ul.USER_LOGIN_ID 

LEFT JOIN STUDENT_CLASS_INFO sci
ON s.STUDENT_ID = sci.STUDENT_ID

LEFT JOIN CLASS_GRADE cg
ON sci.CLASS_GRADE_ID = cg.CLASS_GRADE_ID

LEFT JOIN CLASS c
ON cg.CLASS_ID = c.CLASS_ID

LEFT JOIN GRADE g
ON cg.GRADE_ID = g.GRADE_ID

where YEAR(sd.date) = YEAR(sci.year) AND YEAR(sci.year) = year(now());	


    
-- AVERAGE_TERM_MARK_VIEW     

CREATE OR REPLACE VIEW AVERAGE_TERM_MARKS 
AS 
select g.DESCRIPTION AS GRADE,
cg.DESCRIPTION AS CLASS_DESCRIPTION,
sci.YEAR AS YEAR,
stm.GRADE_SUBJECT_ID AS GRADE_SUBJECT_ID,
s.DESCRIPTION AS SUBJECT,
gs.IS_OPTIONAL_SUBJECT AS IS_OPTIONAL,
t.DESCRIPTION AS TERM,
sum(stm.MARKS) AS TOTAL_MARKS 
from ((((((STUDENT_TERM_MARK stm
left join TERM t on((stm.TERM_ID = t.TERM_ID))) 
left join STUDENT_CLASS_INFO sci on((stm.STUDENT_CLASS_INFO_ID = sci.STUDENT_CLASS_INFO_ID))) 
left join GRADE_SUBJECT gs on((stm.GRADE_SUBJECT_ID = gs.GRADE_SUBJECT_ID))) 
left join CLASS_GRADE cg on((sci.CLASS_GRADE_ID = cg.CLASS_GRADE_ID))) 
left join GRADE g on((cg.GRADE_ID = g.GRADE_ID))) 
left join SUBJECT s on((gs.SUBJECT_ID = s.SUBJECT_ID))) 
group by g.DESCRIPTION, cg.DESCRIPTION, s.DESCRIPTION, t.DESCRIPTION;


-- STUDENT_COUNT_VIEW

CREATE OR REPLACE VIEW STUDENT_COUNT 
AS select count(distinct sci.STUDENT_CLASS_INFO_ID) AS studentGradeCount,
sci.YEAR AS year,
t.DESCRIPTION AS term,
g.DESCRIPTION AS grade,
cg.DESCRIPTION AS classGrade,
s.DESCRIPTION AS subject 
from (((((((STUDENT_CLASS_INFO sci join CLASS_GRADE cg on((cg.CLASS_GRADE_ID = sci.CLASS_GRADE_ID))) 
left join CLASS c on((c.CLASS_ID = cg.CLASS_ID))) 
left join GRADE g on((g.GRADE_ID = cg.GRADE_ID))) 
left join STUDENT_TERM_MARK stm on((stm.STUDENT_CLASS_INFO_ID = sci.STUDENT_CLASS_INFO_ID))) 
left join GRADE_SUBJECT gs on((gs.GRADE_SUBJECT_ID = stm.GRADE_SUBJECT_ID))) 
left join SUBJECT s on((s.SUBJECT_ID = gs.SUBJECT_ID))) 
left join TERM t on((t.TERM_ID = stm.TERM_ID))) 
where (gs.IS_OPTIONAL_SUBJECT = 0) 
group by sci.YEAR,
t.DESCRIPTION,
s.DESCRIPTION,
g.DESCRIPTION,
c.DESCRIPTION;


-- FINAL_AVERAGE_TERM_MARK_VIEW  

CREATE OR REPLACE VIEW FINAL_AVERAGE_TERM_MARKS 
AS 
select atm.GRADE AS GRADE,
atm.CLASS_DESCRIPTION AS CLASS_DESCRIPTION,
atm.YEAR AS YEAR,
atm.GRADE_SUBJECT_ID AS GRADE_SUBJECT_ID,
atm.SUBJECT AS SUBJECT,
atm.TERM AS TERM,
atm.TOTAL_MARKS AS TOTAL_MARKS,
sc.studentGradeCount AS STUDENT_COUNT,
round((atm.TOTAL_MARKS / sc.studentGradeCount),2) AS AVERAGE 
from (AVERAGE_TERM_MARKS atm 
left join STUDENT_COUNT sc on((atm.CLASS_DESCRIPTION = sc.classGrade))) 
where ((atm.YEAR = sc.year) and (atm.CLASS_DESCRIPTION = sc.classGrade));


-- GRADE SUBJECT VIEW

CREATE OR REPLACE VIEW GRADE_SUBJECT_AVERAGE 
AS 
select round((sum(atm.TOTAL_MARKS) / sc.studentGradeCount),2) AS grade_Average,
atm.TERM AS term,atm.YEAR AS year,
atm.SUBJECT AS subject,
sc.grade AS grade,
atm.TOTAL_MARKS AS total_marks,
sc.studentGradeCount AS studentGradeCount 
from (STUDENT_COUNT sc 
left join AVERAGE_TERM_MARKS atm on(((atm.TERM = sc.term) 
and (atm.YEAR = sc.year) and (atm.GRADE = sc.grade) 
and (atm.SUBJECT = sc.subject)))) 
where (atm.IS_OPTIONAL = 0) 
group by atm.YEAR, atm.SUBJECT, sc.grade, atm.TERM;

-- STUDENT_TERM_SUBJECT_MARKS

CREATE OR REPLACE VIEW STUDENT_TERM_SUBJECT_MARKS 
AS 
select sci.STUDENT_ID AS student_id,
sci.YEAR AS year,
g.DESCRIPTION AS grade,
cg.DESCRIPTION AS classGrade,
t.DESCRIPTION AS term,
stm.MARKS AS marks,
s.DESCRIPTION AS subject 
from (((((((STUDENT_TERM_MARK stm 
left join STUDENT_CLASS_INFO sci on((stm.STUDENT_CLASS_INFO_ID = sci.STUDENT_CLASS_INFO_ID))) 
left join CLASS_GRADE cg on((cg.CLASS_GRADE_ID = sci.CLASS_GRADE_ID))) 
left join GRADE_SUBJECT gs on((gs.GRADE_SUBJECT_ID = stm.GRADE_SUBJECT_ID))) 
left join SUBJECT s on((s.SUBJECT_ID = gs.SUBJECT_ID))) 
left join TERM t on((t.TERM_ID = stm.TERM_ID))) 
left join CLASS c on((c.CLASS_ID = cg.CLASS_ID))) 
left join GRADE g on((g.GRADE_ID = cg.GRADE_ID))) 
group by sci.YEAR,t.DESCRIPTION,sci.STUDENT_ID,s.DESCRIPTION;

-- STUDENT SUBJECT AVERAGE VIEW

CREATE OR REPLACE VIEW STUDENT_SUBJECT_AVERAGE 
AS 
select stsm.student_id AS student_id,
fatm.SUBJECT AS subject,
fatm.YEAR AS year,
fatm.AVERAGE AS average,
fatm.GRADE AS grade,
fatm.CLASS_DESCRIPTION AS class,
stsm.marks AS marks,
fatm.TERM AS term 
from (STUDENT_TERM_SUBJECT_MARKS stsm 
left join FINAL_AVERAGE_TERM_MARKS fatm on(((fatm.SUBJECT = stsm.subject) 
and (fatm.TERM = stsm.term) and (fatm.YEAR = stsm.year) 
and (fatm.CLASS_DESCRIPTION = stsm.classGrade)))) 
group by stsm.student_id, fatm.YEAR,
fatm.CLASS_DESCRIPTION, fatm.TERM,
fatm.SUBJECT, fatm.CLASS_DESCRIPTION;

-- CLASS GRADE AVERAGE VIEW

CREATE OR REPLACE VIEW CLASS_GRADE_AVERAGE 
AS 
select ssa.student_id AS student_id,
ssa.year AS year,
ssa.grade AS grade,
ssa.class AS class,
ssa.term AS term,
ssa.subject AS subject,
ssa.marks AS marks,
ssa.average AS class_average,
gsa.grade_Average AS grade_average 
from (STUDENT_SUBJECT_AVERAGE ssa join GRADE_SUBJECT_AVERAGE gsa) 
where ((ssa.year = gsa.year) 
and (ssa.grade = gsa.grade) 
and (ssa.term = gsa.term) 
and (ssa.subject = gsa.subject));


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


