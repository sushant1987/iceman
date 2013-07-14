/**
 * 
 */
package com.amzedia.xstore.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amzedia.xstore.model.Customer;
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
	 * This api will bring the customer
	 * information by passing id of customer
	 * 
	 * @param String
	 * @return Customer
	 */
	@RequestMapping(value="/findcustomer/{id}")
	@ResponseBody public Customer getCustomerById(@PathVariable String id) {
		int customerId = Integer.parseInt(id);
		return this.customerService.getCustomer(customerId);
	}

}
