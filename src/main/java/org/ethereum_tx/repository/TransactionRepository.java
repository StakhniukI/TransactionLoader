package org.ethereum_tx.repository;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheMongoRepository<Transaction> {

    // put your custom logic here as instance methods

    public List<Transaction> findByBlockNumberAndTxIndex(String blockNumber, String txIndex){
        return findByBlockNumberAndTxIndex(blockNumber, txIndex);
    }
}
