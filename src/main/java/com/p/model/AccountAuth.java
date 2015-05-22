package com.p.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AccountAuth implements Serializable {

	private static final long serialVersionUID = 5733967566904320005L;

	private List<Account> accounts;

	public AccountAuth(List<Account> accounts) {
		super();
		this.accounts = accounts;
	}

	public AccountAuth() {
		super();
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
}
