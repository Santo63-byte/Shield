package com.sbyte.shield.datasource.entity.ledger;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "SHDUSRRGST", schema = "shield_auth")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "ldguid", referencedColumnName = "ldguid")
    private LedgerMaster ledgerMaster;

    @Column(name = "usrfstnme")
    private String userFirstName;

    @Column(name = "usrlstnme")
    private String userLastName;

    @Column(name = "usreml")
    private String userEmail;

    @Column(name = "usrphn")
    private String userPhone;

    @Column(name = "usraddrinf")
    private String userAddress;

    @Column(name = "usrdob")
    private String userDOB;

    @Column(name="usridref")
    private String userIdRef; // can be sent to client

    @Column(name="usrtknvrsn")
    private Integer userTokenVersion;

    @Column(name="usrrle")
    private String userRole;
}
