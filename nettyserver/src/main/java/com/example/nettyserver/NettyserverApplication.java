package com.example.nettyserver;

import com.example.nettyserver.core.DiscardServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyserverApplication {

	public static void main(String[] args) {

		SpringApplication.run(NettyserverApplication.class, args);

		int port = 8080;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		}

		try {
			new DiscardServer(port).run();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

}
