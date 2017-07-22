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
<%@ taglib uri="/WEB-INF/tld/custom-functions.tld" prefix="func"%>
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
	function search(){
		document.form.action = 'searchExamMarks.htm';
		document.form.submit();
		}

		function saveExamMarks(){
			var ans = window.confirm('<spring:message code="STU.EXAM.MARKS.SAVE.CONFIRMATION" />')
			if(ans){
				document.form.action = 'saveExamMarks.htm';
				document.form.submit();
// 		var result = validate();
// 				 if(result){
// 						alert('<spring:message code="REF.UI.MANAGE.EXAM.ADMISSION.DUPLICATE"/>');
// 						}
// 		if(!(result)){
// 			var ans = window.confirm('<spring:message code="STU.EXAM.MARKS.SAVE.CONFIRMATION" />')
// 			if(ans){
// 				document.form.action = 'saveExamMarks.htm';
// 				document.form.submit();
// 			}
// 			}
		}
		}

		function cancel(){
				document.getElementById('btn_row').style.display='none';
				document.getElementById('search_results').style.display='none';
			}

		function validate(){
			var regEx = "[a-zA-Z0-9]";
			var patt1 = new RegExp("#");
			var isValid = false;
			   $("input:text[id^=admissionText]").each(function() {
				  if ($(this).val().length > 0){
					  if (patt1.test($(this).val())) {isValid = true;}
					  else{
			      	if ($("input:text[id^=admissionText][value=" + $(this).val() + "]").length > 1) {
			        	isValid = true;
			      	}}
			      	}
			      }

		      );
		   return isValid;

		}

		// finds the class grades for a selected exam.
		function findClasses(selectedValue, classGradeError) {
			var url = '<c:url value="/findGradeClasses.htm" />';	
			$.ajax({
	                url:url,
		        data:({
		              'selectedValue': selectedValue
		        }),
		        success: function(response) {
		        	if(response != ''){
		        	var responseArray = response.split(",");

		        	document.getElementById('classes').innerHTML = '';

		        	var dropDownDiv = document.getElementById('classes');
		        	var selector = document.createElement('select');
					selector.id = "classGrade";
					selector.name = "grade";
					dropDownDiv.appendChild(selector);

					var option = document.createElement('option');
					option.value = '0';
					option.appendChild(document.createTextNode('<spring:message code="OPT.PLEASE.SELECT"/>'));
					selector.appendChild(option);

					if (responseArray.length > 0) {

						for ( var i = 0; i < responseArray.length; i++) {

							if (responseArray[i] != "") {
								option = document.createElement('option');
								var responseValue = responseArray[i].split("-");
								option.value = responseValue[1];
								option.appendChild(document.createTextNode(responseValue[0]));
								
								selector.appendChild(option);
								
								// if the class grade is selected then select it as the default.
								if(classGradeError != null){
									
									// converts the responseValue[1], which is a string int a int.
									// as it is needed to check the comparability.
									var val = parseInt(responseValue[1] , 10);
									
									if(val == classGradeError){
									option.selected = 'selected';
									}
								}
							}
						}
						
					}
		        } else {
		        	document.getElementById('classes').innerHTML = '';
		        }},
		        error: function(transport) {
		        	alert("error");

		        }
	        });
	     }
</script>
</head>
<body onload="<c:if test = "${examKeyError != null}">findClasses('${examKeyError}',
'<c:out value = "${classGradeError} "/>')</c:if>">
<h:headerNew parentTabId="10" page="studentExamMarks.htm" />

<div id="page_container">
<div id="breadcrumb">
<ul>
	<li><a href="adminWelcome.htm"><spring:message
		code='application.home' /></a>&nbsp;&gt;&nbsp;</li>
	<li><spring:message code='REF.UI.MANAGE.EXAM.ADD' /></li>
</ul>
</div>
<div class="help_link"><a href="#"
	onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/student/studentExamMarksHelp.html"/>','helpWindow',780,550)"><img
	src="resources/images/ico_help.png" width="20" height="20"
	align="absmiddle"><spring:message code='application.help' /></a></div>
<div class="clearfix"></div>
<h1><spring:message code='REF.UI.MANAGE.EXAM.ADD' /></h1>
<div id="content_main"><form:form action="#" method="post"
	name="form" commandName="examMark">

	<div class="clearfix"></div>
	<c:if test="${message != null}">
		<div><label class="missing_required_error">${message}</label></div>
	</c:if>

	<c:if test="${successMessage != null}">
		<div><label class="success_sign">${successMessage}</label></div>
	</c:if>
	<div class="section_full_search">
	<div class="box_border">
	<p><spring:message code='REF.UI.MANAGE.EXAM.SEARCH.ADD' /></p>
	<div class="row">
	<div class="float_left">
	<div class="lbl_lock"><span class="required_sign">*</span> <label><spring:message
		code='REF.UI.EXAM.MARK.EXAM' />:</label></div>
	<select name="examId" id="examId" onchange="findClasses(this.value, null)">
		<option value="0" id="optionExam"><spring:message
			code="application.drop.down" /></option>
		<c:if test="${examList != null}">
			<c:forEach var="exam" items="${examList}">
				<option value="${exam.examId}"
				<c:if test="${examKeyError != null && exam.examId eq examKeyError}"> 
				selected="selected"
				</c:if>
				>${exam.description}</option>
			</c:forEach>
		</c:if>
	</select>
	</div>
	<div class="float_left">
	<div class="lbl_lock"><span class="required_sign">*</span><label><spring:message
		code='REF.UI.EXAM.MARK.GRADE' />:</label></div>
	<div id="classes"></div>
	</div>

	<div class="float_left" style="margin-right: inherit">
	<div class="lbl_lock"><span class="required_sign">*</span><label><spring:message
		code='REF.UI.EXAM.MARK.YEAR' />:</label></div>
	<select name="year" id="year">
		<option value="0" id="optionYear"><spring:message
			code='application.drop.down' /></option>
		<c:if test="${yearList != null}">
			<c:forEach var="year" items="${yearList}">
			<!-- if the year is equals to the selected year then seleted the option of the drop down. -->
				<option value="${year}" <c:if test = "${errorYear != null && year eq errorYear}">
				selected = "selected"</c:if>>${year}</option>
			</c:forEach>
		</c:if>
	</select></div>
	<div class="float_right">
	<div class="buttion_bar_type1"><sec:authorize access="hasRole('Search Student Exam Marks')"><input type="button"
		value="<spring:message code='REF.UI.EXAM.MARK.SEARCH' />"
		onClick="search();  " class="button"></sec:authorize></div>
	</div>
	</div>
	<div class="clearfix"></div>
	</div>
	</div>
	<script language="javascript">
	  $(document).ready(function() {
	    $("#tblFreezed tr").each(function(index){
	     $("#tblScrool tr").eq(index).height($("#tblFreezed tr").eq(index).innerHeight());
	    });

		if ($.browser.msie) {
            $("#tblFreezed th").eq(0).height($("#tblScrool th").eq(0).innerHeight());
        }
        else {
            $("#tblFreezed th").eq(0).height($("#tblScrool th").eq(0).innerHeight() + 2);
        }


	  });
	  </script>
	<div class="section_box" id="search_results" style="display: none">
	<div class="section_box_header">
	<h2><label><spring:message
		code='REF.UI.EXAM.MARK.EXAM.VIEW' /></label> ${examName} ${gradeName}
	${modelYear}</h2>
	</div>
	<div class="column_single">
	<div id="termMarksGrid">
	<div><spring:message code='REF.UI.EXAM.MARK.FYI' /></div>
	<table id="tblFreezed" class="basic_grid basic_grid_freezed"
		style="width: 320px; * margin-right: -0px;" border="0" cellspacing="1">
		<tr>
			<th style="height: 34px; * height: 32px; width: 195px"><label><spring:message
				code='REF.UI.EXAM.MARK.NAME' /></label></th>
			<th style="width:125px" align="center"><label><spring:message
				code='REF.UI.EXAM.MARK.ADMISSION' /></label></th>
		</tr>
		<c:if test="${fn:length(studentSubjectList) > 0}">
			<script language="javascript">
            	document.getElementById('search_results').style.display='';
			</script>
		</c:if>
		<c:choose>
			<c:when test="${studentList != null }">
				<c:forEach var="student" items="${studentList}" varStatus="status">
					<tr
            <c:if test="${studentStatusIdsList != null && studentStatusIdsList[status.count -1] == 3 }">class="suspend" title="Suspended student"
						<c:choose>
            	<c:when test="${(status.count) % 2 == 0}">
            		class="odd"
            	</c:when>
            	<c:otherwise>
            		class="even"
            	</c:otherwise>
            </c:choose>
            </c:if>>
						<td><p class="wWrap"><c:out value="${student.value}"></c:out> </p></td>
						<c:forEach var="admission" items="${admissionList}"
							varStatus="admissionStatus">
							<c:if test="${admission.key eq student.key}">
								<td><%-- if the admission number is null teacher or the clerical staff
								can be saved data. If it is greater than zero and not null then only the
								admin has the privilages to edit data. Teacher or clerical staff can be viewd. --%>
								<c:if
									test="${admission.value != null && fn:length(admission.value) > 0}">
									<sec:authorize
										access="hasRole('Save Exam Marks') and !hasRole('Edit Exam Marks')">
										<input type="text" readonly
											name="${student.value}${student.key}Admission"
											value="${admission.value}" maxlength="15"
											id="admissionText${admissionStatus.count}"
											style="width: 95px" />
									</sec:authorize>
								</c:if>
								<%-- if the admission number is null that means a newly added student, or admission number
								is zero means student is already exists but the admission number is empty. Then allow the
								role to save data when that role has the privileges Save Exam Marks and not the Edit Exam Marks. --%>
								<c:if
									test="${admission.value == null || fn:length(admission.value) == 0}">
									<sec:authorize
										access="hasRole('Save Exam Marks') and !hasRole('Edit Exam Marks')">
										<input type="text"
											name="${student.value}${student.key}Admission"
											value="${admission.value}" maxlength="15"
											id="admissionText${admissionStatus.count}"
											style="width: 95px" />
									</sec:authorize>
								</c:if> <sec:authorize
									access="hasRole('Save Exam Marks') and hasRole('Edit Exam Marks')">
									<input type="text"
										name="${student.value}${student.key}Admission"
										value="${admission.value}" maxlength="15"
										id="admissionText${admissionStatus.count}"
										style="width: 95px" />
								</sec:authorize>
								<%-- Iterates through a list of keys of admission numbers. If the admission number key
						equals with the key of the studentList then creates a hidden field to store the admission id
						for the specific student name.--%>
						<c:forEach var="admissionId" items="${admissionIdList}"
							varStatus="admissionStatus">
							<c:if test="${admissionId.key eq student.key}">
								<input type="hidden"
									name="${student.value}${student.key}examAdmissionId"
									value="${admissionId.value}"
									id="admissionText${admissionStatus.count}" />
							</c:if>

						</c:forEach></td>
							</c:if>
						</c:forEach>


					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<div style="width:581px; *width:580px; overflow-x:scroll">
	<table id="tblScrool" class="basic_grid marksGrid" border="0"
		cellspacing="1">
		<tr>
			<c:choose>
				<c:when test="${studentSubjectList != null }">
					<c:if test="${fn:length(studentSubjectList) > 0}">
						<script language="javascript">
            	document.getElementById('search_results').style.display='';
			</script>
					</c:if>
					<%
					    int temp = 0;
						int count = (Integer) request.getAttribute("examSubjectCount");// the count of the subjects
						// for the particular exam.
					%>
					<c:forEach var="subject" items="${subjectList}">
						<c:forEach var="studentSubject" items="${studentSubjectList}"
							varStatus="status">
							<%-- studentSubject[2] - name of the subject for the particular exam.
							studentSubjectList is a list contains the subject and the marks for the relevant student.
							while iterates through this list when the subject name equals with the name of the subject
							that iterates with the subjectList check wether it is already put into the table header. If
							it is not there then print the table header. (Because many students can follow the same subject.
							So duplicates can be there.In order to avoid this make this comparison. This can be done because
							studentSubject list is grouped with the subject.)--%>
							<c:if test="${subject eq studentSubject[2]}">
								<c:if test="${!fn:contains(tempStr, studentSubject[2])}">

									<%
									    if (count > temp) {
									%><c:choose>
									<%-- If optionalSubjectList contains the subject, print as the optional. --%>
										<c:when test="${func:contains(optionalSubjectList, studentSubject[6])}">
											<th style="height: 34px; * height: 32px; width: 80px"
												align="justify"><c:out value="${subject}"/>
												<spring:message
												code='REF.UI.EXAM.MARK.OPTIONAL' />
												<%-- Keeps the hidden data for the
									 studentSubject[3] - the key of the grade.
									 studentSubject[4] - the key of the exam.
									 studentSubject[5] - the selected year.--%></th>
										</c:when>
										<c:otherwise>
											<th style="height: 34px; * height: 32px; width: 80px"
												align="justify"><c:out value="${subject}" />
											</th>
										</c:otherwise>
										
									</c:choose>
									<input type="hidden" name="gradeId"
										value="<c:out value="${studentSubject[3]}"/>" />
									<input type="hidden" name="exam"
										value="<c:out value="${studentSubject[4]}"/>" />
									<input type="hidden" name="yearVal"
										value="<c:out value="${studentSubject[5]}"/>" />
									<%
									    temp++;
									%>
									<%
									    }
									%>
								</c:if>
								<c:set var="tempStr" value="${studentSubject[2]}" />
							</c:if>
						</c:forEach>
					</c:forEach>
				</c:when>
			</c:choose>
		</tr>
		<c:choose>
			<c:when test="${studentList != null }">
				<c:forEach var="student" items="${studentList}"
					varStatus="studentStatus">

					<tr 
					<c:if test="${studentStatusIdsList != null && studentStatusIdsList[studentStatus.count -1] == 3 }">class="suspend" title="Suspended student"
						<c:choose>
            			<c:when test="${(studentStatus.count) % 2 == 0}">
            				class="odd"
            			</c:when>
            			<c:otherwise>
            				class="even"
            			</c:otherwise>
            			</c:choose>
            			</c:if>>
						<c:choose>
							<c:when test="${studentSubjectList != null }">
								<%
								    int temp = 0;
									int count = (Integer) request.getAttribute("examSubjectCount");
								%>
								<c:forEach var="studentSubject" items="${studentSubjectList}"
									varStatus="status">

									<c:if
										test="${!fn:contains(tempStr, studentSubject[2]) &&
								studentSubject[13] == 0}">
										<%
										    if (count > temp) {
										%>
										<%-- This ${studentSubject[7]} indicates the marks type.
										 ${studentSubject[13]} indicates as the exam marks key.
										 ${studentSubject[17]} indicates as the absent type. --%>
										<c:if test="${studentSubject[7] eq 1}">
											<c:choose>
												<c:when
													test="${studentSubject[17] eq 1 && studentSubject[13] != null}">

													<td><%-- If the exam marks status is less than zero teacher or the clerical staff
								can be save data. If it is greater than zero and not null then only the
								admin has the privilages to edit data. Teacher or clerical staff can be viewd. --%>
<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />

													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="3"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="<spring:message
												code='EXAM.MARKS.AB' />">%
												</sec:authorize> <%-- If exam marks absent field one then displays as 'AB'. --%>

														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="3"
															<c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="<spring:message
												code='EXAM.MARKS.AB' />">%${studentSubject[14] > 0}
												</sec:authorize>
													</td>
												</c:when>
												<c:when
													test="${studentSubject[16] eq 1 && studentSubject[13] != null}">
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />

													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="3"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="<spring:message
												code='EXAM.MARKS.NA' />">%
												</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="3"
															<c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="<spring:message
												code='EXAM.MARKS.NA' />">%
												</sec:authorize></td>
												</c:when>
												<c:otherwise>
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />

													<%-- If this privilege is there indicates that role has the privileges to
													enter and edit marks. --%> <sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="3"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="${studentSubject[11]}">%
													</sec:authorize> <%-- If  'Save Exam Marks' is there and not 'Edit Exam Marks' privilege
													and the status is larger than zero then that role has the readabillity. --%>

														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="3"
															<c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="${studentSubject[11]}">%
													</sec:authorize>
													 <%-- If  'Save Exam Marks' is there and not 'Edit Exam Marks' privilege
													and the status is equals zero then that role has the save permission. --%>
													</td>
												</c:otherwise>
											</c:choose>
										</c:if>
										<%-- If the exam marks type is zero process for the gradings. --%>
										<c:if test="${studentSubject[7] eq 0}">
											<c:choose>
												<c:when
													test="${studentSubject[17] eq 1 && studentSubject[13] != null}">
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />

													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="2"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="<spring:message
												code='EXAM.MARKS.AB' />">
													</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="2"
															<c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="<spring:message
												code='EXAM.MARKS.AB' />">${studentSubject[14] > 0}
														</sec:authorize></td>
												</c:when>
												<c:when
													test="${studentSubject[16] eq 1 && studentSubject[13] != null}">
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />

													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="2"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="<spring:message
												code='EXAM.MARKS.NA' />">
													</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="2"
															<c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="<spring:message
												code='EXAM.MARKS.NA' />">${studentSubject[14] > 0}
														</sec:authorize></td>
												</c:when>
												<c:otherwise>
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />
													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">
														<input type="text" id="textMark" maxlength="2"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="${studentSubject[15]}">
													</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="2"
															 <c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="${studentSubject[15]}">
														</sec:authorize></td>
												</c:otherwise>
											</c:choose>
										</c:if>
										<%
										    temp++;
										%>
										<%
										    }
										%>
									</c:if>
									<%-- if the exam marks type is not empty then check the student name. --%>
									<c:if
										test="${studentSubject[13] != 0 && student.key eq studentSubject[1]}">
										<%
										    if (count > temp) {
										%>
										<c:if test="${studentSubject[7] eq 1}">
											<c:choose>
												<c:when
													test="${studentSubject[17] eq 1 && studentSubject[13] != null}">
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />
													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="3"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="<spring:message
												code='EXAM.MARKS.AB' />">%
												</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="3"
															<c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="<spring:message
												code='EXAM.MARKS.AB' />">%${studentSubject[14] > 0}
												</sec:authorize></td>
												</c:when>
												<c:when
													test="${studentSubject[16] eq 1 && studentSubject[13] != null}">
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />
													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="3"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="<spring:message
												code='EXAM.MARKS.NA' />">%
												</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="3"
															<c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="<spring:message
												code='EXAM.MARKS.NA' />">%${studentSubject[14] > 0}
												</sec:authorize></td>
												</c:when>
												<c:otherwise>
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" /><input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />

													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="3"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="${studentSubject[11]}">%
												</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="3"
															 <c:if
														test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="${studentSubject[11]}">%
												</sec:authorize></td>
												</c:otherwise>
											</c:choose>
										</c:if>
										<%-- If the studentSubject[7] --%>
										<c:if test="${studentSubject[7] eq 0}">
											<c:choose>
												<c:when
													test="${studentSubject[17] eq 1 && studentSubject[13] != null}">
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />
													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="2"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="<spring:message
												code='EXAM.MARKS.AB' />">
													</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="2"
															<c:if
														test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="<spring:message
												code='EXAM.MARKS.AB' />">${studentSubject[14] > 0}
														</sec:authorize></td>
												</c:when>
												<c:when
													test="${studentSubject[16] eq 1 && studentSubject[13] != null}">
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />
													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="2"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="<spring:message
												code='EXAM.MARKS.NA' />">
													</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="2"
															 <c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="<spring:message
												code='EXAM.MARKS.NA' />">${studentSubject[14] > 0}
														</sec:authorize></td>
												</c:when>
												<c:otherwise>
													<td>
													<input type="hidden" name="${student.value}"
						value="<c:out value="${student.value}"/>" />
													<input type="hidden" name="${studentSubject[13]}"
										value="<c:out value="${studentSubject[13]}"/>" />
													<sec:authorize
														access="hasRole('Edit Exam Marks') and hasRole('Save Exam Marks')">

														<input type="text" id="textMark" maxlength="2"
															name="${studentSubject[2]}${student.key}${student.value}"
															value="${studentSubject[15]}">
													</sec:authorize>
														<sec:authorize
															access="hasRole('Save Exam Marks') and !(hasRole('Edit Exam Marks'))">
															<input type="text" id="textMark" maxlength="2"
															<c:if test="${studentSubject[14] > 0}">
																readonly="readonly"
																</c:if>
																name="${studentSubject[2]}${student.key}${student.value}"
																value="${studentSubject[15]}">
														</sec:authorize></td>
												</c:otherwise>
											</c:choose>
										</c:if>
										<%
										    temp++;
										%>
										<%
										    }
										%>
									</c:if>
									<%-- Keeps the temparory values with the jstl. --%>
									<c:set var="tempStr" value="${studentSubject[2]}" />
									<c:set var="studentStatusCount" value="${studentStatus.count}" />
									<c:set var="studentName" value="${student.value}" />

								</c:forEach>

							</c:when>
						</c:choose>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>

	</table>
	</div>
	</div>
	</div>
	<div class="clearfix"></div>
	</div>

	<sec:authorize access="hasRole('Save Exam Marks')">
		<div id="btn_row" class="button_row" style="display: none"><c:if
			test="${fn:length(studentSubjectList) > 0}">
			<script language="javascript">
            	document.getElementById('btn_row').style.display='';
			</script>
		</c:if>
		<div class="buttion_bar_type2"><input type="reset"
			value="<spring:message code='REF.UI.RESET' />" class="button">
		<input type="button" value="<spring:message code='REF.UI.SAVE' />"
			class="button" onclick="saveExamMarks()"> <input
			type="button" value="<spring:message code='REF.UI.CANCEL' />"
			class="button" onclick="cancel()"></div>
		<div class="clearfix"></div>
		</div>
	</sec:authorize>
</form:form></div>
</div>
<h:footer />
</body>
</html>
