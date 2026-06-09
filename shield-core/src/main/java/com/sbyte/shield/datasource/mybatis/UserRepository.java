package com.sbyte.shield.datasource.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.sbyte.shield.datasource.entity.ledger.UserCredInfo;
import com.sbyte.shield.dto.BasicUserCredentialDTO;
import com.sbyte.shield.dto.UserFilterDTO;

@Mapper
public interface UserRepository {

    List<UserCredInfo> findAllActiveUsers();

    BasicUserCredentialDTO getUserExistingCredContext(@Param("filter") UserFilterDTO filter);

}
