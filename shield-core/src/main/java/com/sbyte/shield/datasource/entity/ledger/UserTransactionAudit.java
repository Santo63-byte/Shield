package com.sbyte.shield.datasource.entity.ledger;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "SHDUSRADT", schema = "shield_auth")
public class UserTransactionAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ldguid", referencedColumnName = "ldguid")
    private LedgerMaster ledgerMaster;

    @Column(name = "usracn", nullable = false, length = 50)
    private String userAction;

    @Column(name = "usrip", length = 50)
    private String userIp;

    @Column(name ="usrdvc", length = 100)
    private String userDevice;

    @Column(name = "usragnt", columnDefinition = "TEXT")
    private String userAgent;

    @Column(name = "usracntme")
    private OffsetDateTime userActionTime;

}