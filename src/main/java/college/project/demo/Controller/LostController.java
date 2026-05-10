package college.project.demo.Controller;

import college.project.demo.Entities.LostItem;
import college.project.demo.Entities.Users;
import college.project.demo.Repository.LostRepo;
import college.project.demo.Repository.Repo;
import college.project.demo.Service.MatchingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LostController {

    @Autowired
    private LostRepo lostrepo;

    @Autowired
    private MatchingItem matchingItem;

    @Autowired
    private Repo repo;

    @PostMapping("/api/lostItem")
    public LostItem saveItem(@RequestBody LostItem lostItem, Principal prince) {

        String Email=prince.getName();
        Users user= repo.findByEmail(Email);
        lostItem.setUser(user);

        LostItem lost= lostrepo.save(lostItem);
        matchingItem.LostMatchItem(lostItem.getImageName());
        return lost;
    }

    @GetMapping("/api/lostitems/{id}")
    public List<LostItem> getItems(@PathVariable Long id){
        return lostrepo.findById(id);
    }

    @DeleteMapping("/api/deletelostitem/{id}")
    public void deleteItem(@PathVariable Long id){
        lostrepo.deleteById(id);
    }

}
