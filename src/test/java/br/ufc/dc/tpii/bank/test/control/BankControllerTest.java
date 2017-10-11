package br.ufc.dc.tpii.bank.test.control;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufc.dc.tpii.bank.account.OrdinaryAccount;
import br.ufc.dc.tpii.bank.account.SavingsAccount;
import br.ufc.dc.tpii.bank.account.SpecialAccount;
import br.ufc.dc.tpii.bank.account.exception.NegativeAmountException;
import br.ufc.dc.tpii.bank.control.BankController;
import br.ufc.dc.tpii.bank.control.exception.BankTransactionException;
import br.ufc.dc.tpii.bank.control.exception.IncompatibleAccountException;
import br.ufc.dc.tpii.bank.persistence.AccountVector;

public class BankControllerTest {
	
	AccountVector vector;
	BankController controller;
	
	OrdinaryAccount simple;
	SavingsAccount savings;
	SpecialAccount special;
	
	@Before
	public void setUp() {
		vector = new AccountVector();
		controller = new BankController(vector);
		simple = new OrdinaryAccount("123A");
		savings = new SavingsAccount("123C");
		special = new SpecialAccount("123D");
	}

	@Test
	public void testAddAccount() {
		try {
			controller.addAccount(simple);
			assertEquals("Erro ao adicionar conta", simple, vector.list()[vector.list().length-1]);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testRemoveAccount() {
		try {
			controller.addAccount(simple);
			controller.removeAccount("123A");
			assertArrayEquals("Erro ao remover conta", null, vector.list());
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDoCredit() {
		try {
			controller.addAccount(simple);
			controller.doCredit("123A", 20);
			assertEquals("Erro ao creditar conta", 20, simple.getBalance(), 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDoDebit() {
		try {
			controller.addAccount(simple);
			controller.doCredit("123A", 50);
			controller.doDebit("123A", 23);
			assertEquals("Erro ao debitar conta", 27, simple.getBalance(), 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetBalance() {
		try {
			controller.addAccount(simple);
			controller.doCredit("123A", 40);
			assertEquals("Erro no getBalance", 40, controller.getBalance("123A"), 0);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDoTransfer() {
		try {
			controller.addAccount(simple);
			controller.addAccount(savings);
			simple.credit(20);
			savings.credit(20);
			controller.doTransfer("123A", "123C", 10);
			assertEquals("Erro na transferencia conta Simples", 10, simple.getBalance(), 0.0);
			assertEquals("Erro na transferencia conta Poupanca", 30, savings.getBalance(), 0.0);
		} catch (NegativeAmountException | BankTransactionException e) {
			e.printStackTrace();
		}
	}
	
	@Test (expected = IncompatibleAccountException.class)
	public void testDoEarnInterestWrong() throws IncompatibleAccountException, BankTransactionException {
		try {
			controller.addAccount(simple);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
		controller.doEarnInterest("123A");
	}

	@Test
	public void testDoEarnInterest() {
		try {
			controller.addAccount(savings);
			savings.credit(20);
		} catch (NegativeAmountException | BankTransactionException e) {
			e.printStackTrace();
		}
		
		try {
			controller.doEarnInterest("123C");
			assertEquals("Erro no earnInterest", 20.02, controller.getBalance("123C"), 0);
		} catch(BankTransactionException e) { e.printStackTrace(); }
	}
	
	@Test (expected = IncompatibleAccountException.class)
	public void testDoEarnBonusWrong() throws IncompatibleAccountException, BankTransactionException {
		try {
			controller.addAccount(simple);
		} catch (BankTransactionException e) {
			e.printStackTrace();
		}
		controller.doEarnBonus("123A");
	}

	@Test
	public void testDoEarnBonus() {
		try {
			controller.addAccount(special);
			special.credit(20);
		} catch (NegativeAmountException | BankTransactionException e) {
			e.printStackTrace();
		}

		try {
			controller.doEarnBonus("123D");
			assertEquals("Erro no earnBonus", 20.2, controller.getBalance("123D"), 0);
			assertEquals("earnBonus nao seta bonus para 0", 0, special.getBonus(), 0);
		} catch(BankTransactionException e) { e.printStackTrace(); }
	}

}
