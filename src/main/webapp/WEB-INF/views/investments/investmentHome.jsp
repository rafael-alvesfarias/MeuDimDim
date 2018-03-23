<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Investimentos</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".cellControl").click(function() {
			var cellMenu = $(this).next(".cellMenu");
			var hidden = cellMenu.is(":hidden");
			$(".cellMenu").each(function() {
				$(this).hide();
			});
			if (hidden) {
				cellMenu.show();
			} else {
				cellMenu.hide();
			}
		});

		$(".editableCells td").mouseenter(function() {
			var imagem = $(this).find("img");
			console.log(imagem);
			imagem.show();
		}).mouseleave(function() {
			var imagem = $(this).find("img");
			console.log(imagem);
			imagem.hide();
		});
	});
</script>
</head>
<body>
	<spring:url value="/newInvestment" var="urlNew" htmlEscape="true"></spring:url>
	<spring:url value="/editInvestment" var="urlEditar" htmlEscape="true"></spring:url>
	<spring:url value="/deleteInvestment" var="urlExcluir" htmlEscape="true"></spring:url>
	<div class="header">
		<c:import url="../header.jsp" />
	</div>
	<div class="main">
		<h2 class="titulo">Investimentos</h2>
		<div class="div-5">
			<div class="panel">
				<h3 class="panelTitle">Lançamentos Futuros</h3>
				<!-- DESPESAS FIXAS -->
				<table class="editableCells">
				<c:forEach var="budget" items="${investmentBudgets}">
						<thead>
							<tr>
								<th colspan="14" class="tableTitle">${budget.name}</th>
							</tr>
							<tr>
								<th>Data</th>
								<th>Descrição</th>
								<th>Valor</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="entry" items="${budget.entries}"
								varStatus="status">
								<tr>
									<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
										<fmt:formatDate pattern="dd/MM/yyyy" value="${entry.dueDate}" />
									</td>
									<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
										<div>${entry.name}</div>
										<div class="cellControl">
											<img src="<c:url value='/resources/imagens/down-arrow.png'/>" />
										</div>
										<div class="cellMenu">
											<ul>
												<li><a href="${urlEditar}/${entry.id}">Editar</a></li>
												<li><a href="${urlExcluir}/${entry.id}?mes=${mes}">Excluir</a></li>
											</ul>
										</div>
									</td>
									<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
										R$ ${entry.value}
									</td>
								</tr>
							</c:forEach>
						</tbody>
						<tr class="total">
							<td colspan="2">TOTAL</td>
							<td>R$ ${budget.total}</td>
						</tr>
				</c:forEach>
				</table>
			</div>
		</div>
		<div class="btn-group div-10 line-separator-top">
			<a href="${urlNew}" id="btnNew"><input type="button" value="Novo Investimento" class="btn" /></a>
		</div>
	</div>
	<div class="footer">
		<c:import url="../footer.jsp" />
	</div>
</body>
</html>