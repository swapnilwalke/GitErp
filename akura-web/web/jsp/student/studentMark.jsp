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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code='APPLICATION.NAME' /></title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<script src="resources/js/jquery-1.6.2.min.js" language="javascript"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script type="text/javascript">

	//Term Mark save confirmation message
	function saveMark(){
		var ans = window.confirm("<spring:message code='REF.SAVE.CONFIRMATION' />")
		if(ans){
			document.form.action='saveOrUpdateStudentTermMark.htm'; 
			document.form.submit();
		}
	}
	
	$(document).ready(function() {
		
		$("#tblFreezed tr").each(function(index) {
			$("#tblScrool tr").eq(index).height($("#tblFreezed tr").eq(index).innerHeight());
		});
		
		// Check whether the browser is IE or other, to set the table header hight 
		if ($.browser.msie) {
        	$("#tblFreezed th").eq(0).height($("#tblScrool th").eq(0).innerHeight());
		}if ($.browser.mozilla) {
        	$("#tblFreezed th").eq(0).height($("#tblScrool th").eq(0).innerHeight()+2);
		} 
		
		else {
			$("#tblFreezed th").eq(0).height($("#tblScrool th").eq(0).innerHeight());
		}

		// Set the width for student name colummn
		// (Really this is a table rendered separately)
		$("#tblFreezed").width(200);
		
		$("#tblMonthGradeFreezed tr").each(function(index) {
			if ($("#tblMonthGrade tr").eq(index + 1)) {
				$("#tblMonthGrade tr").eq(index + 1).height($("#tblMonthGradeFreezed tr").eq(index).innerHeight());
			}
		});

		$("#tblMonthGradeFreezed tr:first").height(
			$("#tblMonthGrade tr:eq(0)").innerHeight() + $("#tblMonthGrade tr:eq(1)").innerHeight() + 1
		);
		
	});

</script>
</head>
<body>

	<!-- Import the page header from custom tag library -->
	<h:headerNew parentTabId="10" page="studentMarks.htm" />

	<!-- Generate dynamic page content -->
	<div id="page_container">
		<div id="breadcrumb">
			<ul>
				<li><a href="adminWelcome.htm"><spring:message
							code='application.home' /> </a>&nbsp;&gt;&nbsp;</li>
				<li><spring:message code='STUDENT.STUDENTMARK.ADD' /></li>
			</ul>
		</div>
		<div class="help_link">
			<a href="#"
				onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/student/studentMarksHelp.html"/>','helpWindow',780,550)"><img
				src="resources/images/ico_help.png" width="20" height="20"
				align="absmiddle"> <spring:message code='application.help' />
			</a>
		</div>
		<div class="clearfix"></div>
		<h1>
			<spring:message code='STUDENT.STUDENTMARK.ADD' />
		</h1>
		<div>
			<c:if test="${message != null}">
				<label class="missing_required_error">${message}</label>
			</c:if>
		</div>
		<div id="content_main">
			<form action="" method="post" name="form">
				<!-- Initialize JSTL variables -->
				<c:set var="gradeclassdescription" value="" />
				<c:set var="termdescription" value="" />
				<div class="clearfix"></div>
				<div class="section_full_search">
					<div class="box_border">
						<p>
							<spring:message code='STUDENT.STUDENTMARK.SEARCHCLASS' />
						</p>
						<div class="row">
							<!-- Render class list -->
							<div class="float_left">
								<div class="lbl_lock">
									<span class="required_sign">*</span> <label><spring:message
											code='STUDENT.STUDENTMARK.SELECTCLASS' />:</label>
								</div>
								<select name="selectedClass">
									<option value="0">
										<spring:message code='application.drop.down' />
									</option>
									<c:forEach var="gradeclass" items="${gradeClassList}">
										<c:set var="testval" value="" />
										<c:if test="${gradeclass.classGradeId eq classGradeId}">
											<c:set var="testval" value="SELECTED" />
											<c:set var="gradeclassdescription"
												value="${gradeclass.description}" />
										</c:if>
										<option label="${gradeclass.description}"
											value="${gradeclass.classGradeId}"
											<c:out value="${testval}"></c:out>>
											${gradeclass.description}</option>
									</c:forEach>
								</select>
							</div>

							<!-- Render term list -->
							<div class="float_left">
								<div class="lbl_lock">
									<span class="required_sign">*</span> <label><spring:message
											code='STUDENT.STUDENTMARK.TERM' />:</label>
								</div>
								<select name="selectedTerm">
									<option value="0">
										<spring:message code='application.drop.down' />
									</option>
									<c:forEach items="${studentTermList}" var="term">
										<c:set var="testval" value="" />
										<c:if test="${term.termId eq termId}">
											<c:set var="testval" value="SELECTED" />
											<c:set var="termdescription" value="${term.description}" />
										</c:if>
										<option label="${term.description}" value="${term.termId}"
											<c:out value="${testval}"></c:out>>
											${term.description}</option>
									</c:forEach>
								</select>
							</div>

							<!-- Render grade list -->
							<div class="float_left" style="margin-right: inherit">
								<div class="lbl_lock">
									<span class="required_sign">*</span> <label><spring:message
											code='STUDENT.STUDENTMARK.SELECTGRADE' />:</label>
								</div>
								<select name="selectGrid">

									<c:choose>
										<c:when test="${gradingType eq 'MG' }">
											<option value="0" selected>
												<spring:message code='application.drop.down' />
											</option>
											<option value="TG">
												<spring:message code='STUDENT.STUDENTMARK.TERMGRADE' />
											</option>
											<option value="MG" selected>
												<spring:message code='STUDENT.STUDENTMARK.MONTHLYGRADE' />
											</option>
										</c:when>
										<c:when test="${gradingType eq 'TG' }">
											<option value="0" selected>
												<spring:message code='application.drop.down' />
											</option>
											<option value="TG" selected>
												<spring:message code='STUDENT.STUDENTMARK.TERMGRADE' />
											</option>
											<option value="MG">
												<spring:message code='STUDENT.STUDENTMARK.MONTHLYGRADE' />
											</option>
										</c:when>
										<c:otherwise>
											<option value="0" selected>
												<spring:message code='application.drop.down'/>
											</option>
											<option value="TG">
												<spring:message code='STUDENT.STUDENTMARK.TERMGRADE' />
											</option>
											<option value="MG">
												<spring:message code='STUDENT.STUDENTMARK.MONTHLYGRADE' />
											</option>
										</c:otherwise>
									</c:choose>
								</select>
							</div>
							<!-- set privillage for search student term mark  -->
						<sec:authorize access="hasRole('Search Student Marks')">
							<div class="float_right">

								<div class="buttion_bar_type1">

									<input type="button"
										value="<spring:message code='STUDENT.STUDENTMARK.SEARCH.CLASS' />"
										onClick="document.form.action='searchStudentTermMarkList.htm'; document.form.submit();"
										class="button">
								</div>
							</div>
						</sec:authorize>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>

				<!-- represents the error messages. -->
				<c:if test="${(not empty studentList) and (empty gradeSubjectList)}">
					<div class="box_border">

						<p>
							<font color="red"><spring:message
									code='STUDENT.STUDENTMARK.ERRORMSG1' /> </font>
						</p>
					</div>
				</c:if>
				<c:if test="${(empty studentList) and (not empty gradeSubjectList)}">
					<div class="box_border">

						<p>
							<font color="red"><spring:message
									code='STUDENT.STUDENTMARK.ERRORMSG2' /> </font>
						</p>
					</div>
				</c:if>

				<c:if
					test="${(not empty studentList) and (not empty gradeSubjectList)}">
					<div class="section_box" id="search_results">
						<div class="section_box_header">
							<h2>
								${gradeclassdescription} (${termdescription })
								<spring:message code='STUDENT.STUDENTMARK.MARKS' />
							</h2>
						</div>
						<div class="column_single">
					<p> <font size="1"> 
					<spring:message code="STUDENT.STUDENTMARK.MAXIMUM.MARK"></spring:message>
					</font></p>
						<!-- Term Marks View -->
							<c:if
								test="${(not empty studentList) and (not empty gradeSubjectList) and (not empty studenttermmarkmap)}">
								<div id="termMarksGrid">
									<table id="tblFreezed" class="basic_grid basic_grid_freezed"
										style="width: 200px; *margin-right: -4px;" border="0"
										cellspacing="1">
										<tr>
											<th> <!-- style="height: 34px !important; *height: 32px;" --><spring:message
													code='STUDENT.STUDENTMARK.STUDENTNAME' /></th>
										</tr>
										<c:forEach var="studentclass" items="${studentList}"
											varStatus="status">
											
											<!-- Set CSS class and title for the table row as needed -->
											<tr
												<c:choose>
            		<c:when test="${studentclass.student.statusId == 3}">class="suspend" title="Suspended student"</c:when>
            		<c:when test="${studentclass.student.statusId == 2}"> title="Past student"</c:when>	
            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
            		</c:choose>>
												<td class="left"><p class="wWrap" <c:if test="${studentclass.student.statusId == 3}">style="color: white;"</c:if>>${studentclass.student.nameWtInitials}</p></td>
											</tr>
										</c:forEach>
									</table>
									<div style="width: 706px; *width: 706px; overflow-x: scroll">
										<table id="tblScrool" class="basic_grid marksGrid" border="0"
											cellspacing="1">
											<tr>
												<c:forEach var="gradesubject" items="${gradeSubjectList}">
													<th width="80" style="height: 34px; *height: 32px">${gradesubject.subject.description}
													<font size="2">
													<c:if test="${gradesubject.isOptionalSubject}"><br>
							(<spring:message code="REF.UI.MANAGE.GRADE.SUBJECT.OPTIONAL"></spring:message>)
													</c:if></font>
													<br><font size="2">(${gradesubject.maximumMark})</font></th>
												</c:forEach>
											</tr>
											<c:forEach var="studentclass" items="${studentList}"
												varStatus="status">
												<tr
													<c:choose>
            		<c:when test="${studentclass.student.statusId == 3}">class="suspend" title="Suspended student"</c:when>
            		<c:when test="${studentclass.student.statusId == 2}"> title="Past student"</c:when>
            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
            		</c:choose>>

													<c:forEach var="gradesubject" items="${gradeSubjectList}"
														varStatus="status">

														<c:set var="trueFalse" value="enabled" scope="page" />
														<c:set var="myString"
															value="${gradesubject.gradeSubjectId},${studentclass.studentClassInfoId}" />
														<c:choose>
															<c:when test="${! empty studenttermmarkmap[myString]}">

																<c:set var="isdisable" value="" />
																<c:set var="color" value="#FFFFFF" />
																<c:choose>
																	<c:when test="${studenttermmarkmap[myString].absent}">
																		<c:set var="termMark" value="ab" />
																	</c:when>
																	<c:otherwise>
																		<c:set var="termMark"
																			value="${studenttermmarkmap[myString].marks}" />
																	</c:otherwise>
																	</c:choose>

																<td <c:if test="${studentclass.student.statusId == 3}">style="background-color: #8B8B83;"</c:if>>
																<input type="hidden" name="termmarkid"
																	value="${studenttermmarkmap[myString].studentTermMarkId}"
																	 <c:if test="${studentclass.student.statusId == 2}">readonly</c:if>
																	
																	>
																	<!-- This is to update the term marks -->
																	<c:if test="${studenttermmarkmap[myString].editMarkState}">
                                                                    	<c:choose>

																			<%-- user object must in Session to get role to check admin/teacher or Other user --%>
																			<c:when test="${user.role eq 'ROLE_ADMIN'}">
																				<input name="gradesubjectmark" type="text"
																					tabindex="${status.count}"
																					style="background-color:<c:out value="${color}"/>"
																					value="${termMark}"<c:out value="${isdisable}"/>
																					 <c:if test="${studentclass.student.statusId == 2}">readonly</c:if>
																					>
																			</c:when>

																			<c:when test="${user.role eq 'ROLE_TEACHER'}">
																				<input name="gradesubjectmark" type="text"
																					tabindex="${status.count}"
																					style="background-color:<c:out value="${color}"/>"
																					value="${termMark}"<c:out value="${isdisable}"/>
																					readonly="readonly">
																			</c:when>

																		<c:otherwise>

																			<c:set var="editable" scope="page" value=""/>
																			<!-- set autherize when there are only Add student mark & View stuent mark privillages -->
																			<sec:authorize access="hasAnyRole('Add Student mark','View Student mark')">
																				<c:set var="editable" scope="page" value="readonly='readonly'"/>
																			</sec:authorize>

																			<!-- set autherize when there are all privillages  -->
																			<sec:authorize ifAllGranted="Add Student mark,Edit Student mark,View Student mark">
																				<c:set var="editable" scope="page" value=""/>
																			</sec:authorize>

																			<sec:authorize access="hasAnyRole('Add Student mark','Edit Student mark','View Student mark')">
																				<input name="gradesubjectmark" type="text"
																					tabindex="${status.count}"
																					style="background-color:<c:out value="${color}"/>"
																					value="${termMark}"
																					<c:out value="${isdisable}"/> ${editable}>
																			</sec:authorize>


																		</c:otherwise>
																	</c:choose>
																	</c:if>

																	<!-- This is to enter the term marks first time-->
																	<c:if test="${!studenttermmarkmap[myString].editMarkState}">
																		<c:set var="addMarks" scope="page" value=""/>

																		 <!-- set autherize when there is only View student mark  privillages -->
																		<sec:authorize access="hasAnyRole('View Student mark')">
																				<c:set var="addMarks" scope="page" value="readonly='readonly'"/>
																			</sec:authorize>

																			<!-- set autherize when there are both  Add student mark & View stuent mark privillages -->
																			<sec:authorize ifAllGranted="Add Student mark,View Student mark">
																				<c:set var="addMarks" scope="page" value=""/>
																			</sec:authorize>

																		<sec:authorize
																			access="hasAnyRole('Add Student mark','View Student mark')">
																			<input 
																			<c:if test="${studentclass.student.statusId == 2}">readonly="readonly"</c:if>
																			name="gradesubjectmark" type="text"
																				tabindex="${status.count}"
																				style="background-color:<c:out value="${color}"></c:out>"
																				value="${termMark}"<c:out value="${isdisable}"></c:out> ${addMarks}>
																		</sec:authorize>
																	</c:if>
																</td>
															</c:when>
															<c:otherwise>
																<c:set var="color" value="#CCCCCC" />
																<c:set var="isdisable" value="disabled" />
																<c:set var="termMark" value="" />
																<td <c:if test="${studentclass.student.statusId == 3}">style="background-color: #8B8B83;"</c:if>>
																<input name="gradesubjectmark" type="text"
																	tabindex="${status.count}"
																	style="background-color:<c:out value="${color}"></c:out>"
																	value="${termMark}"
																	<c:out value="${isdisable}"></c:out>>
																</td>
															</c:otherwise>
														</c:choose>
													</c:forEach>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</c:if>

					<!-- Sub Term Marks View -->
							<c:if
								test="${(not empty studentList) and (not empty gradeSubjectList) and (not empty subtermList)}">
								<div id="monthGradeGrid">
									<table id="tblMonthGradeFreezed"
										class="basic_grid basic_grid_freezed"
										style="width: 150px; *margin-right: -4px;" border="0"
										cellspacing="1">
										<tr>
											<th>Student Name</th>
										</tr>
										<c:forEach var="studentclass" items="${studentList}"
											varStatus="status">
											<tr
												<c:choose>
					<c:when test="${studentclass.student.statusId == 3}">class="suspend" title="Suspended student"</c:when>
            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
            		</c:choose>>
												<td class="left" ><p class="wWrap" <c:if test="${studentclass.student.statusId == 3}">style="color: white;"</c:if>
												 <c:if test="${studentclass.student.statusId == 2}">readonly</c:if>
												>
												${studentclass.student.nameWtInitials}</p>
												</td>
											</tr>
										</c:forEach>
									</table>
									<div style="width: 706px; *width: 706px; overflow-x: scroll">
										<table id="tblMonthGrade" class="basic_grid marksGrid"
											border="0" cellspacing="1">
											<tr>
												<c:forEach var="gradesubject" items="${gradeSubjectList}">
													<th colspan="${subtermListSize}" class="center">${gradesubject.subject.description}<br>
													(${gradesubject.maximumMark})</th>
												</c:forEach>
											</tr>

											<tr>
												<c:forEach var="gradesubject" items="${gradeSubjectList}">
													<c:forEach var="subterm" items="${subtermList}">
														<th class="center">${subterm.description}</th>
													</c:forEach>
												</c:forEach>
											</tr>

											<c:forEach var="studentclass" items="${studentList}"
												varStatus="status">
												<tr
													<c:choose>
           		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
           		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
           		</c:choose>>

													<%
													    int count = 1;
													%>
													<c:forEach var="gradesubject" items="${gradeSubjectList}">
														<c:forEach var="subterm" items="${subtermList}">
															<c:set var="myString"
																value="${gradesubject.gradeSubjectId},${studentclass.studentClassInfoId},${subterm.subTermId}" />
															<c:choose>
																<c:when
																	test="${! empty studentsubtermmarkmap[myString]}">
																	<c:set var="isdisable" value="" />
																	<c:set var="color" value="#FFFFFF" />
																	<c:choose>
																		<c:when
																			test="${studentsubtermmarkmap[myString].absent}">
																			<c:set var="grading" value="ab" />
																		</c:when>
																		<c:otherwise>
																			<c:if test="${isMarks eq 'false'}">
																				<c:set var="gradingID"
																					value="${studentsubtermmarkmap[myString].gradingID}" />
																				<c:set var="grading"
																					value="${gradingMap[gradingID]}" />
																			</c:if>
																			<c:if test="${isMarks eq 'true'}">
																				<c:set var="grading"
																					value="${studentsubtermmarkmap[myString].marks}" />
																			</c:if>
																		</c:otherwise>

																	</c:choose>



																		<!-- This to update Sub Term Marks -->
																	<td <c:if test="${studentclass.student.statusId == 3}">style="background-color: #8B8B83;"</c:if>
																	class="center">
																	<input type="hidden" name="subTermMarkId"
																		value="${studentsubtermmarkmap[myString].stuSubTermMarkId}"
																		 <c:if test="${studentclass.student.statusId == 2}">readonly</c:if>
																		><c:if
																			test="${studentsubtermmarkmap[myString].editMarkState ==true}">
																			<c:choose>
																					<%-- user object must in Session to get role to check admin/teacher or Other user --%>
																						<c:when test="${user.role eq 'ROLE_ADMIN'}">
																				<input name="monthlymarks" tabindex="<%=count%>"
																					type="text" value="${grading}"
																					style="background-color:<c:out value="${color}"></c:out>"
																					 <c:if test="${studentclass.student.statusId == 2}">readonly</c:if>
																					<c:out value="${isdisable}"></c:out>>
																						</c:when>

																			<c:when test="${user.role eq 'ROLE_TEACHER'}">
																				<input name="monthlymarks" tabindex="<%=count%>"
																					type="text" value="${grading}"
																					style="background-color:<c:out value="${color}"></c:out>"
																					<c:out value="${isdisable}"></c:out>
																					readonly="readonly">
																			</c:when>
																			<c:otherwise>
																			<!-- Set autherize when there are add and view privillages only -->
																			<sec:authorize access="hasAnyRole('Add Student mark','View Student mark')">
																				<c:set var="editable" scope="page" value="readonly='readonly'"/>
																			</sec:authorize>

																			<!-- Set autherize when there are all privillages  -->
																			<sec:authorize ifAllGranted="Add Student mark,Edit Student mark,View Student mark">
																				<c:set var="editable" scope="page" value=""/>
																			</sec:authorize>

																			<sec:authorize access="hasAnyRole('Add Student mark','Edit Student mark','View Student mark')">
																			<input name="monthlymarks" tabindex="<%=count%>"
																					type="text" value="${grading}"
																					style="background-color:<c:out value="${color}"></c:out>"
																					<c:out value="${isdisable}"></c:out> ${editable} >
																				</sec:authorize>


																		</c:otherwise>

																			</c:choose>

																		</c:if>

																		<!-- This to add sub term Mark first time -->
																		<c:if
																			test="${studentsubtermmarkmap[myString].editMarkState ==false}">
																			<c:set var="addMarks" scope="page" value=""/>
																			<!-- set atherize when there is view privillages only -->
																		<sec:authorize access="hasAnyRole('View Student mark')">
																				<c:set var="addMarks" scope="page" value="readonly='readonly'"/>
																			</sec:authorize>

																			<!--  set autherize when there are both add and View privillage -->
																			<sec:authorize ifAllGranted="Add Student mark,View Student mark">
																				<c:set var="addMarks" scope="page" value=""/>
																				
																				
																				
																			</sec:authorize>

																		<sec:authorize
																			access="hasAnyRole('Add Student mark','View Student mark')">
																				<input name="monthlymarks" tabindex="<%=count%>"
																					type="text" value="${grading}"
																					style="background-color:<c:out value="${color}"></c:out>"
																					
																					<c:out value="${isdisable}"></c:out> ${addMarks} <c:if test="${studentclass.student.statusId == 2}">readonly</c:if>>
																			</sec:authorize>
																		</c:if></td>

																</c:when>
																<c:otherwise>
																	<c:set var="isdisable" value="disabled" />
																	<c:set var="color" value="#CCCCCC" />
																	<c:set var="grading" value="" />

																	<td <c:if test="${studentclass.student.statusId == 3}">style="background-color: #8B8B83;"</c:if>
																	class="center">
																	<input name="monthlymarks"
																tabindex="<%=count%>" type="text"
																style="background-color:<c:out value="${color}"></c:out>"
																value="${grading}" <c:out value="${isdisable}"></c:out>
																 <c:if test="${studentclass.student.statusId == 2}">readonly</c:if>>

																</td>

																</c:otherwise>
															</c:choose>


															<%count++;%>
														</c:forEach>
													</c:forEach>
												</tr>

											</c:forEach>
										</table>
									</div>
								</div>
							</c:if>
						</div>
						<div class="clearfix"></div>
					</div>

					<c:if test="${gradingType eq 'TG' and showMarkingComplete}">
						<input type="checkbox" name="markingCompleted"
							value="markingCompleted"
							<c:if test="${markingCompleted}">checked disabled</c:if> />
							<c:if test="${markingCompleted}"><input type="hidden" name="markingCompleted"
							value="markingCompleted" /></c:if>
				   <spring:message code="STUDENT.STUDENTMARK.MARKED"></spring:message></c:if><br>
					
				   <!-- Set privillages for save,delete cancel term mark for teacher -->
					<c:choose>
					<c:when test="${user.role eq 'ROLE_TEACHER'}">
						<div id="btn_row" class="button_row">
						<div class="buttion_bar_type2">
							<!-- Hidden fields -->
							<c:if
								test="${(classGradeId ne 'null') and (termId ne 'null') and (gradingType ne 'null')}">
								<input type="hidden" name="selectedClass"
									value="${classGradeId}">
								<input type="hidden" name="selectedTerm" value="${termId}">
								<input type="hidden" name="selectGridId" value="${gradingType}">
							</c:if>
							<!-- Render buttons -->
							<input type="button"
								value="<spring:message code='REF.UI.RESET'/>"
								onClick="document.form.action='searchStudentTermMarkList.htm'; document.form.submit();document.getElementById('search_results').style.display=''; document.getElementById('btn_row').style.display=''"
								class="button"> <input type="button"
								value="<spring:message code='REF.UI.SAVE'/>"
								onClick="saveMark();"
								class="button"> <input type="button"
								value="<spring:message code='REF.UI.CANCEL'/>" class="button"
								onclick="document.location.href='studentMarks.htm'">
						</div>
						<div class="clearfix"></div>
					</div>
					</c:when>

					<c:otherwise>
						<sec:authorize ifAllGranted="Add Student mark,Edit Student mark">

					<div id="btn_row" class="button_row">
						<div class="buttion_bar_type2">
							<!-- Hidden fields -->
							<c:if
								test="${(classGradeId ne 'null') and (termId ne 'null') and (gradingType ne 'null')}">
								<input type="hidden" name="selectedClass"
									value="${classGradeId}">
								<input type="hidden" name="selectedTerm" value="${termId}">
								<input type="hidden" name="selectGridId" value="${gradingType}">
							</c:if>
							<!-- Render buttons -->
							<input type="button"
								value="<spring:message code='REF.UI.RESET'/>"
								onClick="document.form.action='searchStudentTermMarkList.htm'; document.form.submit();document.getElementById('search_results').style.display=''; document.getElementById('btn_row').style.display=''"
								class="button"> <input type="button"
								value="<spring:message code='REF.UI.SAVE'/>"
								onClick="saveMark();"
								class="button"> <input type="button"
								value="<spring:message code='REF.UI.CANCEL'/>" class="button"
								onclick="document.location.href='studentMarks.htm'">
						</div>
						<div class="clearfix"></div>
					</div>
					</sec:authorize>
					</c:otherwise>
					</c:choose>


				</c:if>

			</form>
		</div>
	</div>
	
	<!-- Import footer from custom tag library -->
	<h:footer />
	
</body>
</html>