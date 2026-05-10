package college.project.demo.Repository;

import college.project.demo.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NotificationRepo  extends JpaRepository<Notification , Long> {
    List<Notification> findByEmail(String email);
}
