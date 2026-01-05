package com.example.demo.config;

import com.example.demo.ws.CompteSoapService;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration Apache CXF pour publier le service SOAP.
 * Expose le endpoint sur /ws.
 */
@Configuration
public class CxfConfig {
    
    @Autowired
    private CompteSoapService compteSoapService;
    
    @Autowired
    private Bus bus;
    
    /**
     * Crée et publie l'endpoint SOAP.
     * URL: http://localhost:8082/services/ws
     * WSDL: http://localhost:8082/services/ws?wsdl
     */
    @Bean
    public EndpointImpl endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, compteSoapService);
        endpoint.publish("/ws");
        return endpoint;
    }
}
