package college.project.demo.Repository;

import college.project.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Repo extends JpaRepository<Users,Integer> {

    Users findByEmail(String email);

    Users findByCode(String code);
}
