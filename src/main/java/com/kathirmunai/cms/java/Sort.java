package com.kathirmunai.cms.java;

import java.util.List;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor(access = AccessLevel.PROTECTED)
class Patient {
	private String name;
	private int age;
}

public class Sort{  
	public static void main(String[] args) {  

		List<Patient> patients = Lists.newArrayList(
				new Patient("Sathiya", 10), 
				new Patient("Vettri", 12)
				);

		patients.sort( (Patient h1, Patient h2) -> h1.getName().compareTo(h2.getName()));
		System.out.println(patients);

	}
}  