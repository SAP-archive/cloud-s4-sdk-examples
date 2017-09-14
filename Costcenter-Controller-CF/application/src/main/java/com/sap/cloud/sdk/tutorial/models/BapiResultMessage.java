package com.sap.cloud.sdk.tutorial.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.sap.cloud.sdk.s4hana.connectivity.rfc.BapiQueryResult;
import com.sap.cloud.sdk.s4hana.serialization.RemoteFunctionMessage;

@Data
public class BapiResultMessage {

    public enum BapiResultMessageType { SUCCESS, INFORMATION, ERROR, WARNING }

    private String text;
    private String clasz;
    private String number;
    private BapiResultMessageType type;


    public static List<BapiResultMessage> createMesages(final BapiQueryResult costCenterDetails) {
        final List<BapiResultMessage> messages = new ArrayList<>();
        messages.addAll(createMesages(costCenterDetails.getSuccessMessages(), BapiResultMessageType.SUCCESS));
        return messages;
    }

    private static List<BapiResultMessage> createMesages(final List<RemoteFunctionMessage> remoteMessages, final BapiResultMessageType type) {
        final List<BapiResultMessage> messages = new ArrayList<>();
        for(final RemoteFunctionMessage remoteMessage : remoteMessages) {
            final BapiResultMessage message = new BapiResultMessage();
            message.setClasz(remoteMessage.getMessageClass().getValue());
            message.setText(remoteMessage.getMessageText());
            message.setNumber(remoteMessage.getMessageNumber().getValue());
            messages.add(message);
        }
        return messages;
    }
}
