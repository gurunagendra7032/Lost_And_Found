package college.project.demo.Controller;

import college.project.demo.DTOS.SignUp;
import college.project.demo.Entities.Role;
import college.project.demo.Entities.Users;
import college.project.demo.Repository.Repo;
import college.project.demo.Service.CustomUserDetailService;
import college.project.demo.Service.EmailService;
import college.project.demo.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private EmailService emailService;




    @PostMapping("/signup")
    public String save(@RequestBody SignUp dto){
        Users user = customUserDetailService.convertToEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        try {
            emailService.sendRegistrationEmail(
                    user.getEmail(),
                    user.getName()
            );
        } catch (Exception e) {
            System.out.println("Email sending failed: " + e.getMessage());
        }
        return "Register Successfully Completed";
    }

    @PostMapping("/adm")
    public String saveAdmin(@RequestBody Users user){
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repo.save(user);
        return "Created Admin Successfully";
    }

    @PostMapping("/adm/login")
    public String Adlogin(@RequestBody Users user){
        Users users=repo.findByEmail(user.getEmail());
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(),user.getPassword()
        ));
        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user);

        }else{
            throw new UsernameNotFoundException("user is Invalid");
        }

    }


    @PostMapping("/login")
    public String login(@RequestBody Users loginUser) {
        Users user = repo.findByEmail(loginUser.getEmail());

        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginUser.getEmail(),loginUser.getPassword()
        ));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user);

        }else{
            throw new UsernameNotFoundException("user is Invalid");
        }
    }

    @GetMapping("/hello")
    public String getResponse(){
        return "Hy buddy ! how Are u";
    }




}
