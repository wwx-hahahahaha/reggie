package com.irving.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.irving.domain.AddressBook;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper extends BaseMapper<AddressBook>
{

}
