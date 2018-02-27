<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="div-5">
	<div class="painel-medium">
		<c:choose>	
			<c:when test="${investment.id != null}">
				<h3 class="tituloPainel">Editar Investimento</h3>
			</c:when>
			<c:otherwise>
				<h3 class="tituloPainel">Novo Investimento</h3>
			</c:otherwise>	
		</c:choose>
		<c:url var="salvar" value="/investment"></c:url>
		<form:form action="${salvar}" method="POST" commandName="investment" id="formInvestment">
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
					<td><label for="data">Data de lançamento</label></td>
					<td><form:input id="dueDate" path="dueDate" size="10"/></td>
				</tr>
				<tr>
					<td><label for="data">Data de retirada</label></td>
					<td><form:input id="withdrawlDate" path="withdrawlDate" size="10"/></td>
				</tr>
				<tr>
					<td><label for="data">Taxa de rendimento (% a.a.)</label></td>
					<td><form:input id="withdrawlDate" path="withdrawlDate" size="12"/></td>
				</tr>
				<tr>
					<td><label for="data">Imposto sobre rendimento (%)</label></td>
					<td><form:input id="taxRate" path="taxRate" size="12"/></td>
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