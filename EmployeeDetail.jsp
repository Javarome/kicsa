<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/kicsa.tld" prefix="kicsa" %>
<%@ page errorPage="SystemError.jsp" %>
<html:html>
	<nested:root name="employeeForm">
		<nested:nest property="employee">
	<head><title><nested:write property="firstName" /> <nested:write property="lastName" /> - KiCsa - Orange France</title>   
  <link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
</head>	
  <body link="#FF6600" vlink="#FF6600" alink="#000000">
  <%@ include file="Header.jsp" %>    
	<html:errors/>
		<table align="center"0" cellpadding="4" cellspacing="0">
			<tbody>  
				<tr> 
          <td colspan="2" align="center" valign="middle"> 
            <p>
							<a href="<nested:write property="URI"/>">
								<font class="seizeOrange"><nested:write property="firstName"/></font><font class="seize"><nested:write property="lastName"/></font>
							</a>
						</p>
            <p class="lien_orange">
							<a href="<nested:write property="entityURI"/>">
								<nested:write property="entityName" />
							</a>
						</p>
            <p class="lien_orange">&nbsp;</p>
          </td>
        </tr>
        <tr class="orange"> 
          <td colspan="2" align="center"><bean:message key="heading2.abilities"/>
          </td>
        </tr>
		</nested:nest>
		<nested:iterate id="ability" property="abilities">
			<nested:notEqual property="skillKey" value="0">
			<nested:notEqual property="deleted" value="true">
        <tr>
          <td>						
						<nested:link href="SkillDetail" paramId="detailledSkill" paramProperty="skillKey"><nested:write property="skillName" /></nested:link>
					</td>
          <td><nested:write property="level" /></td>
        </tr>
			</nested:notEqual>
			</nested:notEqual>
		</nested:iterate>
		<nested:nest property="employee">
				<tr> 
				<td align="center" colspan="5"> 
			<table>
			<tr>
			<td align="center">
					<form method="get" action="EmployeeEdit" >
						<input type="hidden" name="method" value="update">
						<input type="hidden" name="employeeToEdit" value="<nested:write property="id"/>"> 
						<input type="submit" name="updateStart" value="<bean:message key="prompt.modify"/>"> 
					</form>
				</td>
				<td alight="center">
					<form method="post" action="EmployeeEdit" name="removeForm" onSubmit="return confirm ('Etes-vous sûr de vouloir supprimer cette ressource ?');" >
						<input type="hidden" name="method" value="remove">
						<input type="hidden" name="employeeToEdit" value="<nested:write property="id"/>"> 
						<input type="submit" name="removeStart" value="<bean:message key="prompt.remove"/>"> 
					</form>
				</td>
			 </tr>
			 </table>
				</td>
			</tr>
			<tr> 
				<td align="center" colspan="5"><span class="info">Fiche mise à jour le 20/08/2002</span><i> 
					</i></td>
			</tr>        
			</tbody> 
      </table>
		</nested:nest>
	</nested:root>
			<div align="center">			
				<kicsa:activity id="EmployeeDetail" hrefId="transitionHRef" labelId="transitionLabel">
					 <a href="<%= transitionHRef %>"><%= transitionLabel %></a> 
				</kicsa:activity>
			</div>
<%@ include file="Footer.jsp" %>
</body>
</html:html>
