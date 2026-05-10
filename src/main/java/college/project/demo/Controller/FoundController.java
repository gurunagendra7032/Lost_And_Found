package college.project.demo.Controller;

import college.project.demo.Entities.FoundItem;
import college.project.demo.Entities.Users;
import college.project.demo.Repository.FoundRepo;
import college.project.demo.Service.MatchingItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class FoundController {

    @Autowired
    private FoundRepo foundrepo;

    @Autowired
    private MatchingItem matchingItem;


    @PostMapping("/api/save")
    public FoundItem saveItem(@RequestBody FoundItem foundItem){
        FoundItem founditem= foundrepo.save(foundItem);
        matchingItem.FoundMatchItem(foundItem.getImageName());
        return founditem;

    }

    @GetMapping("/api/details")
    public List<FoundItem> getImages(){
        return foundrepo.findAll();
    }

    @DeleteMapping("/api/deletefounditem/{id}")
    public void deleteItem(@PathVariable Long id){
        foundrepo.deleteById(id);
    }

}
