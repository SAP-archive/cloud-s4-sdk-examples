package com.mycompany.controllers;

import com.google.common.collect.Lists;
import com.mycompany.models.CostCenterForecast;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CostCenterController
{
    @Autowired
    CrudRepository costCenterRepository;

    @RequestMapping(path = "/cost-center", method = RequestMethod.GET)
    public ResponseEntity<List<CostCenterForecast>> getCostCenters(){
        final List<CostCenterForecast> costCenters = Lists.newArrayList(costCenterRepository.findAll());
        return ResponseEntity.ok(costCenters);
    }

    @RequestMapping(path = "/cost-center", method = RequestMethod.POST)
    public ResponseEntity<List<CostCenterForecast>> postCostCenter(@RequestBody CostCenterForecast costCenter){
        costCenterRepository.save(costCenter);
        final List<CostCenterForecast> costCenters = Lists.newArrayList(costCenterRepository.findAll());
        return ResponseEntity.ok(costCenters);
    }
}
