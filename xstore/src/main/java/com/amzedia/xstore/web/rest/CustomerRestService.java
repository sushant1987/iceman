/**
 * 
 */
package com.amzedia.xstore.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amzedia.xstore.XstoreException;
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
	@RequestMapping(value = "/findcustomer/{id}")
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

	@RequestMapping(value = "/register")
	@ResponseBody
	public ResponseWrapper register(@RequestBody Customer customer) {
		try {
			return this.customerService.registerCustomer(customer);
		} catch (XstoreException e) {
			e.printStackTrace();
			return null;
		}
	}

}
