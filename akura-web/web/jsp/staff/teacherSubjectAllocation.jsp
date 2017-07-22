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
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="PRAGMA" content="NO-CACHE">
<title><spring:message code="APPLICATION.NAME"/></title>

<!-- Calender controll CSS and JS -->

<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">

<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript"
	src="resources/js/jquery.ui.datepicker.min.js"></script>

<script>
	var classSize = '<c:out value="${classSize}"/>';
	$(function() {
		$("#dateOfBirth").datepicker({
			changeYear : true,
			changeMonth : true,
			yearRange : "c-100:c+2",
			showOn : "button",
			buttonImage : "../images/calendar.jpg",
			buttonImageOnly : true
		});
	});

	$(function() {
		$("#dateOfHire").datepicker({
			changeYear : true,
			changeMonth : true,
			yearRange : "c-100:c+2",
			showOn : "button",
			buttonImage : "../images/calendar.jpg",
			buttonImageOnly : true
		});
	});

	$(function() {
		$("#dateOfDeparture").datepicker({
			changeYear : true,
			changeMonth : true,
			yearRange : "c-100:c+2",
			showOn : "button",
			buttonImage : "../images/calendar.jpg",
			buttonImageOnly : true
		});
	});

	function updateSubjectTeacher(thisValue, selectedValue, selectedGrade, selectedSubject,
			classes, schoolClassList, selectedYear, selectedSubjectTeacherId ) {
		setAddEditPane(thisValue, 'Edit');
		
		findClasses(selectedGrade,classes);
		loadSubjects(selectedGrade,selectedSubject);

		document.form.updateSelectedGrade.value = selectedGrade;
		document.form.updateSelectedSubject.value = selectedSubject;
		document.form.updateStaffID.value = selectedValue;
		document.form.updateGradeSubjectId.value = selectedSubject;
		document.form.selectedYearVal.value = selectedYear;
		document.form.updateSelectedSubjectTeacherId.value = selectedSubjectTeacherId;
		document.getElementById('selectStaff').value = selectedValue;
		document.getElementById('selectGrade').value = selectedGrade;
		document.getElementById('year').value = selectedYear;
		document.getElementById('selectGradeSubjects').value = selectedSubject;
		

	}

	function deleteSubjectTeacher(thisObj, staffId, gradeId, subjectId, year) {
		var elementWraper = $(thisObj).closest('.section_box');
		$(elementWraper).find('.basic_grid tr').removeClass('highlight');
		$(thisObj).parents('tr').addClass('highlight');
		$(elementWraper).find('.section_full_inside').hide();
		var ans = window
				.confirm("<spring:message code='REF.DELETE.CONFIRMATION'/>");
		if (ans) {
			document.form.staffId.value = staffId;
			document.form.selectedYearVal.value = year;
			document.form.updateSelectedGrade.value = gradeId;
			document.form.updateSelectedSubject.value = subjectId;
			document.form.action = 'deleteSubjectTeacher.htm';
			document.form.submit();
		} else {
			$(thisObj).parents('tr').removeClass('highlight');
		}
	}
	
	function reset(){
		document.getElementById('selectStaff').value = "0";
		document.getElementById('selectGrade').value = "0";
		document.getElementById('year').value = "0";
		document.getElementById('selectGradeSubjects').value = "0";
		document.getElementById('classes').innerHTML = '';
	}
</script>

<script type="text/javascript">

function findClasses(selectedValue,classes) {

		var url = '<c:url value="/findClasses.htm" />';

		$.ajax({
                url:url,
	        data:({
	              'selectedValue': selectedValue
	        }),
	        success: function(response) {

	        	var c = response;
	        	var a;

	        	a = c.split(",");

	        	var b;
	        	document.getElementById('classes').innerHTML = '';
	        	if(classes==null){
	        		for (var i=0; i<a.length;i++){
	  	              b=a[i].split("-");
	  	        	var checkBoxDiv = document.getElementById('classes');

	  	        		var input = document.createElement('input');
	  	        	    input.type = 'checkbox';
	  	        	    input.name = 'classId';
	  	        	  	input.className ="checkbox"
	  	        	    input.value = b[1];
	  	        	    checkBoxDiv.appendChild(input);
	  	        	    checkBoxDiv.appendChild(document.createTextNode(b[0]));
	  	            }
	        	}
	        	else{
	        		var newClass = classes.split(',');
	        		for (var i=0; i<a.length;i++){
		  	              b=a[i].split("-");
		  	        	var checkBoxDiv = document.getElementById('classes');

		  	        		var input = document.createElement('input');
		  	        	    input.type = 'checkbox';
		  	        	    input.name = 'classId';
		  	        	  	input.className ="checkbox"
		  	        	    input.value = b[1];
		  	        	  for ( var y = 0; y < newClass.length; y++) {

		  					if (newClass[y] == b[0]){
		  						input.checked = true;
		  					}
		  				}

		  	        	    checkBoxDiv.appendChild(input);
		  	        	    checkBoxDiv.appendChild(document.createTextNode(b[0]));
		  	            }

	        	}



	        },
	        error: function(transport) {
	        	alert("error");

	        }
        });
     }

function loadSubjects(selectedValue, comments) {

	var url = '<c:url value="/populateGradeClassesList.htm" />';

	$.ajax({
		url : url,
		data : ({
			'selectedValue' : selectedValue
		}),
		success : function(response) {

			var c = response;
			var a;

			a = c.split(",");
			if(a[0] == ""){
			a.length = 0;
				}

			var b;
			document.getElementById('selectSubjects').innerHTML = '';
			if (comments == null) {
				var dropDownDiv = document.getElementById('selectSubjects');

				var selector = document.createElement('select');
				selector.id = "selectGradeSubjects";
				selector.name = "selectGradeSubjects";
				selector.path = "subjectId";
				dropDownDiv.appendChild(selector);

				var option = document.createElement('option');
				option.value = '0';
				option
						.appendChild(document
								.createTextNode("<spring:message code="application.drop.down" />"));
				selector.appendChild(option);

				for ( var i = 0; i < a.length; i++) {
					b = a[i].split("_");

					option = document.createElement('option');
					option.value = b[1];
					option.appendChild(document.createTextNode(b[0]));
					selector.appendChild(option);
				}

			} else {
				var newSubject = comments;
				var dropDownDiv = document.getElementById('selectSubjects');

				var selector = document.createElement('select');
				selector.id = "selectGradeSubjects";
				selector.name = "selectGradeSubjects";
				selector.path = "subjectId";
				dropDownDiv.appendChild(selector);

				var option = document.createElement('option');
				option.value = '0';
				option
						.appendChild(document
								.createTextNode("<spring:message code="application.drop.down" />"));
				selector.appendChild(option);

				for ( var i = 0; i < a.length; i++) {
					b = a[i].split("_");

						option = document.createElement('option');
						option.value = b[1];
						
						if (comments == b[1]) {
							option.selected = 'selected';
						}
						option.appendChild(document.createTextNode(b[0]));
						selector.appendChild(option);
				}
			}
		},
		error : function(transport) {
			alert("error");

		}
	});
}


function load(flagValue,flagId, stStaff, stGrade, stYear) {

	var flagVal = null;
	if (!(flagValue == null || flagValue == "")) {
		flagVal = flagValue;
	}
	
	if (flagVal != null) {
		if (!(flagId == null || flagId == "")) {
			
			$(document).ready(function() {
				$("#flag").parents('tr').addClass('highlight');
			});
// 			updateSubjectTeacher(this, stTeacher, stGrade, stSubject,
// 					stClass, schoolClass, stYear, flagValue)
		} 
		else {
			
// 			document.subjectteacher.subjectteacherId.value = 0;
// 			//setAddEditPane(thisValue, 'Add');
			document.getElementById('selectStaff').value = stStaff;				
			document.getElementById('selectGrade').value = stGrade;
			document.getElementById('year').value = stYear;
 			//document.getElementById('selectSubject').value = stSubject;				
// 			//document.getElementById('classes').innerHTML = '';					
// 			findClasses(stGrade, stClass);
// 			loadSubjects(stGrade, stSubject);
			
			
		}
		
	} else {
		$(document).ready(function() {
			$(".section_full_inside").hide();
		});
	}
}


</script>
<!-- END Calender controll CSS and JS -->
</head>
<body onLoad="load('<c:out value="${subTeacher}"></c:out>', '<c:out value="${subTeacher.subjectTeacherId}"></c:out>', '<c:out value="${subTeacher.staff.staffId}"></c:out>', '<c:out value="${subTeacher.gradeId}"></c:out>', '<c:out value="${subTeacher.year}"></c:out>')">
	<h:headerNew parentTabId="1" page="teacherSubjectAllocation.htm" />
	<script language="javascript">
		$(document).ready(function() {
			var url = window.location.toString();
			if (url.split("?")[1] === "new") {
				var firstElement = $("#menubar li").first();
				$("#menubar li a").removeAttr("href")
				$("#menubar li a").addClass("disabled")
				//  $("#menubar ul").append(firstElement);
			}
		});
	</script>
	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb">
				<ul>
					<li><a href="adminWelcome.htm"><spring:message code="application.home"/></a>&nbsp;&gt;&nbsp;</li>
					<li><spring:message code="STAFF.TEACHER_SUB_ALLOCATION"/></li>
				</ul>
			</div>
			<div class="help_link"><a href="#" onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/staff/teacherSubjectAllocationHelp.html"/>','helpWindow',780,550)"><img src="resources/images/ico_help.png" width="20" height="20" align="absmiddle"> <spring:message code="application.help"/></a></div>
		</div>
		<div class="clearfix"></div>
		<h1><spring:message code="STAFF.TEACHER_SUB_ALLOCATION"/></h1>
		<div id="content_main">
			<div class="messageArea" style="padding-left:14px;">
				<label class="required_sign"> <c:if
						test="${message != null}">${message}</c:if> </label>
				<label class="success_sign"> <c:if
						test="${successMessage != null}">${successMessage}</c:if> </label>
			</div>
			<form:form method="POST" name="form" commandName="subjectteacher"
				action="teacherSubjectAllocation.htm">

				<input type="hidden" name="staffId" />
				<input type="hidden" name="updateStaffID" />
				<input type="hidden" name="updateGradeSubjectId" />
				<input type="hidden" name="updateSelectedGrade" />
				<input type="hidden" name="updateSelectedSubject" />
				<input type="hidden" name="updateSelectedSubjectTeacherId" />
				<input type="hidden" name="selectedValue" />
				<input type="hidden" name="selectedYearVal" />
				<div class="section_full_search">
					<div class="box_border">

						<div class="row">
							<div class="float_left">
								<div class="lbl_lock">
									<label><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.LAST_NAME"/></label>
								</div>
								<input type="text" name="lName" />
							</div>
							<div class="float_left">
								<div class="lbl_lock">
									<label><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.EMP_NO"/></label>
								</div>
								<input type="text" name="regNo" />
							</div>
							<div class="float_right">
								<div class="buttion_bar_type1">
								<sec:authorize access="hasRole('Search Teacher Subject Allocation')">
									<input type="button" value="<spring:message code='REF.UI.SEARCH'/>"
										onClick="document.form.action='searchsubjectteacher.htm'; document.form.submit();document.getElementById('search_results').style.display=''; document.getElementById('btn_row').style.display=''"
										class="button">
								</sec:authorize>
								</div>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<div class="clearfix"></div>
				<div class="section_box">
					<div>

						<div class="section_box_header">
							<h2><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.SEARCH_RESULTS"/></h2>
						</div>

						<div class="column_single">
							<table class="basic_grid" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<th width="363"><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.TEACHER"/></th>
									<th width="117"><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.GRADE"/></th>
									<th width="110"><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.SUBJECT"/></th>
									<th width="120"><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.CLASS"/></th>
									<th width="140"><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.YEAR"/></th>									
									<th width="56" align="right" class="right">
										
									<sec:authorize access="hasRole('Add/Edit Teacher Subject Allocation')">	
										<img src="resources/images/ico_new.gif" class="icon_new"
											onClick="clearMessages(); setAddEditPane(this,'Add');reset();"
											width="18" height="20" title="<spring:message code='STAFF.TEACHER_SUB_ALLOCATION.NEW_GS_ALLOCATION'/>">
										
									</sec:authorize>
									
									</th>
								</tr>
								<tr
									<c:choose>
		            					<c:when test="${teacherList != null  || message != null}">
			 								id="search_results"
										</c:when>
		            					<c:otherwise>
											id="search_results" style="display:none;"
										</c:otherwise>
		            				</c:choose>
		            			>

								<c:if test="${not empty teacherList}">
									<c:forEach items="${teacherList}" var="subjectteacher"
										varStatus="status">
										<tr
											<c:choose>
		            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
		            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
		            		</c:choose>>
											<c:set var="classval" value="" />
											<c:forEach items="${subjectteacher.schoolClassList}"
												var="scoolclass" varStatus="loopCount">

												<c:if test="${loopCount.count == 1}">
													<c:set var="classval"
														value="${classval}${scoolclass.description}" />
												</c:if>
												<c:if test="${loopCount.count != 1}">
													<c:set var="classval"
														value="${classval},${scoolclass.description}" />
												</c:if>
											</c:forEach>
											<td class="left" <c:if test="${selectedObj.subjectTeacherId == subjectteacher.subjectTeacherId}">
															id="flag"
												</c:if>>${subjectteacher.staff.nameWithIntials}</td>
											<td>${subjectteacher.gradeSubject.grade.description}</td>
											<td>${subjectteacher.gradeSubject.subject.description}</td>
											<td>${classval}</td>
											<td>${subjectteacher.year}</td>
											
												<td nowrap class="right">
												
												<c:if test="${subjectteacher.deleted == false }">
												
													<sec:authorize access="hasRole('Add/Edit Teacher Subject Allocation')">
													<img src="resources/images/ico_edit.gif"
														title="<spring:message code='STAFF.TEACHER_SUB_ALLOCATION.EDIT_GS_ALLOCATION'/>"
														onClick="clearMessages(); updateSubjectTeacher(this, '<c:out value="${subjectteacher.staff.staffId}" />','<c:out value="${subjectteacher.gradeSubject.grade.gradeId}" />','<c:out value="${subjectteacher.gradeSubject.subject.subjectId}" />','<c:out value="${classval}" />','<c:out value="${schoolClassList}"/>','<c:out value="${subjectteacher.year}"/>','<c:out value="${subjectteacher.subjectTeacherId}"/>');"
														width="18" height="20" class="icon"> 
													</sec:authorize>
													
													<sec:authorize access="hasRole('Delete Teacher Subject Allocation')">
													<img src="resources/images/ico_delete.gif"
														onClick="deleteSubjectTeacher(this,'<c:out value="${subjectteacher.staff.staffId}" />','<c:out value="${subjectteacher.gradeSubject.grade.gradeId}" />','<c:out value="${subjectteacher.gradeSubject.subject.subjectId}" />','<c:out value="${subjectteacher.year}"/>')"
														title="<spring:message code='REF.UI.DELETE'/>" width="18" height="20" class="icon">
													</sec:authorize></td>
												
												</c:if>
										</tr>
									</c:forEach>
								</c:if>

							</table>
						</div>
					</div>

					<div class="section_full_inside">
						<h3><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.NEW_GS_ALLOCATION"/></h3>
						<div class="box_border">
							<div class="row">
								<div class="float_left">
									<div class="lbl_lock">
										<span class="required_sign">*</span> <label><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.TEACHER_NAME"/></label>
									</div>


									<form:select path="staff.staffId" id="selectStaff">
										<option  value="0" ><spring:message code="application.drop.down"/></option>
										<c:forEach var="acedemicstaff" items="${acedemicstafflist}">

											<option label="${acedemicstaff.nameWithIntials}"
												value="${acedemicstaff.staffId}">
												${acedemicstaff.nameWithIntials}</option>
										</c:forEach>
									</form:select>
									<div class="lbl_lock">
										<span class="required_sign">*</span> <label><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.GRADE"/> :</label>
									</div>

									<form:select path="gradeId" id="selectGrade" onchange="findClasses(this.value,null);loadSubjects(this.value,null)">
										 <option  value="0" ><spring:message code="application.drop.down"/></option>
										<c:forEach var="grade" items="${gradeList}">
						                    <c:choose>
						                    	<c:when test="${selected eq grade.gradeId}">
						                    		<option value="${grade.gradeId}" selected="selected">${grade.description}</option>
						                    	</c:when>
						                    	<c:otherwise>
						                    		<option value="${grade.gradeId}">${grade.description}</option>
						                    	</c:otherwise>
						                    </c:choose>

						                </c:forEach>
					                </form:select>
									<div class="lbl_lock">
										<span class="required_sign">*</span> <label><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.SUBJECT"/> :</label>
									</div>
									<div id="selectSubjects"></div>
									<div class="lbl_lock">
		                  				<span class="required_sign">*</span><label><spring:message code="STAFF.CLASS_TEACHER_ALLOCATION.YEAR"/></label>
		                		   </div>
		               			<form:select path="year" id="year">
		               			<option value="0" ><spring:message code="application.drop.down"/></option>
		                    	<option label="${currentYear}" value="${currentYear}" >${currentYear}</option>
		                    	<option label="${currentYear+1}" value="${currentYear+1}" >${currentYear+1}</option>
		                		</form:select>
								</div>
								<div class="float_right">
									<div class="lbl_lock">
										<span class="required_sign">*</span> <label><spring:message code="STAFF.TEACHER_SUB_ALLOCATION.CLASS"/> :</label>
									</div>


										<div id="classes" class="inline_checkboxes" >

										</div>

								</div>

							<div class="row">
								<div class="buttion_bar_type1">
								<sec:authorize access="hasRole('Add/Edit Teacher Subject Allocation')">
										<input type="button" class="button"
											onClick="document.form.action='addSubjectTeacher.htm'; document.form.submit();setAddEditPane(this,'Save')"
											value="<spring:message code='REF.UI.SAVE'/>"> 
										<input type="button" class="button" 
										onClick="setAddEditPane(this,'Cancel'); clearMessages()" value="<spring:message code='REF.UI.CANCEL'/>">
								</sec:authorize>
								</div>
							</div>
							<div class="clearfix"></div>
							</div>
						</div>
					</div>
					<div class="clearfix"></div>
				</div>
			</form:form>
		</div>
	</div>
	<h:footer />
</body>
</html>

