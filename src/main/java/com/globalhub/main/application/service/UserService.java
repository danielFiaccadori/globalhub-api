package com.globalhub.main.application.service;

import com.globalhub.main.application.adapters.mapper.UserMapper;
import com.globalhub.main.application.dto.user.UserDetailsDTO;
import com.globalhub.main.application.dto.user.UserUpdatePasswordRequestDTO;
import com.globalhub.main.application.dto.user.UserUpdateRequestDTO;
import com.globalhub.main.domain.exception.UserNotFoundException;
import com.globalhub.main.domain.user.User;
import com.globalhub.main.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, UserMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Page<UserDetailsDTO> findAll(Pageable pageable) {
        Page<User> page = repository.findAll(pageable);
        return page.map(mapper::toUserDetailsDTO);
    }

    public UserDetailsDTO findById(UUID uuid) {
        User user = repository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));
        return mapper.toUserDetailsDTO(user);
    }

    @Transactional
    public UserDetailsDTO update(UUID uuid, UserUpdateRequestDTO updateRequestData) {
        User toUpdateUser = repository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));

        Optional.ofNullable(updateRequestData.newEmail()).ifPresent(toUpdateUser::setEmail);
        Optional.ofNullable(updateRequestData.newRole()).ifPresent(toUpdateUser::setRole);

        repository.save(toUpdateUser);
        return mapper.toUserDetailsDTO(toUpdateUser);
    }

    @Transactional
    public boolean updatePassword(UUID uuid, UserUpdatePasswordRequestDTO updatePasswordRequestData) {
        User toUpdateUser = repository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));

        String encodedPassword = passwordEncoder.encode(updatePasswordRequestData.newPassword());
        toUpdateUser.setPassword(encodedPassword);

        repository.save(toUpdateUser);
        return true;
    }

    @Transactional
    public void updateActivity(UUID uuid, boolean activity) {
        User toUpdateActivityUser = repository.findById(uuid)
                .orElseThrow(() -> new UserNotFoundException(uuid));

        toUpdateActivityUser.setIsActive(activity);

        repository.save(toUpdateActivityUser);
    }

}
