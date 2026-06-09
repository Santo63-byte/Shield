package com.sbyte.shield.datasource.entity.ledger;
import java.time.OffsetDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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

    // @OneToMany(mappedBy = "ledgerMaster", cascade = CascadeType.ALL)
    // private  UserTransactionAudit userTransactionAudit;
}
