//package ecommerce_package;
//public class Category {
//    private int id;
//    private String name;
//    private String description;
//
//    
//    public Category(int id, String name, String description) {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//    }
//
//    
//    public Category(String name, String description) {
//        this.name = name;
//        this.description = description;
//    }
//
//    
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//   
//    @Override
//    public String toString() {
//        return "Category{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                '}';
//    }


package ecommerce_package;

public class Category {
    private int categoryId;
    private String name;

    // --- Constructors ---
    public Category() {}

    public Category(int categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }

    // --- Getters and Setters ---
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // --- Utility ---
    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                '}';
    }
}

