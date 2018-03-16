<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
	<meta charset="UTF-8">
	<title>Cadastro de Receita</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script src="/resources/script/jquery.inputmask.bundle.js"></script>
	<script type="text/javascript">
		function clearForm() {
			$("#formIncome input[type=text]").each(function(a, b) {
				$(b).val("");
			});
			$("#formIncome input[type=hidden]").each(function(a, b) {
				$(b).val("");
			});
			$("#formIncome select").each(function(a, b) {
				$(b).children("option").eq(0).prop("selected", true);
			});
			$("#formIncome input[type=checkbox]").each(function(a, b) {
				$(b).prop("checked", false);
			});
		}
	</script>
</head>
<body>
	<c:url value="/investments" var="urlInvestments" />
	<div class="header">
		<c:import url="../header.jsp"/>
	</div>
	<div class="main">
		<h2 class="titulo">Investimentos</h2>
		<div class="div-5">
			<div class="panel">
				<c:choose>	
					<c:when test="${investment.id != null}">
						<h3 class="panelTitle">Editar Investimento</h3>
					</c:when>
					<c:otherwise>
						<h3 class="panelTitle">Novo Investimento</h3>
					</c:otherwise>	
				</c:choose>
				<c:url var="salvar" value="/investment"></c:url>
				<form:form action="${salvar}" method="POST" modelAttribute="investment" id="formInvestment">
					<form:hidden path="id" id="id"/>
					<table>
						<tr>
							<td><label for="descricao">Descrição</label></td>
							<td><form:input id="name" path="name" size="40"/></td>
						</tr>
						<tr>
							<td><label for="valor">Valor</label></td>
							<td><form:input cssClass="inputMoney" id="value" path="value" size="12"/></td>
						</tr>
						<tr>
							<td><label for="dueDate">Data de lançamento</label></td>
							<td><form:input cssClass="inputDate" id="dueDate" path="dueDate" size="10"/></td>
						</tr>
						<tr>
							<td><label for="withdrawlDate">Data de retirada</label></td>
							<td><form:input cssClass="inputDate" id="withdrawlDate" path="withdrawlDate" size="10"/></td>
						</tr>
						<tr>
							<td><label for="returnRate">Taxa de rendimento (% a.a.)</label></td>
							<td><form:input cssClass="inputDecimal" id="returnRate" path="returnRate" size="12"/></td>
						</tr>
						<tr>
							<td><label for="taxRate">Imposto sobre rendimento (%)</label></td>
							<td><form:input cssClass="inputDecimal" id="taxRate" path="taxRate" size="12"/></td>
						</tr>
					</table>
					<div class="group">
			
					</div>
					<div class="botoes">
						<a href="${urlInvestments}"><input type="button" value="Cancelar" class="botao-direita"/></a>
						<input type="submit" value="Salvar" class="botao-direita"/>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="footer">
	</div>
</body>
</html>