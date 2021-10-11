package com.pankbuto.ecommerce.service;

import com.pankbuto.ecommerce.models.AuthenticationToken;
import com.pankbuto.ecommerce.models.User;
import com.pankbuto.ecommerce.repository.TokenRepository;
import com.pankbuto.ecommerce.utils.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private TokenRepository repository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        repository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return repository.findTokenByUser(user);
    }

    public void authenticate(String token) throws Exception {

        if(!Helper.notNull(token)) {
            throw new Exception("authentication token not present");
        }

        if(!Helper.notNull(getUser(token))) {
            throw new Exception("authentication token is not valid. Please try again");
        }

    }

    public User getUser(String token) {
        AuthenticationToken authenticationToken = this.repository.findTokenByToken(token);
        if(Helper.notNull(authenticationToken)) {
            if(Helper.notNull(authenticationToken.getUser())) {
                return authenticationToken.getUser();
            }
        }

        return null;
    }
}
