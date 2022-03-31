package com.alissonpedrina.cli;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = "com.alissonpedrina")
@EnableTransactionManagement
public class CliApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CliApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AkumoEditor ide = new AkumoEditor();
        System.out.println("alisson init");
        ide.init();
    }
}
