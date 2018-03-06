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
    <bean:message key="menu.skillSearch"/> 
   - KiCsa - Orange France</title>
<link rel="STYLESHEET" type="text/css" href="../dsi.css">
<script language="Javascript1.1" src="../staticJavascript.jsp"></script>
<html:javascript formName="skillSearchForm" dynamicJavascript="true" staticJavascript="false"/>
</head>

<body>
<%@ include file="Header.jsp" %>
<html:errors/>
<table border="0" cellpadding="2" cellspacing="0" align="center">
  <html:form name="skillSearchForm" type="org.orange.kicsa.presentation.view.SkillSearchForm" action="/SkillSearch" onsubmit="return validateSkillSearchForm(this);"> 
  <tr> 
  <nested:nest property="skill">
          <td align="left" valign="top" rowspan="3"> 
            <p><span class="titre_noir_2">   
    <bean:message key="menu.skillSearch"/> 
		/span></p>
          </td>
          <td valign="top">&nbsp;</td>
          <td align="right" valign="top" bgcolor="#FFF8F0"><span class="normal"><bean:message key="prompt.shortName"/></span><font class="nomorange"> 
            </font></td>
          <td valign="top" bgcolor="#FFF8F0"><select name="select">
        <option value="equal">Egal &agrave;</option>
        <option value="startsWith">Commence par</option>
        <option value="endsWith">Finit par</option>
        <option value="contains">Contient</option>
      </select>&nbsp;</td>
          <td align="left" valign="top" bgcolor="#FFF8F0"><font class="nomorange"> 
            <nested:text property="shortName" size="30" /> </font></td>
        </tr>
        <tr> 
          <td align="center" valign="top">&nbsp;</td>
          <td bgcolor="#FFF8F0" align="right" valign="top"><span class="normal"><bean:message key="prompt.longName"/></span></td>
          <td bgcolor="#FFF8F0" >
      
    </td>
          <td bgcolor="#FFF8F0" align="left" valign="top"><font class="nomorange"> 
            <nested:text property="longName" size="30" /> </font></td>
        </tr>
        <tr> 
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
          <td align="center">&nbsp;</td>
        </tr>
        <tr> 
          <td align="center" bgcolor="#ee6d04" colspan="5"> 
            <div align="center"><font face="Arial" size="3" color="#ffffff"><b><bean:message key="heading2.levelsForThisSkill"/></b></font></div>
          </td>
        </tr>
        <nested:iterate id="level" property="levels"> 
        <tr> 
          <td valign="top" colspan="5" height="10"> </td>
        </tr>
        <tr> 
          <td valign="top" align="right"><b class="entiteorange"> <bean:write name="level" property="value.name"/> 
            </b></td>
          <td width="5" valign="top"><img src="../images/fldro.gif" width="13" height="18"></td>
          <td align="center" class="normal" valign="top" bgcolor="#FFF8F0"> 
            <div align="right"><bean:message key="prompt.modify"/></div>
          </td>
          <td width="5" valign="top" bgcolor="#FFF8F0">&nbsp;</td>
          <td width="" valign="top" bgcolor="#FFF8F0"><span class="info"><bean:message key="description.description"/></span><br>
		  <%-- nested:nest property="value">
            <nested:textarea property="description" rows="4" cols="50" /> 
	      </nested:nest --%>	
            <html:textarea name="level" property="value.description" rows="4" cols="50" /> 		
          </td>
        </tr>
        </nested:iterate> 
        <tr> 
          <td align="center" colspan="5">&nbsp;</td>
        </tr>
        <tr> 
          <td align="center" colspan="5" bgcolor="#ee6d04"> 
            <div align="center"><font face="Arial" size="3" color="#ffffff"><b><bean:message key="heading2.relationshipsWithOtherSkills"/></b></font></div>
          </td>
        </tr>
        </nested:nest> 
		
		<nested:iterate id="relationship" property="relationships" > 
				
			<%-- Relation --%>
        <nested:equal property="destinationName" value=""> 
        <tr> 
          <td colspan="5" heigth="5">&nbsp;</td>
        </tr>
        <tr> 
          <td align="right" valign="top"> 
            <div align="right"><b class="entiteorange"> 
              <nested:select property="destinationKey.id"> 
			  <logic:equal parameter="method" value="update">  
  			    <option value=""><bean:message key="choice.selectSkill"/></option> 
			  </logic:equal> 
              <html:options collection="skillList" property="key" labelProperty="value"/> 
              </nested:select> 
			  </b>
			  </div>
          </td>
          <td align="center"><img src="../images/fldro.gif" width="13" height="18"></td>
          <td align="right" class="normal" bgcolor="#FFF8F0"> <font face="Arial" size="2"><bean:message key="prompt.newRelationship"/></font> 
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
 	      <span class="info"><bean:message key="description.forThisSkill"/></span></td>
        </tr>
        </nested:equal>
	 </nested:iterate> 
	 
        <tr> 
          <td align="center" colspan="5">&nbsp;</td>
        </tr>
        <tr> 
          <td align="center" colspan="5" bgcolor="#ee6d04"> 
            <div align="center"><font face="Arial" size="3" color="#ffffff"><b><bean:message key="heading2.ownersForThisSkill"/></b></font></div>
          </td>
        </tr>
        <nested:iterate id="owner" property="owners" > 
				<nested:equal property="firstName" value=""> 
        <tr> 
          <td valign="top" height="5"> </td>
          <td width="5" valign="top"></td>
          <td width="5" class="normal" valign="top"></td>
          <td width="" valign="top"></td>
          <td width="" valign="top" height="10"></td>
        </tr>
        <tr> 
          <td align="right" valign="top"> 
            <div align="right"><font face="Arial" size="2"><b class="entiteorange"> 
              <html:select name="owner" property="id"> <html:options collection="employeeList" property="key" labelProperty="value"/> 
              </html:select> </b></font></div>
          </td>
          <td align="center"><img src="../images/fldro.gif" width="13" height="18"></td>
          <td align="right" class="normal" bgcolor="#FFF8F0"> <font face="Arial" size="2"><bean:message key="prompt.newOwner"/></font> 
          </td>
          <td bgcolor="#FFF8F0">&nbsp;</td>
          <td bgcolor="#FFF8F0"> <span class="info"><bean:message key="description.forThisSkill"/></span></td>
        </tr>
        </nested:equal> 
				</nested:iterate> 
        <tr> 
          <td align="center" colspan="5"> <br>
              <input type="submit" name="search" value="<bean:message key="button.search"/>"> 
 					</td>
        </tr>
        </html:form> 
      </table>
			<div align="center">			
				<kicsa:activity id="EmployeeEdit" hrefId="transitionHRef" labelId="transitionLabel">
					 <a href="<%= transitionHRef %>"><%= transitionLabel %></a> 
				</kicsa:activity>
			</div>
<%@ include file="Footer.jsp" %>
</body>
</html:html>
