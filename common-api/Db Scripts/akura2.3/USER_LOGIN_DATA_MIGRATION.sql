/**
 * Updates the user login table with the deleted flag.
 * If the user login identification number does not
 * match with the registration number with the staff,
 * then considers it as a deleted account.
*/

use `akura`;

SET SQL_SAFE_UPDATES = 0;
UPDATE USER_LOGIN user
    SET user.deleted = 1, user.status = 0, user.EMAIL = 'invalid', USERNAME = 'invalid', USER_IDENTIFICATION_NO = 'invalid'  
        WHERE user.user_login_id IN
            (SELECT temp.id FROM (SELECT userLogin.user_login_id AS id 
                FROM USER_LOGIN userLogin
            INNER JOIN USER_ROLE role
                ON role.user_role_id = userLogin.user_role_id
            LEFT JOIN STAFF staff
                ON userLogin.USER_IDENTIFICATION_NO = staff.registration_no
            WHERE role.user_role_id IN (2,4) AND 
            staff.registration_no is null)AS temp);


/**
 * Updates the user login table with the identification column.
 * If the user login identification number
 * matches with the registration number with the staff,
 * then replace the identification number as the staff key.
*/
SET SQL_SAFE_UPDATES = 0;
UPDATE USER_LOGIN user
    INNER JOIN USER_ROLE role
                ON role.user_role_id = user.user_role_id
            LEFT JOIN STAFF staff
                ON user.USER_IDENTIFICATION_NO = staff.registration_no    
            SET user.USER_IDENTIFICATION_NO = staff.staff_id
                WHERE user.user_login_id IN
                (SELECT temp.id FROM (SELECT userLogin.user_login_id AS id 
                    FROM USER_LOGIN userLogin
                INNER JOIN USER_ROLE role
                    ON role.user_role_id = userLogin.user_role_id
                LEFT JOIN STAFF staff
                    ON userLogin.USER_IDENTIFICATION_NO = staff.registration_no
                WHERE role.user_role_id IN (2,4) AND 
                    userLogin.USER_IDENTIFICATION_NO = staff.registration_no)AS temp);

/**
 * Updates the user login table with the deleted flag.
 * If the user login identification number does not
 * match with the admission number with the student,
 * then considers it as a deleted account.
*/
SET SQL_SAFE_UPDATES = 0;
UPDATE USER_LOGIN user
    SET user.deleted = 1, user.status = 0, user.EMAIL = 'invalid', USERNAME = 'invalid', USER_IDENTIFICATION_NO = 'invalid' 
        WHERE user.user_login_id IN
            (SELECT temp.id FROM (SELECT userLogin.user_login_id AS id 
                FROM USER_LOGIN userLogin
            INNER JOIN USER_ROLE role
                ON role.user_role_id = userLogin.user_role_id
            LEFT JOIN STUDENT student
                ON userLogin.USER_IDENTIFICATION_NO = student.ADMISSION_NO
            WHERE role.user_role_id IN (3) AND 
            student.ADMISSION_NO is null)AS temp);
            

/**
 * Updates the user login table with the identification column.
 * If the user login identification number
 * matches with the admission number with the student,
 * then replace the identification number as the student key.
*/
SET SQL_SAFE_UPDATES = 0;
UPDATE USER_LOGIN user
    INNER JOIN USER_ROLE role
                ON role.user_role_id = user.user_role_id
            LEFT JOIN STUDENT student
                ON user.USER_IDENTIFICATION_NO = student.ADMISSION_NO    
            SET user.USER_IDENTIFICATION_NO = student.student_id
                WHERE user.user_login_id IN
                (SELECT temp.id FROM (SELECT userLogin.user_login_id AS id 
                    FROM USER_LOGIN userLogin
                INNER JOIN USER_ROLE role
                    ON role.user_role_id = userLogin.user_role_id
                LEFT JOIN STUDENT student
                    ON userLogin.USER_IDENTIFICATION_NO = student.ADMISSION_NO
                WHERE role.user_role_id IN (3) AND 
                    userLogin.USER_IDENTIFICATION_NO = student.ADMISSION_NO)AS temp);
                    

/**
 * Updates the user login table with the deleted flag.
 * If the user login identification number does not
 * match with the national identification number with the parent,
 * then considers it as a deleted account.
*/
SET SQL_SAFE_UPDATES = 0;
UPDATE USER_LOGIN user
    SET user.deleted = 1, user.status = 0, user.EMAIL = 'invalid', USERNAME = 'invalid', USER_IDENTIFICATION_NO = 'invalid'  
        WHERE user.user_login_id IN
            (SELECT temp.id FROM (SELECT userLogin.user_login_id AS id 
                FROM USER_LOGIN userLogin
            INNER JOIN USER_ROLE role
                ON role.user_role_id = userLogin.user_role_id
            LEFT JOIN PARENT parent
                ON userLogin.USER_IDENTIFICATION_NO = parent.NATIONAL_ID_NO
            WHERE role.user_role_id IN (5) AND 
            parent.NATIONAL_ID_NO is null)AS temp);
            
/**
 * Updates the user login table with the identification column.
 * If the user login identification number
 * matches with the national id number with the parent,
 * then replace the identification number as the parent key.
*/
SET SQL_SAFE_UPDATES = 0;
UPDATE USER_LOGIN user
    INNER JOIN USER_ROLE role
                ON role.user_role_id = user.user_role_id
            LEFT JOIN PARENT parent
                ON user.USER_IDENTIFICATION_NO = parent.NATIONAL_ID_NO    
            SET user.USER_IDENTIFICATION_NO = parent.PARENT_ID
                WHERE user.user_login_id IN
                (SELECT temp.id FROM (SELECT userLogin.user_login_id AS id 
                    FROM USER_LOGIN userLogin
                INNER JOIN USER_ROLE role
                    ON role.user_role_id = userLogin.user_role_id
                LEFT JOIN PARENT parent
                    ON userLogin.USER_IDENTIFICATION_NO = parent.NATIONAL_ID_NO
                WHERE role.user_role_id IN (5) AND 
                    userLogin.USER_IDENTIFICATION_NO = parent.NATIONAL_ID_NO)AS temp);
