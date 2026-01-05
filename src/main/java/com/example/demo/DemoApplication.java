package com.example.demo;

import com.example.demo.entities.Compte;
import com.example.demo.entities.TypeCompte;
import com.example.demo.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

/**
 * Application principale Spring Boot pour le TP13.
 * Service SOAP avec JAX-WS et Apache CXF.
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
    
    /**
     * Initialise quelques comptes de test au démarrage.
     */
    @Bean
    CommandLineRunner initData(CompteRepository compteRepository) {
        return args -> {
            // Créer quelques comptes de test
            compteRepository.save(new Compte(null, 5000.0, new Date(), TypeCompte.COURANT));
            compteRepository.save(new Compte(null, 15000.0, new Date(), TypeCompte.EPARGNE));
            compteRepository.save(new Compte(null, 3500.0, new Date(), TypeCompte.COURANT));
            
            System.out.println("============================================================");
            System.out.println("  TP13 - Service SOAP avec Spring Boot et Apache CXF");
            System.out.println("============================================================");
            System.out.println();
            System.out.println("  WSDL:        http://localhost:8082/services/ws?wsdl");
            System.out.println("  H2 Console:  http://localhost:8082/h2-console");
            System.out.println();
            System.out.println("  Comptes créés: " + compteRepository.count());
            System.out.println("============================================================");
        };
    }
}
