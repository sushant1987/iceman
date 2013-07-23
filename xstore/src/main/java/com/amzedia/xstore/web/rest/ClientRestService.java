/**
 * 
 */
package com.amzedia.xstore.web.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.model.Group;
import com.amzedia.xstore.model.ListResponseWrapper;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.services.interfaces.IClientService;

/**
 * @author Sushant
 * 
 */
@Controller
@RequestMapping(value = "/client")
public class ClientRestService {

	@Autowired
	private IClientService clientService;

	// TODO update client

	/**
	 * This api will bring the client information by passing id of client
	 * 
	 * @author Sushant
	 * @param String
	 * @return Client
	 */

	@RequestMapping(value = "/{id}")
	@ResponseBody
	public ResponseWrapper getClientById(@PathVariable String id) {
		int clientId = Integer.parseInt(id);
		try {
			return this.clientService.getClient(clientId);
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This api will save client
	 * 
	 * @param Client
	 * @return boolean
	 */
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseBody
	public ResponseWrapper registerClient(@RequestBody Client client) {
		return this.clientService.registerClient(client);
	}

	/**
	 * This api will update client info
	 * 
	 * @param Client
	 *                TODO
	 * @return boolean
	 */
	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseWrapper updaterClient(@RequestBody Client client) {
		try {
			return this.clientService.updateClient(client);
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseWrapper loginClient(@RequestBody Client client) {
		try {
			return this.clientService.loginClient(client);
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/activateordeactivate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseWrapper deactivateOrActivateClient(
			@RequestBody Client client) {
		try {
			return this.clientService
					.deactivateOrActivateClient(client);
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET /*
								     * headers=
								     * "Accept=application/xml, application/json"
								     */)
	@ResponseBody
	public ModelAndView test() {
		Customer user = new Customer();
		user.setCustomerName("Sushant");
		user.setId(0);
		user.setCustomerType("Super");
		ModelAndView mav = new ModelAndView();
		mav.addObject(user);
		mav.setViewName("sushant0");
		return mav;
	}

	@RequestMapping(value = "/boolean", method = RequestMethod.GET /*
								        * headers=
								        * "Accept=application/xml, application/json"
								        */)
	@ResponseBody
	public Model testboolean(Model mav) {
		mav.addAttribute("bc", true);
		return mav;
	}

	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	@ResponseBody
	public ResponseWrapper dummy() {
		try {
			return this.clientService.dummy();
		} catch (XstoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/getdummyclient", method = RequestMethod.GET)
	@ResponseBody
	public Client dummyClient() {
		Client client = new Client();
		BasicInfo info = new BasicInfo();
		info.setAddress("na");
		info.setCity("NYC");
		info.setCountry("States");
		info.setEmail("abc@dada.com");
		info.setFax("1245700");
		info.setFirstName("yes papa");
		info.setLastName("jonny jonny");
		info.setMiddleName("no");
		info.setPhoneNumber("9793234212");
		info.setPin("2112");
		info.setState("no states");
		client.setStatus(true);
		client.setPassword("123456");
		client.setUserName("jonny");
		client.setPlanType("A");
		client.setBasicInfo(info);
		return client;
	}
	
	@RequestMapping(value = "/groups/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ListResponseWrapper getAllGroupsByClient(@PathVariable String id) {
		int clientId = Integer.parseInt(id);
		ListResponseWrapper listResponseWrapper = new ListResponseWrapper();
		listResponseWrapper = this.clientService.getAllGroupByClient(clientId);
		return listResponseWrapper;
		
	}

}
