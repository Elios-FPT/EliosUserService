package vn.edu.fpt.elios_user_service.infra.persistence.repository.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Repository
class UserSoftDeleteRepositoryImpl implements UserSoftDeleteRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void deactivateById(UUID id) {
        em.createQuery("UPDATE UserEntity e SET e.isActive = false WHERE e.id = :id AND e.isActive = true")
                .setParameter("id", id)
                .executeUpdate();
    }
}


