package com.sap.opensap;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RequestParameters {
    private String controllingArea;
    private int costCenterIndex;
    private int numberOfCreations;
    private int namingSuffixStartCounter;
    private String namingPrefix;
}
