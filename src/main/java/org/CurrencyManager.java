package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CurrencyManager {

    @Inject
    @RestClient
    CurrencyService currencyService;

    @Inject
    EntityManager em;

    @Transactional
    public CurrencyResponse processCurrencyRequest(String from, String to, double value, Long userId) {
        
       
        User user = em.find(User.class, userId);
        if (user == null) {
            throw new RuntimeException("Korisnik sa ID " + userId + " nije pronađen.");
        }

        
        CurrencyResponse response = currencyService.getRates(from, to);

        
        double rate = response.getRate(); 
        double calculatedConvertedValue = value * rate;

        response.setValue(value); 
        response.setConvertedValue(calculatedConvertedValue); 
        
        
        response.setFromCurrency(from);
        response.setToCurrency(to);

        
        response.setUser(user);
        
       
        em.persist(response);

        return response;
    }
}