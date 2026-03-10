package com.globalhub.main.controller;

import com.globalhub.main.application.dto.AuthenticationResponseDTO;
import com.globalhub.main.application.dto.user.UserLoginDTO;
import com.globalhub.main.application.dto.user.UserRegisterDTO;
import com.globalhub.main.application.response.BaseResponse;
import com.globalhub.main.domain.events.user.UserRegisteredEvent;
import com.globalhub.main.domain.user.User;
import com.globalhub.main.domain.user.UserRole;
import com.globalhub.main.infrastructure.security.TokenService;
import com.globalhub.main.repository.UserRepository;
import com.globalhub.main.utils.RggGenerator;
import com.globalhub.main.utils.SecurityUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
    private final ApplicationEventPublisher eventPublisher;
    private final RggGenerator rggGenerator;
    private final SecurityUtils securityUtils;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UserRepository repository,
                                    TokenService tokenService,
                                    ApplicationEventPublisher eventPublisher,
                                    RggGenerator rggGenerator,
                                    SecurityUtils securityUtils,
                                    BCryptPasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.repository = repository;
        this.tokenService = tokenService;
        this.eventPublisher = eventPublisher;
        this.rggGenerator = rggGenerator;
        this.securityUtils = securityUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthenticationResponseDTO>> login(@RequestBody @Valid UserLoginDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK)
                .body(BaseResponse.ok(new AuthenticationResponseDTO(token, "User successfully logged in!")));
    }

    @PostMapping("/register") @Transactional
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<BaseResponse<String>> register(@RequestBody @Valid UserRegisterDTO data) {
        if (data.role().equals(UserRole.SUPER_ADMIN)) {
            throw new AccessDeniedException("Novos super administradores não podem ser registrados no sistema!");
        }

        if (data.role().equals(UserRole.ADMIN) && !securityUtils.hasRole("SUPER_ADMIN")) {
            throw new AccessDeniedException("Apenas super administradores podem registrar novos administradores!");
        }

        if (repository.findByEmailOrRgg(data.email()).isPresent())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponse.error("This user already exists!"));

        String encryptedPassword = passwordEncoder.encode(data.password());
        String rgg = rggGenerator.generateNewRgg();

        User newUser = new User(data.email(), encryptedPassword, data.name(), data.cpf(), rgg, data.role());
        repository.save(newUser);

        eventPublisher.publishEvent(new UserRegisteredEvent(newUser))   ;
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponse.ok("User successfully registered!"));
    }

}
