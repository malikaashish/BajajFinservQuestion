package com.cdac.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cdac.main.service.WebhookService;

@Component
public class StartupRunner implements CommandLineRunner {

    private final WebhookService webhookService;
    public StartupRunner(WebhookService webhookService) {
        this.webhookService = webhookService;
    }
    @Override
    public void run(String... args) {
        webhookService.startProcess();
    }
}
