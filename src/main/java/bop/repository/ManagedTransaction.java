package bop.repository;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ManagedTransaction {

    private static final Log log = LogFactory.getLog(ManagedTransaction.class);
    private final int operationsThreshold;
    private final AtomicInteger totalOperationsCount;
    private final AtomicInteger operationsCount;
    private final GraphDatabaseService graphDatabaseService;
    private Transaction transaction;

    private boolean transactionBeginFlag;

    @Autowired
    public ManagedTransaction(int operationsThreshold, GraphDatabaseService graphDatabaseService) {
        this.operationsThreshold = operationsThreshold;
        this.graphDatabaseService = graphDatabaseService;
        operationsCount = new AtomicInteger();
        totalOperationsCount = new AtomicInteger();
    }

    @PostConstruct
    public void start() {
        log.info("Starting managed transaction");
        transaction = graphDatabaseService.beginTx();
        transactionBeginFlag = true;

    }

    @PreDestroy
    public void stop() {
        log.info("Stopping managed transaction");
        if (transactionBeginFlag){
            transactionBeginFlag = false;
            transaction.success();
        }
    }

    public void prepareTransaction() {
        incrementOperationsCount();
        if (shouldCreateNewTransaction()) {
            commitAndStartNewTransaction();
        }
    }

    public void finishTransaction(){
        if (transactionBeginFlag){
            transactionBeginFlag = false;
            transaction.success();
        }
    }

    private boolean shouldCreateNewTransaction() {
        if (operationsCount.get() >= operationsThreshold) {
            return true;
        }
        return false;
    }

    private void commitAndStartNewTransaction() {
        log.info("========== Refreshing transaction, total: " + totalOperationsCount.addAndGet(operationsCount.getAndSet(0)));
        if (transactionBeginFlag){
            transactionBeginFlag = false;
            transaction.success();
        }
        transaction = graphDatabaseService.beginTx();
        transactionBeginFlag = true;
    }

    private void incrementOperationsCount() {
        operationsCount.incrementAndGet();
    }
}
