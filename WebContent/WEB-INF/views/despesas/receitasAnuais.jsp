<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>Receitas</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/recursos/css/style.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/recursos/css/incomes.css'/>">
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
	<spring:url value="/updateIncome" var="urlUpdate" htmlEscape="true"></spring:url>
	<spring:url value="/deleteIncome" var="urlDelete" htmlEscape="true"></spring:url>
	<spring:url value="/receitasMensais" var="urlMonthlyIncomes" htmlEscape="true"></spring:url>
	<c:import url="../header.jsp"/>
	<div class="box">
		<h2 class="titulo line-separator-bottom">Receitas</h2>
		<div class="painel">
			<h3 class="tituloPainel">Receitas Anuais</h3>
			<!-- DESPESAS FIXAS -->
			<table class="editableCells">
				<thead>
					<tr>
						<th colspan="14" class="tableTitle">Receitas</th>
					</tr>
					<tr>
						<th/>
						<th><a href="${urlReceitasMensais}/jan">Janeiro</a></th>
						<th><a href="${urlReceitasMensais}/fev">Fevereiro</a></th>
						<th><a href="${urlReceitasMensais}/mar">Março</a></th>
						<th><a href="${urlReceitasMensais}/apr">Abril</a></th>
						<th><a href="${urlReceitasMensais}/may">Maio</a></th>
						<th><a href="${urlReceitasMensais}/jun">Junho</a></th>
						<th><a href="${urlReceitasMensais}/jul">Julho</a></th>
						<th><a href="${urlReceitasMensais}/aug">Agosto</a></th>
						<th><a href="${urlReceitasMensais}/set">Setembro</a></th>
						<th><a href="${urlReceitasMensais}/out">Outubro</a></th>
						<th><a href="${urlReceitasMensais}/nov">Novembro</a></th>
						<th><a href="${urlReceitasMensais}/dec">Dezembro</a></th>
						<th>TOTAL</th>
					</tr>
				</thead>
				<tfoot>
					<tr>
						<th>TOTAL</th>
						<c:forEach var="total" items="${incomesBudget.totals}">
							<td>R$ ${total}</td>
						</c:forEach>
					</tr>
				</tfoot>
				<tbody>
					<c:forEach var="incomesSet" items="${incomesBudget.annualIncomes}" varStatus="status">
						<tr>
							<th style="min-width: 121px;">${incomesSet.name}</th>
							<c:forEach var="month" begin="1" end="12">
								<c:choose>
									<c:when test="${incomesSet.incomes[month] != null}">
										<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
											<div>
												R$ ${incomesSet.incomes[month].value}
											</div>
											<div class="cellControl">
												<img src="<c:url value='/recursos/imagens/down-arrow.png'/>"/>
											</div>
											<div class="cellMenu">
												<ul>
													<li><a href="${urlUpdate}/${incomesSet.incomes[month].id}">Editar</a></li>
													<li><a href="${urlDelete}/${incomesSet.incomes[month].id}?month=${month}">Excluir</a></li>
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
				<a href="#newIncome" id="btnNewIncome"><input type="button" value="Nova Receita" class="botao-esquerda"/></a>
			</div>
		</div>
	</div>
	
	<c:import url="popupIncome.jsp"/>

	<c:import url="../footer.jsp"/>
</body>
</html>