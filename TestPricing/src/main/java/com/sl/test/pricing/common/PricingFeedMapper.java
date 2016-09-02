package com.sl.test.pricing.common;

import com.sl.test.pricing.domain.PricingFeed;

public class PricingFeedMapper  {  

	public static PricingFeed apply(String input) {  

		
		PricingFeed pricingFeed = new PricingFeed(); 
		String[] pricing =  input.split(":");  
		pricingFeed.setItemName(pricing[0]);
		pricingFeed.setWeight(Double.valueOf(pricing[1]  )  );
		
		
		return pricingFeed;
	}

	
	
	
	
}
