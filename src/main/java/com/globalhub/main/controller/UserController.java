package com.globalhub.main.controller;

import com.globalhub.main.application.dto.user.UserDetailsDTO;
import com.globalhub.main.application.dto.user.UserUpdatePasswordRequestDTO;
import com.globalhub.main.application.dto.user.UserUpdateRequestDTO;
import com.globalhub.main.application.response.BaseResponse;
import com.globalhub.main.application.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Page<UserDetailsDTO>>> findAll(Pageable pageable) {
        Page<UserDetailsDTO> users = userService.findAll(pageable);
        return ResponseEntity.ok().body(BaseResponse.ok(users));
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<UserDetailsDTO>> findById(@PathVariable UUID uuid) {
        return ResponseEntity.ok().body(BaseResponse.ok(userService.findById(uuid)));
    }

    @PutMapping("/update/{uuid}/details")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<UserDetailsDTO>> update(@PathVariable UUID uuid,
                                                               @Valid @RequestBody UserUpdateRequestDTO updateRequestData) {
        UserDetailsDTO updatedUser = userService.update(uuid, updateRequestData);
        return ResponseEntity.ok().body(BaseResponse.ok(updatedUser, "User updated successfully!"));
    }

    @PutMapping("/{uuid}/password")
    public ResponseEntity<BaseResponse<Boolean>> updatePassword(@PathVariable UUID uuid,
                                                                @Valid @RequestBody UserUpdatePasswordRequestDTO updatePasswordRequestData) {
        boolean success = userService.updatePassword(uuid, updatePasswordRequestData);
        return ResponseEntity.ok().body(BaseResponse.ok(success, "User password updated successfully!"));
    }

    @DeleteMapping("/{uuid}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> deactivateUser(@PathVariable UUID uuid) {
        userService.updateActivity(uuid, false);
        return ResponseEntity.ok().body(BaseResponse.ok(null, "User successfully deactivated ;("));
    }

    @PutMapping("/{uuid}/activate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<Void>> activateUser(@PathVariable UUID uuid) {
        userService.updateActivity(uuid, true);
        return ResponseEntity.ok().body(BaseResponse.ok(null, "User successfully reactivated :)"));
    }

}
