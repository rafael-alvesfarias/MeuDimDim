<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:url value="/home" var="urlHome" htmlEscape="true"></spring:url>
<spring:url value="/incomes/annual" var="urlIncomes" htmlEscape="true"></spring:url>
<spring:url value="/expenses/annual" var="urlExpenses" htmlEscape="true"></spring:url>
<spring:url value="/newInvestment" var="urlInvestments" htmlEscape="true"></spring:url>
<div class="cabecalho">
	<a href="${urlHome}"><img src="<c:url value='/resources/imagens/logo.png'/>"/></a>
	<h1>meu<span>dimdim</span></h1>
	<h2>rumo à independência financeira</h2>
</div>
<div class="topMenu">
	<ul>
		<li><a href="${urlHome}">Home</a></li>
		<li><a href="${urlIncomes}">Receitas</a></li>
		<li><a href="${urlExpenses}">Despesas</a></li>
		<li><a href="${urlInvestments}">Investimentos</a></li>
	</ul>
</div>