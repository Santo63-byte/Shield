package com.sbyte.shield.datasource.entity.token;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;

@Entity
@Table(name = "SHDBKLTKN", schema = "shield_auth")

@Getter
@Setter
public class BklToken { /// Blacklisted Token Entity

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator
    @Column(name = "tknid", nullable = false, unique = true)
    private String tokenId;

    @Column(name = "bkltkn")
    private String token;

    @Column(name = "usrid", nullable = false)
    private String userId;

    @Column(name = "crtdat", nullable = false)
    private Long createdAt;

    @Column(name = "exprat", nullable = false)
    private Long expiresAt;

    @Column(name = "ssnid")
    private String sessionId;

    @Column(name = "dvcid")
    private String deviceId;

    @Column(name = "tmestmp", nullable = false)
    private LocalDateTime timestamp;

}
