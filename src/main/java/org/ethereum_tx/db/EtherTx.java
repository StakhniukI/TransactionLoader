package org.ethereum_tx.db;

import io.quarkus.mongodb.panache.common.MongoEntity;
import org.bson.types.ObjectId;

@MongoEntity(collection="EtherTx")
public class EtherTx  {
    public ObjectId id; // used by MongoDB for the _id field
    public String blockNumber;
    public String transactionIndex;


}
