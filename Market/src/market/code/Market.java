package market.code;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Market implements IMarketable {
    private HashMap<String, Product> products;
    private HashMap<String, Sale> sales;
    private LinkedList<Category> categories;

    public Market() {
        this.products = new HashMap<>();
        this.sales =  new HashMap<>();
        this.categories =  new LinkedList<>();
    }

    //Products manipulation
    @Override
    public HashMap<String, Product> getAllProducts() {
        return products;
    }
    @Override
    public void addProduct(String name, Category category, int count, float price)  {
        Product product = new Product(name, category, count, price);
        products.put(product.id,product);
        System.out.println("Product successfully added!");
        System.out.println("Product code: " + product.id);
        Helper.sleep();
    }

    @Override
    public Product findProductByCode(String code) {
        return products.get(code);
    }

    @Override
    public void editProduct(String code ,String name, Category category, int count, float price) {
        Product product=  products.get(code);
        product.setCategory(category);
        product.setName(name);
        product.setCount(count);
        product.setPrice(price);
    }

    @Override
    public void deleteProduct(String code) {
        products.remove(code);
        System.out.println("Product deleted!");
        Helper.sleep();
    }

    @Override
    public List<Product> getProdByCat(int index) {
       List<Product> list = new ArrayList<>();
       Category category= categories.get(index);
       for (Product product : products.values()){
           if(product.getCategory()==category){
               list.add(product);
           }
       }
       return list;
    }

    @Override
    public List<Product> getProdByPriceRange(float min, float max) {
        List<Product> list = new ArrayList<>();
        for (Product product : products.values()){
            if(product.getPrice()>=min && product.getPrice()<=max){
                list.add(product);
            }
        }
        return list;
    }

    @Override
    public List<Product> getProdByRegex(String text) {
        List<Product> list = new ArrayList<>();
        for (Product product : products.values()){
            if(product.getName().contains(text)){
                list.add(product);
            }
        }
        return list;
    }

    //Category manipulation

    public LinkedList<Category> getCategories() {
        return categories;
    }
    @Override
    public void addCategory(String name)  {
        Category category= new Category(name);
        if(isExistInCategory(category)){
            System.out.println("Category with this name already exist, do you want to add it anyway?(y/n) ");
            Scanner scanner = new Scanner(System.in);
            try {
                String val = scanner.nextLine();
                if ("y".equals(val)) {
                    categories.add(category);
                    return;
                }
                System.out.println("We return you to the main page!");
                Helper.sleep();
                return;
            }catch (Exception e){
                System.out.println("Something go wrong!");
                System.out.println("We return you to the main page!");
                Helper.sleep();
                return;
            }
        }
        categories.add(category);
        System.out.println("Category added!");
        Helper.sleep();
    }
    @Override
    public void editCategory(String name, int index) {
        categories.get(index).setName(name);
        System.out.println("Category edited!");
        Helper.sleep();
    }
    @Override
    public void deleteCategory(int index)  {
        categories.remove(index);
        System.out.println("Category deleted!");
        Helper.sleep();
    }

    @Override
    public HashMap<String, Sale> getAllSales() {
        return sales;
    }

    @Override
    public void addSale(List<SaleItem> saleItems) {
        float price =0;
        for (SaleItem item: saleItems) {
            if(item.getCount()>item.getProduct().getCount()){
                System.out.println("We dont have enough products!");
                System.out.println("Product name: "+item.getProduct().getName());
                return;
            }
            price+=item.getCurrentPrice()*item.getCount();
        }

        Sale newSale = new Sale(price,saleItems);
        sales.put(newSale.id,newSale);
        for (SaleItem item: saleItems) {
            item.getProduct().setCount(item.getProduct().getCount()-item.getCount());
        }
        System.out.println("Sale successfully added!");
        System.out.println("Sale id: " + newSale.id);
        Helper.sleep();
    }
    @Override
    public void returnSaleItem(Sale sale,SaleItem saleItems,int count) {
        if(saleItems.isReturned()){
            System.out.println("You already returned this product!");
            return;
        }
        if(saleItems.getCount()>=count && count>0){
            saleItems.setReturned(true);
            Product product = nullableProduct(saleItems);
            product.setCount(product.getCount()+count);
            if(saleItems.getCount()!=count){
                SaleItem newSaleItem= new SaleItem(product,saleItems.getCount()-count);
                newSaleItem.setCurrentPrice(saleItems.getCurrentPrice());
                sale.getSaleItemsList().add(newSaleItem);
            }
        }else {
            System.out.println("Enter correct value");
            Helper.sleep();
        }
    }

    @Override
    public void returnSale(Sale sale) {
        if(sale.isReturned()){
            System.out.println("You already returned this sale!");
            return;
        }
        sale.setReturned(true);
        for (SaleItem item:sale.getSaleItemsList()) {
            item.setReturned(true);
            Product product = nullableProduct(item);
            product.setCount(product.getCount()+item.getCount());
        }
    }

    @Override
    public List<Sale> getSalesByDateRange(LocalDate min, LocalDate max) {
        List<Sale> list = new ArrayList<>();

        for (Sale sale : sales.values()){
            if(sale.getDateTime().isAfter(min.atStartOfDay()) && sale.getDateTime().isBefore(max.atStartOfDay())){
                list.add(sale);
            }
        }
        return list;
    }

    public Product nullableProduct(SaleItem item){
        Product product = products.get(item.getProduct().id);
        if(product==null){
            product = item.getProduct();
            product.setCount(0);
            products.put(product.id,product);
        }
       return product;
    }
    @Override
    public Sale findSaleByCode(String code) {
        return sales.get(code);
    }

    public boolean isExistInCategory(Category category){
        return categories.stream().anyMatch((c)-> c.getName().equals(category.getName()));
    }
}
