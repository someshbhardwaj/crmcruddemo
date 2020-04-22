package com.crm.springboot.crmcruddemo.service;

import java.util.List;

import com.crm.springboot.crmcruddemo.entity.Customer;
//luv2code.springdemo.entity.Customer;

public interface CustomerService {

	public List<Customer> getCustomers();

	public void saveCustomer(Customer theCustomer);

	public Customer getCustomer(int theId);

	public void deleteCustomer(int theId);
	
	public List<Customer> searchCustomers(String theSearchName);
	
}
