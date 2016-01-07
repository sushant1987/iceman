package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.OfferingListId;

@Service
@Transactional
public class OfferingListIdServiceImpl implements OfferingListIdService{ 
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<OfferingListId> getAll() {
		Query query=entityManager.createQuery("Select offeringlistid from OfferingListId offeringlistid");
	    List<OfferingListId> queryofferinglistid=query.getResultList();
		return queryofferinglistid;
	}

	@Override
	public void save(List<OfferingListId> list) {
		for(OfferingListId offering: list){
			entityManager.persist(offering);
		}
	}

	@Override
	public void delete() {
		entityManager.createQuery("delete from OfferingListId offeringlistid").executeUpdate();
	}

}
