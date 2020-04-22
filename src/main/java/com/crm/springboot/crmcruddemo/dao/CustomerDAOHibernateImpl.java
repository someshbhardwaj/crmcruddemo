package com.crm.springboot.crmcruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.crm.springboot.crmcruddemo.entity.Customer;

@Repository
public class CustomerDAOHibernateImpl implements CustomerDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public CustomerDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	

	@Override
	@Transactional
	public List<Customer> getCustomers() {
		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// create a query
		Query<Customer> theQuery =
				currentSession.createQuery("from Customer", Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
		
		// return the results		
		return customers;
	}


	@Override
	@Transactional
	public void saveCustomer(Customer theCustomer) {
			Session currentSession = entityManager.unwrap(Session.class);
			System.out.println("Update or save request");
			currentSession.saveOrUpdate(theCustomer);
	}


	@Override
	@Transactional
	public Customer getCustomer(int theId) {
		
		Session currentSession = entityManager.unwrap(Session.class);	
		Customer theCustomer = currentSession.get(Customer.class, theId);			
		return theCustomer;
		
	}


	@Override
	@Transactional
	public void deleteCustomer(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery = currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		theQuery.executeUpdate();		
	}


	@Override
	@Transactional
	public List<Customer> searchCustomers(String theSearchName) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query theQuery;
		if(theSearchName != null && theSearchName.trim().length()>0){
			//theQuery = currentSession.createQuery("from Customer", Customer.class);
			
			
				//theQuery = currentSession.createQuery("from Customer where name=: customerName");
				theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
				theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");
		}else {
			
			theQuery = currentSession.createQuery("from Customer", Customer.class);
		}
		
		List<Customer> customers = theQuery.getResultList();
		
		return customers;
	}

}







