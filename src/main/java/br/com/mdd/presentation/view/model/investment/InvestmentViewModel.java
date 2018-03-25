package br.com.mdd.presentation.view.model.investment;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import br.com.mdd.domain.model.Investment;
import br.com.mdd.presentation.view.model.EntryViewModel;

public class InvestmentViewModel extends EntryViewModel {

	@NumberFormat(style = Style.NUMBER)
	private BigDecimal returnRate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date withdrawlDate;
	
	@NumberFormat(style = Style.NUMBER)
	private BigDecimal taxRate;
	
	public static InvestmentViewModel fromInvestment(Investment i) {
		InvestmentViewModel viewModel = new InvestmentViewModel();
		if (i.getDueDate() != null) {
			viewModel.setDueDate(i.getDueDate().toDate());
		}
		viewModel.setId(i.getId());
		viewModel.setName(i.getName());
		viewModel.setReturnRate(i.getReturnRate());
		viewModel.setTaxRate(i.getTaxRate());
		viewModel.setValue(i.getValue());
		if (i.getEntryDate() != null) {
			viewModel.setEntryDate(i.getEntryDate().toDate());
		}
		if (i.getWithdrawlDate() != null) {
			viewModel.setWithdrawlDate(i.getWithdrawlDate().toDate());
		}
		viewModel.setPosted(i.isPosted());
		if (i.getAccount() != null) {
			viewModel.setAccount(i.getAccount().getId());
		}
		
		return viewModel;
	}


	public Date getWithdrawlDate() {
		return withdrawlDate;
	}

	public void setWithdrawlDate(Date withdrawlDate) {
		this.withdrawlDate = withdrawlDate;
	}

	public BigDecimal getReturnRate() {
		return returnRate;
	}

	public void setReturnRate(BigDecimal returnRate) {
		this.returnRate = returnRate;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
}
