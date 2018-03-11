package com.mycompany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Rate {
    private String currencyFrom;
    private String currencyTo;
    private Double rate;
}
