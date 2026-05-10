package college.project.demo.Controller;

import college.project.demo.Entities.Notification;
import college.project.demo.Repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class NotificationController {



        @Autowired
        private NotificationRepo notificationRepo;

        @GetMapping("/notifications/{email}")
        public List<Notification> getNotifications(@PathVariable String email){
            return notificationRepo.findByEmail(email);
        }

}
