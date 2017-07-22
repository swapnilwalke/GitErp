<%--
    < ÀKURA, This application manages the daily activities of a Teacher and a Student of a School>

    Copyright (C) 2012 Virtusa Corporation.
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
 --%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code="APPLICATION.NAME" />
</title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<link rel="shortcut icon" href="resources/images/favicon.ico" type="image/x-icon" />

<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>

</head>
<body>
	<div id="page_container">
		<div id="breadcrumb_area">

			<div class="help_link">
				<a href="#"
					onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/reporting/studentPerformanceReportsHelp.html"/>','helpWindow',780,550)"><img
					src="resources/images/ico_help.png" width="20" height="20"
					align="absmiddle"> <spring:message code="application.help"/></a>
			</div>
		</div>
		<div class="clearfix"></div>
		<h1><spring:message code="REPORT.STUDENT.GENERAL.REPORTS"/></h1>
		<div id="content_main">
			<div class="clearfix"></div>

			<div class="section_box">
				<div class="section_box_header">
					<h2><spring:message code="REPORT.CLASS.WISE.STUDENT.TERM.MARKS.EVALUATION"/></h2>
				</div>

				<!-- Report 3  has 2 div tags-->
				<div class="section_full_inside">

					<div class="box_border">
						<!-- form 1 used for first report -->
						<form:form action="studentPreformanceReport.htm" method="POST"
							commandName="cRStudentPerformanceTemplate" name="form3">
							<label class="required_sign"> <c:if
										test="${message != null}">${message}</c:if> </label>
							<input type="hidden" name="selectedValue" />
							<input type="hidden" name="gradeClass" />

							<input type="hidden" name="formIndex" />
							<label class="required_sign"> <form:errors
									path="inBetweenGreaterValue" /><br> </label>
							<table>

								<tr>
									<td><span class="required_sign">*</span><label><spring:message
												code="REPORT.STUDENT.GRADE" /> </label></td>
									<td><form:select id="selectGrade" path="gradeDescription">
											<form:option value="0" id="selectOptionGrade">  <spring:message
												code="application.drop.down" />  </form:option>

										    <c:forEach items="${gradeList}" var="grade">
								                  	<option value="${grade.gradeId}" <c:if test='${selectedGradeId != null &&
								                  	grade.gradeId eq selectedGradeId}'> selected="selected"</c:if>>${grade.description}</option>
								            </c:forEach>

										</form:select>
									</td>
								</tr>

								<tr>
									<td><span class="required_sign">*</span> <label><spring:message code="REPORT.CLASS.WISE.STUDENT.MARK.SHEET.TERM" />
									</label>
									</td>

									<td>
										<form:select path="term">
											<option value="0">
												<spring:message code="application.drop.down" />
											</option>
											<form:options items="${termList}" itemValue="termId"
												itemLabel="description" />

										</form:select>
									</td>
								</tr>

								<tr>
									<td><span class="required_sign">*</span>
									<label><spring:message
												code="REPORT.STUDENT.PERFORMANCE.INBETWEEN" /></label>
									</td>
									<td><form:input path="inBetweenLessValue" />
									</td>

								</tr>
								<tr>
								    <td><span class="required_sign">*</span>
								    <label><spring:message
												code="REPORT.STUDENT.PERFORMANCE.AND" /></label>
									</td>
									<td><form:input path="inBetweenGreaterValue"
											cssStyle="width=300px" />
									</td>
								</tr>

                                <tr>
									<td colspan="2"><input type="submit" class="button"
										onClick="generateTrport('this')" value="<spring:message code="REPORT.UI.GENERATE.REPORT"/>">
									</td>
								</tr>

                            <tr>
								<td colspan="2">
									  <label><font size="2" face="Consolas"><spring:message code="REPORT.STUDENT.PERFORMANCE.SPECIALNOTE"/> </font></label>
								</td>
							</tr>
							</table>
						</form:form>
						<!-- end form 2 enterd before div style clearfix -->
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>
	</div>
	<h:footer />
</body>
</html>
