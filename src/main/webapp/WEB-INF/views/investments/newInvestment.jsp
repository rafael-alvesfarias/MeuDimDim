<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html>
<head>
	<meta charset="UTF-8">
	<title>Cadastro de Receita</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/style.css'/>">
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
	<script src="/resources/script/jquery.inputmask.bundle.js"></script>
	<script type="text/javascript">
		function clearForm() {
			$("#formIncome input[type=text]").each(function(a, b) {
				$(b).val("");
			});
			$("#formIncome input[type=hidden]").each(function(a, b) {
				$(b).val("");
			});
			$("#formIncome select").each(function(a, b) {
				$(b).children("option").eq(0).prop("selected", true);
			});
			$("#formIncome input[type=checkbox]").each(function(a, b) {
				$(b).prop("checked", false);
			});
		}
	</script>
</head>
<body>
	<c:import url="../header.jsp"/>
	<div class="box">
		<h2 class="titulo line-separator-bottom">Investimentos</h2>
		<c:import url="formInvestment.jsp"></c:import>
	</div>
</body>
</html>