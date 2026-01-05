package com.example.demo.repositories;

import com.example.demo.entities.Compte;
import com.example.demo.entities.TypeCompte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository JPA pour l'entité Compte.
 * Fournit les opérations CRUD automatiquement.
 */
@Repository
public interface CompteRepository extends JpaRepository<Compte, Long> {
    
    // Méthodes de recherche personnalisées
    List<Compte> findByType(TypeCompte type);
    
    List<Compte> findBySoldeGreaterThan(double solde);
}
