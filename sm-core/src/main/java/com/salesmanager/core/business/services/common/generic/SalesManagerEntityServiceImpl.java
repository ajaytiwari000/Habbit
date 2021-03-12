package com.salesmanager.core.business.services.common.generic;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/** @param <T> entity type */
public abstract class SalesManagerEntityServiceImpl<
        K extends Serializable & Comparable<K>, E extends SalesManagerEntity<K, ?>>
    implements SalesManagerEntityService<K, E> {

  /** Classe de l'entité, déterminé à partir des paramètres generics. */
  private Class<E> objectClass;

  private JpaRepository<E, K> repository;

  @SuppressWarnings("unchecked")
  public SalesManagerEntityServiceImpl(JpaRepository<E, K> repository) {
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    this.objectClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
    this.repository = repository;
  }

  protected final Class<E> getObjectClass() {
    return objectClass;
  }

  public E getById(K id) {
    return repository.getOne(id);
  }

  public E save(E entity) throws ServiceException {
    return repository.saveAndFlush(entity);
  }

  public E create(E entity) throws ServiceException {
    try {
      return save(entity);
    } catch (Exception e) {
      throw new ServiceException("Create failed", e);
    }
  }

  public E update(E entity) throws ServiceException {
    try {
      return save(entity);
    } catch (Exception e) {
      throw new ServiceException("Update failed", e);
    }
  }

  public void delete(E entity) throws ServiceException {
    try {
      repository.delete(entity);
    } catch (Exception e) {
      throw new ServiceException("Delete failed", e);
    }
  }

  public void flush() {
    repository.flush();
  }

  public List<E> list() {
    return repository.findAll();
  }

  public Long count() {
    return repository.count();
  }
}
