<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#despesaFixa").change(function(){
			var despesaFixa = $(this).is(":checked");
			$("#formDespesa").attr("action", "despesa?despesaFixa=" + despesaFixa);
		});
	});
</script>

<div id="novaDespesa" class="modalDialog" title="Nova Despesa">
	<div>
		<h3 class="tituloPainel">Nova Despesa</h3>
		<form:form action="despesa?despesaFixa=false" method="POST" commandName="despesa" id="formDespesa">
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
						<select id="categoria">
							<option value="1">Alimentação</option>
							<option value="2">Transporte</option>
							<option value="3">Saúde</option>
							<option value="4">Lazer</option>
							<option value="5">Moradia</option> 
						</select>
					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
				<tr>
					<td colspan="2"><input type="checkbox" id="despesaFixa"/> Despesa Fixa</td>
				</tr>
				<tr>
					<td colspan="2"><input type="checkbox" id="pago"/> Está pago</td>
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