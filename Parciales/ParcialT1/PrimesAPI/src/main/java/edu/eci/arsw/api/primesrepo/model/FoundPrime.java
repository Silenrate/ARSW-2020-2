package edu.eci.arsw.api.primesrepo.model;

import java.util.Objects;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
public class FoundPrime {
    String user;

    String prime;

    public FoundPrime() {
    }

    public FoundPrime(String user, String prime) {
        this.user = user;
        this.prime = prime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPrime() {
        return prime;
    }

    public void setPrime(String prime) {
        this.prime = prime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoundPrime that = (FoundPrime) o;
        return Objects.equals(prime, that.prime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, prime);
    }
}
