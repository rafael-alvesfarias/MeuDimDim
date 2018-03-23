<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:url value="/home" var="urlHome" htmlEscape="true"></spring:url>
<spring:url value="/incomes" var="urlIncomes" htmlEscape="true"></spring:url>
<spring:url value="/expenses" var="urlExpenses" htmlEscape="true"></spring:url>
<spring:url value="/investments" var="urlInvestments" htmlEscape="true"></spring:url>
<spring:url value="/accounts" var="urlAccounts" htmlEscape="true"></spring:url>
<spring:url value="/logout" var="urlLogout" htmlEscape="true"></spring:url>
<script type="text/javascript">
	$(document).ready(function() {
		$(".loginControl").click(function() {
			var cellMenu = $(this).next(".loginMenu");
			var hidden = cellMenu.is(":hidden");
			$(".loginMenu").each(function() {
				$(this).hide();
			});
			if (hidden) {
				cellMenu.show();
			} else {
				cellMenu.hide();
			}
		});
	});
</script>
<div class="logo">
	<a href="${urlHome}"><img src="<c:url value='/resources/imagens/logo.png'/>"/></a>
	<h1>meu<span>dimdim</span></h1>
	<h2>rumo à independência financeira</h2>
</div>
<div class="login">
	<div class="loginInfo">
		<a href=""><img src="<c:url value='/resources/imagens/login.png'/>"/></a>
		<div>
			<sec:authorize access="isAuthenticated()">
				Olá, <span><sec:authentication property="principal.username"/></span>
			</sec:authorize>
		</div>
	</div>
	<div class="loginControl">
		<img src="<c:url value='/resources/imagens/down-arrow.png'/>" />
	</div>
	<div class="loginMenu">
		<ul>
			<li><a href="<c:url value="/logout" />">Sair</a></li>
		</ul>
	</div>
</div>
<div class="navMenu">
	<ul>
		<li><a href="${urlHome}">Home</a></li>
		<li><a href="${urlIncomes}">Receitas</a></li>
		<li><a href="${urlExpenses}">Despesas</a></li>
		<li><a href="${urlInvestments}">Investimentos</a></li>
		<li><a href="${urlAccounts}">Contas</a></li>
	</ul>
</div>
<div class="clearfix"></div>