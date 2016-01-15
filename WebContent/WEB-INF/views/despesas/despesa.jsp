<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div id="novaDespesa" class="modalDialog" title="Nova Despesa">
	<div>
		<h3 class="tituloPainel">Nova Despesa</h3>
		<form:form action="despesa" method="POST" commandName="despesa" id="formDespesa">
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
							<form:options items="${despesa.categorias}" itemValue="nome" itemLabel="descricao"/>
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