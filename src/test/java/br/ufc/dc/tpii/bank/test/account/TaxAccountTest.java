package br.ufc.dc.tpii.bank.test.account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufc.dc.tpii.bank.account.TaxAccount;
import br.ufc.dc.tpii.bank.account.exception.InsufficientFundsException;
import br.ufc.dc.tpii.bank.account.exception.NegativeAmountException;

public class TaxAccountTest {
	
	TaxAccount account;
	
	@Before
	public void setUp() {
		account = new TaxAccount("123D");
	}

	@Test
	public void testDebit() {
		try {
			account.credit(50);
			assertEquals(50, account.getBalance(), 0);
			account.debit(30);
			assertEquals(50 - 30*1.001, account.getBalance(), 0.01);
		} catch (NegativeAmountException | InsufficientFundsException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCredit() {
		try {
			account.credit(50);
		} catch (NegativeAmountException e) {
			e.printStackTrace();
		}
		assertEquals(50, account.getBalance(), 0);
	}

}
