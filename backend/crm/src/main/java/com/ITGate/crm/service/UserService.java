package com.ITGate.crm.service;

import com.ITGate.crm.dto.user.UserRequestDTO;
import com.ITGate.crm.dto.user.UserResponseDTO;
import com.ITGate.crm.mapper.UserMapper;
import com.ITGate.crm.model.Role;
import com.ITGate.crm.model.User;
import com.ITGate.crm.repository.RoleRepository;
import com.ITGate.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream().map(userMapper::toUserDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur non trouvé avec l'id: " + id
                )
        );
        return userMapper.toUserDTO(user);
    }

    public UserResponseDTO getUserByLogin(String login) {
        User user = userRepository.findByLogin(login).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Utilisateur non trouvé avec le login: " + login
                )
        );
        return userMapper.toUserDTO(user);
    }

    public UserResponseDTO createUser(UserRequestDTO request) {
        // Check password
        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le mot de passe est obligatoire");
        }
        if (request.getPassword().length() < 6 || request.getPassword().length() > 255) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir entre 6 et 255 caractères");
        }

        // Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet email est déjà utilisé");
        }

        // Check login
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce login est déjà utilisé");
        }

        // Find role
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Rôle introuvable")
                );

        // DTO → Entity
        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        // Save
        User savedUser = userRepository.save(user);

        // Entity → DTO
        return userMapper.toUserDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec l'id: " + id)
                );

        // Check if email changed and taken
        if (!user.getEmail().equalsIgnoreCase(request.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cet email est déjà utilisé");
        }

        // Check if login changed and taken
        if (!user.getLogin().equalsIgnoreCase(request.getLogin()) && userRepository.existsByLogin(request.getLogin())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce login est déjà utilisé");
        }

        // Update basic fields
        userMapper.updateUser(request, user);

        // Update password if provided
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            if (request.getPassword().length() < 6 || request.getPassword().length() > 255) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Le mot de passe doit contenir entre 6 et 255 caractères");
            }
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        // Update role if provided
        if (request.getRoleId() != null) {
            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() ->
                            new ResponseStatusException(HttpStatus.NOT_FOUND, "Rôle introuvable")
                    );
            user.setRole(role);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toUserDTO(updatedUser);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur non trouvé avec l'id: " + id)
                );

        userRepository.delete(user);
    }
}

