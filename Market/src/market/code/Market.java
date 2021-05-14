package market.code;

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

    public LinkedList<Category> getCategories() {
        return categories;
    }

    @Override
    public HashMap<String, Product> getAllProducts() {
        return products;
    }

    @Override
    public HashMap<String, Sale> getAllSales() {
        return sales;
    }


    @Override
    public void addProduct(String name, Category category, int count, float price)  {
        Product product = new Product(name, category, count, price);
        products.put(product.id,product);
        System.out.println("Product successfully added!");
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
    public void addSale(ArrayList<SaleItem> saleItems) {
        float price =0;
        for (SaleItem item: saleItems) {
            price+=item.getCurrentPrice();
        }
        Sale newSale = new Sale(price,saleItems);
        sales.put(newSale.id,newSale);
    }

    @Override
    public void returnSaleItem(SaleItem saleItems) {

    }


    public boolean isExistInCategory(Category category){
        return categories.stream().anyMatch((c)-> c.getName().equals(category.getName()));
    }
}
