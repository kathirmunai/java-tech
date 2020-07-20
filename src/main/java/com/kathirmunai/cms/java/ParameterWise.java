package com.kathirmunai.cms.java;

@FunctionalInterface
interface ProviderNoParam{  
	public String consult();
}  

@FunctionalInterface
interface ProviderMultipleParam{  
	public String consult(int patient);
}  

public class ParameterWise{  
	public static void main(String[] args) {  

		ProviderNoParam noParam=()->{  
			return "I have no consult today.";  
		};  

		ProviderMultipleParam multipleParam=(patient)->{  
			return "I have got "+patient+" consults today.";  
		}; 

		System.out.println(noParam.consult());  
		System.out.println(multipleParam.consult(10));  
	}  
}  