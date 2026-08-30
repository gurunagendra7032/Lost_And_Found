package college.project.demo.Repository;

import college.project.demo.Entities.FoundItem;
import college.project.demo.Entities.Item_Status;
import college.project.demo.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundRepo extends JpaRepository<FoundItem,Long> {

    List<FoundItem> findByimageNameIgnoreCase(String Name);

    List<FoundItem> findByUser(Users user);

    List<FoundItem> findByImageNameContainingIgnoreCaseAndUser(String keyword,Users user);

    FoundItem findByReference ( String reference);

    long countByStatus(Item_Status status);

    List<FoundItem> findByStatus(Item_Status status);

}
