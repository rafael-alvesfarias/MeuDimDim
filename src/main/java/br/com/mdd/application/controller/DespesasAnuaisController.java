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

import br.com.mdd.domain.model.Despesa;
import br.com.mdd.domain.model.DespesaFixa;
import br.com.mdd.domain.model.Orcamento;
import br.com.mdd.presentation.model.OrcamentoDespesasAnuaisViewModel;

@Controller
public class DespesasAnuaisController {
	
	@RequestMapping("/despesasAnuais")
	public String despesasAnuais(Model model){
		Orcamento orcamentoDespesasFixas = getOrcamento().anual().comPrevisao().gerar();
		OrcamentoDespesasAnuaisViewModel orcamentoViewModel = new OrcamentoDespesasAnuaisViewModel(orcamentoDespesasFixas);
		model.addAttribute("orcamentoDespesasFixas", orcamentoViewModel);
		model.addAttribute("despesa", new DespesaFixa());
		return "/despesas/despesasAnuais";
	}
	
	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	public String salvar(@ModelAttribute(value = "despesa") DespesaFixa despesa, Model model) {
		return despesasAnuais(model);
	}
	
	private static final Orcamento getOrcamento(){
		Set<Despesa> despesas = new TreeSet<Despesa>();
		
		DespesaFixa agua = new DespesaFixa("Água", new BigDecimal("45.98"), new LocalDate(2016, 1, 10));
		DespesaFixa luz = new DespesaFixa("Luz", new BigDecimal("98.73"), new LocalDate(2016, 1, 12));
		DespesaFixa telefone = new DespesaFixa("Telefone", new BigDecimal("55.3"), new LocalDate(2016, 1, 20));
		
		despesas.add(agua);
		despesas.add(luz);
		despesas.add(telefone);
		
		return new Orcamento(despesas);	
	}

}
