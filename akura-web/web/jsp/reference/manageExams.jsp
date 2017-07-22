<%--
    < �KURA, This application manages the daily activities of a Teacher and a Student of a School>

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

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code='APPLICATION.NAME' />
</title>
<link href="resources//css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources//css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script type="text/javascript">
	function addNew(thisValue) {

		setAddEditPane(thisValue, 'Add');
		document.form.description.value = "";
		document.form.examId.value = 0;
		document.form.gradeId.value = 0;
		$('input[value=true]:radio').attr('checked', 'checked');
	}

	function deleteExam(thisObj) {
		var elementWraper = $(thisObj).closest('.section_box');
		$(elementWraper).find('.basic_grid tr').removeClass('highlight');
		$(thisObj).parents('tr').addClass('highlight');
		$(elementWraper).find('.section_full_inside').hide();
		var ans = window
				.confirm("<spring:message code='REF.DELETE.CONFIRMATION' />");
		if (ans) {
			document.form.action = 'manageDeleteExam.htm';
			document.form.submit();
		} else {
			$(thisObj).parents('tr').removeClass('highlight');
		}
	}

	function editExam(thisVal, description, examId, mark, gradeId) {
		setAddEditPane(thisVal, 'Edit');
		document.form.description.value = description;
		document.form.examId.value = examId;
		document.form.mark.value = mark;
		document.form.gradeId.value = gradeId;
		$('input[value=' + mark + ']:radio').attr('checked', 'checked');
	}
	
	function showArea(){
		   $(document).ready(function() {
				$(".area").hide();
			});
	   }	
	
	function loadTitle(thisValue){

        if (!(thisValue == null || thisValue == "")) {

                $(document).ready(function() {

                        $("#flag").parents('tr').addClass('highlight');

                        document.getElementById('editpanetitle').innerHTML = "<spring:message code="REF.UI.EXAM.IMAGE.EDIT"/>";

                });             
   		}

	}
</script>
</head>

<body onload="loadTitle('<c:out value="${selectedObj}"></c:out>')">

	<h:headerNew parentTabId="26" page="referenceModule.htm" />

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb">

				<ul>
					<li><a href="adminWelcome.htm"><spring:message
								code='REF.UI.HOME' /> </a>&nbsp;&gt;&nbsp;</li>
					<li><a href="referenceModule.htm"><spring:message
								code='REF.UI.REFERENCE' /> </a>&nbsp;&gt;&nbsp;</li>
					<li><spring:message code='REF.UI.MANAGE.EXAMS.TITLE' />
					</li>
				</ul>
			</div>
		</div>


		<div class="help_link">
			<a href="#"
				onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/manageExamHelp.html"/>','helpWindow',780,550)"><img
				src="resources/images/ico_help.png" width="20" height="20"
				align="absmiddle"> <spring:message code='REF.UI.HELP' /> </a>
		</div>



		<div class="clearfix"></div>
		<h1>
			<spring:message code='REF.UI.MANAGE.EXAMS.TITLE' />
		</h1>
		<div id="content_main">
			

			<form:form action="manageSaveOrUpdateExam.htm" method="post"
				commandName="exam" name="form">


				<div class="section_box">
					<div id="search_results">
						<div class="section_box_header">
							<h2>
								<spring:message code='REF.UI.EXAMS.SUB_TITLE' />
							</h2>
						</div>
		
						<div class= "area">
							<label class="required_sign"> <c:if test="${message != null}">${message}</c:if></label>
							<form:errors path="description" cssClass="required_sign" />
						</div>
						<div class="column_single">
							<table class="basic_grid" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<th width="30%"><spring:message code='REF.UI.EXAM' />
									</th>
									<th width="30%"><spring:message code='REF.UI.EXAM.GRADE' />
									</th>
									<th width="40%"><spring:message
											code='REF.UI.EXAM.MARK_TYPE' />
									</th>

									<th width="78" class="right">
									<sec:authorize access="hasAnyRole('Save or Update a Manage Exams')"><img
										src="resources/images/ico_new.gif" class="icon_new"
										onClick="showArea();addNew(this)" width="18" height="20"
										title="<spring:message code='REF.UI.EXAM.IMAGE.ADD'/>">
										</sec:authorize>
									</th>
								</tr>
								<c:choose>
									<c:when test="${not empty examList}">
								<c:forEach var="exam" items="${examList}" varStatus="status">
									<tr>
										<c:choose>
											<c:when test="${(status.count) % 2 == 0}">
												<tr class="odd">
											</c:when>
											<c:otherwise>
												<tr class="even">
											</c:otherwise>
										</c:choose>
										<td <c:if test="${selectedObj.description == exam.description }">
															id="flag" 
												</c:if>><c:out value="${exam.description}"></c:out>
										</td>
										<c:forEach items="${gradeList}" var="grade">
											<c:if test="${grade.gradeId eq exam.gradeId}">
												<td ><c:out value="${grade.description}"></c:out>
												</td>
											</c:if>
										</c:forEach>

										<td><c:choose>
												<c:when test="${exam.mark}">
													<c:out value="Marks"></c:out>
												</c:when>
												<c:otherwise>
													<c:out value="Grades"></c:out>
												</c:otherwise>
											</c:choose></td>


										<td nowrap class="right">
										<sec:authorize access="hasAnyRole('Save or Update a Manage Exams')">
										<img
											src="resources/images/ico_edit.gif"
											title="<spring:message code='REF.UI.EXAM.IMAGE.EDIT'/>"
											onClick="showArea();setAddEditPane(this,'Edit');
										editExam(this,'<c:out value="${exam.description}"/>', '<c:out value="${exam.examId}"/>',
										'<c:out value="${exam.mark}"/>', '<c:out value="${exam.gradeId}"/>');"
											width="18" height="20" class="icon">
											</sec:authorize>
											<sec:authorize access="hasAnyRole('Delete a Manage Exams')">
											<img
											src="resources/images/ico_delete.gif"
											onClick="showArea();document.form.examId.value='${exam.examId}';
                  deleteExam(this);"
											title="<spring:message code='REF.UI.DELETE'/>" width="18"
											height="20" class="icon">
											</sec:authorize></td>
									</tr>
								</c:forEach>
								</c:when>
								<c:otherwise>
								<tr>
									<td width="830">
									<h5><spring:message
										code='REF.UI.EXAM.NO.RESULTS' /></h5>
									</td>
									<td nowrap class="right"></td>
									<td></td>
					<td></td>
								</tr>
							</c:otherwise>
							</c:choose>
							</table>
						</div>
					</div>


					<div class="section_full_inside" style='display: ${showEditSection != null ?'block':'none'}'>
						<h3 id="editpanetitle">
							<spring:message code='REF.UI.EXAM.SUB_FORM.TITLE' />
						</h3>
						<div class="box_border">
							<div class="row">
								<div class="float_left">
									<div class="lbl_lock">
										<span class="required_sign">*</span>
										<label><spring:message code='REF.UI.EXAM.GRADE' />:</label>
									</div>
									<form:select path="gradeId" id="gradeId">
										<form:option value="0" id="optionGrade">
											<spring:message code='application.drop.down' />
										</form:option>
										<c:if test="${gradeList != null}">
											<c:forEach var="grade" items="${gradeList}">
												<option value="${grade.gradeId}" <c:if test='${selectedGradeId != null &&
								                  	grade.gradeId eq selectedGradeId}'> selected="selected" </c:if>>${grade.description}</option>
											</c:forEach>
										</c:if>
									</form:select>
								</div>
							</div>
							<div class="row">
								<div class="float_left">
									<div class="lbl_lock">
										<span class="required_sign">*</span>
										<label><spring:message code='REF.UI.EXAM' />:</label>
									</div>
									<form:hidden path="examId" />

									<form:input path="description" maxlength="45" />

								</div>

								<div class="float_left" style="margin-left: 90px;">
									<div class="lbl_lock">
										<span class="required_sign">*</span> <label><spring:message
												code='REF.UI.EXAM.MARK_TYPE' />:</label>
									</div>
									<div class="radio_buttion_bar_type1" style="margin-left: 10px;">
										<form:radiobutton path="mark" value="true"
											cssClass="radio_btn" />
										<label><spring:message code='REF.UI.EXAM.MARK' /> </label>&nbsp;&nbsp;
										<form:radiobutton path="mark" cssClass="radio_btn"
											value="false" />
										<label><spring:message code='REF.UI.EXAM.GRADES' /> </label>
									</div>
								</div>


								<div class="float_right"></div>
							</div>

							<div class="row">
								<div class="buttion_bar_type1">
								<sec:authorize access="hasAnyRole('Save or Update a Manage Exams')">
									<input type="submit" class="button"
										value="<spring:message code='REF.UI.SAVE'/>">
										<input
										type="button" class="button"
										onClick="showArea();setAddEditPane(this,'Cancel')"
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
