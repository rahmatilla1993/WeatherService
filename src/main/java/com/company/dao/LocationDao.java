package com.company.dao;

import com.company.entity.Location;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
public class LocationDao extends BaseDao<Location, Long> {

    @Transactional(readOnly = true)
    public Optional<Location> findByName(String name) {
        try {
            Location location = em.createQuery("from Location where name = :name", Location.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.of(location);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
