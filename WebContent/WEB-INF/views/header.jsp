<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<spring:url value="/summary/annual" var="urlSummary" htmlEscape="true"></spring:url>
<spring:url value="/incomes/annual" var="urlIncomes" htmlEscape="true"></spring:url>
<spring:url value="/despesasAnuais" var="urlExpenses" htmlEscape="true"></spring:url>
<spring:url value="/savings/annual" var="urlSavings" htmlEscape="true"></spring:url>
<div class="cabecalho">
	<img src="<c:url value='/recursos/imagens/logo.png'/>"/>
	<h1>meu<span>dimdim</span></h1>
	<h2>rumo à independência financeira</h2>
</div>
<div class="topMenu">
	<ul>
		<li><a href="${urlSummary}">Resumo</a></li>
		<li><a href="${urlIncomes}">Receitas</a></li>
		<li><a href="${urlExpenses}">Despesas</a></li>
		<li><a href="${urlSavings}">Investimentos</a></li>
	</ul>
</div>