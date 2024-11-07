# TransactionLoader

For run this project: 
Please install - Java 17,
and MongoDB - 
```shell script
brew install --cask gcollazo-mongodb
```

then Please run build
```shell script
./mvnw compile quarkus:dev
```

Then please trigger Endpoint http://localhost:8080/ethereum/loadTransactions to start loading transactions for latest block;


and Then go to MongoDB Compass to see result 
!!! (Transactions Collections IN ether_txs database)
