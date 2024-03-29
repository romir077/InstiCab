package com.InstiCab.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long transactionId;
    private Long tripId;
    private Time timeTransaction;
    private Date dateTransaction;
    private int amount;
    private int status;
    private String username;

}
