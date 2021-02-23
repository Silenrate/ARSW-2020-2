package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.model.PrimeException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@Service("primeServicesStub")
public class PrimeServiceStub implements PrimeService {

    private List<FoundPrime> primos = new CopyOnWriteArrayList<>();

    public PrimeServiceStub() {
        primos.add(new FoundPrime("wal", "2"));
        primos.add(new FoundPrime("wal", "7"));
        primos.add(new FoundPrime("wal", "13"));
    }

    @Override
    public void addFoundPrime(FoundPrime foundPrime) throws PrimeException {
        boolean alreadyExists = false;
        for (FoundPrime prime : primos) {
            if (prime.equals(foundPrime)) {
                alreadyExists = true;
                break;
            }
        }
        if (alreadyExists) {
            throw new PrimeException("Ese primo ya existe");
        }
        primos.add(foundPrime);
    }

    @Override
    public List<FoundPrime> getFoundPrimes() throws PrimeException {
        return primos;
    }

    public FoundPrime getPrime(String prime) throws PrimeException {
        FoundPrime foundPrime = new FoundPrime();
        for (FoundPrime primo : primos) {
            if (primo.getPrime().equals(prime)) {
                foundPrime = primo;
            }
        }
        if (foundPrime.getPrime() == null) throw new PrimeException("Ese primo no existe");
        return foundPrime;
    }
}
