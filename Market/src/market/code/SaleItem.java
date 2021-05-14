package market.code;

public class SaleItem {

    final String id;
    private Product product;
    private int count;
    private float currentPrice;
    private boolean isReturned=false;

    public SaleItem(Product product, int count) {
        this.id = IdGenerator.generate();
        this.product = product;
        this.count = count;
        this.currentPrice = product.getPrice();
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
