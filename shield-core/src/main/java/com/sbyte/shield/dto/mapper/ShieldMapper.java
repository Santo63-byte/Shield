package com.sbyte.shield.dto.mapper;

import com.sbyte.shield.dto.CredentialsDTO;
import com.sbyte.shield.dto.RegistrationDTO;
import com.sbyte.shield.modals.LoginModal;
import com.sbyte.shield.modals.LogoutModal;
import com.sbyte.shield.modals.RegisterUpdateModal;
import com.sbyte.shield.modals.RegistrationModal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ShieldMapper {
    ShieldMapper ShieldDTOMapper = Mappers.getMapper(ShieldMapper.class);

    @Mapping(target = "company", expression = "java(registrationModal.getRegistrationType() != null && registrationModal.getRegistrationType().isCompany())")
    @Mapping(target = "individual", expression = "java(registrationModal.getRegistrationType() != null && registrationModal.getRegistrationType().isIndividual())")
    @Mapping(target = "companyName", source = "company.companyName")
    @Mapping(target = "companyCode", source = "company.companyCode")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "role", source = "user.role")
    @Mapping(target = "createdAt", source = "user.createdAt")
    @Mapping(target = "action", source = "user.action")
    @Mapping(target = "userName", source = "user.credentials.userName")
    @Mapping(target = "email", source = "user.credentials.email")
    @Mapping(target = "password", source = "user.credentials.password")
    @Mapping(target = "credentialId", source = "user.credentials.credentialId")
    @Mapping(target = "serviceName", source = "user.credentials.serviceName")
    RegistrationDTO convertRegistrationModaltoDTO(RegistrationModal registrationModal);


    @Mapping(target ="userId", source = "userId")
    @Mapping(target ="userPhone", source = "registrationModal.phoneNumber")
    @Mapping(target ="email", source = "registrationModal.email")
    @Mapping(target ="userName", source = "registrationModal.userName")
    RegistrationDTO convertUpdateRegistrationModaltoDTO(String userId, RegisterUpdateModal registrationModal);


    @Mapping(source = "credentials.userName", target = "userName")
    @Mapping(source = "credentials.password", target = "password")
    @Mapping(source = "credentials.email", target = "email")
    @Mapping(source = "credentials.credentialId", target = "credentialId")
    @Mapping(source = "credentials.serviceName", target = "serviceName")
    @Mapping(source = "credentials.captchaToken", target = "captcha")
    @Mapping(source = "credentials.phoneNumber", target = "phoneNumber")
    @Mapping(source = "device", target = "device")
    @Mapping(source = "companyInfo.companyName", target = "company.companyName")
    @Mapping(source = "companyInfo.companyCode", target = "company.companyCode")
    CredentialsDTO convertLoginModaltoDTO(LoginModal loginModal);


}
