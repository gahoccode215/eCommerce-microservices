package com.ecommerce.user.service;

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

//    private void updateUserFromRequest(User user, UserRequest userRequest) {
//        user.setFirstName(userRequest.getFirstName());
//        user.setLastName(userRequest.getLastName());
//        user.setPhone(userRequest.getPhone());
//        user.setEmail(user.getEmail());
//        if (userRequest.getAddress() != null) {
//            Address address = new Address();
//            address.setStreet(user.getAddress().getStreet());
//            address.setState(user.getAddress().getState());
//            address.setCity(user.getAddress().getCity());
//            address.setZipCode(user.getAddress().getZipCode());
//            address.setCity(user.getAddress().getCity());
//            address.setCountry(user.getAddress().getCountry());
//        }
//    }
private void updateUserFromRequest(User user, UserRequest userRequest) {
    user.setFirstName(userRequest.getFirstName());
    user.setLastName(userRequest.getLastName());
    user.setEmail(userRequest.getEmail());
    user.setPhone(userRequest.getPhone());
    if (userRequest.getAddress() != null) {
        Address address = new Address();
        address.setStreet(userRequest.getAddress().getStreet());
        address.setState(userRequest.getAddress().getState());
        address.setZipCode(userRequest.getAddress().getZipCode());
        address.setCity(userRequest.getAddress().getCity());
        address.setCountry(userRequest.getAddress().getCountry());
        user.setAddress(address);
    }
}

    public Optional<UserResponse> fetchUser(String id) {
        return userRepository.findById(id).map(this::mapToUserResponse);
    }

    public boolean updateUser(String id, UserRequest updatedUserRequest) {
        return userRepository.findById(String.valueOf(id))
                .map(existingUser -> {
                    updateUserFromRequest(existingUser, updatedUserRequest);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
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
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipCode(user.getAddress().getZipCode());
        }
        return response;
    }
}
