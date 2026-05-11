package college.project.demo.Service;

import college.project.demo.Entities.FoundItem;
import college.project.demo.Entities.LostItem;
import college.project.demo.Repository.FoundRepo;
import college.project.demo.Repository.LostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class MatchingItem {



        @Autowired
        private FoundRepo foundRepo;

        @Autowired
        private LostRepo lostrepo;

        @Autowired
        private NotificationService notificationService;

        public void LostMatchItem(String imageName){
            List<FoundItem> match=foundRepo.findByimageNameIgnoreCase(imageName);
            for(FoundItem foundItem : match) {
                if(imageName.equalsIgnoreCase(foundItem.getImageName())){

                    if(foundItem.getUser()!=null) {
                        notificationService.createNotification(
                                foundItem.getUser().getEmail(),
                                "Possible matched item found In a Found Item Table"
                        );
                    }
                        break;
                }
            }
        }


        public void FoundMatchItem(String imageName) {

            for (LostItem lostItem : lostrepo.findAll()) {
                if (imageName.equalsIgnoreCase(lostItem.getImageName())) {
                    if(lostItem.getUser()!=null){
                        notificationService.createNotification(
                                lostItem.getUser().getEmail(),
                                "Possible matched item found in last Items Table "
                        ); }
                    break;
                    }
                }
            }



}
