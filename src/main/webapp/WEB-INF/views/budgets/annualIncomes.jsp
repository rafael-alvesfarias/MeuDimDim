<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Receitas</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/incomes.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#btnNewIncome").click(function(){
				limparPopup();
			});
			
			$(".cellControl").click(function() {
				var cellMenu = $(this).next(".cellMenu");
				var hidden = cellMenu.is(":hidden");
				$(".cellMenu").each(function(){
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
				imagem.show();
			}).mouseleave(function() {
				var imagem = $(this).find("img");
				imagem.hide();
			});
		});
	</script>
</head>
<body>
	<spring:url value="/newIncome" var="urlNewIncome" htmlEscape="true"></spring:url>
	<spring:url value="/updateIncome" var="urlUpdate" htmlEscape="true"></spring:url>
	<spring:url value="/deleteIncome" var="urlDelete" htmlEscape="true"></spring:url>
	<spring:url value="/incomes/monthly" var="urlMonthlyIncomes" htmlEscape="true"></spring:url>
	<c:import url="../header.jsp"/>
	<div class="box">
		<h2 class="titulo line-separator-bottom">Receitas</h2>
		<div class="painel-large">
			<h3 class="tituloPainel">Receitas Anuais</h3>
			<!-- DESPESAS FIXAS -->
			<table class="editableCells">
				<thead>
					<tr>
						<th colspan="14" class="tableTitle">Receitas</th>
					</tr>
					<tr>
						<th/>
						<th><a href="${urlMonthlyIncomes}/jan">Janeiro</a></th>
						<th><a href="${urlMonthlyIncomes}/fev">Fevereiro</a></th>
						<th><a href="${urlMonthlyIncomes}/mar">Março</a></th>
						<th><a href="${urlMonthlyIncomes}/apr">Abril</a></th>
						<th><a href="${urlMonthlyIncomes}/may">Maio</a></th>
						<th><a href="${urlMonthlyIncomes}/jun">Junho</a></th>
						<th><a href="${urlMonthlyIncomes}/jul">Julho</a></th>
						<th><a href="${urlMonthlyIncomes}/aug">Agosto</a></th>
						<th><a href="${urlMonthlyIncomes}/set">Setembro</a></th>
						<th><a href="${urlMonthlyIncomes}/out">Outubro</a></th>
						<th><a href="${urlMonthlyIncomes}/nov">Novembro</a></th>
						<th><a href="${urlMonthlyIncomes}/dec">Dezembro</a></th>
						<th>TOTAL</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>TOTAL</th>
						<c:forEach var="total" items="${incomesBudget.totalIncomes}">
							<td>R$ ${total}</td>
						</c:forEach>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach var="incomeGroup" items="${incomesBudget.incomeGroups}" varStatus="status">
						<tr>
							<th style="min-width: 121px;">${incomeGroup.name}</th>
							<c:forEach var="month" begin="1" end="12">
								<c:choose>
									<c:when test="${incomeGroup.incomes[month] != null}">
										<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
											<div>
												R$ ${incomeGroup.incomes[month].value}
											</div>
											<div class="cellControl">
												<img src="<c:url value='/resources/imagens/down-arrow.png'/>"/>
											</div>
											<div class="cellMenu">
												<ul>
													<li><a href="${urlUpdate}/${incomeGroup.incomes[month].id}">Editar</a></li>
													<li><a href="${urlDelete}/${incomeGroup.incomes[month].id}?month=${month}">Excluir</a></li>
												</ul>
											</div>
										</td>
									</c:when>
									<c:otherwise>
										<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}"/>
									</c:otherwise>
								</c:choose>
							</c:forEach>
							<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
								R$ ${incomesSet.total}
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="botoes">
				<a href="${urlNewIncome}" id="btnNewIncome"><input type="button" value="Nova Receita" class="botao-direita"/></a>
			</div>
		</div>
	</div>
	
	<c:import url="../footer.jsp"/>
</body>
</html>