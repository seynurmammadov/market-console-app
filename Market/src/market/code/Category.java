package market.code;

public class Category {
    final String id;
    private String name;

    public Category( String name) {
        this.id = IdGenerator.generate();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
