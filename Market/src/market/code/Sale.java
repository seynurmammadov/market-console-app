package market.code;

import java.time.LocalDateTime;
import java.util.List;

public class Sale {
    private static long totalSalesCount =0L;
    final String id;
    private float price;
    private List<SaleItem> saleItemsList;
    private LocalDateTime dateTime;
    private boolean isReturned=false;
    public Sale( float price, List<SaleItem> saleItemsList) {
        this.id = IdGenerator.generate();
        this.dateTime = LocalDateTime.now();

        this.price = price;
        this.saleItemsList = saleItemsList;
        totalSalesCount++;
    }

    public static long getTotalSalesCount() {
        return totalSalesCount;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<SaleItem> getSaleItemsList() {
        return saleItemsList;
    }

    public void setSaleItemsList(List<SaleItem> saleItemsList) {
        this.saleItemsList = saleItemsList;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
