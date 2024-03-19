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

    private String balance_after;

    private Integer channel_type;

    private String created_by;

    private String currency;

    private Date date_modified;

    private String device_id;
    private String device_type;

    private String entity_id;

    private String modified_by;

    private Integer processed;

    private String provider;

    private String response_code;

    private String response_message;

    private Integer status;

    private String tran_fee;

    private Date transaction_date;

    private String transaction_id;

    private String transaction_resource_id;

    private String transaction_type;

    private String parent_id;

    private String action;

    private String institution_id;

    private String entity_type;

    private Integer settlement_status;

    private String payment_method;

    private Date impact_date;

    private String balance_before;

    private Integer amount_impact;

    private Integer is_settled;

    private String settlement_cycle;

    private Integer settlement_cycle_id;

    private Integer institution_settlement_status;

    private String settled_to;

    private Integer institution_commission;


}
