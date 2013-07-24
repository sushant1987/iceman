/**
 * 
 */
package com.amzedia.xstore.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amzedia.xstore.XstoreException;
import com.amzedia.xstore.model.BasicInfo;
import com.amzedia.xstore.model.Customer;
import com.amzedia.xstore.model.ResponseWrapper;
import com.amzedia.xstore.services.interfaces.ICustomerService;

/**
 * @author Tarun Keswani
 * 
 */

@Controller
@RequestMapping(value = "/customer")
public class CustomerRestService {

	@Autowired
	private ICustomerService customerService;

	/**
	 * This api will bring the customer information by passing id of
	 * customer
	 * 
	 * @param String
	 * @return Customer
	 */
	@RequestMapping(value = "/{id}")
	@ResponseBody
	public ResponseWrapper getCustomerById(@PathVariable String id) {
		int customerId = Integer.parseInt(id);
		try {
			return this.customerService.getCustomer(customerId);
		} catch (XstoreException e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/login")
	@ResponseBody
	public ResponseWrapper loginCustomer(@RequestBody Customer customer) {
		try {
			return this.customerService.loginCustomer(customer);
		} catch (XstoreException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	@ResponseBody
	public Customer dummy() {
		Customer customer = new Customer();
		BasicInfo bi = new BasicInfo();
		bi.setAddress("BTM");
		bi.setCity("Bangalore");
		bi.setCountry("India");
		bi.setEmail("sushant1887@gmail.com");
		bi.setFax("123456");
		bi.setFirstName("Sushant");
		bi.setLastName("Singh");
		bi.setMiddleName("Kumar");
		bi.setPhoneNumber("+919739910602");
		bi.setPin("240001");
		bi.setState("Karnataka");
		customer.setCustomerType("VIP");
		customer.setNewsLetter(true);
		customer.setPassword("123456");
		customer.setStatus(true);
		customer.setUsername("sushant1887");
		customer.setBasicInfo(bi);
		return customer;
	}

}
