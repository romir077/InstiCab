package com.InstiCab.dao;

import com.InstiCab.models.Transaction;
import com.InstiCab.models.Trip;
import com.InstiCab.utils.RowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public List<Transaction> getPassengerAllTransactions(String username) {
        final String sql = "SELECT t.transaction_id, t.time_transaction, t.date_transcation, t.amount, " +
                "t.status, t.username" +
                " FROM " +
                "transaction as t WHERE" +
                " t.status!=1 and t.username = ? order by t.transaction_id";
        try {
            return jdbcTemplate.query(sql, RowMappers.transactionRowMapper, username);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Error");
        }
    }

    public boolean transactionPending(String username) {
        final String sql = "SELECT * FROM transaction WHERE status!=1 AND username=?";
        try {
            return !jdbcTemplate.query(sql, RowMappers.transactionRowMapper, username).isEmpty();
        } catch (Exception e) {
            System.out.println(e);
            throw new UsernameNotFoundException("Error");
        }
    }

    public void endTransaction(String username) throws Exception {
        final String sql = "UPDATE transaction SET status = 1, time_transaction = ?, date_transcation = ? WHERE username = ?";
        try {
            jdbcTemplate.update(sql, Time.valueOf(LocalTime.now()), Date.valueOf(LocalDate.now()), username);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
