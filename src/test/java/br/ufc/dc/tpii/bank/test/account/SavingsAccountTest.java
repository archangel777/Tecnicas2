package br.ufc.dc.tpii.bank.test.account;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufc.dc.tpii.bank.account.SavingsAccount;
import br.ufc.dc.tpii.bank.account.exception.InsufficientFundsException;
import br.ufc.dc.tpii.bank.account.exception.NegativeAmountException;

public class SavingsAccountTest {
	
	SavingsAccount account;
	
	@Before
	public void setUp() {
		account = new SavingsAccount("123B");
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

}
