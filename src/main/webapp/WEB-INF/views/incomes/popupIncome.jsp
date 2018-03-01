<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
	$(document).ready(function(){
		if($.isNumeric($("#id").val())) {
			window.location.hash = "newIncome";
		}
	});
	function limparPopup() {
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

<div id="newIncome" class="modalDialog" title="Nova Receita">
	<div>
		<c:choose>
			<c:when test="${income.id != null}">
				<h3 class="tituloPainel">Editar Receita</h3>
			</c:when>
			<c:otherwise>
				<h3 class="tituloPainel">Nova Receita</h3>
			</c:otherwise>	
		</c:choose>
		<c:url var="salvar" value="/income"></c:url>
		<form:form action="${salvar}" method="POST" commandName="income" id="formIncome">
			<form:hidden path="id" id="id"/>
			<table>
				<tr>
					<td><label for="descricao">Descrição</label></td>
					<td><form:input id="name" path="name" size="40"/></td>
				</tr>
				<tr>
					<td><label for="valor">Valor</label></td>
					<td><form:input id="value" path="value" size="12"/></td>
				</tr>
				<tr>
					<td><label for="data">Data de vencimento</label></td>
					<td><form:input id="dueDate" path="dueDate" size="10"/></td>
				</tr>
				<tr>
					<td><label for=category>Categoria</label></td>
					<td>
						<form:select path="category" id="category">
							<form:options items="${income.categories}" itemValue="name" itemLabel="description"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td colspan="2"><form:checkbox path="received"/> Recebida</td>
				</tr>
			</table>
			<div class="group">
	
			</div>
			<div class="botoes">
				<a href="#close"><input type="button" value="Cancelar" class="botao-direita"/></a>
				<input type="submit" value="Salvar" class="botao-direita"/>
			</div>
		</form:form>
	</div>
</div>