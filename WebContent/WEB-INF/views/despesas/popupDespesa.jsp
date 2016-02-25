<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
	$(document).ready(function(){
		if($.isNumeric($("#idDespesa").val())) {
			window.location.hash = "novaDespesa";
		}
	});
	function limparPopup() {
		$("#formDespesa input[type=text]").each(function(a, b) {
			alert($(b).val());
		});
		$("#formDespesa input[type=hidden]").each(function(a, b) {
			alert($(b).val());
		});
		$("#formDespesa input[type=select]").each(function(a, b) {
			alert($(b).val());
		});
	}
</script>

<div id="novaDespesa" class="modalDialog" title="Nova Despesa">
	<div>
		<c:choose>
			<c:when test="${despesa.id != null}">
				<h3 class="tituloPainel">Editar Despesa</h3>
			</c:when>
			<c:otherwise>
				<h3 class="tituloPainel">Nova Despesa</h3>
			</c:otherwise>	
		</c:choose>
		<c:url var="salvar" value="/despesa"></c:url>
		<form:form action="${salvar}" method="POST" commandName="despesa" id="formDespesa">
			<form:hidden path="id" id="idDespesa"/>
			<table>
				<tr>
					<td><label for="descricao">Descrição</label></td>
					<td><form:input id="descricao" path="descricao" size="40"/></td>
				</tr>
				<tr>
					<td><label for="valor">Valor</label></td>
					<td><form:input id="valor" path="valor" size="12"/></td>
				</tr>
				<tr>
					<td><label for="data">Data de vencimento</label></td>
					<td><form:input id="data" path="dataLancamento" size="10"/></td>
				</tr>
				<tr>
					<td><label for="categoria">Categoria</label></td>
					<td>
						<form:select path="categoria" id="categoria">
							<form:options items="${despesa.categorias}" itemValue="name" itemLabel="description"/>
						</form:select>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td colspan="2"><form:checkbox path="despesaFixa"/> Despesa Fixa</td>
				</tr>
				<tr>
					<td colspan="2"><form:checkbox path="pago"/> Está pago</td>
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