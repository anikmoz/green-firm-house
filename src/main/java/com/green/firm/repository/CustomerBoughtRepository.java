package com.green.firm.repository;

import com.green.firm.domain.CustomerBought;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CustomerBought entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerBoughtRepository extends JpaRepository<CustomerBought, Long> {}
