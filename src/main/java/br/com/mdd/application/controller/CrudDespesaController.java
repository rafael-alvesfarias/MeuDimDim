package br.com.mdd.application.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CrudDespesaController {
	
	@RequestMapping(value = "/despesa", method = RequestMethod.GET)
	public String nova() {
		return "/despesas/despesa";
	}

}
