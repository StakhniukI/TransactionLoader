package org.ethereum_tx;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.ethereum_tx.service.TxLoader;

import java.net.MalformedURLException;
import java.net.URL;


@Path("/ethereum")
public class Resource {

    private static String BASE_URL = "https://quarkus.io/";

    @Inject
    TxLoader txLoader;

    @GET
    @Path("/block/{blockId}/transaction/{transactionId}")
    public byte[] findTransaction(@PathParam("path") String path) {

        URL url;
        try {
            url = new URL(BASE_URL + path);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Invalid/broken URL");
        }


        return null;
    }
}
