package com.InstiCab.dao;

import com.InstiCab.models.Transaction;
import com.InstiCab.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public void saveTransaction(Transaction transaction) throws Exception {
        final String sql = "INSERT INTO transaction(status,amount,username,date_transcation,time_transaction) VALUES (?, ?, ?, ?, ?)";
        try {
            jdbcTemplate.update(sql,transaction.getStatus(),transaction.getAmount(),transaction.getUsername(),
                    transaction.getDateTranscation(), transaction.getTimeTransaction());
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
