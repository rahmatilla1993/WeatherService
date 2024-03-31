package com.company.dao;

import com.company.entity.Auditable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public abstract class BaseDao<T extends Auditable, ID extends Serializable> {

    private final Class<T> persistenceClass;
    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("unchecked")
    public BaseDao() {
        Type superclass = getClass().getGenericSuperclass();
        persistenceClass = (Class<T>) ((ParameterizedType) (superclass)).getActualTypeArguments()[0];
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return em.createQuery("from " + persistenceClass.getSimpleName(), persistenceClass)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(em.find(persistenceClass, id));
    }

    public boolean save(T entity) {
        em.persist(entity);
        return true;
    }

    public boolean edit(T entity) {
        em.merge(entity);
        return true;
    }

    public boolean delete(ID id) {
        em.createQuery("delete from " + persistenceClass.getSimpleName() + " where id = :id")
                .setParameter("id", id)
                .executeUpdate();
        return true;
    }
}
