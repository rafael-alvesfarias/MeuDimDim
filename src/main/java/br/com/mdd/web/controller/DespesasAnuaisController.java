package br.com.mdd.web.controller;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

import org.joda.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.mdd.domain.model.DespesaFixa;
import br.com.mdd.domain.model.Orcamento;
import br.com.mdd.web.view.model.OrcamentoDespesasAnuaisViewModel;

@Controller
public class DespesasAnuaisController {
	
	@RequestMapping("/despesasAnuais")
	public String despesasAnuais(Model model){
		Orcamento orcamentoDespesasFixas = getOrcamento().anual().comPrevisao().gerar();
		OrcamentoDespesasAnuaisViewModel orcamentoViewModel = new OrcamentoDespesasAnuaisViewModel(orcamentoDespesasFixas);
		model.addAttribute("orcamentoDespesasFixas", orcamentoViewModel);
		return "/despesas/despesasAnuais";
	}
	
	private static final Orcamento getOrcamento(){
		Set<DespesaFixa> despesas = new TreeSet<DespesaFixa>();
		
		DespesaFixa agua = new DespesaFixa("Água", new BigDecimal("45.98"), new LocalDate(2015, 1, 10));
		DespesaFixa luz = new DespesaFixa("Luz", new BigDecimal("98.73"), new LocalDate(2015, 1, 12));
		DespesaFixa telefone = new DespesaFixa("Telefone", new BigDecimal("55.3"), new LocalDate(2015, 1, 20));
		
		despesas.add(agua);
		despesas.add(luz);
		despesas.add(telefone);
		
		return new Orcamento(despesas);	
	}

}
