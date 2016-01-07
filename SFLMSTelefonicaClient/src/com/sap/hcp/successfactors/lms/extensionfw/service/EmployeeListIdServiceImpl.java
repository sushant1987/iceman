package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.EmployeeListId;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.ItemListId;

@Service
@Transactional
public class EmployeeListIdServiceImpl implements EmployeeListIdService{

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<EmployeeListId> getAll() {
		Query query=entityManager.createQuery("Select employeelistid from EmployeeListId employeelistid");
	    List<EmployeeListId> queryemployeelistid=query.getResultList();
		return queryemployeelistid;
	}

	@Override
	public void save(List<EmployeeListId> list) {
		for(EmployeeListId employee: list){
			entityManager.persist(employee);
		}
	}

	@Override
	public void delete() {
		entityManager.createQuery("delete from EmployeeListId employeelistid").executeUpdate();
	}

}
