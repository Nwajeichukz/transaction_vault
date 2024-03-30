package com.mendusa.transactions.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    private Integer amount;

    private String balanceAfter;

    private Integer channelType;

    private String createdBy;

    private String currency;

    private Date dateModified;

    private String deviceId;
    private String deviceType;

    private String entityId;

    private String modifiedBy;

    private Integer processed;

    private String provider;

    private String responseCode;

    private String responseMessage;

    private Integer status;

    private String tranFee;

    private Date transactionDate;

    private String transactionId;

    private String transactionResourceId;

    private String transactionType;

    private String parentId;

    private String action;

    private String institutionId;

    private String entityType;

    private Integer settlementStatus;

    private String paymentMethod;

    private Date impactDate;

    private String balanceBefore;

    private Integer amountImpact;

    private Integer isSettled;

    private String settlementCycle;

    private Integer settlementCycleId;

    private Integer institutionSettlementStatus;

    private String settledTo;

    private Integer institutionCommission;


}
