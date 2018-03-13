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
<title>Despesas</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css"
	href="<c:url value='/resources/css/style.css'/>">
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
	<spring:url value="/newExpense" var="urlNewExpense" htmlEscape="true"></spring:url>
	<spring:url value="/editExpense" var="urlEditar" htmlEscape="true"></spring:url>
	<spring:url value="/deleteExpense" var="urlExcluir" htmlEscape="true"></spring:url>
	<spring:url value="/despesasMensais" var="urlDespesasMensais" htmlEscape="true"></spring:url>
	<div class="box">
		<c:import url="../header.jsp" />
		<div class="main">
		<h2 class="titulo line-separator-bottom">Despesas</h2>
		<div class="div-5">
			<div class="painel-medium">
				<h3 class="tituloPainel">Despesas Fixas</h3>
				<!-- DESPESAS FIXAS -->
				<table class="editableCells">
				<c:forEach var="budget" items="${fixedExpensesBudgets}">
						<thead>
							<tr>
								<th colspan="14" class="tableTitle">${budget.name}</th>
							</tr>
							<tr>
								<th>Vencimento</th>
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
		<!-- DESPESAS VARIÁVEIS -->
		<div class="div-5">
			<div class="painel-medium">
				<h3 class="tituloPainel">Despesas Variáveis</h3>
				<!-- DESPESAS FIXAS -->
				<table class="editableCells">
				<c:forEach var="budget" items="${variableExpensesBudgets}">
						<thead>
							<tr>
								<th colspan="14" class="tableTitle">${budget.name}</th>
							</tr>
							<tr>
								<th>Vencimento</th>
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
												<li><a
													href="${urlEditar}/${entry.id}">Editar</a></li>
												<li><a
													href="${urlExcluir}/${entry.id}?mes=${mes}">Excluir</a></li>
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
		<div class="botoes div-10">
			<a href="${urlNewExpense}" id="btnNewExpense"><input
				type="button" value="Nova Despesa" class="botao-direita" /></a>
		</div>
	</div>
	</div>
	<c:import url="../footer.jsp" />
</body>
</html>