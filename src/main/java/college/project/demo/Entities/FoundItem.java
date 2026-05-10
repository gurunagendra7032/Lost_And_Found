package college.project.demo.Entities;

import jakarta.persistence.*;

@Entity
public class FoundItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String imageName;
    private String imageUrl;
    private String imageDescription;
    private String location;

    @ManyToOne
    private Users user;


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDescription() {
        return imageDescription;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }



    public FoundItem(){}

    public FoundItem(String imageDescription, String imageUrl, String imageName , String location) {
        this.imageDescription = imageDescription;
        this.imageUrl = imageUrl;
        this.imageName = imageName;
        this.location=location;

    }
}
