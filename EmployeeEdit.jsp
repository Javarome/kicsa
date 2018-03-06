<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ page errorPage="SystemError.jsp" %>
<html:html>
<head>
	<title> 
	  <logic:equal parameter="method" value="create"> 
		  <bean:message key="menu.employeeCreate"/> 
	  </logic:equal> 
	  <logic:equal parameter="method" value="update"> 
		  <bean:message key="menu.employeeUpdate"/> 
    </logic:equal> 
	   - KiCsa - Orange France
	</title>

	<link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
	<script language="Javascript1.1" src="../staticJavascript.jsp"></script>
	<html:javascript formName="employeeForm" dynamicJavascript="true" staticJavascript="false"/>
  </head>

	<body link="#FF6600" vlink="#FF6600" alink="#000000">
	  <%@ include file="Header.jsp" %>
	  <html:errors/>  
	  <table border="0" cellpadding="2" cellspacing="0" align="center">   
		  <html:form name="employeeForm" type="org.orange.kicsa.presentation.view.EmployeeForm" action="/EmployeeEdit" onsubmit="return validateEmployeeForm(this);">
			<input type="hidden" name="method" value="<%= request.getParameter ("method") %>">
			<nested:nest property="employee"> 
				<tr> 
					<td align="left" valign="top" rowspan="5" width="251"> 
						<font class="seize">
						<logic:equal parameter="method" value="create"> 
							<bean:message key="menu.employeeCreate"/>
						</logic:equal>
 						<logic:equal parameter="method" value="update"> 
							<bean:message key="menu.employeeUpdate"/>
						</logic:equal></font>
					</td>
					<td valign="top">&nbsp;</td>
					<td align="right" valign="top" bgcolor="#FFF8F0">	
						<bean:message key="prompt.lastName"/></td>
					<td valign="top" bgcolor="#FFF8F0">&nbsp;</td>
					<td align="left" valign="top" bgcolor="#FFF8F0" > 
						<nested:text property="lastName" size="40"/>
					</td>
				</tr>
				<tr> 
					<td align="center" valign="top" width="56">&nbsp;</td>
					<td bgcolor="#FFF8F0" align="right" valign="top" >
					<span class="normal"><bean:message key="prompt.firstName"/> </span></td>
					<td bgcolor="#FFF8F0" align="center" valign="top" >&nbsp;</td>
					<td bgcolor="#FFF8F0" align="left" valign="top" >
						<nested:text property="firstName" size="40"/>
						</td>
				</tr>
				<tr> 
					<td align="center" valign="top" width="56">&nbsp;</td>
					<td bgcolor="#FFF8F0" align="right" valign="top" >
					<span class="normal"><bean:message key="prompt.URI"/> </span></td>
					<td bgcolor="#FFF8F0" align="center" valign="top" >&nbsp;</td>
					<td bgcolor="#FFF8F0" align="left" valign="top" >
						<nested:text property="URI" size="40"/>
						</td>
				</tr>
				<tr> 
					<td align="center" width="56">&nbsp;</td>
					<td class="normal" align="right" bgcolor="#FFF8F0"><bean:message key="prompt.entityAbbreviation"/> </td>
					<td align="center" bgcolor="#FFF8F0">&nbsp;</td>
					<td bgcolor="#FFF8F0" > 
						<nested:text property="entityName" size="40" />
					</td>
				</tr>
				<tr> 
					<td align="center" valign="top" width="56">&nbsp;</td>
					<td bgcolor="#FFF8F0" align="right" valign="top">
					<span class="normal"><bean:message key="prompt.entityURI"/> </span></td>
					<td bgcolor="#FFF8F0" align="center" valign="top">&nbsp;</td>
					<td bgcolor="#FFF8F0" align="left" valign="top">
						<nested:text property="entityURI" size="40" />
						</td>
				</tr>
			</nested:nest> 
  <tr> 
    <td colspan="5" align="center">&nbsp;</td>
  </tr>

  <tr class="orange"> 
    <td colspan="5" align="center"><bean:message key="heading2.abilities"/>
    </td>
  </tr>
	
  <nested:iterate id="ability" property="abilities" > 
	
	 <%-- Normal ability --%>
	 
   <nested:notEqual property="skillName" value=""> 
			<tr> 
				<td valign="top" align="right" class="normal" colspan="5" height="10"></td>
			</tr>
			<tr> 
				<td valign="top" align="right">
					<font class="titre"><nested:write property="skillName"/></font>
				</td>
				<td valign="top"><img src="../images/fldro.gif" width="13" height="18"></td>
				<td align="center" class="normal" valign="top" bgcolor="#FFF8F0" > 
					<div align="right"><bean:message key="prompt.modify"/></div>
				</td>
				<td valign="top" bgcolor="#FFF8F0">&nbsp;</td>
				<td valign="top" bgcolor="#FFF8F0"  nowrap>
				<bean:message key="description.skillLevel"/>&nbsp;<nested:select property="level"> 
				<html:options collection="skillLevels" property="key" labelProperty="value.name"/> 
					</nested:select>
					<br>
					<bean:message key="description.ownerForThisSkill"/>&nbsp;<input type="checkbox" name="checkbox3">
					</td>
			</tr>
			<tr> 
				<td valign="top" align="right" >&nbsp;</td>
				<td  valign="top">&nbsp;</td>
				<td align="center" class="normal" valign="top" bgcolor="#FFF8F0" > 
					<div align="right"><bean:message key="prompt.remove"/></div>
				</td>
				<td  valign="top" bgcolor="#FFF8F0">&nbsp;</td>
				<td valign="top" bgcolor="#FFF8F0">
					<nested:checkbox property="deleted"/></td>
			</tr>
		</nested:notEqual> 
	
  <%-- Blank (new) ability --%>
	
  <nested:equal property="skillName" value=""> 
  <tr> 
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr> 
    <td align="right" valign="top" width="251"> 
      <div align="right"> 
    	  <nested:select property="skillKey.id"> 
					<logic:equal parameter="method" value="update">  
							<option value=""><bean:message key="choice.selectSkill"/></option> 
					</logic:equal> 
					<html:options collection="skillList" property="key" labelProperty="value"/> 
				</nested:select>
        </div>
				</td>
				<td align="center" width="56"><img src="../images/fldro.gif" width="13" height="18"></td>
				<td align="right" bgcolor="#FFF8F0" width="150"><bean:message key="prompt.newAbility"/></td>
				<td bgcolor="#FFF8F0" width="54">&nbsp;</td>
				<td bgcolor="#FFF8F0" width="216" nowrap> <span class="info"><bean:message key="description.skillLevel"/></span> 
				 <nested:select property="level"> 
			  <logic:equal parameter="method" value="update">  
  			    <option value=""><bean:message key="choice.selectSkillLevel"/></option> 
			  </logic:equal> 
			  <html:options collection="skillLevels" property="key" labelProperty="value.name"/> 
      </nested:select> 
      <span class="info"><br><bean:message key="description.ownerForThisSkill"/><input type="checkbox" name="checkbox32" >
      </span></td>
  </tr>
  </nested:equal> 
  </nested:iterate> 
  <tr> 
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr> 
    <td align="center" colspan="5"> 
	<logic:equal parameter="method" value="create"> 
      <input type="submit" value="<bean:message key="button.create"/>">
	</logic:equal>
	<logic:equal parameter="method" value="update"> 
      <input type="submit" value="<bean:message key="button.update"/>">
	</logic:equal>
    </td>
  </tr>
  </html:form> 
</table>

<%@ include file="Footer.jsp" %>
</body>
</html:html>
