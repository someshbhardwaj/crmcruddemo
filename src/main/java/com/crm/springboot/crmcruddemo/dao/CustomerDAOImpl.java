package com.crm.springboot.crmcruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.crm.springboot.crmcruddemo.entity.Customer;


@Repository
public class CustomerDAOImpl implements CustomerDAO {

	// need to inject the session factory
	
	//private SessionFactory sessionFactory;
	private EntityManager sessionFactory;

	@Autowired		
	CustomerDAOImpl(EntityManager theSessionFactory){
		sessionFactory = theSessionFactory;
	}
	@Override
	public List<Customer> getCustomers() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.unwrap(Session.class);
				
		// create a query  ... sort by last name
		Query<Customer> theQuery = 
				currentSession.createQuery("from Customer order by lastName",
											Customer.class);
		
		// execute query and get result list
		List<Customer> customers = theQuery.getResultList();
				
		// return the results		
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {

		// get current hibernate session
		Session currentSession = sessionFactory.unwrap(Session.class);
		
		// save/upate the customer ... finally LOL
		currentSession.saveOrUpdate(theCustomer);
		
	}

	@Override
	public Customer getCustomer(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.unwrap(Session.class);
		
		// now retrieve/read from database using the primary key
		Customer theCustomer = currentSession.get(Customer.class, theId);
		
		return theCustomer;
	}

	@Override
	public void deleteCustomer(int theId) {

		// get the current hibernate session
		Session currentSession = sessionFactory.unwrap(Session.class);
		
		// delete object with primary key
		Query theQuery = 
				currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();		
	}

	@Override
	@Transactional
	public List<Customer> searchCustomers(String theSearchName){
		System.out.println("Inside DAO IMPL the ... search variable:  "+theSearchName);

		if(sessionFactory!=null) {
		Session currentSession = sessionFactory.unwrap(Session.class);
		Query theQuery = null;
		System.out.println("Inside DAO IMPL the search variable 111:  "+theSearchName);

		if(theSearchName != null && theSearchName.trim().length()>0){
			//theQuery = currentSession.createQuery("from Customer where name=: customerName");
			theQuery = currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
			theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

			//theQuery.setParameter("customerName", theSearchName);
			
		}
		else{
			theQuery = currentSession.createQuery("from Customer", Customer.class);
			
		}
		List<Customer> customersList = theQuery.getResultList();
		return customersList;
		}
		
		else {
			System.out.println("Session is not valid");
			return null;
		}
	}
	

}











