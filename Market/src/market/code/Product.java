package market.code;

public class Product implements IPrice {
    final String id;
    private String name;
    private float price;
    private Category category;
    private int count;

    public Product( String name, Category category, int count, float price) {
        this.id = Generator.generateId();
        this.name = name;
        this.category = category;
        this.count = count;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
