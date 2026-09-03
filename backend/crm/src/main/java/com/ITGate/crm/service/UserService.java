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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll()
                .stream().map(userMapper::toUserDTO)
                .toList();
    }

    public UserResponseDTO getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + id
                )
        );;
        return userMapper.toUserDTO(user);
    }

    public UserResponseDTO createUser(UserRequestDTO request){
        // Check email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Check login
        if (userRepository.existsByLogin(request.getLogin())) {
            throw new RuntimeException("Login already exists");
        }

        // Find role
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() ->
                        new RuntimeException("Role not found")
                );

        // DTO → Entity
        User user = userMapper.toUser(request);
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        // Set relationship
        user.setRole(role);

        // Save
        User savedUser = userRepository.save(user);

        // Entity → DTO
        return userMapper.toUserDTO(savedUser);
    }

    public UserResponseDTO updateUser(Long id,UserRequestDTO request){
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id)
                );

        // Update basic fields
        userMapper.updateUser(request, user);
        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(request.getPassword())
            );
        }

        // Update role if provided
        if (request.getRoleId() != null) {

            Role role = roleRepository.findById(request.getRoleId())
                    .orElseThrow(() ->
                            new RuntimeException("Role not found")
                    );

            user.setRole(role);
        }

        User updatedUser = userRepository.save(user);

        return userMapper.toUserDTO(updatedUser);
    }

    public void deleteUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id)
                );

        userRepository.delete(user);
    }
}
