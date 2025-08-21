package com.ecommerce.user.service;

import com.app.ecom.dto.UserRequest;
import com.app.ecom.dto.UserResponse;
import com.ecommerce.user.repository.UserRepository;
import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public List<UserResponse> fetchAllUsers() {
        List<User> userList = userRepository.findAll();
        return userRepository.findAll().stream().map(this::mapToUserResponse)
                .collect(Collectors.toList());

    }

    public void addUser(UserRequest userRequest) {
        User user = new User();
        updateUserFromRequest(user, userRequest);
        userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
        user.setEmail(user.getEmail());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(user.getAddress().getStreet());
            address.setState(user.getAddress().getState());
            address.setCity(user.getAddress().getCity());
            address.setZipCode(user.getAddress().getZipCode());
            address.setCity(user.getAddress().getCity());
            address.setCountry(user.getAddress().getCountry());
        }
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public boolean updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);
    }

    private UserResponse mapToUserResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());
        if (user.getAddress() != null) {
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getCity());
            addressDTO.setId(user.getId());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipCode(user.getAddress().getZipCode());
        }
        return response;
    }
}
