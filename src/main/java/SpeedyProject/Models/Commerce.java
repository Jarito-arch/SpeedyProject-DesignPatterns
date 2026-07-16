package SpeedyProject.Models;
public class Commerce {
    private int id;
    private String name;
    private String category;
    private String imagePath;

    public Commerce() {
    }

    public Commerce(int id, String name, String category, String imagePath) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imagePath = imagePath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
