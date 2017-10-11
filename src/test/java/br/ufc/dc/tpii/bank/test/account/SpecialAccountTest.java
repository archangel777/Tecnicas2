package br.ufc.dc.tpii.bank.test.account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufc.dc.tpii.bank.account.SpecialAccount;
import br.ufc.dc.tpii.bank.account.exception.InsufficientFundsException;
import br.ufc.dc.tpii.bank.account.exception.NegativeAmountException;

public class SpecialAccountTest {
	
	SpecialAccount account;
	
	@Before
	public void setUp() {
		account = new SpecialAccount("123C");
	}

	@Test
	public void testDebit() {
		try {
			account.credit(50);
			assertEquals(50, account.getBalance(), 0);
			account.debit(30);
			assertEquals(20, account.getBalance(), 0);
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
	
	@Test
	public void testBonus() {
		try {
			account.credit(50);
			assertEquals("Erro no bonus", 0.5, account.getBonus(), 0);
		} catch (NegativeAmountException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testEarnBonus() {
		try {
			account.credit(50);
			account.earnBonus();
			assertEquals("Erro no credito depois de ganhar o bonus", 50.5, account.getBalance(), 0);
			assertEquals("Erro no bonus depois de zerar", 0, account.getBonus(), 0);
		} catch (NegativeAmountException e) {
			e.printStackTrace();
		}
	}

}
