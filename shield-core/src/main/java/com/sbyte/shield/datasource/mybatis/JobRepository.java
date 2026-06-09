package com.sbyte.shield.datasource.mybatis;
import java.time.LocalDateTime;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface JobRepository {

        void deleteBlacklistedTokensOlderThan(@Param("cutoffTime") LocalDateTime cutoffTime);
    
}
