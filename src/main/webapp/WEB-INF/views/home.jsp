<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Receitas</title>
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/header.css'/>">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		console.log("loaded");
	});
</script>
</head>
<body>
	<spring:url value="/budget?type=summary&period=monthly"
		var="urlMonthlySummary" htmlEscape="true" />
	<div class="header">
		<c:import url="header.jsp" />
	</div>
	<div class="main">
		<h2 class="titulo">Home</h2>
		<!-- Lançamentos do mês -->
		<div class="div-5">
			<div class="panel">
				<h3 class="panelTitle">Lançamentos do Mês</h3>
				<!-- Receitas -->
				<table class="editableCells">
					<thead>
						<tr>
							<th colspan="14" class="tableTitle">Receitas</th>
						</tr>
						<tr>
							<th>Descrição</th>
							<th>Vencimento</th>
							<th>Valor</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="income" items="${incomes}" varStatus="status">
							<tr>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									${income.name}
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									<fmt:formatDate pattern="dd/MM/yyyy" value="${income.dueDate}" />
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									R$ ${income.value}
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th colspan="2">TOTAL</th>
							<td>R$ ${totalIncomes}</td>
						</tr>
					</tfoot>
				</table>
				
				<!-- Despesas -->
				<table class="editableCells">
					<thead>
						<tr>
							<th colspan="14" class="tableTitle">Despesas</th>
						</tr>
						<tr>
							<th>Descrição</th>
							<th>Vencimento</th>
							<th>Status</th>
							<th>Valor</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="expense" items="${expenses}" varStatus="status">
							<tr>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									${expense.name}
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									<fmt:formatDate pattern="dd/MM/yyyy" value="${expense.dueDate}" />
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									${expense.paid ? 'Pago': 'Pendente'}</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									R$ ${expense.value}
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th colspan="3">TOTAL</th>
							<td>R$ ${totalExpenses}</td>
						</tr>
					</tfoot>
				</table>
				
				<!-- Investimentos -->
				<table class="editableCells">
					<thead>
						<tr>
							<th colspan="14" class="tableTitle">Investimentos</th>
						</tr>
						<tr>
							<th>Descrição</th>
							<th>Data de aplicação</th>
							<th>Valor</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="investment" items="${investments}" varStatus="status">
							<tr>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									${investment.name}
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									<fmt:formatDate pattern="dd/MM/yyyy" value="${investment.dueDate}" />
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									R$ ${investment.value}
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr>
							<th colspan="2">TOTAL</th>
							<td>R$ ${totalInvestments}</td>
						</tr>
					</tfoot>
				</table>
			</div>
		</div>
		<!-- Últimos lançamentos -->
		<div class="div-5">
			<div class="panel">
				<h3 class="panelTitle">Últimos Lançamentos</h3>
				<!-- Despesas -->
				<table class="editableCells">
					<thead>
						<tr>
							<th>Descrição</th>
							<th>Tipo</th>
							<th>Vencimento</th>
							<th>Valor</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entry" items="${lastEntries}" varStatus="status">
							<tr>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									${entry.name}
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									${entry.type}
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									<fmt:formatDate pattern="dd/MM/yyyy" value="${entry.dueDate}" />
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									R$ ${entry.value}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="footer">
		<c:import url="footer.jsp" />
	</div>
</body>
</html>