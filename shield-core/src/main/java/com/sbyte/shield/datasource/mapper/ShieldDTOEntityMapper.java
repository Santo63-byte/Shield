package com.sbyte.shield.datasource.mapper;

import com.sbyte.shield.datasource.entity.token.BklToken;
import com.sbyte.shield.dto.BklTokenDTO;
import com.sbyte.shield.dto.LedgerDTO;
import com.sbyte.shield.dto.RFTokenDTO;
import com.sbyte.shield.datasource.entity.ledger.LedgerMaster;
import com.sbyte.shield.datasource.entity.token.RFToken;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = "spring", imports = {OffsetDateTime.class})
public interface ShieldDTOEntityMapper {

    ShieldDTOEntityMapper INSTANCE = Mappers.getMapper(ShieldDTOEntityMapper.class);

    // Entity to DTO mapping (reverse mapping for fetch operations)
    @Mapping(target = "registrationDTO.userName", source = "userName")
    @Mapping(target = "registrationDTO.password", source = "userPassword")
    @Mapping(target = "registrationDTO.status", source = "userStatus")
    @Mapping(target = "registrationDTO.createdAt", source = "userCreatedTime")
    @Mapping(target = "registrationDTO.updatedAt", source = "userLastUpdatedTime")
    @Mapping(target = "ledgerId", source = "ledgerId")
    @Mapping(target = "registrationDTO.userFirstName", source = "userInfo.userFirstName")
    @Mapping(target = "registrationDTO.userLastName", source = "userInfo.userLastName")
    @Mapping(target = "registrationDTO.email", source = "userInfo.userEmail")
    @Mapping(target = "registrationDTO.userPhone", source = "userInfo.userPhone")
    @Mapping(target = "registrationDTO.userAddress", source = "userInfo.userAddress")
    @Mapping(target = "registrationDTO.userDOB", source = "userInfo.userDOB")
    @Mapping(target = "registrationDTO.userId", source = "userInfo.userIdRef")
    @Mapping(target = "registrationDTO.role", source = "userInfo.userRole")
    LedgerDTO mapEntityToLedgerDTO(LedgerMaster ledgerMaster);

    @Mapping(target = "userName", source = "registrationDTO.userName")
    @Mapping(target = "userPassword", source = "registrationDTO.password")
    @Mapping(target = "userStatus", source = "registrationDTO.status")
    @Mapping(target = "userCreatedTime", source = "registrationDTO.createdAt")
    @Mapping(target = "userLastUpdatedTime", source = "registrationDTO.updatedAt")
    @Mapping(target = "ledgerId", source = "ledgerId")
    @Mapping(target = "userInfo.userFirstName",source = "registrationDTO.userFirstName")
    @Mapping(target = "userInfo.userLastName", source = "registrationDTO.userLastName")
    @Mapping(target = "userInfo.userEmail", source = "registrationDTO.email")
    @Mapping(target = "userInfo.userPhone", source = "registrationDTO.userPhone")
    @Mapping(target = "userInfo.userAddress", source = "registrationDTO.userAddress")
    @Mapping(target = "userInfo.userDOB", source = "registrationDTO.userDOB")
    @Mapping(target = "userInfo.userIdRef", source = "registrationDTO.userId")
    @Mapping(target = "userTransactionAudit.userAction", source = "registrationDTO.action")
    @Mapping(target = "userTransactionAudit.userIp", source = "registrationDTO.userIp")
    @Mapping(target = "userTransactionAudit.userDevice", source = "registrationDTO.userDevice")
    @Mapping(target = "userTransactionAudit.userAgent", source = "registrationDTO.userAgent")
    @Mapping(target = "userTransactionAudit.userActionTime", source = "registrationDTO.userActionTime")
    LedgerMaster mapLedgerDTOToEntity(LedgerDTO ledgerDTO);

    @AfterMapping
    default void mapLedgerDTOToEntity(@MappingTarget LedgerMaster ledgerMaster){
        if (ledgerMaster.getUserInfo() != null) {
            ledgerMaster.getUserInfo().setLedgerMaster(ledgerMaster);
        }
        if(ledgerMaster.getUserTransactionAudit() != null){
            ledgerMaster.getUserTransactionAudit().setLedgerMaster(ledgerMaster);
        }
    }
    RFToken mapRFTokenDTOToEntity(RFTokenDTO rfTokenValue);

    BklTokenDTO mapBklTokenEntitiesToDTOs(BklToken bklToken);
}
