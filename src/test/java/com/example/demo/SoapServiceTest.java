package com.example.demo;

import com.example.demo.entities.Compte;
import com.example.demo.entities.TypeCompte;
import com.example.demo.repositories.CompteRepository;
import com.example.demo.ws.CompteSoapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le service SOAP CompteSoapService.
 */
@SpringBootTest
class SoapServiceTest {

    @Autowired
    private CompteSoapService compteSoapService;

    @Autowired
    private CompteRepository compteRepository;

    @BeforeEach
    void setUp() {
        compteRepository.deleteAll();
    }

    @Test
    void testCreateCompte() {
        Compte compte = compteSoapService.createCompte(1000.0, TypeCompte.COURANT);
        
        assertNotNull(compte);
        assertNotNull(compte.getId());
        assertEquals(1000.0, compte.getSolde());
        assertEquals(TypeCompte.COURANT, compte.getType());
    }

    @Test
    void testGetComptes() {
        // Créer des comptes
        compteSoapService.createCompte(5000.0, TypeCompte.COURANT);
        compteSoapService.createCompte(10000.0, TypeCompte.EPARGNE);

        List<Compte> comptes = compteSoapService.getComptes();
        
        assertEquals(2, comptes.size());
    }

    @Test
    void testGetCompteById() {
        Compte created = compteSoapService.createCompte(2500.0, TypeCompte.EPARGNE);
        
        Compte found = compteSoapService.getCompteById(created.getId());
        
        assertNotNull(found);
        assertEquals(created.getId(), found.getId());
        assertEquals(2500.0, found.getSolde());
    }

    @Test
    void testGetCompteByIdNotFound() {
        Compte found = compteSoapService.getCompteById(999L);
        assertNull(found);
    }

    @Test
    void testUpdateSolde() {
        Compte created = compteSoapService.createCompte(1000.0, TypeCompte.COURANT);
        
        Compte updated = compteSoapService.updateSolde(created.getId(), 1500.0);
        
        assertNotNull(updated);
        assertEquals(1500.0, updated.getSolde());
    }

    @Test
    void testDeleteCompte() {
        Compte created = compteSoapService.createCompte(3000.0, TypeCompte.COURANT);
        
        boolean deleted = compteSoapService.deleteCompte(created.getId());
        assertTrue(deleted);
        
        // Vérifier que le compte n'existe plus
        assertNull(compteSoapService.getCompteById(created.getId()));
    }

    @Test
    void testDeleteCompteNotFound() {
        boolean deleted = compteSoapService.deleteCompte(999L);
        assertFalse(deleted);
    }

    @Test
    void testGetComptesByType() {
        compteSoapService.createCompte(1000.0, TypeCompte.COURANT);
        compteSoapService.createCompte(2000.0, TypeCompte.COURANT);
        compteSoapService.createCompte(5000.0, TypeCompte.EPARGNE);

        List<Compte> courants = compteSoapService.getComptesByType(TypeCompte.COURANT);
        List<Compte> epargnes = compteSoapService.getComptesByType(TypeCompte.EPARGNE);
        
        assertEquals(2, courants.size());
        assertEquals(1, epargnes.size());
    }

    @Test
    void testCountComptes() {
        assertEquals(0, compteSoapService.countComptes());
        
        compteSoapService.createCompte(1000.0, TypeCompte.COURANT);
        compteSoapService.createCompte(2000.0, TypeCompte.EPARGNE);
        
        assertEquals(2, compteSoapService.countComptes());
    }
}
