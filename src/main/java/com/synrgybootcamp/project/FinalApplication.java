package com.synrgybootcamp.project;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class FinalApplication {

	public static void main(String[] args) {

		// Set Cloudinary instance
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", "dh9nmeyfy", // insert here you cloud name
				"api_key", "638294414412252", // insert here your api code
				"api_secret", "gIjGcbHsPM_NkOhE95bdELROsSQ")); // insert here your api secret
		SingletonManager manager = new SingletonManager();
		manager.setCloudinary(cloudinary);
		manager.init();

		SpringApplication.run(FinalApplication.class, args);
	}

}
