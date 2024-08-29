package controller;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AliasService aliasService;  // Inject the AliasService

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) throws AuthenticationException {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        // Load the user details
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

        // Generate JWT token
        String jwt = jwtUtil.generateToken(userDetails.getUsername());

        // Fetch aliasType and aliasValue for the user
        String aliasType = aliasService.getAliasType(authRequest.getUsername());
        String aliasValue = aliasService.getAliasValue(authRequest.getUsername());

        // Return the response with JWT, aliasType, and aliasValue
        return ResponseEntity.ok(new AuthResponse(jwt, aliasType, aliasValue));
    }

    @Getter
    public static class AuthRequest {
        private String username;
        private String password;
    }

    @Getter
    public static class AuthResponse {
        private String jwt;
        private String aliasType;
        private String aliasValue;

        public AuthResponse(String jwt, String aliasType, String aliasValue) {
            this.jwt = jwt;
            this.aliasType = aliasType;
            this.aliasValue = aliasValue;
        }
    }
}
