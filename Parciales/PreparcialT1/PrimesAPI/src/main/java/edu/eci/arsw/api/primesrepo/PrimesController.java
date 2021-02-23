package edu.eci.arsw.api.primesrepo;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.model.PrimeException;
import edu.eci.arsw.api.primesrepo.service.PrimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@RequestMapping(value = "/primes")
@RestController
public class PrimesController {

    @Autowired
    PrimeService primeService;

    @GetMapping
    public ResponseEntity<?> getPrimes() {
        try {
            return new ResponseEntity<>(primeService.getFoundPrimes(), HttpStatus.ACCEPTED);
        } catch (PrimeException e) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{primeNumber}")
    public ResponseEntity<?> getPrime(@PathVariable String primeNumber) {
        try {
            return new ResponseEntity<>(primeService.getPrime(primeNumber), HttpStatus.ACCEPTED);
        } catch (PrimeException e) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> addPrime(@RequestBody FoundPrime primeNumber) {
        if (primeNumber.getPrime() == null || primeNumber.getUser() == null) {
            return new ResponseEntity<>("JSON Bad Format", HttpStatus.BAD_REQUEST);
        }
        try {
            primeService.addFoundPrime(primeNumber);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (PrimeException e) {
            Logger.getLogger(PrimesController.class.getName()).log(Level.SEVERE, e.getMessage(), e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }


}
