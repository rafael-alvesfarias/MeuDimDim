<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>Despesas</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/recursos/css/style.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
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
	<spring:url value="/expense" var="urlNewExpense" htmlEscape="true"></spring:url>
	<spring:url value="/editarDespesa" var="urlEditar" htmlEscape="true"></spring:url>
	<spring:url value="/excluirDespesa" var="urlExcluir" htmlEscape="true"></spring:url>
	<spring:url value="/despesasMensais" var="urlDespesasMensais" htmlEscape="true"></spring:url>
	<c:import url="../header.jsp"/>
	<div class="box">
		<h2 class="titulo line-separator-bottom">Despesas</h2>
		<div class="painel-large">
			<h3 class="tituloPainel">Despesas Anuais</h3>
			<!-- DESPESAS FIXAS -->
				<table class="editableCells">
					<thead>
						<tr>
							<th colspan="14" class="tableTitle">Despesas Fixas</th>
						</tr>
						<tr>
							<th/>
							<th><a href="${urlDespesasMensais}/janeiro">Janeiro</a></th>
							<th><a href="${urlDespesasMensais}/fevereiro">Fevereiro</a></th>
							<th><a href="${urlDespesasMensais}/marco">Março</a></th>
							<th><a href="${urlDespesasMensais}/abril">Abril</a></th>
							<th><a href="${urlDespesasMensais}/maio">Maio</a></th>
							<th><a href="${urlDespesasMensais}/junho">Junho</a></th>
							<th><a href="${urlDespesasMensais}/julho">Julho</a></th>
							<th><a href="${urlDespesasMensais}/agosto">Agosto</a></th>
							<th><a href="${urlDespesasMensais}/setembro">Setembro</a></th>
							<th><a href="${urlDespesasMensais}/outubro">Outubro</a></th>
							<th><a href="${urlDespesasMensais}/novembro">Novembro</a></th>
							<th><a href="${urlDespesasMensais}/dezembro">Dezembro</a></th>
							<th>TOTAL</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th>TOTAL</th>
							<c:forEach var="total" items="${orcamentoDespesasFixas.totais}">
								<td>R$ ${total}</td>
							</c:forEach>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="conuntoDespesas" items="${orcamentoDespesasFixas.despesasAnuais}" varStatus="status">
							<tr>
								<th style="min-width: 121px;">${conuntoDespesas.nomeDespesa}</th>
								<c:forEach var="mes" begin="1" end="12">
									<c:choose>
										<c:when test="${conuntoDespesas.despesas[mes] != null}">
											<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
												<div>
													R$ ${conuntoDespesas.despesas[mes].value}
												</div>
												<div class="cellControl">
													<img src="<c:url value='/recursos/imagens/down-arrow.png'/>"/>
												</div>
												<div class="cellMenu">
													<ul>
														<li><a href="${urlEditar}/${conuntoDespesas.despesas[mes].id}">Editar</a></li>
														<li><a href="${urlExcluir}/${conuntoDespesas.despesas[mes].id}?mes=${mes}">Excluir</a></li>
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
									R$ ${conuntoDespesas.total}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<br>
				<!-- DESPESAS VARIÁVEIS -->
				<table class="editableCells">
					<thead>
						<tr>
							<th colspan="14" class="tableTitle">Despesas Variáveis</th>
						</tr>
						<tr>
							<th/>
							<th><a href="${urlDespesasMensais}/janeiro">Janeiro</a></th>
							<th><a href="${urlDespesasMensais}/fevereiro">Fevereiro</a></th>
							<th><a href="${urlDespesasMensais}/marco">Março</a></th>
							<th><a href="${urlDespesasMensais}/abril">Abril</a></th>
							<th><a href="${urlDespesasMensais}/maio">Maio</a></th>
							<th><a href="${urlDespesasMensais}/junho">Junho</a></th>
							<th><a href="${urlDespesasMensais}/julho">Julho</a></th>
							<th><a href="${urlDespesasMensais}/agosto">Agosto</a></th>
							<th><a href="${urlDespesasMensais}/setembro">Setembro</a></th>
							<th><a href="${urlDespesasMensais}/outubro">Outubro</a></th>
							<th><a href="${urlDespesasMensais}/novembro">Novembro</a></th>
							<th><a href="${urlDespesasMensais}/dezembro">Dezembro</a></th>
							<th>TOTAL</th>
						</tr>
					</thead>
					<tfoot>
						<tr>
							<th>TOTAL</th>
							<c:forEach var="total" items="${orcamentoDespesasVariaveis.totais}">
								<td>R$ ${total}</td>
							</c:forEach>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach var="conuntoDespesas" items="${orcamentoDespesasVariaveis.despesasAnuais}" varStatus="status">
							<tr>
								<th style="min-width: 121px;">${conuntoDespesas.nomeDespesa}</th>
								<c:forEach var="mes" begin="1" end="12">
									<c:choose>
										<c:when test="${conuntoDespesas.despesas[mes] != null}">
											<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
												<div>
													R$ ${conuntoDespesas.despesas[mes].value}
												</div>
												<div class="cellControl">
													<img src="<c:url value='/recursos/imagens/down-arrow.png'/>"/>
												</div>
												<div class="cellMenu">
													<ul>
														<li><a href="${urlEditar}/${conuntoDespesas.despesas[mes].id}">Editar</a></li>
														<li><a href="${urlExcluir}/${conuntoDespesas.despesas[mes].id}">Excluir</a></li>
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
									R$ ${conuntoDespesas.total}
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			<div class="botoes">
				<a href="${urlNewExpense}" id="btnNewExpense"><input type="button" value="Nova Despesa" class="botao-direita"/></a>
			</div>
		</div>
	</div>
	
	<c:import url="../footer.jsp"/>
</body>
</html>