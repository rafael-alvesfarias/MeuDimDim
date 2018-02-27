package br.com.mdd.application.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.mdd.domain.model.Investment;
import br.com.mdd.persistence.dao.GenericDAO;
import br.com.mdd.presentation.view.model.investment.InvestmentViewModel;

@Controller
public class InvestmentController {
	
	private InvestmentViewModel investment;
	
	@Autowired
	@Qualifier("genericDAO")
	private GenericDAO<Investment> investmentDAO;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping(value = "/newInvestment", method = RequestMethod.GET)
	public String newInvestment(Model model){
		investment = new InvestmentViewModel();
		model.addAttribute("investment", investment);
		
		return "/investments/newInvestment";
	}

	@RequestMapping(value = "/investment", method = RequestMethod.POST)
	public String saveIncome(@ModelAttribute(value = "investment") InvestmentViewModel investment, Model model) {
		Investment i = new Investment(investment.getName(), investment.getValue(), new LocalDate(investment.getDueDate()), investment.getReturnRate());
		i.setId(investment.getId());
		
		investmentDAO.save(i);
		
		return newInvestment(model);
	}
	
	@Transactional(readOnly=true)
	@RequestMapping("/updateInvestment/{id}")
	public String updateInvestment(Model model, @PathVariable Integer id){
		
		Investment i = investmentDAO.find(id, Investment.class);
		
		investment = InvestmentViewModel.fromInvestment(i);
		
		model.addAttribute("investment", investment);
		
		return "/investments/editInvestment";
	}
	
	@Transactional
	@RequestMapping(value="/deleteInvestment/{id}")
	public String deleteInvestment(Model model, @PathVariable Integer id, @RequestParam(required=false) Integer mes) {
		Investment investment = investmentDAO.find(id, Investment.class);
		//Verifica se investimento não gerada automaticamente
		if (mes != null && mes > investment.getDueDate().getMonthOfYear()) {
			@SuppressWarnings("unchecked")
			Map<Integer, Integer> exclusionsMap = (Map<Integer, Integer>) session.getAttribute("exclusionsMap");
			if (exclusionsMap == null) {
				exclusionsMap = new HashMap<Integer, Integer>();
				session.setAttribute("exclusionsMap", exclusionsMap);
			}
			exclusionsMap.put(id, mes);
		} else {
			investmentDAO.remove(investment);
		}
		
		return "/home";
	}
}
