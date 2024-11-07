package org.ethereum_tx.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Logger;

@ApplicationScoped
public class TxLoader {

    Logger LOG = Logger.getLogger(TxLoader.class.getName());

    public void loadTransactions(String url) {

        Web3j web3 = Web3j.build(new HttpService("https://eth-archival.rpc.grove.city/v1/a1f551c9"));

        try {
            BigInteger blockNumber = web3.ethBlockNumber().send().getBlockNumber();
            System.out.println("Latest Ethereum block number: " + blockNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
