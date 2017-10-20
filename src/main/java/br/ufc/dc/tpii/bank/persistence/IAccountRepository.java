package br.ufc.dc.tpii.bank.persistence;

import br.ufc.dc.tpii.bank.account.AbstractAccount;
import br.ufc.dc.tpii.bank.persistence.exception.AccountCreationException;
import br.ufc.dc.tpii.bank.persistence.exception.AccountDeletionException;
import br.ufc.dc.tpii.bank.persistence.exception.AccountNotFoundException;
import br.ufc.dc.tpii.bank.persistence.exception.FlushException;

public interface IAccountRepository {

	void create(AbstractAccount account) throws AccountCreationException;

	void delete(String number) throws AccountDeletionException;

	AbstractAccount retrieve(String number) throws AccountNotFoundException;

	AbstractAccount[] list();

	int mumberOfAccounts();

	void flush() throws FlushException;
}
