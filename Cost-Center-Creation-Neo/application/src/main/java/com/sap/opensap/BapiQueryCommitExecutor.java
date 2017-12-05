package com.sap.opensap;

import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;

import com.sap.conn.jco.JCoContext;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;

import com.sap.cloud.sdk.s4hana.connectivity.ErpDestination;
import com.sap.cloud.sdk.s4hana.connectivity.ErpEndpoint;
import com.sap.cloud.sdk.s4hana.connectivity.exception.QueryExecutionException;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.BapiQuery;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.BapiQueryResult;

@RequiredArgsConstructor
public class BapiQueryCommitExecutor {

    private final BapiQuery queryToExecute;

    private static final String ROLLBACK_BAPI_NAME = "BAPI_TRANSACTION_ROLLBACK";
    private static final String COMMIT_BAPI_NAME = "BAPI_TRANSACTION_COMMIT";

    public BapiQueryResult execute(ErpEndpoint erpEndpoint) throws QueryExecutionException {
        BapiQueryResult queryResult = null;

        try {
            final JCoDestination jCoDestination = JCoDestinationManager.getDestination(erpEndpoint.getDestinationName());

            JCoContext.begin(jCoDestination);

            queryResult = queryToExecute.execute(erpEndpoint);

            handleCommitAndRollback(erpEndpoint, queryResult);

            JCoContext.end(jCoDestination);
        } catch (JCoException e) {
            throw new QueryExecutionException(e.getMessage());
        }
        return queryResult;
    }

    private void handleCommitAndRollback(final ErpEndpoint erpEndpoint, final BapiQueryResult queryResult) throws QueryExecutionException {
        if (queryResult.hasFailed()) {
            executeRollbackBapi(erpEndpoint);
        } else {
            BapiQueryResult commitResult = executeCommitBapi(erpEndpoint);

            if (commitResult.hasFailed()) {
                executeRollbackBapi(erpEndpoint);
            }
        }
    }

    private BapiQueryResult executeRollbackBapi(ErpEndpoint erpEndpoint) throws QueryExecutionException {
        return new BapiQuery(ROLLBACK_BAPI_NAME).execute(erpEndpoint);
    }

    private BapiQueryResult executeCommitBapi(ErpEndpoint erpEndpoint) throws QueryExecutionException {
        return new BapiQuery(COMMIT_BAPI_NAME).execute(erpEndpoint);
    }
}
