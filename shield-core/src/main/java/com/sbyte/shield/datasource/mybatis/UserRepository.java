package com.sbyte.shield.datasource.mybatis;

import com.sbyte.shield.dto.BasicUserCredentialDTO;
import com.sbyte.shield.dto.UserFilterDTO;
import com.sbyte.shield.datasource.entity.ledger.UserCredInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRepository {

    List<UserCredInfo> findAllActiveUsers();

    BasicUserCredentialDTO getUserExistingCredContext(UserFilterDTO filter);

}
