package com.ITGate.crm.service;

import com.ITGate.crm.dto.auth.LoginRequestDTO;
import com.ITGate.crm.dto.auth.LoginResponseDTO;
import com.ITGate.crm.dto.auth.RegisterRequestDTO;
import com.ITGate.crm.enums.RoleName;
import com.ITGate.crm.model.Role;
import com.ITGate.crm.model.User;
import com.ITGate.crm.repository.RoleRepository;
import com.ITGate.crm.repository.UserRepository;
import com.ITGate.crm.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDTO login(LoginRequestDTO request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByLogin(request.getLogin()).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with login: "
                                + request.getLogin()
                )
        );
        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(user.getLogin())
                        .password(user.getPassword())
                        .authorities(
                                "ROLE_" +
                                        user.getRole()
                                                .getName()
                                                .name()
                        )
                        .build()
        );
        return new LoginResponseDTO(
                "Login successful",
                user.getId(),
                user.getNom(),
                user.getPrenom(),
                user.getEmail(),
                user.getLogin(),
                user.getRole().getName().name(),
                token
        );
    }

    public LoginResponseDTO register(RegisterRequestDTO request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet email est déjà utilisé");
        }
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce login est déjà utilisé");
        }

        Role role = roleRepository.findByName(RoleName.AGENT_CRM)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rôle par défaut introuvable"));

        User user = new User();
        user.setNom(request.getNom());
        user.setPrenom(request.getPrenom());
        user.setEmail(request.getEmail());
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setTelephone(request.getTelephone());
        user.setRole(role);

        User savedUser = userRepository.save(user);

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(savedUser.getLogin())
                        .password(savedUser.getPassword())
                        .authorities(
                                "ROLE_" + savedUser.getRole().getName().name()
                        )
                        .build()
        );

        return new LoginResponseDTO(
                "Inscription réussie",
                savedUser.getId(),
                savedUser.getNom(),
                savedUser.getPrenom(),
                savedUser.getEmail(),
                savedUser.getLogin(),
                savedUser.getRole().getName().name(),
                token
        );
    }
}
