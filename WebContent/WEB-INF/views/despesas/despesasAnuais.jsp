<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<html>
<head>
	<meta charset="UTF-8">
	<title>Despesas</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/recursos/css/style.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
</head>
<body>
	<spring:url value="/editarDespesa" var="urlEditar" htmlEscape="true"></spring:url>
	<c:import url="../header.jsp"/>
	<div class="box">
		<h2 class="titulo line-separator-bottom">Despesas</h2>
		<div class="painel">
			<h3 class="tituloPainel">Despesas Anuais</h3>
			<!-- DESPESAS FIXAS -->
				<table>
					<thead>
						<tr>
							<th colspan="14" class="tableTitle">Despesas Fixas</th>
						</tr>
						<tr>
							<th/>
							<th>Janeiro</th>
							<th>Fevereiro</th>
							<th>Março</th>
							<th>Abril</th>
							<th>Maio</th>
							<th>Junho</th>
							<th>Julho</th>
							<th>Agosto</th>
							<th>Setembro</th>
							<th>Outubro</th>
							<th>Novembro</th>
							<th>Dezembro</th>
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
												<a href="${urlEditar}/${conuntoDespesas.despesas[mes].id}">
													R$ ${conuntoDespesas.despesas[mes].value}
												</a>
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
				<table>
					<thead>
						<tr>
							<th colspan="14" class="tableTitle">Despesas Variáveis</th>
						</tr>
						<tr>
							<th/>
							<th>Janeiro</th>
							<th>Fevereiro</th>
							<th>Março</th>
							<th>Abril</th>
							<th>Maio</th>
							<th>Junho</th>
							<th>Julho</th>
							<th>Agosto</th>
							<th>Setembro</th>
							<th>Outubro</th>
							<th>Novembro</th>
							<th>Dezembro</th>
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
												<a href="${urlEditar}/${conuntoDespesas.despesas[mes].id}">
													R$ ${conuntoDespesas.despesas[mes].value}
												</a>
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
				<a href="#novaDespesa"><input type="button" value="Nova Despesa" class="botao-esquerda"/></a>
			</div>
		</div>
	</div>
	
	<c:import url="popupDespesa.jsp"/>

	<c:import url="../footer.jsp"/>
</body>
</html>