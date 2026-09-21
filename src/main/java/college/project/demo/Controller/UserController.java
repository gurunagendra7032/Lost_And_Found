package college.project.demo.Controller;

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
        System.out.println("this si id ........*#&&#&#"+Id);
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
    public String login(@RequestBody Users loginUser) {
        Users user = repo.findByEmail(loginUser.getEmail());

        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUser.getEmail(),loginUser.getPassword()
        ));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(
                    user.getEmail(),
                    "USER"
            );


        }else{
            throw new UsernameNotFoundException("user is Invalid");
        }
    }

    @GetMapping("/hello")
    public String getResponse(){
        return "Hy buddy ! how Are u";
    }




}
