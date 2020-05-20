package com.crm.springboot.crmcruddemo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.springboot.crmcruddemo.entity.Customer;

public interface CustomerDAOJPA extends JpaRepository<Customer, Integer> {

}

/*
 * Below code can also be used
 * 
 *  import org.springframework.data.domain.Pageable;
	import org.springframework.data.repository.PagingAndSortingRepository;
	public interface CustomerDAOJPA extends PagingAndSortingRepository<PagingEntity, Integer> {
	}
 */


