package com.sbyte.shield.datasource.entity.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "SHDLVETKN", schema = "shield_auth")
public class RFToken { ///  Active Refresh Token Entity

    @Id
    @Column(name = "lvetknid")
    private Long tokenid;

    @Column(name = "lverftkn")
    private String refreshToken;

    @Column(name = "usrid")
    private String loggedInUserId;

    @Column(name = "crtdat", nullable = false)
    private Long createdAt;

    @Column(name = "exprat", nullable = false)
    private Long expiresAt;

    @Column(name = "ssnid")
    private String sessionId;

    @Column(name = "dvcid")
    private String deviceId;

}
