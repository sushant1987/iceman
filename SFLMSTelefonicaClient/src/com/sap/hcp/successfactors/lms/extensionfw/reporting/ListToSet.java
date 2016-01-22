package com.sap.hcp.successfactors.lms.extensionfw.reporting;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sap.hcp.successfactors.lms.extensionfw.pojo.OfferingParticipant;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Participant;

public class ListToSet {
	private Set<Participant> set=new HashSet<Participant>();
	public Set<Participant> convert(List<OfferingParticipant> arr){
		for(OfferingParticipant obj: arr){
			Participant p = new Participant();
			p.setParticipantId(obj.getParticipantId());
			p.setName(obj.getName());
			p.setFirstSurname(obj.getFirstSurname());
			p.setSecondSurname(obj.getSecondSurname());
			p.setDateOfBirth(obj.getDateOfBirth());
			p.setEmail(obj.getEmail());
			p.setPhoneNumber(obj.getPhoneNumber());
			p.setSex(obj.getSex());
			set.add(p);
			
		}
		return set;
	}
}
