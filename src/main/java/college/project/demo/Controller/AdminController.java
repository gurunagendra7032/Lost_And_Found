package college.project.demo.Controller;

import college.project.demo.Entities.FoundItem;
import college.project.demo.Entities.Item_Status;
import college.project.demo.Entities.LostItem;
import college.project.demo.Repository.FoundRepo;
import college.project.demo.Repository.LostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class AdminController {

    @Autowired
    private FoundRepo foundRepo;

    @Autowired
    private LostRepo lostRepo;


    @GetMapping("/admin/Allfounditems")
      public long getCountItems(){
          return foundRepo.count();
      }
      @GetMapping("/admin/founditems")
      public List<FoundItem> getAllItems(){
        return foundRepo.findAll();
      }

      @GetMapping("/admin/AlllostItems")
      public long getLostItems(){
        return lostRepo.count();
      }

    @GetMapping("/admin/lostitems")
    public List<LostItem> getAllLostItems(){
        return lostRepo.findAll();
    }

      @PutMapping("admin/change/status")
      public  String getFoundItem(@RequestParam String reference){

        FoundItem foundItem=foundRepo.findByReference(reference);
        foundItem.setStatus(Item_Status.WITH_ADMIN);
        foundRepo.save(foundItem);
        return " The Item added to Currently Stored Items";

      }

      @GetMapping("admin/currentStorageItems")
      public long countStorageItems(){
        return foundRepo.countByStatus(Item_Status.WITH_ADMIN);
      }

      @GetMapping("admin/currentStorage/AllItems")
      public List<FoundItem> getCurrentItems(){
        return foundRepo.findByStatus(Item_Status.WITH_ADMIN);
      }

      @PutMapping("/admin/handover")
      public String returnItem(@RequestParam String reference){
          FoundItem found=foundRepo.findByReference(reference);
          found.setStatus(Item_Status.RETURNED);
          foundRepo.save(found);

          return " The Item is Handover Original Owner";
      }

      @GetMapping("/admin/returnAllItems")
      public List<FoundItem> getAllReturnItems(){
        return foundRepo.findByStatus(Item_Status.RETURNED);
      }

      @GetMapping("/admin/countReturnItems")
      public long getAllreturnItemCount(){
        return foundRepo.countByStatus(Item_Status.RETURNED);
      }


}
