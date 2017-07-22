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

USE `akura`;



DELIMITER $$
DROP PROCEDURE IF EXISTS `sp_student_advance_search` $$
CREATE PROCEDURE sp_student_advance_search(IN classId integer(10), IN sportId integer(10), IN subjectId integer(10), IN prefectTypeId integer(10), IN workingSegmentId integer(10))
BEGIN
  SET @join1 =''; 
  SET @join2 ='';
  SET @join3 ='';
  SET @join4 ='';
  SET @join5 ='';
  SET @whr ='true ';
  SET @whr1 ='';
  SET @whr2 ='';
  SET @whr3 ='';
  SET @whr4 ='';


  IF (sportId>0) THEN
	SET @join1 ='LEFT JOIN STUDENT_SPORT sport ON st.student_id = sport.student_id 
                 LEFT JOIN SPORT_CATEGORY cat ON sport.sport_category_id = cat.sport_category_id ';
	SET @whr1 =CONCAT('AND cat.sport_id = ',sportId,' ');			 
  END IF;

  IF (prefectTypeId>0) THEN
	SET @join2 ='LEFT JOIN STUDENT_PREFECT stPref ON st.student_id = stPref.student_id  
                 LEFT JOIN PREFECT_TYPE prefType ON stPref.prefect_type_id = prefType.prefect_type_id '; 
    SET @whr2 =CONCAT('AND prefType.prefect_type_id =', prefectTypeId,' ');
  END IF;

  IF (workingSegmentId>0) THEN
	SET @join3 ='LEFT JOIN STUDENT_PARENT stp ON st.student_id = stp.student_id 
                 LEFT JOIN PARENT p ON p.parent_id = stp.parent_id 
                 LEFT JOIN WORKING_SEGMENT ws ON p.working_segment_id = ws.working_segment_id  ';
    SET @whr3 = CONCAT('AND ws.working_segment_id = ',workingSegmentId,' ');       	 
  END IF;

  IF (subjectId>0) THEN
	SET @join4 ='LEFT JOIN STUDENT_TERM_MARK term ON info.student_class_info_id = term.student_class_info_id 
                 LEFT JOIN GRADE_SUBJECT sub ON term.grade_subject_id = sub.grade_subject_id ';
    SET @whr4 = CONCAT('AND sub.subject_id = ',subjectId,' ');        	 
  END IF;

  IF (classId>0) THEN
	SET @whr =CONCAT('info.class_grade_id =', classId ,' ');
  END IF;


  SET @query = CONCAT('SELECT  st.student_Id,
			    st.full_Name,
			    cg.description, cg.description, cg.description, cg.description,
			    cg.description, cg.description, cg.description, cg.description,
			    max(info.year),
			    st.student_status_Id,
			    st.first_School_Day,
			    stTempLeave.to_date,
			    stTempLeave.from_date,
			    stSuspDetails.to_date,
			    stSuspDetails.from_date

				FROM STUDENT st LEFT JOIN STUDENT_CLASS_INFO info ON st.student_id = info.student_id 
				LEFT JOIN STUDENT_TEMPORARY_LEAVE stTempLeave ON st.student_id = stTempLeave.student_id
				LEFT JOIN STUDENT_SUSPEND_DETAILS stSuspDetails ON st.student_id = stSuspDetails.student_id 
				LEFT JOIN CLASS_GRADE cg ON info.class_grade_id = cg.class_grade_id 
				', @join1 , @join2 , @join3 , @join4 , '
				WHERE(' , @whr , @whr1 , @whr2 , @whr3 , @whr4 ,') 
     GROUP BY st.student_id 
     ORDER BY st.full_name');
  PREPARE stmt FROM @query;
  EXECUTE stmt;
  DEALLOCATE PREPARE stmt;
END$$
DELIMITER ;

