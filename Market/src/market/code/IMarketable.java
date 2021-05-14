package market.code;
/* - Satislar +
 - Mehsullar+
 - Satis elave etmek - satis elave edilerken hansi mehsullardan hansi sayda satis oldugu gonderilir
 - Satisdan mehsulun geri qaytarilmasi
 - Umumi satisin geri qaytarilmasi
 - Verilen tarix araligina gore hemin tarix araligina olan satislarin qaytarilmasi
 - Verilen bir tarixe gore hemin tarix (il,ay,gun) ucun olan satislarin qaytarilmasi
 - Verilmis mebleg araligina gore edilmis satislarin qaytarilmasi
 - Verilmis nomreye esasen satisin qaytarilmasi
 - Yeni mehsul elave etmek
 - Mehsulun adini,sayini ve meblegini,categoriyasini deyismek (code-a gore tapilacaq)
 - Verilmis kateqoriyaya esasen hemin kateqoriyada olan mehsullarin qaytarilmasi
 - Verilmis qiymet araligina esasen hemin araliqda olan mehsullarin qaytarilmasi
 - Verilmis ada esasen mehsullarin search edilib qaytarilmasi*/

import java.util.ArrayList;
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
     void addSale(ArrayList<SaleItem> saleItems);
     void returnSaleItem(SaleItem saleItems);

}
