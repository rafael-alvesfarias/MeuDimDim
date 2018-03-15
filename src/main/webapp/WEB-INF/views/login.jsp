<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Entrar no sistema</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script src="/resources/script/jquery.inputmask.bundle.js"></script>
</head>
<body>
	<c:url value="/login" var="urlLogin" />
	<c:url value="/registration" var="urlRegistration" />
	<div class="box">
		<c:import url="header.jsp" />
		<div class="main">
		<div class="div-5">
			<div class="modalDialog" title="Entrar">
				<div>
					<h3 class="panelTitle">Entrar</h3>
					<c:url var="salvar" value="/expense"></c:url>
					<form method="POST" action="${urlLogin}">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<table>
							<tr>
								<td><label for="username">Usuário</label></td>
								<td><input type="text" name="username" autofocus="autofocus"/></td>
							</tr>
							<tr>
								<td><label for="password">Senha</label></td>
								<td><input type="password" name="password" /></td>
							</tr>
							<tr>
								<td></td>
							</tr>
						</table>
						<div class="botoes">
							<input type="submit" value="Entrar"	class="botao-direita" />
							<a href="${urlRegistration}"><input type="button" value="Registre-se" class="botao-direita" /></a> 
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	</div>
</body>
</html>