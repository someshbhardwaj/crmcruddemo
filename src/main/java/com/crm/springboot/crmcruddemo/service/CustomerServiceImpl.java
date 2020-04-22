package com.crm.springboot.crmcruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.springboot.crmcruddemo.dao.CustomerDAO;
//luv2code.springdemo.dao.CustomerDAO;
import com.crm.springboot.crmcruddemo.entity.Customer;

@Service
public class CustomerServiceImpl implements CustomerService {

	
	private CustomerDAO customerDAO;
	
	// need to inject customer dao
	@Autowired
	CustomerServiceImpl(@Qualifier("customerDAOHibernateImpl") CustomerDAO theCustomerDAO){
		customerDAO = theCustomerDAO;
	}
	@Override
	//@Transactional
	public List<Customer> getCustomers() {
		return customerDAO.getCustomers();
	}

	@Override
	//@Transactional
	public void saveCustomer(Customer theCustomer) {

		customerDAO.saveCustomer(theCustomer);
	}

	@Override
	//@Transactional
	public Customer getCustomer(int theId) {
		
		return customerDAO.getCustomer(theId);
	}

	@Override
	//@Transactional
	public void deleteCustomer(int theId) {
		
		customerDAO.deleteCustomer(theId);
	}

	@Override
	public List<Customer> searchCustomers(String theSearchName) {
		System.out.println("Inside the Service Impl "+theSearchName);
		return customerDAO.searchCustomers(theSearchName);
	}

}





