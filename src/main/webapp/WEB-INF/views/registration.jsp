<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Registre-se no sistema</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script src="/resources/script/jquery.inputmask.bundle.js"></script>
</head>
<body>
	<c:url value="/registration" var="urlRegistration" />
	<div class="box">
		<c:import url="header.jsp" />
		<div class="main">
		<div class="div-5">
			<div class="modalDialog" title="Registre-se">
				<div>
					<h3 class="panelTitle">Cadastro de usuário</h3>
					<form:form method="POST" action="${urlRegistration}" modelAttribute="user">
						<table>
							<tr>
								<td><label for="username">Usuário</label></td>
								<td>
									<form:input type="text" path="username" autofocus="autofocus"/>
									<form:errors path="username"></form:errors>
								</td>
							</tr>
							<tr>
								<td><label for="password">Senha</label></td>
								<td>
									<form:input type="password" path="password" />
									<form:errors path="password"></form:errors>
								</td>
							</tr>
							<tr>
								<td><label for="passwordConfirm">Confirmação da senha</label></td>
								<td>
									<form:input type="password" path="passwordConfirm" />
									<form:errors path="passwordConfirm"></form:errors>
								</td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</table>
						<div class="botoes">
							<input type="submit" value="Enviar"	class="botao-direita" />
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>