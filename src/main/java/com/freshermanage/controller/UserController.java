package com.freshermanage.controller;

import com.freshermanage.jwt.JwtTokenProvider;
import com.freshermanage.model.ERole;
import com.freshermanage.model.Roles;
import com.freshermanage.model.Users;
import com.freshermanage.payload.request.LoginRequest;
import com.freshermanage.payload.request.SignupRequest;
import com.freshermanage.payload.response.JwtResponse;
import com.freshermanage.payload.response.MessageResponse;
import com.freshermanage.security.CustomUserDetails;
import com.freshermanage.service.RoleService;
import com.freshermanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/login")
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        if (userService.existsByUserName(signupRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Username is already!"));
        }
        if (userService.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Email is already!"));
        }
        Users user = new Users();
        user.setUserName(signupRequest.getUserName());
        user.setPassword(encoder.encode(signupRequest.getPassword()));
        user.setFullName(signupRequest.getFullName());
        user.setEmail(signupRequest.getEmail());
        Set<String> strRole = signupRequest.getListRoles();
        Set<Roles> listRole = new HashSet<>();
        if (strRole == null) {
            Roles userRole = roleService.findByRoleName(ERole.ROLE_FRESHER).orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            listRole.add(userRole);
        } else {
            strRole.forEach(role -> {
                switch (role) {
                    case "admin":
                        Roles adminRole = roleService.findByRoleName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role"));
                        listRole.add(adminRole);
                    case "fresher":
                        Roles fresherRole = roleService.findByRoleName(ERole.ROLE_FRESHER)
                                .orElseThrow(() -> new RuntimeException("Error"));
                        listRole.add(fresherRole);
                }
            });
        }
        user.setListRole(listRole);

        return null;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getDetails();
        String jwt = tokenProvider.generateToken(customUserDetails);
        List<String> listRole = customUserDetails.getAuthorities().stream()
                .map(item -> item.getAuthority()).collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, customUserDetails.getUsername(),customUserDetails.getEmail(),listRole));
    }
}


