package college.project.demo.Service;

import college.project.demo.DTOS.SignUp;
import college.project.demo.Entities.Role;
import college.project.demo.Entities.Users;
import college.project.demo.Repository.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private Repo repo;

//    public Users getUserDetails(String username){
//        return repo.findByEmail(username);
//    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Users user = repo.findByEmail(username);
       return User.builder()
               .username(user.getEmail())
               .password(user.getPassword())
               .authorities(user.getRole().name())
               .build();

    }

    //convert DTO to Entity

    public Users convertToEntity(SignUp dto) {
        Users user = new Users();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setRole(Role.USER);

        return user;
    }
}
