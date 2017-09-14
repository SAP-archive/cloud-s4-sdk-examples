package com.sap.cloud.sdk.tutorial.models;

import com.google.common.collect.Lists;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.BapiQueryResult;
import com.sap.cloud.sdk.s4hana.serialization.RemoteFunctionMessage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class BapiResultMessageTest {

    private BapiQueryResult costCenterDetails;

    private RemoteFunctionMessage remoteFunctionMessage;

    private static String text = "text";
    private static String clasz = "clasz";
    private static String number = "number";
    private BapiResultMessage.BapiResultMessageType type = BapiResultMessage.BapiResultMessageType.INFORMATION;

    @Test
    public void testSetterGetter(){
        final BapiResultMessage bapiResultMessage = new BapiResultMessage();
        bapiResultMessage.setText(text);
        bapiResultMessage.setClasz(clasz);
        bapiResultMessage.setNumber(number);
        bapiResultMessage.setType(type);

        assertEquals(
                Lists.newArrayList(
                        bapiResultMessage.getText(),
                        bapiResultMessage.getClasz(),
                        bapiResultMessage.getNumber(),
                        bapiResultMessage.getType()),
                Lists.newArrayList(
                        text, clasz, number, type));
    }
}
