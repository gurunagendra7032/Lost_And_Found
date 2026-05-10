package college.project.demo.Repository;

import college.project.demo.Entities.FoundItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoundRepo extends JpaRepository<FoundItem,Long> {

    List<FoundItem> findByimageNameIgnoreCase(String Name);

}
