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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.virtusa.akura.api.dto.ExamSubject"%>

<%@page import="java.util.List"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code='APPLICATION.NAME' />
</title>
<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/list_grade_subject.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="JavaScript" src="resources/js/list_exam_subject.js"
	type="text/JavaScript"></script>

<script type="text/javascript">

//saves the exam subjects.
	function saveExamSubjects(thisValue) {
		setAddEditPane(thisValue,'Save');

		if(${selectedDescription != null}){
			document.form.examSubject.value = 'edit';

		} else {
			document.form.examSubject.value = 'save';

		}

		var toListItems = document.getElementById("ToList").getElementsByTagName("li");

		if(toListItems.length > 0) {
			var allSubjectIdArray = new Array();
			var allSubjectNamesArray = new Array();
			var optionalSudjectIds = new Array();//added

		    for (var i = 0; i < toListItems.length; i++) {
		    	if(toListItems[i].getElementsByTagName("input")[0].checked){//added
		    		optionalSudjectIds[i] = toListItems[i].getElementsByTagName("input")[0].value;
		    	}
		    	allSubjectIdArray[i] = toListItems[i].getElementsByTagName("input")[0].value;
		    	allSubjectNamesArray[i] = toListItems[i].getElementsByTagName("div")[0].outerText;
		    }
	    	document.form.allSubjectIds.value = allSubjectIdArray;
	    	document.form.allSubjectNames.value = allSubjectNamesArray;
	    	document.form.optionalSudjectIds.value = optionalSudjectIds;//added
	    	
		}
		
		document.form.examDescription.value = '${selectedDescription}';
		document.form.action = "saveOrUpdateExamSubjects.htm";
	    document.form.submit();
	}

	// delete exam subject for the relevant exam subject description.
	function deleteExamSubjects(thisObj, examDescription) {
		var elementWraper = $(thisObj).closest('.section_box');
		$(elementWraper).find('.basic_grid tr').removeClass('highlight');
		$(thisObj).parents('tr').addClass('highlight');
		$(elementWraper).find('.section_full_inside').hide();
		document.form.examDescription.value = examDescription;
		var ans = window.confirm("<spring:message code='REF.DELETE.CONFIRMATION'/>");
		if(ans){
			document.form.action='deleteExamSubject.htm';
			document.form.submit();
		}
		else {
			$(thisObj).parents('tr').removeClass('highlight');
		}
	}

	// edit the exam subjects.
	function editExamSubjects(thisVal, examDescription, examSubjectId) {
		document.getElementById("examSubject").value = 'edit';
		setAddEditPane(thisVal, 'Edit');
		document.form.examDescription.value = examDescription;
		document.form.examSubjectId.value = examSubjectId;
		document.form.action = "editExamSubject.htm";
		document.form.submit();
	}

	function onClickOption(thisVal) {
		var toListItems = document.getElementById("ToList").getElementsByTagName("li");

	    for (var i = 0; i < toListItems.length; i++) {
	    	toListItems[i].className = "";
	    }
	    thisVal.className = "item_selected";
	}

	function addNew(thisVal) {
		setAddEditPane(thisVal,'Add');
		document.form.action = "addState.htm";
		document.form.submit();
	}

	// load subjects for a selected exam.
	function loadSubjects(selectedValue){
		var url = '<c:url value="/findSubjectsByExam.htm" />';
		emptyContainer();
		$.ajax({
                url:url,
	        data:({
	              'selectedValue': selectedValue,
	              'examSubjectId': document.form.examSubjectId.value
	        }),
	        success: function(response) {
	        	//splits the response with the ','.
	        	var responseArray = response.split(",");
	        	
	        	document.getElementById('subject').innerHTML = '';
	        	var dropDownDiv = document.getElementById('subject');
	        	
	        	// creates a selector.
	        	var selector = document.createElement('select');
				selector.id = "FromList";
				selector.name = "select";
				selector.multiple = "multiple"
				selector.size = "15"
				//appends the dropdown to the selector.
				dropDownDiv.appendChild(selector);
				if (responseArray.length > 0) {

					for ( var i = 0; i < responseArray.length; i++) {

						if (responseArray[i] != "") {
							option = document.createElement('option');
							var responseValue = responseArray[i].split("-");
							option.value = responseValue[1];
							option.appendChild(document.createTextNode(responseValue[0]));
							selector.appendChild(option);
						}
					}
				}
	        },
	        error: function(transport) {
	        	alert("error");

	        }
        });
     }
	
	function loadTitle(thisValue){
		
        if (!(thisValue == null || thisValue == "")) {
                $(document).ready(function() {
                	loadSubjects(document.getElementById("selectExamId").value);
						document.form.examDescription.value = '${selectedDescription}';

                        $("#flag").parents('tr').addClass('highlight');

                        document.getElementById('editpanetitle').innerHTML = "<spring:message code="REF.UI.MANAGE.EXAM.SUBJECT.EDIT"/>";

                });     
                
   		}
	}
	
	//added
	function loadOpenStateData(){
		
		fromList = document.getElementById("FromList");
	    toList = document.getElementById("ToList");
	    var len = fromList.length;
	    <c:forEach items="${allSubjectIds}" var="subjectId" varStatus="i">
	        var newOption = document.createElement("li");
	        newOption.innerHTML ="<div style='width:210px;'>" + '${allSubjectIds[i.count -1]}'  + "</div><div style='width:70px;'><input type='checkbox' value='${subjectId}'  ${optionalSudjectIds[i.count-1]==subjectId ? " checked='checked'":''}  name='optionalSubjects'></div><div class='clearfix'></div>"; 
	        toList.appendChild(newOption);

		</c:forEach>
	    
		//get the toList length
	    var toListOps = toList.getElementsByTagName("li");
		var removeCount = toListOps.length;
	    //remove selected from the fromList
		for (j = 0; j < len; j++) { //iterates over fromList
		
			for(k = 0; k<toListOps.length; k++){//iterates over toList
				var remove = false;
			
		        if (fromList.options[j].value == toListOps[k].getElementsByTagName("input")[0].value) {
		        	remove = true;
		            break;
		        }
			}
			
			if(remove){
		    	fromList.remove(j);
		    	len--;
		    	removeCount--;
		    	j--;
			}
			if(removeCount==0){
				break;
			}
	    }
	    
	}
	
</script>
</head>
<body <c:if test="${exam.examId != 0}">onload="loadTitle('<c:out value="${selectedObj}"></c:out>');"</c:if>>

	<h:headerNew parentTabId="26" page="referenceModule.htm" />

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb">
				<ul>
					<li><a href="adminWelcome.htm"><spring:message
								code='REF.UI.HOME' />
					</a>&nbsp;&gt;&nbsp;</li>
					<li><a href="referenceModule.htm"><spring:message
								code='REF.UI.REFERENCE' />
					</a>&nbsp;&gt;&nbsp;</li>
					<li><spring:message code='REF.UI.EXAM.SUBJECT.SUB_TITLE' />
					</li>
				</ul>
			</div>
			<div class="help_link">
				<a href="#"
					onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/manageExamSubjectsHelp.html"/>','helpWindow',780,550)"><img
					src="resources/images/ico_help.png" width="20" height="20"
					align="absmiddle"> <spring:message code='REF.UI.HELP' />
				</a>
			</div>
		</div>
		<div class="clearfix"></div>
		<h1>
			<spring:message code='REF.UI.EXAM.SUBJECT.SUB_TITLE' />
		</h1>
		<div id="content_main">
			<form:form action="" method="post" commandName="examSubject"
				name="form">
				<!-- creates hidden fields for the exam subject , subject keys and the exam description. -->
				<input type="hidden" name="allSubjectIds" value="" />
				<input type="hidden" name="allSubjectNames" value=""/>
				<input type="hidden" name="examDescription" />
				<input type="hidden" name="examSubject" />
				<input type="hidden" name="optionalSudjectIds" value=""/>
				<form:hidden path="examSubjectId" />
				<div class="section_box">
					<div class="section_box_header">
						<h2>
							<spring:message code='REF.UI.MANAGE.EXAM.SUBJECT.DETAILS' />
						</h2>
					</div>
					
					<div class="messageArea">
						<div>
							<form:errors path="exam.examId" cssClass="required_sign" />
						</div>
						<div>
							<label class="required_sign">${message}</label>
						</div>
						<c:if test="${errorMessage != null}">
							<div>
								<label class="required_sign">${errorMessage}</label>
							</div>
						</c:if>
					</div>

					<div class="column_single">
						<table class="basic_grid" border="0" cellspacing="0"
							cellpadding="0">
							<tr>
								<th width="20%"><spring:message
										code='REF.UI.MANAGE.EXAM.SUBJECT.EXAM' />
								</th>
								<th><spring:message
										code='REF.UI.MANAGE.EXAM.SUBJECT.MAIN.SUBJECTS' />
								</th>
								<th><spring:message
										code='REF.UI.MANAGE.EXAM.SUBJECT.OPTIONAL.SUBJECTS' />
								</th>
								<th width="42" align="right" class="right"><sec:authorize
										access="hasAnyRole('Add/Edit Exam Subject')">
										<img src="resources/images/ico_new.gif" class="icon_new"
											onClick="addNew(this);" width="18" height="20"
											title="<spring:message code='REF.UI.MANAGE.EXAM.SUBJECT.ADD'/>">
									</sec:authorize>
								</th>
							</tr>
							<c:choose>

								<%-- if the exam subjects list is not empty display when page loads.--%>
								<c:when test="${not empty examSubjects}">
									<c:forEach var="examSubjects" items="${examSubjects}"
										varStatus="status">
										<tr <c:if test="${status.count % 2 == 1}">class="odd"</c:if>
											<c:if test="${status.count % 2 == 0}">class="even"</c:if>>
											<td <c:if test="${fn:split(examSubjects.key, ',')[0] == selectedDescription}">id="flag"</c:if>><c:out value="${fn:split(examSubjects.key, ',')[0]}" />
											</td>
											<td>
												<%
												    int mainCount = 0; // initializes the variable to store the
												                                       // count of the number of main subjects.
												%> <c:forEach items="${examSubjects.value}" var="examSubjectMain">
													<c:if test="${examSubjectMain.optionalSubject eq false}">
														<%
														    mainCount++;
														%>
														<%
														    if (mainCount != 1)
														%>,
	            	 	<c:out value="${examSubjectMain.subject.description}" />
													</c:if>
												</c:forEach>
											</td>
											<td>
												<%
												    int optionalCount = 0;
												%> <c:forEach items="${examSubjects.value}"
													var="examSubjectsOptional">
													<c:if
														test="${examSubjectsOptional.optionalSubject eq true}">
														<%
														    optionalCount++;
														%>
														<%
														    if (optionalCount != 1)
														%>,
					<c:if test="optionalCount != 1">,</c:if>
														<c:out value="${examSubjectsOptional.subject.description}" />
													</c:if>
												</c:forEach>
											</td>
											<td nowrap class="right">
												<%-- Checks the logged role has the privileges to edit or the delete
					exam subjects. --%> <sec:authorize
													access="hasAnyRole('Add/Edit Exam Subject')">
													<img src="resources/images/ico_edit.gif"
														onClick="editExamSubjects(this, '${fn:split(examSubjects.key, ',')[0]}',
						'${fn:split(examSubjects.key, ',')[1]}');"
														title="<spring:message code='REF.UI.MANAGE.EXAM.SUBJECT.EDIT'/>"
														width="18" height="20" class="icon">
												</sec:authorize> <sec:authorize access="hasAnyRole('Delete Exam Subject')">
													<img src="resources/images/ico_delete.gif"
														onClick="deleteExamSubjects(this, '${fn:split(examSubjects.key, ',')[0]}');"
														title="<spring:message code='REF.UI.DELETE'/>" width="18"
														height="20" class="icon">
												</sec:authorize>
											</td>
										</tr>
									</c:forEach>
								</c:when>
								<c:otherwise>
									<tr>
										<td width="830">
											<h5>
												<spring:message code="REF.UI.MANAGE.EXAM.SUBJECT.NO.RESULT" />
											</h5></td>
										<td nowrap class="right"></td>
										<td></td>
										<td></td>
									</tr>
								</c:otherwise>
							</c:choose>
						</table>
					</div>
					<div class="section_full_inside" id="sectionFull" <c:if test = "${selectedDescription != null or add}">
					javascript:style="display: ''; </c:if>style="display: none;">
					<input type="hidden" name="firstTime" value="false"/>
						<c:if test="${selectedDescription == null}">
							<h3>
								<spring:message code='REF.UI.MANAGE.EXAM.SUBJECT.ADD' />
							</h3>
						</c:if>
						<c:if test="${selectedDescription != null}">
							<h3 id="editpanetitle">
								<spring:message code='REF.UI.MANAGE.EXAM.SUBJECT.EDIT' />
							</h3>
						</c:if>
						<div class="box_border">
							<div class="row">
								<div class="float_left">
									<div class="lbl_lock">
										<span class="required_sign">*</span><label><spring:message
												code='REF.UI.MANAGE.EXAM.SUBJECT.EXAM' />:</label>
									</div>
									<form:select path="exam.examId" id="selectExamId"
										onchange="loadSubjects(this.value)">
										<c:if test="${exam.examId != 0}">
											<form:option value="0" id="selectOption">
												<spring:message code='application.drop.down' />
											</form:option>
										</c:if>
										<%-- iterates a list of exams and if it is editing then disable the exam field.--%>
										<c:forEach items="${examList}" var="exam">
											<c:if test="${selectedDescription != null}">

												<option value="${exam.examId}"
													<c:if test="${exam.description != selectedDescription}">disabled="disabled"</c:if>
													<c:if test="${exam.description eq selectedDescription}">selected="selected" </c:if>>${exam.description}</option>
											</c:if>

											<c:if test="${selectedDescription == null}">
												<option value="${exam.examId}"<c:if test='${selectedExamId != null &&
								                  	exam.examId eq selectedExamId}'> selected="selected" </c:if>>${exam.description}</option>
											</c:if>
										</c:forEach>
									</form:select>
								</div>
							</div>
							<div class="row">
								<div class="float_left">
									<div class="lbl_lock">
										<label>&nbsp;</label>
									</div>
									<div id="add_remove_list">
										<div class="list_containers">
											<label><spring:message
													code='REF.UI.MANAGE.EXAM.SUBJECT.AVAILABLE.SUBJECTS' />:</label>
											<c:if test="${not empty availableSubjectsList}">
												<select name="select" size="15" multiple id="FromList">

													<c:forEach items="${availableSubjectsList}"
														var="availableSubject" varStatus="status">
														<option value="${fn:split(availableSubject , '-')[1]}" >${fn:split(availableSubject
															, '-')[0]}</option>
													</c:forEach>
													
												</select>
											</c:if>

											<c:if test="${empty selectedSubjectsList}">
												<div id="subject"></div>
											</c:if>

											<div id="subject"></div>


										</div>
										<div class="controller">
											<input type="button" class="button"
												title="<spring:message code='REF.UI.MANAGE.EXAM.SUBJECT.ADD.SELECTED'/>"
												name="Button" value="&gt;" onClick="add();"><br>
											<br> <input type="button" class="button" name="Button"
												value="&lt;"
												title="<spring:message code='REF.UI.MANAGE.EXAM.SUBJECT.REMOVE.SELECTED'/>"
												onClick="remove();">
										</div>
										<div class="list_containers">
											<span class="required_sign">*</span><label><spring:message
													code='REF.UI.MANAGE.EXAM.SUBJECT.SELECTED.SUBJECT' />:</label>
											<c:choose>
												<c:when test="${not empty selectedSubjectsList}">
													<ul name="subjects" size="15" multiple id="ToList">
														<c:forEach items="${selectedSubjectsList}"
															var="selectedSubject">
															<li onclick="onClickOption(this);">
																<div>${selectedSubject.subject.description}</div> <input
																type="checkbox"
																value="${selectedSubject.subject.subjectId}"
																name="optionalSubjects"
																<c:if test="${selectedSubject.optionalSubject eq true}">checked="checked"</c:if>>
																<div style='width: 50px;'>
																	<spring:message
																		code='REF.UI.MANAGE.EXAM.SUBJECT.OPTIONAL' />
																</div></li>
														</c:forEach>
													</ul>
												</c:when>
												<c:otherwise>
													<ul name="subjects" size="15" multiple id="ToList">
													</ul>
												</c:otherwise>
											</c:choose>
										</div>
										<div class="clearfix"></div>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="buttion_bar_type1">
									<sec:authorize access="hasAnyRole('Add/Edit Exam Subject')">
										<input type="button" class="button"
											onClick="saveExamSubjects(this);"
											value="<spring:message code='REF.UI.SAVE'/>">
										<input type="button" class="button"
											onClick="clearMessages(); setAddEditPane(this,'Cancel');"
											value="<spring:message code='REF.UI.CANCEL'/>">
									</sec:authorize>
								</div>
							</div>
							<div class="clearfix"></div>
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
