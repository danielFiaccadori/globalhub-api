package com.globalhub.main.application.service;

import com.globalhub.main.application.adapters.mapper.UserMapper;
import com.globalhub.main.application.dto.UserDetailsDTO;
import com.globalhub.main.domain.user.User;
import com.globalhub.main.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<UserDetailsDTO> findAll(Pageable pageable) {
        Page<User> page = repository.findAll(pageable);
        return page.stream().map(mapper::toUserDetailsDTO).toList();
    }

}
