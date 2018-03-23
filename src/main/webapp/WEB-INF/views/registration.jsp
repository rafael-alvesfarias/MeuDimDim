<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Registre-se no sistema</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/login.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script src="/resources/script/jquery.inputmask.bundle.js"></script>
</head>
<body>
	<c:url value="/registration" var="urlRegistration" />
	<div class="loginPage">
		<div class="form" title="Registre-se">
			<div>
				<h3 class="panelTitle">Dados do usuário</h3>
				<form:form method="POST" action="${urlRegistration}" modelAttribute="user">
					<form:input type="text" path="username" placeholder="usuário" autofocus="autofocus"/>
					<form:errors path="username"></form:errors>
					
					<form:input type="password" path="password" placeholder="senha"/>
					<form:errors path="password"></form:errors>
					
					<form:input type="password" path="passwordConfirm" placeholder="confirmação de senha"/>
					<form:errors path="passwordConfirm"></form:errors>
					
					<input type="submit" value="Enviar"	class="botao-direita" />
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>