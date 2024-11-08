package org.ethereum_tx.service;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.bson.Document;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterNumber;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

@ApplicationScoped
public class TxLoader {

    @Inject
    MongoClient mongoClient;

    Logger LOG = Logger.getLogger(TxLoader.class.getName());
    String CHAIN_URL = "https://eth-archival.rpc.grove.city/v1/a1f551c9";

    public void loadTransactions() throws IOException {

        BigInteger lastBlock = getLatestBlock();
        BigInteger transactionCountForBlock = getBlockTransactionCount(lastBlock);

        Web3j web3 = Web3j.build(new HttpService(CHAIN_URL));

        ExecutorService executor = Executors.newCachedThreadPool();

        executor.submit(() -> {
            for (long i = transactionCountForBlock.longValue() - 1; i >= 0; i--) {
                Optional<Transaction> transactionOptional = null;
                try {
                    transactionOptional = web3.ethGetTransactionByBlockNumberAndIndex(
                                    new DefaultBlockParameterNumber(lastBlock), BigInteger.valueOf(i)).send()
                            .getTransaction();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (transactionOptional.isPresent()) {
                    Transaction transaction = transactionOptional.get();
                    System.out.println(transaction.getTransactionIndex());
                    Gson gson = new Gson();
                    String json = gson.toJson(transaction);
                    Document doc = Document.parse(json);
                    getTransactionCollection().insertOne(doc);
                }
            }
        });
//

    }

    private BigInteger getLatestBlock() throws IOException {

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

    private Object getLastTransactionNumberForBlockLoaded(BigInteger blockNumber){
        return getTransactionLoadedCollection().find(eq("blockNumber", blockNumber)).first();
    }
    private MongoCollection getTransactionCollection(){
        return mongoClient.getDatabase("ether_txs").getCollection("transactions");
    }
    private MongoCollection getTransactionLoadedCollection(){
        return mongoClient.getDatabase("ether_txs").getCollection("transaction_loaded");
    }
}
