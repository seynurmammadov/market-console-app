package market.code;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface IMarketable {

     void addCategory(String name) ;
     void editCategory(String name,int index);
     void deleteCategory(int index) ;

     HashMap<String,Product> getAllProducts();
     void addProduct(String name, Category category, int count, float price);
     Product findProductByCode(String code) ;
     void editProduct(String code, String name,Category category, int count, float price);
     void deleteProduct(String code) ;
     List<Product> getProdByCat(int index) ;
     List<Product> getProdByPriceRange(float min, float max) ;
     List<Product> getProdByRegex(String text) ;

     HashMap<String,Sale> getAllSales();
     void addSale(List<SaleItem> saleItems);
     Sale findSaleByCode(String code) ;
     void returnSaleItem(Sale sale,SaleItem saleItems,int count);
     void returnSale(Sale sale);
     List<Sale> getSalesByDateRange(LocalDateTime min, LocalDateTime max) ;
     List<Sale> getSalesByPriceRange(float min, float max) ;
     List<Sale> getSalesByDate(LocalDate date) ;

}
