package com.aihangxunxi.aitalk.restapi;

import com.aihangxunxi.aitalk.storage.config.StorageConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(StorageConfiguration.class)
@SpringBootApplication
public class AitalkRestapiApp {

	public static void main(String[] args) {
		SpringApplication.run(AitalkRestapiApp.class, args);
	}

}
