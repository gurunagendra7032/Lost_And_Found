package college.project.demo.Controller;

import college.project.demo.Entities.*;
import college.project.demo.Repository.AdminRepo;
import college.project.demo.Repository.FoundRepo;
import college.project.demo.Repository.LostRepo;
import college.project.demo.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "https://lost-and-found-frontend-drab.vercel.app")
public class AdminController {

    @Autowired
    private FoundRepo foundRepo;

    @Autowired
    private LostRepo lostRepo;
    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AuthenticationManager  authenticationManager;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/adm")
    public Admin saveAdmin(@RequestBody Admin admin){
        admin.setRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        String reference = "COL-" + UUID.randomUUID()
                .toString()
                .substring(0, 10)
                .toUpperCase();
        admin.setRegisterId(reference);
       return  adminRepo.save(admin);

    }

    @PostMapping("/adm/login")
    public String Adlogin(@RequestBody Admin admins){
        Admin admin=adminRepo.findByEmail(admins.getEmail());
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                admin.getEmail(),admin.getPassword()
        ));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(
                    admin.getEmail(),
                    "ADMIN"
            );

        }else{
            throw new UsernameNotFoundException("user is Invalid");
        }

    }


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

      @PutMapping("/admin/{reference}/status")
      public  String getFoundItem(@PathVariable String reference){

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
