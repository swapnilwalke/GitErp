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

USE `akura`;

DELIMITER $$

DROP PROCEDURE IF EXISTS `sp_student_gamification_search` $$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_student_gamification_search`(page_from INT, page_size INT,IN gradeId integer(10), IN classId integer(10), IN yearId integer(10), IN monthId integer(10))
BEGIN

    SET @_page_from = page_from;
    SET @_page_size = page_size;
	SET @_gradeId = gradeId;
	SET @_classId = classId;
	SET @_yearId = yearId;
	SET @_monthId = monthId;
	
    IF (gradeId > 0 && classId > 0 && yearId > 0 && monthId > 0) THEN
	
	  PREPARE stmt FROM"
        select count(b.ddddate), b.dddempno, b.dddempname, b.classdescription, b.photo, b.namewithinitials
        from DAILY_STUDENT_ATTENDANCE_VIEW b
        where b.gradeId = ? AND b.classId = ? AND b.yearId = ? AND b.monthId = ?
		group by b.dddempno, b.gradeId, b.classId, b.yearId, b.monthId
		order by count(b.ddddate) desc,b.dddempno asc
		limit ?, ?";
		
		EXECUTE stmt USING @_gradeId, @_classId, @_yearId, @_monthId, @_page_from, @_page_size;
		
    ELSEIF (gradeId > 0 && classId = 0 && yearId > 0 && monthId > 0) THEN

	  PREPARE stmt FROM"
        select count(b.ddddate), b.dddempno, b.dddempname, b.classdescription, b.photo, b.namewithinitials
        from DAILY_STUDENT_ATTENDANCE_VIEW b
        where b.gradeId = ? AND b.yearId = ? AND b.monthId = ?
		group by b.dddempno, b.gradeId, b.yearId, b.monthId
		order by count(b.ddddate) desc,b.dddempno asc
		limit ?, ?";
		
		EXECUTE stmt USING @_gradeId, @_yearId, @_monthId, @_page_from, @_page_size;
		
	ELSEIF (gradeId > 0 && classId > 0 && yearId > 0 && monthId = 0) THEN

	  PREPARE stmt FROM"
        select count(b.ddddate), b.dddempno, b.dddempname, b.classdescription, b.photo, b.namewithinitials
        from DAILY_STUDENT_ATTENDANCE_VIEW b
        where b.gradeId = ? AND b.classId = ? AND b.yearId = ? 
		group by b.dddempno, b.gradeId, b.classId, b.yearId
		order by count(b.ddddate) desc,b.dddempno asc
		limit ?, ?";
		
		EXECUTE stmt USING @_gradeId, @_classId, @_yearId, @_page_from, @_page_size;
		
	ELSEIF (gradeId > 0 && classId = 0 && yearId > 0 && monthId = 0) THEN

	  PREPARE stmt FROM"
        select count(b.ddddate), b.dddempno, b.dddempname, b.classdescription, b.photo, b.namewithinitials
        from DAILY_STUDENT_ATTENDANCE_VIEW b
        where b.gradeId = ? AND b.yearId = ? 
		group by b.dddempno, b.gradeId, b.yearId
		order by count(b.ddddate) desc,b.dddempno asc
		limit ?, ?";
		
		EXECUTE stmt USING @_gradeId, @_yearId, @_page_from, @_page_size;
		
	ELSEIF (gradeId = 0 && classId = 0 && yearId > 0 && monthId = 0) THEN

	  PREPARE stmt FROM"
        select count(b.ddddate), b.dddempno, b.dddempname, b.classdescription, b.photo, b.namewithinitials
        from DAILY_STUDENT_ATTENDANCE_VIEW b
        where b.yearId = ? 
		group by b.dddempno, b.yearId
		order by count(b.ddddate) desc,b.dddempno asc
	    limit ?, ?";
		
		EXECUTE stmt USING @_yearId, @_page_from, @_page_size;
		
	ELSEIF (gradeId = 0 && classId = 0 && yearId > 0 && monthId > 0) THEN
	
	  PREPARE stmt FROM"
        select count(b.ddddate), b.dddempno, b.dddempname, b.classdescription, b.photo, b.namewithinitials
        from DAILY_STUDENT_ATTENDANCE_VIEW b
        where b.yearId = ? AND b.monthId = ?
		group by b.dddempno, b.yearId
		order by count(b.ddddate) desc,b.dddempno asc
		limit ?, ?";
	
	    EXECUTE stmt USING @_yearId, @_monthId, @_page_from, @_page_size;
		
    END IF;
	
	DEALLOCATE PREPARE stmt;
END
