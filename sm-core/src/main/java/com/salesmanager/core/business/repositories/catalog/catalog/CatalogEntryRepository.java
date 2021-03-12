package com.salesmanager.core.business.repositories.catalog.catalog;

import com.salesmanager.core.model.catalog.catalog.CatalogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogEntryRepository extends JpaRepository<CatalogEntry, Long> {}
