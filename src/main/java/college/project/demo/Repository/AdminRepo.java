package college.project.demo.Repository;

import college.project.demo.Entities.Admin;
import college.project.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {

    Admin findByEmail(String email);

    Admin findByRegisterId(String reference);
}
