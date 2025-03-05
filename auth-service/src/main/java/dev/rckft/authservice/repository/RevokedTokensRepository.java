package dev.rckft.authservice.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Repository
public class RevokedTokensRepository {

    public static final String JTI = "jti";
    public static final String EXPIRY_DATE = "expiryDate";
    private final EntityManager entityManager;

    public RevokedTokensRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Optional<String> findByJti(String jti) {
        Query query = entityManager.createNativeQuery("SELECT jti FROM blockedjti WHERE jti = :jti");
        query.setParameter(JTI, jti);
        List<String> results = query.getResultList();
        return results.stream().findFirst();
    }

    public void saveToken(String jti, Date expirationDate) {
        Query query = entityManager.createNativeQuery("INSERT INTO blockedjti (jti, expireDate) VALUES (:jti, :expiryDate)");
        query.setParameter(JTI, jti);
        query.setParameter(EXPIRY_DATE, expirationDate);
        query.executeUpdate();
    }

}
