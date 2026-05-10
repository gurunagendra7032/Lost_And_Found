package college.project.demo.Repository;

import college.project.demo.Entities.LostItem;
import college.project.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LostRepo extends JpaRepository<LostItem,Long> {

    List<LostItem> findByUser(Users user);
}
