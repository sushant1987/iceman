package com.sap.hcp.successfactors.lms.extensionfw.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.hcp.successfactors.lms.extensionfw.multitenancy.CurrentTenantResolver;
import com.sap.hcp.successfactors.lms.extensionfw.pojo.Parametrised;

@Service
@Transactional
public class ParametrisedServiceImpl implements ParametrisedService{
	
	@Autowired
	private CurrentTenantResolver currentTenantResolver;
	
	//private static final Logger logger = LoggerFactory.getLogger(ParametrisedServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public List<Parametrised> getAllParametrisedData() {
		
		entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, currentTenantResolver.getCurrentTenantId());
		
		Query queryParametrised = entityManager.createQuery(
                "select parametrised from Parametrised parametrised where 1 = 1 order by parametrised.paramName, parametrised.startDate",
                Parametrised.class);
        List<Parametrised> queryParametrisedList = queryParametrised.getResultList();

		return queryParametrisedList;
	}
	
	@Override
	public Parametrised mergeParametrisedData(Parametrised parametrised) {
		
		entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, currentTenantResolver.getCurrentTenantId());
		Parametrised parametrisedReturn = new Parametrised();
        
        if(parametrised.getId() != null){
            
            Parametrised parametrisedExsit = entityManager.find(Parametrised.class, parametrised.getId());
            
            if(parametrisedExsit != null && parametrisedExsit.getStartDate().compareTo(parametrised.getStartDate()) < 0 
                    && parametrisedExsit.getEndDate().compareTo(parametrised.getStartDate()) >= 0) {
                
                Calendar cd = Calendar.getInstance(); 
                cd.setTime(parametrised.getStartDate()); 
                cd.add(Calendar.DATE, -1);
                parametrisedExsit.setEndDate(cd.getTime());
                
                entityManager.merge(parametrisedExsit);
                
                entityManager.flush();
                
                parametrised.setId(null);
                
                parametrisedReturn = entityManager.merge(parametrised);
            }else if(parametrisedExsit != null && parametrisedExsit.getStartDate().compareTo(parametrised.getStartDate()) == 0 
                    && parametrisedExsit.getEndDate().compareTo(parametrised.getEndDate()) == 0){
                
                parametrisedReturn = entityManager.merge(parametrised);
                entityManager.flush();
            }
            
        } else {
			
			entityManager.persist(parametrised);
			parametrisedReturn = parametrised;
		}
        
        return parametrisedReturn;
    }
	
	@Override
	public void saveParametriseddata(Parametrised parametrised){
		entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, currentTenantResolver.getCurrentTenantId());
		entityManager.persist(parametrised);
	}
	
	@Override
	public void deleteParametrisedData(Parametrised parametrised) {
		entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, currentTenantResolver.getCurrentTenantId());
		
		Parametrised parametrisedForDelete = entityManager.find(Parametrised.class, parametrised.getId());
		
		entityManager.remove(parametrisedForDelete);
	}

	@Override
	public List<Parametrised> getParametrisedDataByType(String paramType) {
		entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, currentTenantResolver.getCurrentTenantId());
		Query queryParametrised = entityManager.createQuery(
                "select parametrised from Parametrised parametrised where parametrised.paramType = '"+ paramType + "' order by parametrised.paramName, parametrised.startDate",
                Parametrised.class);
        List<Parametrised> queryParametrisedList = queryParametrised.getResultList();

		return queryParametrisedList;
	}
	
	@Override 
	public List<Parametrised> getParametrisedDataForReport(String paramType){
		entityManager.setProperty(PersistenceUnitProperties.MULTITENANT_PROPERTY_DEFAULT, currentTenantResolver.getCurrentTenantId());
		Query queryParametrised = entityManager.createQuery(
                "select parametrised from Parametrised parametrised where parametrised.paramType = '"+ paramType + "' and '"+(new Date(System.currentTimeMillis()))+"' between parametrised.startDate and parametrised.endDate order by parametrised.paramName, parametrised.startDate",
                Parametrised.class);
		  List<Parametrised> queryParametrisedList = queryParametrised.getResultList();

			return queryParametrisedList;
	}

}
