# TP13 - Web Service SOAP avec JAX-WS et Spring Boot

## Objectif

Ce TP met en place un service web SOAP avec **Spring Boot** et **Apache CXF** pour gérer des comptes bancaires.

## DEMO

https://github.com/user-attachments/assets/7d718485-3bdf-4e33-85bf-cac489d1c9a2

## Technologies utilisées

| Technologie | Version | Description |
|-------------|---------|-------------|
| Spring Boot | 3.2.1 | Framework principal |
| Apache CXF | 4.0.2 | Framework SOAP/JAX-WS |
| Spring Data JPA | - | Persistance des données |
| H2 Database | - | Base de données en mémoire |
| Lombok | - | Génération de code |
| JAXB | 4.0.3 | Sérialisation XML |

## Structure du projet

```
demo/
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/example/demo/
    │   │   ├── config/
    │   │   │   └── CxfConfig.java          # Configuration CXF endpoint
    │   │   ├── entities/
    │   │   │   ├── Compte.java             # Entité JPA + JAXB
    │   │   │   └── TypeCompte.java         # Enum COURANT/EPARGNE
    │   │   ├── repositories/
    │   │   │   └── CompteRepository.java   # Repository JPA
    │   │   ├── ws/
    │   │   │   └── CompteSoapService.java  # Service SOAP
    │   │   └── DemoApplication.java        # Main class
    │   └── resources/
    │       └── application.properties
    └── test/java/com/example/demo/
        ├── DemoApplicationTests.java
        └── SoapServiceTest.java
```

## Lancement

```bash
cd demo
mvn spring-boot:run
```

## URLs

| Service | URL |
|---------|-----|
| **WSDL** | http://localhost:8082/services/ws?wsdl |
| **Endpoint SOAP** | http://localhost:8082/services/ws |
| **Console H2** | http://localhost:8082/h2-console |

### Connexion H2 Console

- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: *(vide)*

## Opérations SOAP disponibles

| Opération | Description | Paramètres |
|-----------|-------------|------------|
| `getComptes` | Liste tous les comptes | - |
| `getCompteById` | Récupère un compte | `id` |
| `createCompte` | Crée un compte | `solde`, `type` |
| `updateSolde` | Met à jour le solde | `id`, `nouveauSolde` |
| `deleteCompte` | Supprime un compte | `id` |
| `getComptesByType` | Filtre par type | `type` |
| `countComptes` | Compte les comptes | - |

## Tests avec SoapUI

### 1. Importer le WSDL

1. Ouvrir SoapUI
2. File > New SOAP Project
3. URL: `http://localhost:8082/services/ws?wsdl`

### 2. Exemples de requêtes

#### getComptes
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ws="http://ws.demo.example.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:getComptes/>
   </soapenv:Body>
</soapenv:Envelope>
```

#### getCompteById
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ws="http://ws.demo.example.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:getCompteById>
         <id>1</id>
      </ws:getCompteById>
   </soapenv:Body>
</soapenv:Envelope>
```

#### createCompte
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ws="http://ws.demo.example.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:createCompte>
         <solde>5000.0</solde>
         <type>COURANT</type>
      </ws:createCompte>
   </soapenv:Body>
</soapenv:Envelope>
```

#### updateSolde
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ws="http://ws.demo.example.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:updateSolde>
         <id>1</id>
         <nouveauSolde>7500.0</nouveauSolde>
      </ws:updateSolde>
   </soapenv:Body>
</soapenv:Envelope>
```

#### deleteCompte
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:ws="http://ws.demo.example.com/">
   <soapenv:Header/>
   <soapenv:Body>
      <ws:deleteCompte>
         <id>1</id>
      </ws:deleteCompte>
   </soapenv:Body>
</soapenv:Envelope>
```

## Tests avec cURL (PowerShell)

### getComptes
```powershell
$body = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.demo.example.com/"><soapenv:Body><ws:getComptes/></soapenv:Body></soapenv:Envelope>'
Invoke-WebRequest -Uri "http://localhost:8082/services/ws" -Method POST -ContentType "text/xml" -Body $body | Select-Object -ExpandProperty Content
```

### createCompte
```powershell
$body = '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ws="http://ws.demo.example.com/"><soapenv:Body><ws:createCompte><solde>10000</solde><type>EPARGNE</type></ws:createCompte></soapenv:Body></soapenv:Envelope>'
Invoke-WebRequest -Uri "http://localhost:8082/services/ws" -Method POST -ContentType "text/xml" -Body $body | Select-Object -ExpandProperty Content
```

## Annotations utilisées

### JAX-WS (SOAP)
- `@WebService` : Déclare une classe comme service SOAP
- `@WebMethod` : Expose une méthode comme opération SOAP
- `@WebParam` : Nomme un paramètre dans le message SOAP

### JAXB (XML)
- `@XmlRootElement` : Élément racine XML
- `@XmlAccessorType` : Mode d'accès aux champs

### JPA
- `@Entity` : Entité persistante
- `@Id`, `@GeneratedValue` : Clé primaire auto-générée
- `@Enumerated` : Stockage d'enum

### Spring
- `@Component` : Bean Spring
- `@Configuration` : Classe de configuration
- `@Bean` : Méthode productrice de bean
- `@Autowired` : Injection de dépendance

## Tests unitaires

```bash
mvn test
```

Les tests vérifient :
- Création de compte
- Récupération par ID
- Liste des comptes
- Mise à jour du solde
- Suppression
- Filtrage par type
- Comptage

## Auteur

TP13 - Microservices Architecture
