package com.globalhub.main.controller;

import com.globalhub.main.application.dto.AuthenticationResponseDTO;
import com.globalhub.main.application.dto.user.UserLoginDTO;
import com.globalhub.main.application.dto.user.UserRegisterDTO;
import com.globalhub.main.application.response.BaseResponse;
import com.globalhub.main.domain.user.User;
import com.globalhub.main.infrastructure.security.TokenService;
import com.globalhub.main.repository.UserRepository;
import com.globalhub.main.utils.RggGenerator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository repository;
    private final TokenService tokenService;
    private final RggGenerator rggGenerator;

    public AuthenticationController(AuthenticationManager authenticationManager, UserRepository repository, TokenService tokenService, RggGenerator rggGenerator) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
        this.rggGenerator = rggGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthenticationResponseDTO>> login(@RequestBody @Valid UserLoginDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.ok(new AuthenticationResponseDTO(token, "User successfully logged in!")));
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BaseResponse<String>> register(@RequestBody @Valid UserRegisterDTO data) {
        if (repository.findByEmailOrRgg(data.email()).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error("This user already exists!"));

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        String rgg = rggGenerator.generateNewRgg();

        User newUser = new User(data.email(), encryptedPassword, rgg, data.role());
        repository.save(newUser);

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok("User successfully registSered!"));
    }

}
