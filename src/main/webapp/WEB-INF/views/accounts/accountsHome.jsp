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
<title>Contas</title>
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
	<spring:url value="/newAccount" var="urlNew" htmlEscape="true"></spring:url>
	<spring:url value="/manageAccount" var="urlManage" htmlEscape="true"></spring:url>
	<spring:url value="/deleteAccount" var="urlExcluir" htmlEscape="true"></spring:url>
	<div class="header">
		<c:import url="../header.jsp" />
	</div>
	<div class="main">
		<h2 class="titulo">Contas</h2>
		<div class="div-5">
			<div class="panel">
				<h3 class="panelTitle">Lista de contas</h3>
				<table class="editableCells">
					<thead>
						<tr>
							<th>Instituição financeira</th>
							<th>Saldo</th>
							<!-- <th>Cheque especial</th>  -->
						</tr>
					</thead>
					<tbody>
						<c:forEach var="account" items="${accounts}" varStatus="status">
							<tr>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									<div>${account.institutionName}</div>
									<div class="cellControl">
										<img src="<c:url value='/resources/imagens/down-arrow.png'/>" />
									</div>
									<div class="cellMenu">
										<ul>
											<li><a href="${urlManage}/${account.id}">Gerenciar</a></li>
											<li><a href="${urlExcluir}/${account.id}">Excluir</a></li>
										</ul>
									</div>
								</td>
								<td class="${(status.index+1)%2 == 0 ? 'linha-par': 'linha-impar'}">
									R$ ${account.balance}
								</td>
							</tr>
						</c:forEach>
					</tbody>
					<tr class="total">
						<td>TOTAL</td>
						<td>R$ ${totalAccounts}</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="btn-group div-10 line-separator-top">
			<a href="${urlNew}" id="btnNewAccount">
				<input type="button" value="Nova Conta" class="btn" />
			</a>
		</div>
	</div>
	<div class="footer">
		<c:import url="../footer.jsp" />
	</div>
</body>
</html>