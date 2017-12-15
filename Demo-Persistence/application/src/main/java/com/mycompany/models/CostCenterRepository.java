package com.mycompany.models;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostCenterRepository extends CrudRepository<CostCenterForecast, Long> {}
