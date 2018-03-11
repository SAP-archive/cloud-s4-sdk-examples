package com.mycompany;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rate {
    private String currencyFrom;
    private String currencyTo;
    private Double rate;
}
