package com.tpe.repository;

import com.tpe.domain.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory,Long> {
}
