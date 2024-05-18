package com.mendusa.transactions.repository;

import com.mendusa.transactions.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//
//    @Query("SELECT t.responseCode, t.paymentMethod, t.provider, COUNT(t) FROM Transaction t WHERE t.transactionDate >= :last24Hours GROUP BY t.responseCode, t.paymentMethod, t.provider")
//            List<Object[]> findTransactionCountByResponseCodeAndPaymentMethodAndProvider(Date last24Hours);

    @Query("SELECT t.responseCode AS responseCode, t.paymentMethod AS paymentMethod, COUNT(t) AS totalCount FROM Transaction t WHERE t.transactionDate >= :last24Hours GROUP BY t.responseCode, t.paymentMethod")
    List<Tuple> findTransactionCountByResponseCodeAndPaymentMethod(Date last24Hours);


    @Query("SELECT t.responseCode AS responseCode, t.provider AS provider, COUNT(t) AS totalCount FROM Transaction t WHERE t.transactionDate >= :last24Hours GROUP BY t.responseCode, t.provider")
    List<Tuple> findTransactionCountByResponseCodeAndProvider(Date last24Hours);


}
