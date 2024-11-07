package org.ethereum_tx;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        Web3j web3 = Web3j.build(new HttpService("https://eth-mainnet.rpc.grove.city/v1/a1f551c9"));

        try {
            Transaction transaction = web3.ethGetTransactionByBlockNumberAndIndex(
                            new DefaultBlockParameterNumber(0x1427d8a), BigInteger.valueOf(0x14)).send()
                    .getTransaction().get();
            System.out.println("Transaction: " + transaction.getTo());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
