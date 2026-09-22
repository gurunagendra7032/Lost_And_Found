package college.project.demo.Service;

import college.project.demo.DTOS.SignUp;
import college.project.demo.Entities.*;
import college.project.demo.Repository.AdminRepo;
import college.project.demo.Repository.FoundRepo;
import college.project.demo.Repository.LostRepo;
import college.project.demo.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private Repo repo;

    @Autowired
    private FoundRepo foundrepo;

    @Autowired
    private LostRepo lostRepo;

    @Autowired
    private AdminRepo adminRepo;

//    public Users getUserDetails(String username){
//        return repo.findByEmail(username);
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user = repo.findByEmail(username);
       System.out.println("First  "+user);
       if(user!=null) {
           return User.builder()
                   .username(user.getEmail())
                   .password(user.getPassword())
                   .roles(user.getRole().name())
                   .build();
       }

        Admin admin = adminRepo.findByEmail(username);

        if (admin != null) {
            return User.builder()
                    .username(admin.getEmail())
                    .password(admin.getPassword())
                    .roles("ADMIN")
                    .build();
        }

        throw new UsernameNotFoundException(
                "Account not found: " + username
        );

    }

    //convert DTO to Entity

    public Users convertToEntity(SignUp dto) {
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setCode(dto.getCode());
        user.setRole(Role.USER);

        return user;
    }



    public List<FoundItem> searchFoundItems(String keyword,Users user) {

        return foundrepo
                .findByImageNameContainingIgnoreCaseAndUser(keyword,user);
    }


    public List<LostItem> searchlostItems(String keyword,Users user) {

        return lostRepo
                .findByImageNameContainingIgnoreCaseAndUser(keyword,user);
    }
}
