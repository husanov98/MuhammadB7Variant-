package uz.pdp.muhammadb7variant2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.muhammadb7variant2.entity.User;
import uz.pdp.muhammadb7variant2.entity.enums.RoleName;
import uz.pdp.muhammadb7variant2.payload.ApiResponse;
import uz.pdp.muhammadb7variant2.payload.UserDto;
import uz.pdp.muhammadb7variant2.repository.RoleRepository;
import uz.pdp.muhammadb7variant2.repository.UserRepository;
import uz.pdp.muhammadb7variant2.security.JwtProvider;

import java.util.UUID;

@Service
public class AuthService implements UserDetailsService {


    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    JavaMailSender mailSender;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse register(UserDto userDto) {
        boolean existByEmail = userRepository.existsByEmail(userDto.getEmail());
        if (existByEmail) {
            return new ApiResponse("this email already exist", false);
        }
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(roleRepository.findByRoleName(RoleName.ROLE_USER));
        user.setEmailCode(UUID.randomUUID().toString());
        userRepository.save(user);
        sendEmail(user.getEmail(), user.getEmailCode());
        return new ApiResponse("Successfully registered,please validate your email", true);
    }

    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("muhammadhusanov98@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Validate account");
            mailMessage.setText("<a href='http://localhost:8080/api/auth/verifyEmail?emailCode=" + emailCode + "&email=" + sendingEmail + "'>Validate</a>");
            mailSender.send(mailMessage);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ApiResponse verifyEmail(String emailCode, String email) {

        var optionalUser = userRepository.findByEmailAndEmailCode(email, emailCode);
        if (optionalUser.isPresent()) {
            var user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            userRepository.save(user);
            return new ApiResponse("Account validated", true);
        }
        return new ApiResponse("Account already validated", false);
    }

    public ApiResponse login(UserDto userDto) {
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDto.getEmail(),
                    userDto.getPassword()
            ));
            User user = (User) authentication.getPrincipal();
            String token = jwtProvider.generateToken(userDto.getEmail(), user.getRoles());
            return new ApiResponse("Token", true, token);
        } catch (BadCredentialsException badCredentialsException) {
            return new ApiResponse("password or login incorrect", false);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new UsernameNotFoundException(email + "not found");

    }
}
