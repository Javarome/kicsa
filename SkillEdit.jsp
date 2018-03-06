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
    <bean:message key="heading1.skillCreate"/> 
  </logic:equal> 
  <logic:equal parameter="method" value="update"> 
    <bean:message key="heading1.skillUpdate"/> 
  </logic:equal>
   - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="../OrangeWeb.css">
<script language="Javascript1.1" src="../staticJavascript.jsp"></script>
<html:javascript formName="skillForm" dynamicJavascript="true" staticJavascript="false"/>
</head>

<body link="#FF6600" vlink="#FF6600" alink="#000000">

<%@ include file="Header.jsp" %>

<html:errors/>

<table border="0" cellpadding="2" cellspacing="0" align="center">

  <html:form name="skillForm" type="org.orange.kicsa.presentation.view.SkillForm" action="/SkillEdit" onsubmit="return validateSkillForm(this);"> 
	
		<nested:nest property="skill">
			<tr> 
				<td valign="top" rowspan="3"> 
					<p>
						<font class="seize">   
							<logic:equal parameter="method" value="create"> 
								<bean:message key="heading1.skillCreate"/> 
							</logic:equal> 
							<logic:equal parameter="method" value="update"> 
								<bean:message key="heading1.skillUpdate"/> 
							</logic:equal>
						</font>
					</p>
				</td>
				<td valign="top">&nbsp;</td>
				<td align="right" valign="top" bgcolor="#FFF8F0">
					<bean:message key="prompt.shortName"/>
				</td>
				<td valign="top" bgcolor="#FFF8F0">&nbsp;</td>
				<td align="left" valign="top" bgcolor="#FFF8F0">
					<nested:text property="shortName" size="30" />
				</td>
			</tr>
		
			<tr> 
				<td align="center" valign="top">&nbsp;</td>
				<td bgcolor="#FFF8F0" align="right" valign="top">
					<bean:message key="prompt.longName"/>
				</td>
				<td bgcolor="#FFF8F0" align="center" valign="top">&nbsp;</td>
				<td bgcolor="#FFF8F0" align="left" valign="top">
					<nested:text property="longName" size="30" />
				</td>
			</tr>
			
			<tr> 
				<td valign="top">&nbsp;</td>
				<td bgcolor="#FFF8F0" align="right" valign="top">
					<bean:message key="prompt.comments"/>
				</td>
				<td bgcolor="#FFF8F0" valign="top">&nbsp;</td>
				<td bgcolor="#FFF8F0" align="left" valign="top">
					<nested:textarea property="comments" rows="4" cols="50" />
				</td>
			</tr>
		
			<tr> 
				<td colspan="5"></td>
			</tr>
			
			<tr class="orange"> 
				<td align="center" colspan="5"> 
					<div align="center">
						<bean:message key="heading2.levelsForThisSkill"/>
					</div>
				</td>
			</tr>
			
			<nested:iterate id="level" property="levels"> 
				<tr> 
					<td valign="top" colspan="5" height="10"> </td>
				</tr>
				<tr> 
					<td valign="top" align="right">
						<font class="titre"><nested:write property="name"/></font>
					</td>
					<td width="5" valign="top">
						<img src="../images/fldro.gif" width="13" height="18">
					</td>
					<td align="center" class="normal" valign="top" bgcolor="#FFF8F0"> 
						<div align="right"><bean:message key="prompt.modify"/></div>
					</td>
					<td width="5" valign="top" bgcolor="#FFF8F0">&nbsp;</td>
					<td valign="top" bgcolor="#FFF8F0">
						<font class="huit"><bean:message key="description.description"/></font><br>	
						<nested:textarea property="description" rows="4" cols="50" /> 		
					</td>
				</tr>
			</nested:iterate> 
					
			<tr> 
				<td align="center" colspan="5">&nbsp;</td>
			</tr>
			<tr class="orange"> 
				<td align="center" colspan="5" > 
					<div align="center"><bean:message key="heading2.relationshipsWithOtherSkills"/></div>
				</td>
			</tr>
		</nested:nest> 
			
		<nested:iterate id="relationship" property="relationships" > 
		
			<%-- Relation existante --%>
			
			<nested:notEqual property="destinationName" value=""> 
				<tr> 
					<td valign="top" align="right" class="normal" colspan="5" height="5"></td>
				</tr>
				<tr> 
					<td valign="top" align="right" class="normal" rowspan="2"> 
						<%-- nested:select property="destinationKey.id"> 
							<html:options collection="skillList" property="key" labelProperty="value"/> 
						</nested:select --%> 
					 <font class="titre"><nested:write property="destinationName"/></font>
					</td>
					<td width="5"><img src="../images/fldro.gif" width="13" height="18"></td>
					<td align="center" valign="top" bgcolor="#FFF8F0"> 
						<div align="right"><bean:message key="prompt.modify"/></div>
					</td>
					<td valign="top" bgcolor="#FFF8F0">&nbsp;</td>
					<td valign="top" bgcolor="#FFF8F0"> 
						<nested:select property="type"> 
							<html:options collection="relationshipTypes" property="key" labelProperty="key"/> 
						</nested:select> 
						<bean:message key="description.forThisSkill"/>
					</td>
				</tr>
				<tr> 
					<td width="5">&nbsp;</td>
					<td align="right" class="normal" valign="top" bgcolor="#FFF8F0"> 
						<div align="right"><bean:message key="prompt.remove"/></div>
					</td>
					<td valign="top" bgcolor="#FFF8F0">&nbsp;</td>
					<td valign="top" bgcolor="#FFF8F0"> 
						<nested:checkbox property="deleted"/>
						</td>
				</tr>
			</nested:notEqual> 
				
			<%-- Relation vierge (nouvelle relation) --%>
			
			<nested:equal property="destinationName" value=""> 
				<tr> 
					<td colspan="5" heigth="5">&nbsp;</td>
				</tr>
				<tr> 
					<td align="right" valign="top"> 
						<div align="right"> 
							<nested:select property="destinationKey.id"> 
								<logic:equal parameter="method" value="update">  
										<option value=""><bean:message key="choice.selectSkill"/></option> 
								</logic:equal> 
								<html:options collection="skillList" property="key" labelProperty="value"/> 
							</nested:select> 
						</div>
					</td>
					<td align="center">
						<img src="../images/fldro.gif" width="13" height="18">
					</td>
					<td align="right" bgcolor="#FFF8F0"> 
						<bean:message key="prompt.newRelationship"/>
					</td>
					<td bgcolor="#FFF8F0">&nbsp;</td>
					<td bgcolor="#FFF8F0"> 
						<nested:select property="type"> 
							<%-- La clé comme la valeur sont le libellé --%>
							<logic:equal parameter="method" value="update">  
								<option value=""><bean:message key="choice.selectRelationshipType"/></option> 
							</logic:equal> 
							<html:options collection="relationshipTypes" property="key" labelProperty="key"/> 
						</nested:select> 
						<bean:message key="description.forThisSkill"/>
					</td>
				</tr>
			</nested:equal>
	
		</nested:iterate> 
		 
		<tr> 
			<td align="center" colspan="5">&nbsp;</td>
		</tr>
		<tr class="orange"> 
			<td align="center" colspan="5"> 
				<div align="center"><bean:message key="heading2.ownersForThisSkill"/></div>
			</td>
		</tr>
		
		<nested:iterate id="owner" property="owners" > 
	
			<nested:notEqual property="firstName" value=""> 
				<tr> 
					<td valign="top" align="right" class="normal" colspan="5" height="10"></td>
				</tr>
				<tr> 
					<td valign="top" align="right" ><font class="douzeOrange"><nested:write property="firstName"/></font><font class="douze">
					<nested:write property="lastName"/></font><br>
						et ses sup&eacute;rieurs</td>
					<td width="5" valign="top"><img src="../images/fldro.gif" width="13" height="18"></td>
					<td align="right" valign="top" bgcolor="#FFF8F0"> 
						<div align="right"><bean:message key="prompt.remove"/></div>
					</td>
					<td valign="top" bgcolor="#FFF8F0">&nbsp;</td>
					<td valign="top" bgcolor="#FFF8F0">
						<nested:checkbox property="deleted" />
						</td>
				</tr>
			</nested:notEqual> 
	
			<nested:equal property="firstName" value=""> 
				<tr> 
					<td valign="top" height="5"> </td>
					<td width="5" valign="top"></td>
					<td width="5" class="normal" valign="top"></td>
					<td valign="top"></td>
					<td valign="top" height="10"></td>
				</tr>
				<tr> 
					<td align="right" valign="top"> 
						<div align="right">
							<html:select name="owner" property="id"> 
								<html:options collection="employeeList" property="key" labelProperty="value"/> 
							</html:select>
						</div>
					</td>
					<td align="center"><img src="../images/fldro.gif" width="13" height="18"></td>
					<td align="right" bgcolor="#FFF8F0"><bean:message key="prompt.newOwner"/>
					</td>
					<td bgcolor="#FFF8F0">&nbsp;</td>
					<td bgcolor="#FFF8F0"><bean:message key="description.forThisSkill"/></td>
				</tr>
			</nested:equal> 
	
		</nested:iterate> 
	
		<input type="hidden" name="method" value="<%= request.getParameter ("method") %>">	
		<tr> 
			<td align="center" colspan="5"> 
				<br>
				<logic:equal parameter="method" value="create"> 
					<input type="submit" name="create" value="<bean:message key="button.create"/>"> 
				</logic:equal> 
				<logic:equal parameter="method" value="update"> 
					<input type="submit" name="update" value="<bean:message key="button.update"/>">
				</logic:equal> 
			</td>
		</tr>
			
	</html:form> 
</table>
<%@ include file="Footer.jsp" %>
</body>
</html:html>
