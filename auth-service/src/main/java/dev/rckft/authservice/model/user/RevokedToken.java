package dev.rckft.authservice.model.user;

import jakarta.persistence.*;

import java.time.Instant;

@Entity(name = "revokedtokens")
public class RevokedToken {

    @Id
    private String jti;

    @Column(nullable = false)
    private Instant expiryDate;

    public RevokedToken() {}

    public RevokedToken(String jti, Instant expiryDate) {
        this.jti = jti;
        this.expiryDate = expiryDate;
    }

    public String getJti() {
        return jti;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
