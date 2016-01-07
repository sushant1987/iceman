package com.sap.hcp.successfactors.lms.extensionfw.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "ITEMLISTID")
public class ItemListId {
	
	@Id
	@SequenceGenerator(name = "itemlistid_seq", sequenceName = "SEQ_ITEMLISTID", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itemlistid_seq")
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "ITEM_ID")
	private Long itemId;
	
	@Column(name = "LEGALENTITY")
	private String legalEntity;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

}
