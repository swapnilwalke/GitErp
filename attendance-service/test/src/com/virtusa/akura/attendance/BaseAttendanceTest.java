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
package com.virtusa.akura.attendance;

import junit.framework.TestCase;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This will act as the Base test class which loads the Spring Configuration and
 * create the context without the actual container.
 * 
 * @author Virtusa Corporation
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring-beans/akura-attendance-beans-main.xml",
		"/spring-beans/akura-common-beans-main.xml",
		"/spring-beans/akura-student-beans-main.xml",
		"/spring-beans/akura-staff-beans-main.xml" })
public class BaseAttendanceTest extends TestCase {

}
