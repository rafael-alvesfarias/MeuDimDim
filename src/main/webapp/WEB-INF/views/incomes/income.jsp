<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Cadastro de Receita</title>
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
		});
	</script>
</head>
<body>
	<div class="header">
		<c:import url="../header.jsp"/>
	</div>
	<div class="main">
		<h2 class="titulo">Receitas</h2>
		<div class="div-5">
			<div class="panel" title="Nova Despesa">
				<c:choose>	
					<c:when test="${income.id != null}">
						<h3 class="panelTitle">Editar Receita</h3>
					</c:when>
					<c:otherwise>
						<h3 class="panelTitle">Nova Receita</h3>
					</c:otherwise>	
				</c:choose>
				<c:url var="salvar" value="/income"></c:url>
				<form:form action="${salvar}" method="POST" modelAttribute="income" id="formIncome">
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
							<td><label for="data">Data de vencimento</label></td>
							<td><form:input cssClass="inputDate" id="dueDate" path="dueDate" size="10"/></td>
						</tr>
						<tr>
							<td><label for=category>Categoria</label></td>
							<td>
								<form:select path="category" id="category">
									<form:options items="${income.categories}" itemValue="name" itemLabel="description"/>
								</form:select>
							</td>
						</tr>
						<tr>
							<td></td>
						</tr>
						<tr>
							<td colspan="2"><form:checkbox path="received"/> Recebida</td>
						</tr>
					</table>
					<div class="btn-group">
						<input type="submit" value="Salvar" class="btn-submit"/>
						<a href="#close"><input type="button" value="Cancelar" class="btn-cancel"/></a>
					</div>
				</form:form>
			</div>
		</div>
	</div>
	<div class="footer">
	</div>
</body>
</html>