/**
 * 
 */
package com.amzedia.xstore.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.Client;
import com.amzedia.xstore.model.User;
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
	
	/**
	 * This api will save client
	 * @param Client
	 * @return boolean
	 */
	@RequestMapping(value = "/registerclient", method = RequestMethod.POST )
	@ResponseBody public boolean registerClient(@RequestBody Client client) {
		return this.clientService.registerClient(client);
	}
	
	/**
	 * This api will update client info
	 * @param Client TODO
	 * @return boolean
	 */
	@RequestMapping(value = "/updateclient", method = RequestMethod.POST )
	@ResponseBody public boolean updaterClient(@RequestBody Client client) {
		return this.clientService.updateClient(client);
	}
	
	//TODO update client
	
	/**
	 * This api will bring the client
	 * information by passing id of client
	 * 
	 * @author Sushant
	 * @param String
	 * @return Client
	 */
	
	@RequestMapping(value="/findclient/{id}")
	@ResponseBody public Client getClientById(@PathVariable String id) {
		int clientId = Integer.parseInt(id);
		return this.clientService.getClient(clientId);
	}
	
	//TODO delete client
	
	//TODO get by user name and password
		
	@RequestMapping(value = "/test", method = RequestMethod.GET /*headers="Accept=application/xml, application/json"*/)
	@ResponseBody public ModelAndView test() {
		User user = new User();
		user.setUserName("Sushant");
		user.setId(0);
		user.setUserType("Super");
		ModelAndView mav = new ModelAndView();
		mav.addObject(user);
		mav.setViewName("sushant0");
		return mav;
	}
	
	@RequestMapping(value = "/boolean", method = RequestMethod.GET /*headers="Accept=application/xml, application/json"*/)
	@ResponseBody public Model testboolean(Model mav) {
		mav.addAttribute("bc",true);
		return mav;
	}
	
	@RequestMapping(value = "/dummy", method = RequestMethod.GET )
	@ResponseBody public boolean dummy() {
		return this.clientService.dummy();
	}
	
	@RequestMapping(value = "/getdummyclient", method = RequestMethod.GET )
	@ResponseBody public Client dummyClient() {
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
		client.setBasicInfo(info);
		return client;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody public Client loginClient(@RequestBody Client client) {
		return this.clientService.loginClient(client);
	}
	
	@RequestMapping(value = "/activateordeactivate", method = RequestMethod.POST)
	@ResponseBody public boolean deactivateOrActivateClient(@RequestBody Client client){
		return this.clientService.deactivateOrActivateClient(client);
	}

}
