package br.com.mdd.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CrudDespesaController {
	
	@RequestMapping("/despesa")
	public String nova() {
		return "/despesas/despesa";
	}
	
	@RequestMapping(value = "/despesa", method = RequestMethod.POST)
	public String salvar(Model model) {
		return null;
	}

}
