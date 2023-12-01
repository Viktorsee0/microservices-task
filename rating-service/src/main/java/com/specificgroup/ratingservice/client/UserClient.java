package com.specificgroup.ratingservice.client;

import com.specificgroup.ratingservice.domain.ScoreRow;
import com.specificgroup.ratingservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;

/**
 * This class is a client of the authentication service
 */
@FeignClient("authentication-service")
public interface UserClient {

    /**
     * Receives the {@link UserDTO} representing the user
     *
     * @param id an id of user
     * @return a user data
     */
    @RequestMapping(method = RequestMethod.GET, value = "api/v1/user/id/{id}")
    UserDTO getUserById(@PathVariable("id") UUID id);
}
