package org.ethereum_tx;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.ethereum_tx.service.TxLoader;

import java.io.IOException;


@Path("/ethereum")
public class Resource {

    @Inject
    TxLoader txLoader;

    @GET
    @Path("/block/{blockId}/transaction/{transactionId}")
    public void findTransaction(@PathParam("brockId") Long blockId,
                                  @PathParam("brockId") Long transactionId) {


//        return null;
    }

    @GET
    @Path("/loadTransactions")
    public void loadTransactions() {
        try {
            txLoader.loadTransactions();
        } catch (IOException e) {
            System.out.println("Problem with loading transaction");
            throw new RuntimeException(e);
        }
    }
}
