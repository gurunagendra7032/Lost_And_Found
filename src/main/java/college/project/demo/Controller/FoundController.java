package college.project.demo.Controller;

import college.project.demo.Entities.FoundItem;
import college.project.demo.Entities.Item_Status;
import college.project.demo.Entities.Users;
import college.project.demo.Repository.FoundRepo;
import college.project.demo.Repository.Repo;
import college.project.demo.Service.CustomUserDetailService;
import college.project.demo.Service.MatchingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class FoundController {

    @Autowired
    private FoundRepo foundrepo;

    @Autowired
    private MatchingItem matchingItem;

    @Autowired
    private Repo repo;

    @Autowired
    private CustomUserDetailService service;


    @PostMapping("/api/save")
    public FoundItem saveItem(@RequestBody FoundItem foundItem , Principal prince){
        String name= prince.getName();
        Users user=repo.findByEmail(name);
        foundItem.setUser(user);
        foundItem.setStatus(Item_Status.FOUND);
        String reference = "FI-" + UUID.randomUUID()
                .toString()
                .substring(0, 5)
                .toUpperCase();

        foundItem.setReference(reference);
        FoundItem founditem= foundrepo.save(foundItem);
        matchingItem.FoundMatchItem(foundItem.getImageName());
        return founditem;

    }

    @GetMapping("/api/details")
    public List<FoundItem> getImages(Principal prince){
        String name= prince.getName();
        Users user=repo.findByEmail(name);

        return foundrepo.findByUser(user);
    }

    @DeleteMapping("/api/deletefounditem/{id}")
    public void deleteItem(@PathVariable Long id){
        foundrepo.deleteById(id);
    }


    @GetMapping("/found/search")
    public List<FoundItem> searchFound(
            @RequestParam String keyword, Principal prince){

        Users user= repo.findByEmail(prince.getName());

        return service.searchFoundItems(keyword,user);
    }

    @GetMapping("/admin/getfounditems")
    public List<FoundItem> AllgetItem(){
        return foundrepo.findAll();
    }

}
