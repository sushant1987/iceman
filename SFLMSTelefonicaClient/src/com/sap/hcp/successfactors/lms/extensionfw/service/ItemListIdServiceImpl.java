package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.ItemListId;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.ReportInfo;

@Service
@Transactional
public class ItemListIdServiceImpl implements ItemListIdService{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<ItemListId> getAll() {
		Query query=entityManager.createQuery("Select itemlistid from ItemListId itemlistid");
	    List<ItemListId> queryitemlistid=query.getResultList();
		return queryitemlistid;
	}

	@Override
	public void save(List<ItemListId> list) {
		for(ItemListId item: list){
			entityManager.persist(item);
		}
	}
	
	@Override
	public void delete(){
		entityManager.createQuery("delete from ItemListId itemlistid").executeUpdate();
	}

}
