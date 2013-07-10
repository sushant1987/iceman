/**
 * 
 */
package com.amzedia.xstore.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Store;
import com.amzedia.xstore.services.interfaces.IStoreService;

/**
 * @author Tarun/Kirti
 * 
 */
@Controller
@RequestMapping(value = "/store")
public class StoreRestService {

	@Autowired
	private IStoreService storeService;

	/**
	 * This api will add new store
	 * 
	 * @param Store
	 * @return boolean TODO
	 */
	@RequestMapping(value = "/addstore", method = RequestMethod.POST)
	@ResponseBody
	public boolean addStore(@RequestBody Store store) {
		return this.storeService.addStore(store);
	}

	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	@ResponseBody
	public Store dummy() {
		Store store = new Store();
		Client client = new Client();
		client.setId(5);
		store.setId(1);
		store.setName("nan");
		store.setTimeZone("IST");
		store.setCurrency("Rs.");
		store.setClient(client);
		return store;

	}
}
