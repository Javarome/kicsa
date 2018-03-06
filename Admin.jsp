<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ page errorPage="SystemError.jsp" %>
<html:html>
  
<head>
<title>Administration - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
<script language="Javascript1.1" src="../staticJavascript.jsp"></script>
<html:javascript formName="adminForm" dynamicJavascript="true" staticJavascript="false"/>
</head>

<body>
<%@ include file="Header.jsp" %>

<html:messages id="messageId" message="true"> 
	<bean:write name="messageId"/>
</html:messages>

<html:errors/> 

<table border="0" cellpadding="2" cellspacing="0" align="center">
  <html:form action="Admin" name="adminForm" type="org.orange.kicsa.presentation.view.AdminForm" onsubmit="return validateAdminForm(this);"> 
  <tr> 
    <td colspan="5" valign="middle"> 
      <div align="left"> 
        <p><font class="seize"><bean:message key="heading1.administration"/></font></p>
      </div>
    </td>
  </tr>
  <tr class="orange"> 
    <td align="center" colspan="5"><bean:message key="heading2.skillRelationships"/></td>
  </tr>
  <nested:iterate id="relationshipType" property="relationshipTypes"> 
  <tr height="5"></tr>
  <nested:notEqual property="name" value=""> 
  <tr> 
    <td ><div align="right"><font class="titre"><nested:write property="name"/></font></div></td>
    <td ><img src="../images/fldro.gif" width="13" height="18"></td>
    <td valign="top" bgcolor="#FFF8F0"> 
     Est parent
    </td>
    <td bgcolor="#FFF8F0">&nbsp;</td>
    <td bgcolor="#FFF8F0"> <nested:checkbox property="parent"/> </td>
  </tr>
  <tr> 
    <td >&nbsp;</td>
    <td >&nbsp;</td>
    <td bgcolor="#FFF8F0"> 
      <div align="right"><bean:message key="prompt.remove"/></div>
    </td>
    <td  bgcolor="#FFF8F0">&nbsp;</td>
    <td bgcolor="#FFF8F0"> <nested:checkbox property="deleted"/> 
    </td>
  </tr>
  </nested:notEqual>
	 <nested:equal property="name" value=""> 
  <tr> 
    <td ><bean:message key="prompt.newRelationshipType"/></td>
    <td  ><img src="../images/fldro.gif" width="13" height="18"></td>
    <td bgcolor="#FFF8F0"> 
      Nom
    </td>
    <td bgcolor="#FFF8F0">&nbsp;</td>
    <td bgcolor="#FFF8F0"><nested:text property="name" size="10"/>
    </td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td bgcolor="#FFF8F0"> 
      Est parent
    </td>
    <td bgcolor="#FFF8F0">&nbsp;</td>
    <td bgcolor="#FFF8F0"> 
      <input type="checkbox" name="checkbox222" value="checkbox">
    </td>
  </tr>
  </nested:equal>
	 </nested:iterate> 
  <tr> 
    <td colspan="5">&nbsp;</td>
  </tr>
  <tr> 
    <td class="orange" colspan="5"> 
      <div align="center"> Compétence racine </div>
    </td>
  </tr>
  <nested:nest property="rootSkill"> 
  <tr> 
    <td colspan="5" > 
      <div align="center"> <nested:select property="id"> 
				<html:options collection="skillList" property="key" labelProperty="value"/> 
        </nested:select> 
        <%-- html:link href="SkillEdit?method=update" paramId="skillToEdit" paramProperty="item"><bean:message key="prompt.modify"/></html:link --%>
      </div>
    </td>
  </tr>
  </nested:nest> 
  <tr height="10"></tr>
  <tr> 
    <td align="center" colspan="5"> 
      <%-- input type="submit" name="update" value="<bean:message key="button.update"/>" --%> 
      <html:submit>
				<bean:message key="button.update"/>
			</html:submit> 
		</td>
  </tr>
  </html:form> 
</table>
</body>
</html:html>
