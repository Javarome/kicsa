<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-nested.tld" prefix="nested" %>
<%@ taglib uri="/WEB-INF/kicsa.tld" prefix="kicsa" %>
<%@ page errorPage="SystemError.jsp" %>
<html:html>

<head>
<% String rootId = application.getAttribute("rootId").toString(); %>
<title>Recherche arborescente - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
</head>

<body link="#FF6600" vlink="#FF6600" alink="#000000" >
	<%@ include file="Header.jsp" %>
	<html:errors />
	<table border="0">
		<tbody>
		<tr> 
			<td width="10"></td>
		  <td valign="top" colspan="3">
				<logic:iterate id="iparent" name="parents">		 
					<html:link href="SkillTree" paramName="iparent" paramId="id" paramProperty="id" styleClass="lien_orange">
						<img border="0" src="../images/FlecheHaut.jpg" alt="Remonter">
					</html:link>&nbsp; 
					<html:link href="SkillTree" paramName="iparent" paramId="id" paramProperty="id" styleClass="lien_orange">
						<bean:write name="iparent" property="shortName"/>
					</html:link>&nbsp;&nbsp;
				</logic:iterate>
		  </td>
		</tr>
		<tr> 
			<td valign="top">&nbsp;</td>
			<td></td>
			<td valign="top"> 
				<table border="0">
					<tbody>
					<tr> 
						<td  colspan="2"> &nbsp;
						<font class="seize">
							<html:link href="SkillDetail" paramId="detailledSkill" paramName="parent" paramProperty="id">
								<bean:write name="parent" property="shortName"/>
							</html:link>
						</font>
						</td>
					</tr>
					<tr align="center"> 
						<td></td>
						<td hcolspan="2"> 
							<table border="0" width="100%" cellspacing="1" cellpadding="0">
								<tbody> 
								
									<logic:iterate id="skill" name="childs" type="org.orange.kicsa.business.skill.Skill"> 
									<tr> 
										<td valign="top" width="20">
											<html:link href="SkillTree" paramName="skill" paramId="id" paramProperty="id">
												<img src="../images/fldro.gif" width="13" height="18" border="0" alt="D&eacute;ployer">
											</html:link>
										</td>
										<td><html:link href="SkillTree" paramName="skill" paramId="id" paramProperty="id" styleClass="lien_orange"><bean:write name="skill" property="shortName"/></html:link> 
									</td>
									</tr>
									<tr> 
										<td> </td>
										<td valign="top" width="500" class="info">
											<bean:write name="skill" property="longName"/>
										</td>
									</tr>
									</logic:iterate> 
									
								</tbody> 
							</table>
						</td>
					</tr>
					<tr align="center"> 
						<td width="5%"></td>
						<td height="26" colspan="2">&nbsp; </td>
					</tr>
					</tbody>
				</table>
			</td>
			</tr>
        </tbody>
      </table>
			<div align="center">			
				<kicsa:activity id="SkillTree" hrefId="transitionHRef" labelId="transitionLabel">
					 <a href="<%= transitionHRef %>"><%= transitionLabel %></a> 
				</kicsa:activity>
			</div>
<%@ include file="Footer.jsp" %>
</body>
</html:html>
