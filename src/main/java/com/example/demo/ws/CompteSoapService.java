package com.example.demo.ws;

import com.example.demo.entities.Compte;
import com.example.demo.entities.TypeCompte;
import com.example.demo.repositories.CompteRepository;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Service SOAP pour la gestion des comptes bancaires.
 * Exposé via Apache CXF avec JAX-WS.
 */
@Component
@WebService(serviceName = "BanqueWS")
public class CompteSoapService {
    
    @Autowired
    private CompteRepository compteRepository;
    
    /**
     * Récupère la liste de tous les comptes.
     * @return Liste des comptes
     */
    @WebMethod
    public List<Compte> getComptes() {
        return compteRepository.findAll();
    }
    
    /**
     * Récupère un compte par son identifiant.
     * @param id L'identifiant du compte
     * @return Le compte correspondant ou null
     */
    @WebMethod
    public Compte getCompteById(@WebParam(name = "id") Long id) {
        return compteRepository.findById(id).orElse(null);
    }
    
    /**
     * Crée un nouveau compte bancaire.
     * @param solde Le solde initial
     * @param type Le type de compte (COURANT ou EPARGNE)
     * @return Le compte créé
     */
    @WebMethod
    public Compte createCompte(
            @WebParam(name = "solde") double solde,
            @WebParam(name = "type") TypeCompte type) {
        Compte compte = new Compte(null, solde, new Date(), type);
        return compteRepository.save(compte);
    }
    
    /**
     * Met à jour le solde d'un compte.
     * @param id L'identifiant du compte
     * @param nouveauSolde Le nouveau solde
     * @return Le compte mis à jour ou null
     */
    @WebMethod
    public Compte updateSolde(
            @WebParam(name = "id") Long id,
            @WebParam(name = "nouveauSolde") double nouveauSolde) {
        return compteRepository.findById(id)
                .map(compte -> {
                    compte.setSolde(nouveauSolde);
                    return compteRepository.save(compte);
                })
                .orElse(null);
    }
    
    /**
     * Supprime un compte par son identifiant.
     * @param id L'identifiant du compte à supprimer
     * @return true si supprimé, false sinon
     */
    @WebMethod
    public boolean deleteCompte(@WebParam(name = "id") Long id) {
        if (compteRepository.existsById(id)) {
            compteRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Récupère les comptes par type.
     * @param type Le type de compte
     * @return Liste des comptes du type spécifié
     */
    @WebMethod
    public List<Compte> getComptesByType(@WebParam(name = "type") TypeCompte type) {
        return compteRepository.findByType(type);
    }
    
    /**
     * Compte le nombre total de comptes.
     * @return Le nombre de comptes
     */
    @WebMethod
    public long countComptes() {
        return compteRepository.count();
    }
}
