package college.project.demo.Service;

import college.project.demo.Entities.Notification;
import college.project.demo.Repository.NotificationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

        @Autowired
        private NotificationRepo notificationRepo;

      @Autowired
      private SimpMessagingTemplate messagingTemplate;

        public void createNotification(String email, String message) {
            Notification notification = new Notification();
            notification.setEmail(email);
            notification.setMessage(message);
            notification.setReadStatus(false);

            notificationRepo.save(notification);

            messagingTemplate.convertAndSend(
                    "/topic/notifications",
                    notification
            );
        }

}
