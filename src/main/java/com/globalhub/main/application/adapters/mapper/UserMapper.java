package com.globalhub.main.application.adapters.mapper;

import com.globalhub.main.application.dto.user.UserDetailsDTO;
import com.globalhub.main.domain.user.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDetailsDTO toUserDetailsDTO(User user);

}
