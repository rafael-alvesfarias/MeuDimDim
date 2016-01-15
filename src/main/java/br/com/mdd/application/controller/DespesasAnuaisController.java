package br.com.mdd.application.controller;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.mdd.domain.model.Categoria;
import br.com.mdd.domain.model.Despesa;
import br.com.mdd.domain.model.DespesaFixa;
import br.com.mdd.domain.model.Orcamento;
import br.com.mdd.presentation.model.DespesaViewModel;
import br.com.mdd.presentation.model.OrcamentoDespesasAnuaisViewModel;

@Controller
public class DespesasAnuaisController {
	
	private Set<Despesa> despesasFixas = getDespesasFixas();
	
	private Set<Despesa> despesasVariaveis = getDespesasVariaveis();
	
	private Set<Categoria> categorias = getCategorias();
	
	@RequestMapping("/despesasAnuais")
	public String despesasAnuais(Model model){
		Orcamento orcamentoDespesasFixas = new Orcamento(despesasFixas).anual().comPrevisao().gerar();
		Orcamento orcamentoDespesasVariaveis = new Orcamento(despesasVariaveis).anual().gerar();
		OrcamentoDespesasAnuaisViewModel orcamentoViewModelDespesasFixasViewModel = new OrcamentoDespesasAnuaisViewModel(orcamentoDespesasFixas);
		OrcamentoDespesasAnuaisViewModel orcamentoViewModelDespesasVariaveisViewModel = new OrcamentoDespesasAnuaisViewModel(orcamentoDespesasVariaveis);
		model.addAttribute("orcamentoDespesasFixas", orcamentoViewModelDespesasFixasViewModel);
		model.addAttribute("orcamentoDespesasVariaveis", orcamentoViewModelDespesasVariaveisViewModel);
		DespesaViewModel despesa = new DespesaViewModel();
		despesa.setCategorias(categorias);
		model.addAttribute("despesa", despesa);
		
		return "/despesas/despesasAnuais";
	}
	
	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "despesa") DespesaViewModel despesa, Model model) {
		Despesa d = null;
		if(despesa.getDespesaFixa()) {
			d = new DespesaFixa(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
			despesasFixas.add(d);
		} else {
			d = new Despesa(despesa.getDescricao(), despesa.getValor(), despesa.getDataLancamento());
			despesasVariaveis.add(d);
		}
		d.setCategoria(getCategoria(despesa.getCategoria()));
		return despesasAnuais(model);
	}
	
	private Categoria getCategoria(String nome) {
		for (Categoria categoria : categorias) {
			if (categoria.getNome().equals(nome)) {
				return categoria;
			}
		}
		return null;
	}
	
	private static final Set<Despesa> getDespesasFixas(){
		Set<Despesa> despesas = new TreeSet<Despesa>();
		
		DespesaFixa agua = new DespesaFixa("Água", new BigDecimal("45.98"), new LocalDate(2016, 1, 10));
		DespesaFixa luz = new DespesaFixa("Luz", new BigDecimal("98.73"), new LocalDate(2016, 1, 12));
		DespesaFixa telefone = new DespesaFixa("Telefone", new BigDecimal("55.3"), new LocalDate(2016, 1, 20));
		
		despesas.add(agua);
		despesas.add(luz);
		despesas.add(telefone);
		
		return despesas;	
	}
	
	private static final Set<Despesa> getDespesasVariaveis(){
		Set<Despesa> despesas = new TreeSet<Despesa>();
		
		Despesa combustivel = new Despesa("Combustível", new BigDecimal("150"), new LocalDate(2016, 1, 5));
		Despesa lazer = new Despesa("Lazer", new BigDecimal("250"), new LocalDate(2016, 1, 5));
		Despesa alimentacao = new Despesa("Alimentação", new BigDecimal("400"), new LocalDate(2016, 1, 5));
		
		despesas.add(combustivel);
		despesas.add(lazer);
		despesas.add(alimentacao);
		
		return despesas;	
	}
	
	private static final Set<Categoria> getCategorias() {
		Set<Categoria> categorias = new TreeSet<>();
		categorias.add(new Categoria("alimentacao", "Alimentação"));
		categorias.add(new Categoria("transporte", "Transporte"));
		categorias.add(new Categoria("saude", "Saúde"));
		categorias.add(new Categoria("lazer", "Lazer"));
		categorias.add(new Categoria("moradia", "Moradia"));
		
		return categorias;
	}

}
