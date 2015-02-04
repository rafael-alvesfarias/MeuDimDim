package br.com.mdd.domain;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import br.com.mdd.domain.DespesaFixa;

public class DespesaFixaTest {
	
	private static final DateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testVerificaVencimentoDespesaFixa() {
		Integer diaDoMes = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		
		DespesaFixa aluguel = new DespesaFixa("Aluguel", new BigDecimal("1200.0"));
		DespesaFixa condominio = new DespesaFixa("Condomínio",  new BigDecimal("350.0"), LocalDate.now());
		Integer vencimentoAluguel = aluguel.getVencimento();
		Integer vencimentoComdominio = condominio.getVencimento();
		
		assertEquals(diaDoMes, vencimentoAluguel);
		assertEquals(diaDoMes, vencimentoComdominio);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCriaDespesaFixaComVencimentoInvalido(){
		new DespesaFixa("telefone",  new BigDecimal("98.0"), 32);
	}
	
	@Test
	public void testObtemDataDoVencimento(){
		String dataDeHoje = formatador.format(new Date());
		
		DespesaFixa aluguel = new DespesaFixa("Aluguel",  new BigDecimal("1200.0"));
		DespesaFixa condominio = new DespesaFixa("Condomínio",  new BigDecimal("350.0"), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		LocalDate dataVencimentoAluguel = aluguel.getDataLancamento();
		LocalDate dataVencimentoComdominio = condominio.getDataLancamento();
		
		assertEquals(dataDeHoje, formatador.format(dataVencimentoAluguel.toDate()));
		assertEquals(dataDeHoje, formatador.format(dataVencimentoComdominio.toDate()));
	}

}
