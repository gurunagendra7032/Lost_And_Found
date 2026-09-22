package college.project.demo.Controller;

import college.project.demo.DTOS.Login;
import college.project.demo.DTOS.SignUp;
import college.project.demo.Entities.Admin;
import college.project.demo.Entities.Role;
import college.project.demo.Entities.Users;
import college.project.demo.Repository.AdminRepo;
import college.project.demo.Repository.Repo;
import college.project.demo.Service.CustomUserDetailService;
import college.project.demo.Service.EmailService;
import college.project.demo.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://lost-and-found-frontend-drab.vercel.app")
public class UserController {

    @Autowired
    private Repo repo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private AdminRepo adminRepo;




    @PostMapping("/signup")
    public String save(@RequestBody SignUp dto) {

        Users user = customUserDetailService.convertToEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        String Id=user.getCode();

        Admin admin=adminRepo.findByRegisterId(Id);

        if (admin == null) {
            throw new RuntimeException("Invalid Admin registration code");
        }
        user.setAdmin(admin);

        repo.save(user);
        System.out.println("2. USER SAVED TO DATABASE");



        return "Register Successfully Completed";
    }






    @PostMapping("/login")
    public String login(@RequestBody Login loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // 1. Check Admin table
        Admin admin = adminRepo.findByEmail(email);

        if (admin != null) {

            if (passwordEncoder.matches(password, admin.getPassword())) {
                return jwtService.generateToken(
                        admin.getEmail(),
                        "ADMIN"
                );
            }

            throw new BadCredentialsException("Invalid password");
        }

        // 2. Check Users table
        Users user = repo.findByEmail(email);

        if (user != null) {

            if (passwordEncoder.matches(password, user.getPassword())) {
                return jwtService.generateToken(
                        user.getEmail(),
                        "USER"
                );
            }

            throw new BadCredentialsException("Invalid password");
        }

        throw new UsernameNotFoundException("Account not found");
    }
    @GetMapping("/hello")
    public String getResponse(){
        return "Hy buddy ! how Are u";
    }




}
