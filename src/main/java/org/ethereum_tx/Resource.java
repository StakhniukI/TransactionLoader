package org.ethereum_tx;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.ethereum_tx.service.TxLoader;


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
}
