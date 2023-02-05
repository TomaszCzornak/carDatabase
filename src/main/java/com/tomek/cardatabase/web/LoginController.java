package com.tomek.cardatabase.web;

import com.tomek.cardatabase.domain.AccountCredentials;
import com.tomek.cardatabase.service.JwtService;
import com.tomek.cardatabase.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class LoginController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<?> getToken(@RequestBody AccountCredentials accountCredentials) {
        /* Generate token and send it in the response
        Authorization
         header*/
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                accountCredentials.getUsername(),
                accountCredentials.getPassword());

//        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(accountCredentials.getUsername());
        //Generate token
        String jwt = jwtService.getToken(userDetails);
        System.out.println(jwt + " masakroza");
        //Build response with the generated token
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization")
                .build();

    }
}
