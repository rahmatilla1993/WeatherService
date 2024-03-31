package com.company.dao;

import com.company.entity.AuthUser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class AuthUserDao extends BaseDao<AuthUser, Long> {

    @Transactional(readOnly = true)
    public Optional<AuthUser> findByEmail(String email) {
        try {
            AuthUser authUser = em.createNamedQuery("findByEmail", AuthUser.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(authUser);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
