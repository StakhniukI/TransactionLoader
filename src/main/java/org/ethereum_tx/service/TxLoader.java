package org.ethereum_tx.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.ethereum_tx.repository.TransactionRepository;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Logger;

@ApplicationScoped
public class TxLoader {

    @Inject
    TransactionRepository transactionRepository;

    Logger LOG = Logger.getLogger(TxLoader.class.getName());
    String CHAIN_URL = "https://eth-archival.rpc.grove.city/v1/a1f551c9";

    public void loadTransactions() throws IOException {

        BigInteger lastBlock = getLastBlock();
        BigInteger transactionCountForBlock = getBlockTransactionCount(lastBlock);

        Web3j web3 = Web3j.build(new HttpService(CHAIN_URL));

        for (long i = transactionCountForBlock.longValue(); i == 0; i--) {
            org.web3j.protocol.core.methods.response.Transaction transaction = web3.ethGetTransactionByBlockNumberAndIndex(
                            new DefaultBlockParameterNumber(lastBlock), BigInteger.valueOf(i)).send()
                    .getTransaction().get();

            transactionRepository.persist(transaction);
        }
    }

    private BigInteger getLastBlock() throws IOException {

        Web3j web3 = Web3j.build(new HttpService(CHAIN_URL));

        BigInteger latestBlockNumber = null;

        try {
            latestBlockNumber = web3.ethBlockNumber().send().getBlockNumber();
            System.out.println("Latest Ethereum block number: " + latestBlockNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latestBlockNumber;
    }

    private BigInteger getBlockTransactionCount(BigInteger blockNumber) {

        Web3j web3 = Web3j.build(new HttpService(CHAIN_URL));

        BigInteger transactionCountForBlock = null;

        try {
            transactionCountForBlock = web3.ethGetBlockTransactionCountByNumber(new DefaultBlockParameterNumber(blockNumber))
                    .send().getTransactionCount();
            System.out.println("Transaction count for block " +  blockNumber + ": " + transactionCountForBlock);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactionCountForBlock;
    }
}
