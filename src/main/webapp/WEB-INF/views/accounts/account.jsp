<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>Despesa</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script src="/resources/script/jquery.inputmask.bundle.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".inputMoney").inputmask("currency", {groupSeparator: ".", prefix: "R$ ", radixPoint: ","});
			$(".inputDecimal").inputmask("numeric", {
				radixPoint: ",",
				groupSeparator: ".",
	            placeholder: "0",
	            autoGroup: true,
	            digits: 2,
	            digitsOptional: false,
	            clearMaskOnLostFocus: false
	        });
			$(".inputDate").inputmask("99/99/9999");
			$("#btnUpdateBalance").click(function() {
				$("#balance").prop("disabled", false);
			});
		});
	</script>
</head>
<body>
	<c:url value="/accounts" var="urlAccounts" />
	<c:url var="salvar" value="/account"/>
	<c:url var="urlDeposit" value="/accountDeposit"/>
	<c:url var="urlWithdraw" value="/accountWithdraw"/>
	<div class="header">
		<c:import url="../header.jsp" />
	</div>
	<div class="main">
		<h2 class="titulo">Contas</h2>
		<div class="div-5">
			<div class="panel" title="Conta">
				<c:choose>
					<c:when test="${account.id != null}">
						<h3 class="panelTitle">Gerenciar Conta</h3>
					</c:when>
					<c:otherwise>
						<h3 class="panelTitle">Nova Conta</h3>
					</c:otherwise>
				</c:choose>
				<form:form action="${salvar}" method="POST" modelAttribute="account" id="formAccount">
					<form:errors path="institutionName" cssClass="error" />
					<form:errors path="number" cssClass="error" />
					<form:errors path="balance" cssClass="error" />
					<form:hidden path="id" id="idDespesa" />
					<table>
						<tr>
							<td><label for="institutionName">Instituição financeira</label></td>
							<td><form:input  id="institutionName" path="institutionName" size="40" /></td>
						</tr>
						<tr>
							<td><label for="number">Número da conta</label></td>
							<td><form:input id="number" path="number" size="12" /></td>
						</tr>
						<tr>
							<td><label for="type">Tipo de conta</label></td>
							<td>
								<form:select path="type" id="type">
									<form:options items="${account.types}" />
								</form:select>
							</td>
						</tr>
						<c:choose>
							<c:when test="${account.id == null}">
								<tr>
									<td><label for="value">Saldo inicial</label></td>
									<td><form:input cssClass="inputMoney" id="startBalance" path="startBalance" size="12" /></td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td><label for="value">Saldo</label></td>
									<td><form:input cssClass="inputMoney" id="balance" path="balance" size="12" disabled="true"/></td>
								</tr>
							</c:otherwise>
						</c:choose>
					</table>
					<div class="btn-group line-separator-top"> 
						<input type="button" id="btnUpdateBalance" value="Atualizar saldo" class="btn" />
					</div>
					<div class="btn-group"> 
						<input type="submit" value="Salvar"	class="btn-submit" />
						<a href="${urlAccounts}"><input type="button" value="Cancelar" class="btn-cancel" /></a> 
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="footer">
	</div>
</body>
</html>