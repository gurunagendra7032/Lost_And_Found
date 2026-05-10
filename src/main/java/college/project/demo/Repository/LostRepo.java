package college.project.demo.Repository;

import college.project.demo.Entities.LostItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LostRepo extends JpaRepository<LostItem,Long> {
}
