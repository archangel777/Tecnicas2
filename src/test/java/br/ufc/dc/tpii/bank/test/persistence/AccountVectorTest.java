package br.ufc.dc.tpii.bank.test.persistence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufc.dc.tpii.bank.account.OrdinaryAccount;
import br.ufc.dc.tpii.bank.persistence.AccountVector;
import br.ufc.dc.tpii.bank.persistence.exception.AccountCreationException;
import br.ufc.dc.tpii.bank.persistence.exception.AccountDeletionException;
import br.ufc.dc.tpii.bank.persistence.exception.AccountNotFoundException;

public class AccountVectorTest {
	
	AccountVector vector;
	
	OrdinaryAccount simple;
	
	@Before
	public void setUp() {
		vector = new AccountVector();
		simple = new OrdinaryAccount("123A");
	}
	
	@Test
	public void testCreate() throws AccountCreationException {
		vector.create(simple);
		assertEquals("Erro ao adicionar conta", simple, vector.list()[0]);
	}

	@Test
	public void testDelete() throws AccountDeletionException, AccountCreationException {
		vector.create(simple);
		vector.delete("123A");
		assertArrayEquals("Erro no delete", null, vector.list()); 
	}

	@Test
	public void testRetrieve() throws AccountCreationException {
		vector.create(simple);
		try {
			assertEquals("Erro no retrieve", simple, vector.retrieve("123A"));
		} catch (AccountNotFoundException e) {
			fail("Conta nao achada.");
		}
	}

	@Test
	public void testMumberOfAccounts() throws AccountCreationException {
		assertEquals("Erro ao pedir numero de contas.", 0, vector.mumberOfAccounts());
		vector.create(new OrdinaryAccount("123A"));
		vector.create(new OrdinaryAccount("123B"));
		assertEquals("Erro ao pedir numero de contas.", 2, vector.mumberOfAccounts());
		vector.create(new OrdinaryAccount("123C"));
		vector.create(new OrdinaryAccount("123B"));
		vector.create(new OrdinaryAccount("123E"));
		assertEquals("Erro ao pedir numero de contas.", 5, vector.mumberOfAccounts());
	}

	@Test
	public void testList() throws AccountCreationException {
		OrdinaryAccount a1 = new OrdinaryAccount("123A");
		OrdinaryAccount a2 = new OrdinaryAccount("123B");
		vector.create(a1);
		vector.create(a2);
		OrdinaryAccount[] list = new OrdinaryAccount[]{a1, a2};
		assertArrayEquals("Erro na lista", list, vector.list());
	}

}
