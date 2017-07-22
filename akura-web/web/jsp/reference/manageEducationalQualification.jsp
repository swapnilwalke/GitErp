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

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE HTML>
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
<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/common.js"></script>

<script type="text/javascript">
	function addNew(thisValue) {
		setAddEditPane(thisValue, 'Add');
		if (document.educationalQualificationForm.description.value != null) {
			document.educationalQualificationForm.description.value = '';
		}
		document.educationalQualificationForm.educationalQualificationId.value = 0;
	}

	function saveEducationalQualification(thisValue) {
		setAddEditPane(thisValue, 'Save');
		document.educationalQualificationForm.action = "saveOrUpdateEducationalQualification.htm";
		document.educationalQualificationForm.submit();
	}

	function updateEducationalQualification(thisValue, selectedValue,
			description) {
		setAddEditPane(thisValue, 'Edit');
		document.educationalQualificationForm.educationalQualificationId.value = selectedValue;
		document.educationalQualificationForm.description.value = description;
	}

	function deleteEducationalQualification(thisValue, selectedValue) {
		var elementWraper = $(thisValue).closest('.section_box');
		$(elementWraper).find('.basic_grid tr').removeClass('highlight');

		document.educationalQualificationForm.educationalQualificationId.value = selectedValue;

		$(thisValue).parents('tr').addClass('highlight');
		$(elementWraper).find('.section_full_inside').hide();

		var ans = window
				.confirm('<spring:message code="REF.DELETE.CONFIRMATION"/>')

		if (ans) {
			$(thisValue).parents('tr').hide();
			document.educationalQualificationForm.action = "manageDeleteEducationalQualification.htm";
			document.educationalQualificationForm.submit();
		} else {
			$(thisValue).parents('tr').removeClass('highlight');
		}
	}
	
	function changePanelTitle() {
		if($('.highlight').length > 0) {
			$('#panelTitle').empty();
			$('#panelTitle').append("<spring:message code='REF.UI.MANAGE.EDUCATIONAL.EDIT' />");
		}
	}
	
</script>
</head>
<body onload="changePanelTitle('<c:out value="${educationalQualification.educationalQualificationId}"/>')" >

	<h:headerNew parentTabId="26" page="referenceModule.htm" />

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb">
				<ul>
					<li><a href="adminWelcome.htm"><spring:message
								code="REF.UI.HOME" />
					</a>&nbsp;&gt;&nbsp;</li>
					<li><a href="referenceModule.htm"><spring:message
								code="REF.UI.REFERENCE" /> </a>&nbsp;&gt;&nbsp;</li>
					<li><spring:message
							code="REF.UI.MANAGE.EDUCATIONAL.QUALIFICATION.TITLE" /></li>
				</ul>
			</div>
			<div class="help_link">
				<a href="#"
					onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/educationalQualificationHelp.html"/>','helpWindow',780,550)"><img
					src="resources/images/ico_help.png" width="20" height="20"
					align="absmiddle"> <spring:message code="REF.UI.HELP" />
				</a>
			</div>
		</div>
		<div class="clearfix"></div>
		<h1>
			<spring:message code="REF.UI.MANAGE.EDUCATIONAL.QUALIFICATION.TITLE" />
		</h1>
		<div id="content_main">
			<form:form action="" method="POST"
				name="educationalQualificationForm"
				commandName="educationalQualification">

				<form:hidden path="educationalQualificationId" />

				<div class="clearfix"></div>
				<div class="section_box">
					<div id="search_results">
						<div class="section_box_header">
							<h2>
								<spring:message
									code="REF.UI.MANAGE.EDUCATIONAL.QUALIFICATION.DETAIL" />
							</h2>
						</div>
						<div class="required_sign messageArea" id="area">
								<c:if test="${!(message == null)}">
								<div class="error">
									&nbsp;<label id="errormsg"><c:out
											value="${message}" /> </label>
								</div>
							</c:if>
							<form:errors path="educationalQualificationId" />
							<form:errors path="description" />
						</div>
						<div class="column_single">
							<table class="basic_grid" border="0" cellspacing="0"
								cellpadding="0">
								<tr>
									<th width="857"><spring:message
											code="REF.UI.MANAGE.EDUCATIONAL.QUALIFICATION" /></th>
									<th width="51" class="right"><sec:authorize
											access="hasRole('Add/Edit Educational Qualification')">
											<img src="resources/images/ico_new.gif" class="icon_new"
												onClick="clearMessages(); addNew(this);" width="18" height="20"
												title="<spring:message code="REF.UI.MANAGE.EDUCATIONAL.QUALIFICATION.ADD"/>">
										</sec:authorize></th>
								</tr>
								<c:choose>
									<c:when test="${not empty educationalQualificationList}">
										<c:forEach items="${educationalQualificationList}"
											var="educationalQualificationData" varStatus="status">
											
											<c:set var="cssClass" value="${(status.count % 2 == 1) ? 'odd' : 'even'}" />
											<c:if test="${((showPanel != null) && (showPanel == 'TRUE')
											 	&& (educationalQualificationData.educationalQualificationId == educationalQualification.educationalQualificationId))}">
												<c:set var="cssClass" value="${cssClass} highlight" />
											</c:if>
											
											<tr class="${cssClass}" >
												<td>${educationalQualificationData.description}</td>
												<td nowrap class="right"><sec:authorize
														access="hasRole('Add/Edit Educational Qualification')">
														<img src="resources/images/ico_edit.gif"
															onClick="clearMessages(); updateEducationalQualification(this, '<c:out value="${educationalQualificationData.educationalQualificationId}" />', '<c:out value="${educationalQualificationData.description}" />');"
															title="<spring:message code="REF.UI.MANAGE.EDUCATIONAL.EDIT"/>"
															width="18" height="20" class="icon">
													</sec:authorize>
													<sec:authorize
														access="hasRole('Delete Educational Qualification')">
														<img src="resources/images/ico_delete.gif"
															onClick="clearMessages(); deleteEducationalQualification(this, '<c:out value="${educationalQualificationData.educationalQualificationId}" />');"
															title="<spring:message code="REF.UI.DELETE"/>" width="18"
															height="20" class="icon">
													</sec:authorize>
												</td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td width="830"><h5>
													<spring:message
														code="REF.UI.MANAGE.EDUCATIONAL.No.QUALIFICATION.FOUND" />
												</h5>
											</td>
											<td nowrap class="right"></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
					</div>
					<sec:authorize
						access="hasRole('Add/Edit Educational Qualification')">
						
						<c:set var="displayStyle" value="${((showPanel != null) && (showPanel == 'TRUE')) ? 'display: block;' : 'display: none;' }" />
						
						<div class="section_full_inside" style="${displayStyle}">
							<h3 id="panelTitle">
								<spring:message
									code="REF.UI.MANAGE.EDUCATIONAL.QUALIFICATION.ADD" />
							</h3>
							<div class="box_border">
								<div class="row">
									<div class="float_left">
										<div class="lbl_lock">
											<span class="required_sign">*</span><label><spring:message
													code="REF.UI.MANAGE.EDUCATIONAL.QUALIFICATION" />:</label>
										</div>
										<form:input path="description"
											onkeypress="return disableEnterKey(event)" maxlength="45"
											value="${((showPanel != null) && (showPanel == 'TRUE')) ? educationalQualification.description : ''}"
										 />
									</div>
									<div class="buttion_bar_type1" style="margin-top: 15px;">
										<input type="button"
											value="<spring:message code="REF.UI.SAVE" />"
											onClick="saveEducationalQualification(this);" class="button"><input
											type="button" class="button"
											onClick="clearMessages(); setAddEditPane(this,'Cancel');"
											value="<spring:message code="REF.UI.CANCEL" />">
									</div>
								</div>
								<div class="clearfix"></div>
							</div>
						</div>
					</sec:authorize>
					<div class="clearfix"></div>
				</div>

			</form:form>
		</div>
	</div>
	<h:footer />
</body>
</html>
