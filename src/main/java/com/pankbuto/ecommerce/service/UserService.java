package com.pankbuto.ecommerce.service;

import com.pankbuto.ecommerce.dto.ResponseDto;
import com.pankbuto.ecommerce.dto.user.SignInDto;
import com.pankbuto.ecommerce.dto.user.SignInResponseDto;
import com.pankbuto.ecommerce.dto.user.SignUpDto;
import com.pankbuto.ecommerce.enums.ResponseStatus;
import com.pankbuto.ecommerce.enums.Role;
import com.pankbuto.ecommerce.models.AuthenticationToken;
import com.pankbuto.ecommerce.models.User;
import com.pankbuto.ecommerce.repository.UserRepository;
import com.pankbuto.ecommerce.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public ResponseDto signUp(SignUpDto signupDto) throws Exception {
        // verify that user already registered with email
        if(Helper.notNull(userRepository.findByEmail(signupDto.getEmail()))) {
            throw new Exception("User already exists");
        }

        String encryptedPassword = signupDto.getPassword();

        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        User user = new User(signupDto.getFirstName(), signupDto.getLastName(), signupDto.getEmail(), Role.user, encryptedPassword);

        User createdUser;

        try {
            createdUser = userRepository.save(user);
            final AuthenticationToken authenticationToken = new AuthenticationToken(createdUser);
            authenticationService.saveConfirmationToken(authenticationToken);

            return new ResponseDto(ResponseStatus.success.toString(), "user created successfully");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();

        return myHash;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public SignInResponseDto signIn(SignInDto signInDto) throws Exception {
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(!Helper.notNull(user)) {
            throw new Exception("user not present");
        }

        try {
            if(!user.getPassword().equals(hashPassword(signInDto.getPassword()))) {
                throw new Exception("please check the password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        AuthenticationToken token = authenticationService.getToken(user);

        if(!Helper.notNull(token)) {
            throw new Exception("token not present");
        }

        return new SignInResponseDto("success", token.getToken());
    }
}
