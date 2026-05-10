package college.project.demo.Repository;

import college.project.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Repo extends JpaRepository<Users,Integer> {

    Users findByEmail(String email);
}
