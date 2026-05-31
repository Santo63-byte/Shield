package com.sbyte.shield.datasource.entity.ledger;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "SHDLDGMST" , schema = "shield_auth")
public class LedgerMaster {

    @Id
    @Column(name = "ldguid", nullable = false)
    private Long ledgerId;

    @Column(name = "usrnme", nullable = false)
    private String userName;

    @Column(name = "usrpsd", nullable = false)
    private String userPassword;

    @Column(name = "usrctdtme", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime userCreatedTime;

    @Column(name = "usrlstupdtme", nullable = false, columnDefinition = "timestamp with time zone")
    private OffsetDateTime userLastUpdatedTime;

    @Column(name = "usraccsts")
    private String userStatus;

    @OneToOne(mappedBy = "ledgerMaster", cascade = CascadeType.ALL)
    private UserInfo userInfo;

    @OneToOne(mappedBy = "ledgerMaster", cascade = CascadeType.ALL)
    private  UserTransactionAudit userTransactionAudit;
}
