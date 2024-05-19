package com.mendusa.transactions.dto;

import com.mendusa.transactions.entity.Transaction;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDto {
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


    public TransactionDto(Transaction transaction) {
        this.amount = transaction.getAmount();
        this.balanceAfter = transaction.getBalanceAfter();
        this.channelType = transaction.getChannelType();
        this.createdBy = transaction.getCreatedBy();
        this.currency = transaction.getCurrency();
        this.dateModified = transaction.getDateModified();
        this.deviceId = transaction.getDeviceId();
        this.deviceType = transaction.getDeviceType();
        this.entityId = transaction.getEntityId();
        this.modifiedBy = transaction.getModifiedBy();
        this.processed = transaction.getProcessed();
        this.provider = transaction.getProvider();
        this.responseCode = transaction.getResponseCode();
        this.responseMessage = transaction.getResponseMessage();
        this.status = transaction.getStatus();
        this.tranFee = transaction.getTranFee();
        this.transactionDate = transaction.getTransactionDate();
        this.transactionId = transaction.getTransactionId();
        this.transactionResourceId = transaction.getTransactionResourceId();
        this.transactionType = transaction.getTransactionType();
        this.parentId = transaction.getParentId();
        this.action = transaction.getAction();
        this.institutionId = transaction.getInstitutionId();
        this.entityType = transaction.getEntityType();
        this.settlementStatus = transaction.getSettlementStatus();
        this.paymentMethod = transaction.getPaymentMethod();
        this.impactDate = transaction.getImpactDate();
        this.balanceBefore = transaction.getBalanceBefore();
        this.amountImpact = transaction.getAmountImpact();
        this.isSettled = transaction.getIsSettled();
        this.settlementCycle = transaction.getSettlementCycle();
        this.settlementCycleId = transaction.getSettlementCycleId();
        this.institutionSettlementStatus = transaction.getInstitutionSettlementStatus();
        this.settledTo = transaction.getSettledTo();
        this.institutionCommission = transaction.getInstitutionCommission();
    }
}
