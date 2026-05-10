package college.project.demo.Repository;

import college.project.demo.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo  extends JpaRepository<Notification , Long> {
    List<Notification> findByEmail(String email);
}
