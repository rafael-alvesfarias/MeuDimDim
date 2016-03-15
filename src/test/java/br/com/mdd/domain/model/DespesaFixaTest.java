package br.com.mdd.domain.model;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import br.com.mdd.domain.model.FixedExpense;

public class DespesaFixaTest {
	
	private static final DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerificaVencimentoDespesaFixa() {
		Integer diaDoMes = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		FixedExpense aluguel = new FixedExpense("Aluguel", new BigDecimal("1200.0"));
		FixedExpense condominio = new FixedExpense("Condomínio",  new BigDecimal("350.0"), LocalDate.now());
		Integer vencimentoAluguel = aluguel.getMaturityDay();
		Integer vencimentoComdominio = condominio.getMaturityDay();
		
		assertEquals(diaDoMes, vencimentoAluguel);
		assertEquals(diaDoMes, vencimentoComdominio);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCriaDespesaFixaComVencimentoInvalido(){
		new FixedExpense("telefone",  new BigDecimal("98.0"), 32);
	}
	
	@Test
	public void testObtemDataDoVencimento(){
		String dataDeHoje = formatador.format(new Date());
		
		FixedExpense aluguel = new FixedExpense("Aluguel",  new BigDecimal("1200.0"));
		FixedExpense condominio = new FixedExpense("Condomínio",  new BigDecimal("350.0"), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		LocalDate dataVencimentoAluguel = aluguel.getDueDate();
		LocalDate dataVencimentoComdominio = condominio.getDueDate();
		
		assertEquals(dataDeHoje, formatador.format(dataVencimentoAluguel.toDate()));
		assertEquals(dataDeHoje, formatador.format(dataVencimentoComdominio.toDate()));
	}

}
