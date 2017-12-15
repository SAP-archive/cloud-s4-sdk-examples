package com.mycompany.models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table( name = "CostCenterForecast" )
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Accessors(chain = true)
public class CostCenterForecast
{
    @Id
    @Column( name = "NAME", length = 100 )
    @Getter
    @Setter
    private String name;

    @Column( name = "FORECAST" )
    @Getter
    @Setter
    private Double forecast;
}
