<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>

<%@ taglib uri="/WEB-INF/kicsa.tld" prefix="kicsa" %>

<%@ page errorPage="SystemError.jsp" %>

<% String rootId = application.getAttribute("rootId").toString(); %>

<html:html> 
<head>
<title>		
	<nested:root name="skillForm">
		<nested:nest property="skill">
			<nested:write property="shortName"/>
		</nested:nest>
	</nested:root> - KiCsa - Orange France
</title>
<link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
</head>

<body link="#FF6600" vlink="#FF6600" alink="#000000">

<%@ include file="Header.jsp" %>

<table border="0" cellpadding="2" cellspacing="0" align="center">

  <nested:root name="skillForm"> 
		<nested:nest property="skill"> 
  <tr> 
    <td valign="top"><font class="seize"><nested:write property="shortName"/></font><br>
      <nested:write property="longName"/> </td>
    <td colspan="2"><font class="huit"><nested:write property="comments"/></font></td>
  </tr>
	<tr class="orange"> 
    <td align="center" colspan="2"> 
			Capacités
		</td>
	</tr>
	
  <nested:iterate id="level" property="levels" > 
		<tr> 
			<td align="center"> 
				<bean:define id="levelKey" name="level" property="key"/> 
				<bean:define id="abilities" name="skillForm" property="abilities"/> 
				<% pageContext.setAttribute ("levelAbilities", ((java.util.Map) pageContext.findAttribute ("abilities")).get (pageContext.findAttribute ("levelKey"))); %>
				
				<div align="right"><font class="titre"><nested:write property="name"/></font>&nbsp;</div>
			</td>
			<td><%-- font class="huit">&nbsp;(<%= ((java.util.Collection) pageContext.findAttribute ("levelAbilities")).size() >&nbsp;<bean:message key="description.people"/>)</font --%></td>
		</tr>
		<tr> 
			<td valign="top"> 
				<div align="right"> <font class="huit"><nested:write property="description"/></font> 
				</div>
			</td>
			<td width="0%"> 
				<%-- kicsa:abilities abilities="abilities" abilityId="ability" levelId="level" --%>
				<logic:iterate id="ability" name="levelAbilities">
					<logic:equal name="ability" property="class.name" value="org.orange.kicsa.business.ability.EmployeeAbilityImpl">
						<html:link href="EmployeeDetail" paramId="id" paramName="ability" paramProperty="employeeKey"> 
							<font class="douzeOrange">
								<bean:write name="ability" property="employeeFirstName" /></font><font class="douze"><bean:write name="ability" property="employeeLastName" />
							</font>
						</html:link><br>
					</logic:equal>			
					<logic:equal name="ability" property="class.name" value="org.orange.kicsa.business.ability.EntityAbilityImpl">
						<a href="<%= ((org.orange.kicsa.business.ability.EntityAbility) pageContext.findAttribute ("ability")).getURI() %>">
							<bean:write name="ability" property="name" />
						</a><br>
					</logic:equal>			
				</logic:iterate>
				<%-- /kicsa:abilities --%>
			</td>
		</tr>
  </nested:iterate>
	
	<tr class="orange"> 
    <td align="center" colspan="2"> 
			Relations
		</td>
	</tr>
	<bean:define id="mySkill" name="skillForm" property="skill"/> 
  <tr> 
	  <td><div align="right"><font class="titre">Parents</font></div></td>
    <td valign="top"> 
			<kicsa:relationships skill="mySkill" type="parents" relationshipId="parent">
				<html:link href="SkillTree" paramId="id" paramName="parent" paramProperty="id"> 
					<bean:write name="parent" property="shortName"/>
				</html:link>
			  &nbsp;
			</kicsa:relationships>
    </td>
	<tr>
	<tr>
		<td><div align="right"><font class="titre">Fils</font></div></td>
    <td valign="top"> 
			<kicsa:relationships skill="mySkill" type="childs" relationshipId="child">
				<html:link href="SkillTree" paramId="id" paramName="child" paramProperty="id"> 
					<bean:write name="child" property="shortName"/>
				</html:link>
			</kicsa:relationships>
    </td>
  </tr>
	<tr>
		<td><div align="right"><font class="titre">Utilise</font></div></td>
    <td valign="top"> 
			<kicsa:relationships skill="mySkill" direction="to" type="Utilise" relationshipId="destination">
				<html:link href="SkillTree" paramId="id" paramName="destination" paramProperty="id"> 
					<bean:write name="destination" property="shortName"/>
				</html:link>
			  &nbsp;
			</kicsa:relationships>
    </td>
  </tr>

  <tr> 
    <td align="center" colspan="2"> 
      <table>
        <tr> 
          <td align="center"> 
            <form method="get" action="SkillEdit">
              <input type="hidden" name="method" value="update">
              <input type="hidden" name="skillToEdit" value="<nested:write property="id"/>"> 
              <input type="submit" value="<bean:message key="prompt.modify"/>"> 
            </form>
          </td>
          <nested:notEqual property="id" value="<%= rootId %>"> 
          <td alight="center"> 
            <form method="post" action="SkillEdit" name="removeForm" onSubmit="return confirm ('Etes-vous sûr de vouloir supprimer cette compétence ?');" >
              <input type="hidden" name="method" value="remove">
              <input type="hidden" name="skillToEdit" value="<nested:write property="id"/>"> 
              <input type="submit" value="<bean:message key="prompt.remove"/>"> 
            </form>
          </td>
          </nested:notEqual> </tr>
      </table>
    </td>
  </tr>
  <tr> 
    <td align="center" colspan="4"></td>
  </tr>
  </nested:nest> 
	</nested:root> 
</table>
			<div align="center">			
				<kicsa:activity id="SkillDetail" hrefId="transitionHRef" labelId="transitionLabel">
					 <a href="<%= transitionHRef %>"><%= transitionLabel %></a> 
				</kicsa:activity>
			</div>
	<%@ include file="Footer.jsp" %>
	</body>
</html:html>