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
	<spring:url value="/expense" var="urlNewExpense" htmlEscape="true"></spring:url>
	<spring:url value="/editarDespesa" var="urlEditar" htmlEscape="true"></spring:url>
	<spring:url value="/excluirDespesa" var="urlExcluir" htmlEscape="true"></spring:url>
	<spring:url value="/despesasMensais" var="urlDespesasMensais"
		htmlEscape="true"></spring:url>
	<c:import url="../header.jsp" />
	<div class="box">
		<h2 class="titulo line-separator-bottom">Despesas</h2>
		<div class="painel-medium">
			<h3 class="tituloPainel">Despesas Fixas</h3>
			<!-- DESPESAS FIXAS -->
			<c:forEach var="budget" items="${fixedExpensesBudgets}">
				<table class="editableCells">
					<thead>
						<tr>
							<th colspan="14" class="tableTitle">Despesas de ${budget.name}</th>
						</tr>
						<tr>
							<th>Vencimento</th>
							<th>Descrição</th>
							<th>Valor</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<td colspan="2"><b>TOTAL</b></td>
							<td>R$ ${budget.total}</td>
						</tr>
					</tfoot>
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
												href="${urlEditar}/${conuntoDespesas.despesas[mes].id}">Editar</a></li>
											<li><a
												href="${urlExcluir}/${conuntoDespesas.despesas[mes].id}?mes=${mes}">Excluir</a></li>
										</ul>
									</div>
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									R$ ${entry.value}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:forEach>
			<!-- DESPESAS VARIÁVEIS -->
			<div class="botoes">
				<a href="${urlNewExpense}" id="btnNewExpense"><input
					type="button" value="Nova Despesa" class="botao-direita" /></a>
			</div>
		</div>
	</div>

	<c:import url="../footer.jsp" />
</body>
</html>