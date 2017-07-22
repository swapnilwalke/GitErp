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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="h"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="strEscapeUtil" uri="/WEB-INF/stringEscapeUtil/"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title><spring:message code="APPLICATION.NAME" /></title>

<link href="resources/css/css_reset.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/theme.css" rel="stylesheet" type="text/css">
<link href="resources/css/main_style.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/jquery.ui.core.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/jquery.ui.theme.css" rel="stylesheet"
	type="text/css">
<link href="resources/css/jquery.ui.datepicker.css" rel="stylesheet"
	type="text/css">

<script language="javascript" src="resources/js/jquery-1.6.2.min.js"></script>
<script language="javascript" src="resources/js/main.js"></script>
<script language="javascript" src="resources/js/common.js"></script>
<script src="resources/js/jquery.ui.core.min.js"></script>
<script src="resources/js/jquery.ui.widget.min.js"></script>
<script src="resources/js/jquery.ui.datepicker.min.js"></script>
<script language="javascript" src="resources/js/list_special_event.js"></script>
<link href="resources/css/listSpecialEvent.css" rel="stylesheet" type="text/css">

<script>
	$(function() {
		$("#eventDate").datepicker({
			changeYear : true,
			changeMonth : true,
			yearRange : "c-1:c+1",
			dateFormat : 'yy-mm-dd',
			showOn : "button",
			buttonImage : "resources/images/calendar.jpg",
			buttonImageOnly : true
		});
	});
</script>
<script type="text/javascript">
	function findOptionList(selectedValue, participents) {

		var url = '<c:url value="/findParticipentList.htm" />';

		$.ajax({
			url : url,
			data : ({
				'selectedValue' : selectedValue
			}),
			success : function(response) {

				var c = response;
				var a;

				a = c.split(",");

				var b;

				document.getElementById('selectParticipent').innerHTML = '';
				document.getElementById('ToList').innerHTML = '';
				
				
				if (participents == null || participents =="") {

					if (selectedValue == 0) {
						
						var dropDownDiv = document
						.getElementById('selectParticipent');
						var selector = document.createElement('select');
						selector.id = "FromList";
						selector.name = "FromList";
						selector.multiple = "multiple";
						selector.size = "15";
						dropDownDiv.appendChild(selector);

					//	var optionGroup = document.createElement('optgroup');
					//	optionGroup.label = "<spring:message code='REF.UI.SPECIAL_EVENT.PARTICIPANT_LIST' />";
					//	selector.appendChild(optionGroup);						
						
						
					} else {

						var dropDownDiv = document
								.getElementById('selectParticipent');

						var selector = document.createElement('select');
						selector.id = "FromList";
						selector.name = "FromList";
						selector.multiple = "multiple";
						selector.size = "15";
						dropDownDiv.appendChild(selector);

					//	var optionGroup = document.createElement('optgroup');
					//	optionGroup.label = "<spring:message code='REF.UI.SPECIAL_EVENT.PARTICIPANT_LIST' />";
					//	selector.appendChild(optionGroup);

						var option;

						for ( var i = 0; i < a.length; i++) {
							b = a[i].split("_");

							option = document.createElement('option');
							option.value = b[1];
							option.appendChild(document.createTextNode(b[0]));
							selector.appendChild(option);
						//	optionGroup.appendChild(option);
						}
					}

				} else {
					
					document.getElementById('selectedParticipent').innerHTML = '';
					
					var lst = participents;
					var selectedParticipant = lst.split(", ");

					var dropDownDiv = document
							.getElementById('selectParticipent');

					var selector = document.createElement('select');
					selector.id = "FromList";
					selector.name = "FromList";
					selector.multiple = "multiple";
					selector.size = "15";
					dropDownDiv.appendChild(selector);

				//	var optionGroup = document.createElement('optgroup');
				//	optionGroup.label = "<spring:message code='REF.UI.SPECIAL_EVENT.PARTICIPANT_LIST' />";
				//	selector.appendChild(optionGroup);

					for ( var i = 0; i < a.length; i++) {
						b = a[i].split("_");
						var selected = false;
										
						for(var j = 0; j < selectedParticipant.length; j++){
							if (selectedParticipant[j] == b[0]) {
								selected = true;
							}
						}
						if(!selected){
							option = document.createElement('option');
							option.value = b[1];
							option.appendChild(document.createTextNode(b[0]));
							selector.appendChild(option);
						}
					}
					
					var dropDownDiv = document
					.getElementById('selectedParticipent');
					
					var selector = document.createElement('select');
					selector.id = "ToList";
					selector.name = "ToList";
					selector.multiple = "multiple";
					selector.size = "15";
					dropDownDiv.appendChild(selector);

			//		var optionGroup = document.createElement('optgroup');
			//		optionGroup.label = "<spring:message code='REF.UI.SPECIAL_EVENT.PARTICIPANT_SELECTED' />";
			//		selector.appendChild(optionGroup);
					
					
						for ( var i = 0; i < a.length; i++) {
							b = a[i].split("_");
							var selected = false;
											
							for(var j = 0; j < selectedParticipant.length; j++){
								if (selectedParticipant[j] == b[0]) {
									selected = true;
								}
							}
							if(selected){
								option = document.createElement('option');
								option.value = b[1];
								option.appendChild(document.createTextNode(b[0]));
								selector.appendChild(option);
							}
						}
					
					
					
					

				}

			},
			error : function(transport) {
				alert("error");

			}
		});
	}

	function addSpecialEventAndParticipant(thisValue) {
		setAddEditPane(thisValue, 'Add');
		document.specialEventform.specialEventsId.value = 0;
		document.getElementById('name').value = "";
		document.getElementById('description').value = "";
		document.getElementById('FromList').innerHTML = '';
		document.getElementById('ToList').innerHTML = '';
		document.specialEventform.eventDate.value = '';
		document.getElementById('selectCategory').value = document
				.getElementById('selectOptionCategory').value;
		if (specialEventform.ToList.length != 0) {
			for ( var i = document.specialEventform.ToList.length; i >= 0; i--) {

				document.specialEventform.ToList.remove(i);
			}
		}
		if (document.specialEventform.FromList.length != 0) {
			for ( var i = 0; i < document.specialEventform.FromList.length; i++) {

				document.specialEventform.FromList.options[i].text = "";
			}
		}

	}

	function saveSpecialEvent() {

		var result = new Array();
		
		if (document.specialEventform.ToList.length != 0) {	
			
			for ( var i = 0; i < document.specialEventform.ToList.length; i++) {
				document.specialEventform.ToList.options[i].selected = "selected";
				if (document.specialEventform.ToList.options[i].selected) {
					result[i] = document.specialEventform.ToList.options[i].text;					
				}
			}
		}

		//setAddEditPane(thisValue, 'Save');
		document.specialEventform.action = "saveOrUpdateSpecialEvent.htm";
		document.specialEventform.submit();

	}

	function deleteSpecialEventAndParticipant(thisValue, selectedValue) {

		var elementWraper = $(thisValue).closest('.section_box');
		$(elementWraper).find('.basic_grid tr').removeClass('highlight');
		document.specialEventform.specialEventsId.value = selectedValue;
		$(thisValue).parents('tr').addClass('highlight');
		$(elementWraper).find('.section_full_inside').hide();

		var ans = window
				.confirm('<spring:message code="REF.DELETE.CONFIRMATION" />')

		if (ans) {
			document.specialEventform.specialEventsId.value = selectedValue;
			document.specialEventform.action = "deleteSpecialEvent.htm";
			document.specialEventform.submit();
		} else {
			$(thisValue).parents('tr').removeClass('highlight');
		}
	}

	function updateSpecialEventAndParticipant(thisValue, selectedValue,
			selectedName, selectedDescription, selectedDate,
			selectedCategoryId, selectedParticipant) {
	
		setAddEditPane(thisValue, 'Edit');
		document.specialEventform.specialEventsId.value = selectedValue;
		document.getElementById('name').value = selectedName;
		document.getElementById('description').value = selectedDescription;
		document.specialEventform.eventDate.value = selectedDate;
		document.getElementById('selectCategory').value = selectedCategoryId;

		findOptionList(selectedCategoryId, selectedParticipant);
		//loadToList(selectedParticipant);
	}
	
	function load(flagValue, spName, spDescription, spDate, spCategory, spParticipant) {
		
		
		//alert();
		
		var flagVal = null;
		if (!(flagValue == null || flagValue == "")) {
			flagVal = flagValue;
		}
		if (flagVal != null) {
			if(flagVal != 0){
				
				//document.specialEventform.eventDate.value = spDate;
				updateSpecialEventAndParticipant(this, flagVal, spName,
						spDescription, document.specialEventform.eventDate.value, spCategory, spParticipant);
			}else{				
				
			document.specialEventform.specialEventsId.value = 0;
				document.getElementById('name').value = spName;
				document.getElementById('description').value = spDescription;
			
			//	document.specialEventform.eventDate.value = spDate;
				document.getElementById('selectCategory').value = spCategory;
				
				
				findOptionList(spCategory, spParticipant);
				//loadToList(spParticipant);
			
			}
			
		} 
	}
	


	 function showArea(){
		   $(document).ready(function() {
				$(".area").hide();
			});
	   }	
		
</script>



</head>

<body
 onLoad="load('<c:out value="${ObjId}"></c:out>','<c:out value="${specialEvents.name}"></c:out>','<c:out value="${strEscapeUtil:escapeJS(specialEvents.description)}"></c:out>','<c:out value="${specialEvents.date}"></c:out>','<c:out value="${strEscapeUtil:escapeJS(objCategory)}"></c:out>','<c:out value="${objParitcipantList}"></c:out>')">
	<h:headerNew parentTabId="26" page="referenceModule.htm" />

	<div id="page_container">
		<div id="breadcrumb_area">
			<div id="breadcrumb">
				<ul>
					<li><a href="adminWelcome.htm"><spring:message code="REF.UI.HOME"/></a>&nbsp;&gt;&nbsp;</li>
					<li><a href="referenceModule.htm"><spring:message code="REF.UI.REFERENCE"/></a>&nbsp;&gt;&nbsp;</li>
					<li><spring:message code="REF.UI.SPECIAL_EVENT.TITLE" /></li>
				</ul>
			</div>
			<div class="help_link">
				<a href="#"
					onClick="PopupCenterScroll('<c:url value="resources/html/akura-help/topics/admin/manageSpecialEventsHelp.html"/>','helpWindow',780,550)"><img
					src="resources/images/ico_help.png" width="20" height="20"
					align="absmiddle"><spring:message code="REF.UI.HELP"/></a>
			</div>
		</div>
		<div class="clearfix"></div>
		<h1>
			<spring:message code="REF.UI.SPECIAL_EVENT.TITLE" />
		</h1>
		<div id="content_main">

			<form:form action="" method="POST" name="specialEventform"
				commandName="specialEvents">

				<form:hidden path="specialEventsId" />
				<input type="hidden" name="selectedCategory" />

				<div class="section_box">
					<div id="search_results">
						<div class="section_box_header">
							<h2>
								<spring:message code="REF.UI.SPECIAL_EVENT.SUB.TITLE" />
							</h2>
						</div>
						<c:if test="${message != null}">

							<div class="area">
								<label class="required_sign">${message}</label>
								<form:errors path="name" id="errormsg"
								class="required_sign" />
							</div>
						</c:if>
						<div class="area">
							<form:errors path="specialEventsId" id="errormsg"
								class="required_sign" />
						</div>
						
						<div class="column_single">
							<table class="basic_grid" border="0" cellspacing="0"
								cellpadding="0" >
								<tr>
									<th width="160"><spring:message
											code="REF.UI.SPECIAL_EVENT.NAME" /></th>
									<th width="120"><spring:message
											code="REF.UI.SPECIAL_EVENT.DATE" /></th>
									<th width="500"><spring:message
											code="REF.UI.SPECIAL_EVENT.PARTICIPANT" /></th>
									<th width="55" align="right" class="right">
									
									<sec:authorize access="hasRole('save/update Special Events Page')">
									
									<img src="resources/images/ico_new.gif" class="icon_new"
										onClick="showArea();addSpecialEventAndParticipant(this)" width="18"
										height="20" title="<spring:message code="REF.UI.SPECIAL_EVENT.IMAGE.ADD" />">
									
									</sec:authorize>
									
									</th>
								</tr>
								<c:choose>
									<c:when test="${not empty specialEventMap}">
										<c:forEach items="${specialEventMap}" var="specialEvent"
											varStatus="status">

											<tr <c:if test="${selectedObj != null && (selectedObj.specialEventsId == specialEvent.key.specialEventsId)}">
															class="highlight"
												</c:if>
												<c:choose>
									            		<c:when test="${status.count % 2 == 1}">class="odd"</c:when>
									            		<c:when test="${status.count % 2 == 0}">class="even"</c:when>
									            		</c:choose>>
												<td>${specialEvent.key.name}</td>
												<td>${specialEvent.key.date}</td>
												<td>${specialEvent.value}</td>
												<td nowrap class="right">
												
												<sec:authorize access="hasRole('save/update Special Events Page')">
												
												<img src="resources/images/ico_edit.gif"
													title="<spring:message code="REF.UI.SPECIAL_EVENT.IMAGE.EDIT"/>"
													onClick="showArea();updateSpecialEventAndParticipant(this,'<c:out value="${specialEvent.key.specialEventsId}" />','<c:out value="${specialEvent.key.name}" />','<c:out value="${strEscapeUtil:escapeJS(specialEvent.key.description)}" />','<c:out value="${specialEvent.key.date}" />','<c:out value="${strEscapeUtil:escapeJS(specialEvent.key.participantCategory.participantCategoryId)}" />',
													'<c:out value="${strEscapeUtil:escapeJS(specialEvent.value)}" />')"
													width="18" height="20" class="icon"> 
												
												</sec:authorize>
												
												<sec:authorize access="hasRole('delete Special Events Page')">
												
												<img src="resources/images/ico_delete.gif"
													onClick="showArea();deleteSpecialEventAndParticipant(this,'<c:out value="${specialEvent.key.specialEventsId}" />')"
													title="<spring:message code="REF.UI.DELETE"/>" width="18" height="20" class="icon">
													
												</sec:authorize>
													
												</td>
											</tr>

										</c:forEach>

									</c:when>
									<c:otherwise>
										<tr>
											<td width="830">
												<h5>
													<spring:message code="REF.UI.PUBLICATION.NO.RESULT" />
												</h5></td>
												<td></td>
												<td></td>
											<td nowrap class="right"></td>
										</tr>
									</c:otherwise>
								</c:choose>
							</table>
						</div>
					</div>
					
					<sec:authorize access="hasRole('save/update Special Events Page')">
					
					<div class="section_full_inside" style='display: ${showEditSection != null ?'block':'none'}'>
						<h3>
						<c:choose>
								<c:when
									test="${(selectedObj != null && (selectedObj.specialEventsId > 0))}">
									<spring:message
										code="REF.UI.SPECIAL_EVENT.IMAGE.EDIT" />
								</c:when>
								<c:otherwise>
									<spring:message code="REF.UI.SPECIAL_EVENT.SUB_FORM.TITLE" />
								</c:otherwise>
							</c:choose>
							
						</h3>
						<div class="box_border">
							<div class="row">
								<div class="float_left">
									<div class="lbl_lock">
										<span class="required_sign">*</span><label><spring:message
												code="REF.UI.SPECIAL_EVENT.NAME" />:</label>
									</div>
									<form:input path="name" id="name" maxlength="45"
										onkeypress="return disableEnterKey(event)" />
								</div>
								<div class="float_right">
									<div class="lbl_lock">
										<span class="required_sign">*</span><label><spring:message
												code="REF.UI.SPECIAL_EVENT.DESCRIPTION" />:</label>
									</div>
									<form:textarea rows="0" cols="20" path="description"
										name="description" id="description"
										onkeypress="return disableEnterKey(event)"  maxlength="400"/>
								</div>

							</div>
							<div class="row">

								<div class="float_left">
									<div class="lbl_lock">
										<span class="required_sign">*</span><label><spring:message
												code="REF.UI.SPECIAL_EVENT.DATE" />:</label>
									</div>
									<form:input path="date" id="eventDate" cssClass="date_field"
										readonly="true" />

									<div class="lbl_lock"></div>
								</div>

								<div class="float_right">
									<div class="lbl_lock">
										<span class="required_sign">*</span><label><spring:message
												code="REF.UI.SPECIAL_EVENT.PARTICIPANT_CATEGORY" />:</label>
									</div>

									<form:select path="participantCategory.participantCategoryId"
										id="selectCategory" onchange="findOptionList(this.value)"
										name="selectedCategory">
										<form:option value="0" id="selectOptionCategory"><spring:message code='application.drop.down' /></form:option>
										<form:options items="${categoryList}" itemLabel="description"
											itemValue="participantCategoryId" />
									</form:select>

								</div>
							</div>
							<div class="row"></div>
							<div class="column_single">

								<div id="add_remove_list">

									<div style="width: 340px; float: left">
										<label><spring:message
												code="REF.UI.SPECIAL_EVENT.PARTICIPANT_LIST" />:</label>
										<div id="selectParticipent">
											<select size="15" id="FromList"></select>
										</div>

									</div>
									<div id="selected_list" class="controller">
										<input type="button" class="button" name="Button" value="&gt;"
											onClick="add()"><br> <br> <input
											type="button" class="button" name="Button" value="&lt;"
											onClick="remove()">
									</div>
									<div style="width: 340px; float: left">
										<span class="required_sign">*</span> <label><spring:message
												code="REF.UI.SPECIAL_EVENT.PARTICIPANT_SELECTED" />:</label>
										<div id="selectedParticipent">
											<select name="ToList" size="15" multiple="multiple"
												id="ToList">
											</select>
										</div>

									</div>

								</div>
								<div class="buttion_bar_type1">
									<input type="button" class="button"
										onClick="showArea();saveSpecialEvent()" value="<spring:message code="REF.UI.SAVE"/>"> <input
										type="button" class="button"
										onClick="showArea();setAddEditPane(this,'Cancel')" value="<spring:message code="REF.UI.CANCEL"/>">
								</div>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
					
					</sec:authorize>
					
				</div>
			</form:form>

		</div>
	</div>
	<h:footer />
</body>
</html>

