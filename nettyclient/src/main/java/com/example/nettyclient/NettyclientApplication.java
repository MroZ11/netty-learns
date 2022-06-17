package com.example.nettyclient;

import com.example.nettyclient.core.DiscardClient;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LogLevel;

import java.util.logging.Level;

@SpringBootApplication
@Log
public class NettyclientApplication {

	public  static DiscardClient discardClient = null;

	public static void main(String[] args) {
		SpringApplication.run(NettyclientApplication.class, args);

		discardClient = new DiscardClient(8080);
		try {
			discardClient.run();
		} catch (Exception e) {
			log.log(Level.WARNING,"netty connect error "+e.getMessage());
		}

	}

}
