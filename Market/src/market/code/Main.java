package market.code;

import market.code.tuple.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {
    static Market market= new Market();
    public static void main(String[] args)  {

        //Adding categories
/*        {
            List<Category> categories = new ArrayList<>();
            Map<String,Product> products = new HashMap<>();
            for(int i=0; i<10;i++){
                categories.add(new Category("Category"+(i+1)));
            }
            for(int i=0; i<10;i++){
                Product product = new Product("Product"+(i+1),categories.get(i),1000,100);
                products.put(product.id,product);
            }
        }*/

        menu();
    }
    //Main menu
    public static void menu()  {
        Helper.titlePrint("Market");
        System.out.println("- 1 Operation on categories");
        System.out.println("- 2 Operation on products");
        System.out.println("- 3 Operation on sales");
        System.out.println("- 0 Get out of the system");
        Helper.printLine();
        Scanner scanner = Helper.generateScanner();
        try {
            int val = scanner.nextInt();
            switch (val){
                case 1:
                    CategoryOperations.operationsOnCategory();
                    break;
                case 2:
                    ProductOperations.operationsOnProduct();
                    break;
                case 3:
                    SalesOperations.operationsOnSales();
                    break;
                case 0:
                    System.exit(-1);
                default:
                    incorrectMenu();
            }
        }catch (Exception e){
            incorrectMenu();
        }

    }
    //Category menu and CRUD
    static class CategoryOperations{
        //Menu
        public static void operationsOnCategory() {
            Helper.titlePrint("Operations on categories");
            System.out.println("-1 Add new category");
            if(!isCategoryEmpty()){
                System.out.println("-2 Edit category");
                System.out.println("-3 Delete category");
                System.out.println("-4 Show all categories");
            }
            System.out.println("-0 Back");
            Helper.printLine();
            Scanner scanner = Helper.generateScanner();
            try {
                int val = scanner.nextInt();
                switch (val){
                    case 0:
                        menu();
                        break;
                    case 1:
                        addNewCategory();
                        break;
                    case 2:
                        editCategory();
                        break;
                    case 3:
                        deleteCategory();
                        break;
                    case 4:
                        showCategories();
                        Helper.sleep();
                        break;
                    default:
                        incorrectCategory();
                        break;
                }
            }catch (Exception e){
                incorrectCategory();
            }
            menu();
        }
        public static void addNewCategory() {
            Helper.titlePrint("Add new category");
            System.out.println("Enter category name:");
            Scanner scanner = Helper.generateScanner();
            String val = scanner.nextLine().trim();
            checkString(val);
            market.addCategory(val);
        }
        public static void editCategory()  {
            if (isCategoryEmpty()){
                System.out.println("Category is empty!");
                return;
            }
            Helper.titlePrint("Select category");
            showCategories();
            Scanner scanner = Helper.generateScanner();
            int index= scanner.nextInt()-1;
            if(!Helper.isPositive(index)) incorrectMenu();
            System.out.println("Enter category name:");
            String val = scanner.next().trim();
            checkString(val);
            market.editCategory(val,index);
        }
        public static void deleteCategory()  {
            if (isCategoryEmpty()){
                System.out.println("Category is empty!");
                return;
            }
            Helper.titlePrint("Select category");
            showCategories();
            Scanner scanner = Helper.generateScanner();
            int index= scanner.nextInt()-1;
            if(!Helper.isPositive(index)) incorrectMenu();
            market.deleteCategory(index);
        }
        public static void showCategories(){
            if (isCategoryEmpty()){
                System.out.println("Category is empty!");
                return;
            }
            String leftAlignFormat = "| %-16s |%n";
            System.out.format("+------------------+%n");
            System.out.format("|       Name       |%n");
            System.out.format("+------------------+%n");
            int i = 0;
            for (Category category: market.getCategories()) {
                System.out.format(leftAlignFormat, (i + 1) + ". " +category.getName());
                i++;
            }
            System.out.format("+------------------+%n");
        }
    }
    //Product menu and CRUD
    static class ProductOperations{
        //Menu
        public static void operationsOnProduct()  {
            Helper.titlePrint("Operations on product");
            System.out.println("- 1 Add new product");
            if(!isProductEmpty()) {
                System.out.println("- 2 Edit product");
                System.out.println("- 3 Delete product");
                System.out.println("- 4 Show all products");
                System.out.println("- 5 Show products by category");
                System.out.println("- 6 Show products by price range");
                System.out.println("- 7 Search by name");
            }
            System.out.println("- 0 Back");
            Helper.printLine();
            Scanner scanner = Helper.generateScanner();
            try {
                int val = scanner.nextInt();
                switch (val){
                    case 0:
                        menu();
                        break;
                    case 1:
                        addNewProduct();
                        break;
                    case 2:
                        editProduct();
                        break;
                    case 3:
                        deleteProduct();
                        break;
                    case 4:
                        showProducts(market.getAllProducts().values());
                        Helper.sleep();
                        break;
                    case 5:
                        showProdByCat();
                        break;
                    case 6:
                        showProdByPriceLenght();
                        break;
                    case 7:
                        searchByName();
                        break;
                    default:
                        incorrectProduct();
                }
            }catch (Exception e){
                incorrectProduct();
            }
            menu();
        }
        public static void addNewProduct() {
            try {
                if(!isCategoryEmpty()){
                    Helper.titlePrint("Add new product");
                    FourTuple<String,Category,Integer,Float> properties= getProductProperties();
                    market.addProduct(properties.first,properties.second,properties.three,properties.four);
                }
                else{
                    noticeAddingCategory();
                }
            }catch (Exception e){
                incorrectProduct();
            }
        }
        public static void editProduct()   {
            try {
                if(!isProductEmpty()){
                    if(!isCategoryEmpty()){
                        Helper.titlePrint("Edit product");
                        Scanner scanner = Helper.generateScanner();

                        System.out.println("Enter product code:");
                        String code = scanner.next().trim();
                        checkString(code);
                        if (market.findProductByCode(code)!=null){
                            Collection<Product> product = new ArrayList<>();
                            product.add(market.findProductByCode(code));
                            showProducts(product);
                            FourTuple<String,Category,Integer,Float> properties= getProductProperties();
                            market.editProduct(code,properties.first,properties.second,properties.three,properties.four);
                        }
                        else {
                            System.out.println("We didn't find this product!");
                            System.out.println("Please enter code correctly!");
                        }
                    }
                    else{
                        noticeAddingCategory();
                    }
                }
                else {
                    System.out.println("Product list is empty!");
                }
            }catch (Exception e){
                incorrectProduct();
            }
        }
        public static void deleteProduct(){
            try {
                if(!isProductEmpty()){
                    Helper.titlePrint("Delete product");
                    Scanner scanner = Helper.generateScanner();
                    System.out.println("Enter product code:");
                    String code = scanner.nextLine();
                    if (market.findProductByCode(code)!=null){
                        market.deleteProduct(code);
                        System.out.println(market.getAllProducts());
                    }
                    else {
                        System.out.println("We didn't find this product!");
                        System.out.println("Please enter code correctly!");
                    }
                }
                else {
                    System.out.println("Product list is empty!");
                }
            }catch (Exception e){
                incorrectProduct();
            }
        }
        public static void showProducts(Collection<Product> collection){
            if (isEmptyCollection(collection)){
                System.out.println("Product list is empty!");
                return;
            }
            String leftAlignFormat = "| %-43s |%-17s |%-17s |%-17s |%-17s |%n";
            System.out.format("+---------------------------------------------+------------------+------------------+------------------+------------------+%n");
            System.out.format("|                     Code                    |       Name       |     Category     |      Count       |      Price       |%n");
            System.out.format("+---------------------------------------------+------------------+------------------+------------------+------------------+%n");
            int i = 0;
            for (Product product: collection) {
                System.out.format(leftAlignFormat, (i + 1) + ". "
                                +product.id,
                        product.getName(),
                        product.getCategory().getName(),
                        product.getCount(),
                        product.getPrice()
                );
                i++;
            }
            System.out.format("+---------------------------------------------+------------------+------------------+------------------+------------------+%n");

        }
        public static void showProdByCat(){
            if (isProductEmpty() && isCategoryEmpty()){
                System.out.println("Product or Category lists is empty!");
                return;
            }
            CategoryOperations.showCategories();
            Scanner scanner = Helper.generateScanner();
            int index= scanner.nextInt()-1;
            if(!Helper.isPositive(index)) incorrectMenu();
            List<Product> list= market.getProdByCat(index);
            showProducts(list);
            Helper.sleep();
        }
        public static void showProdByPriceLenght(){
            if (isProductEmpty()){
                System.out.println("Product list is empty!");
                return;
            }
            System.out.println("Enter minimum price:");
            Scanner scanner = Helper.generateScanner();
            float min= scanner.nextFloat();
            System.out.println("Enter maximum price:");
            if(!Helper.isPositive(min)) incorrectProduct();
            float max= scanner.nextFloat();
            if(!Helper.isPositive(max)) incorrectProduct();
            List<Product> list= market.getProdByPriceRange(min,max);
            showProducts(list);
            Helper.sleep();
        }
        public static void searchByName(){
            if (isProductEmpty()){
                System.out.println("Product list is empty!");
                return;
            }
            System.out.println("Enter searching product name:");
            Scanner scanner = Helper.generateScanner();
            String regex= scanner.next();
            List<Product> list= market.getProdByRegex(regex);
            showProducts(list);
            Helper.sleep();
        }
        public static FourTuple<String,Category,Integer,Float> getProductProperties()  {
            Scanner scanner = Helper.generateScanner();
            System.out.println("Enter product name:");
            String name = scanner.next().trim();
            checkString(name);
            Helper.titlePrint("Select category");
            CategoryOperations.showCategories();
            Category category = market.getCategories().get(scanner.nextInt()-1);
            System.out.println("Enter count of product:");
            int count = scanner.nextInt();
            if(!Helper.isPositive(count)) incorrectProduct();
            System.out.println("Enter price of product:");
            float price = scanner.nextFloat();
            if(!Helper.isPositive(price)) incorrectProduct();
            return new FourTuple<>(name,category,count,price);
        }

        public static void noticeAddingCategory() {
            System.out.println("Before adding a product you need to add at least one category!");
            System.out.println("Do you want add new category? (y/n)");
            Scanner scanner = Helper.generateScanner();
            String val = scanner.nextLine();
            switch (val){
                case "y":
                    CategoryOperations.addNewCategory();
                    break;
                case "n":
                    System.out.println("We return you to the main page!");
                    Helper.sleep();
                    menu();
                default:
                    incorrectProduct();
            }
        }

    }
    //Sales menu and CRUD
    static class SalesOperations{
        //Menu
        public static void operationsOnSales()  {
            Helper.titlePrint("Operations on sales");
            System.out.println("- 1 Add new sale");
            if(!isSalesEmpty()) {
                System.out.println("- 2 Return product in sale");
                System.out.println("- 3 Return sale");
                System.out.println("- 4 Show all sales");
                System.out.println("- 5 Show sales by date range");
                System.out.println("- 6 Show sales by price range");
                System.out.println("- 7 Show sale by date");
                System.out.println("- 8 Show sale by sale code");
            }
            System.out.println("- 0 Back");
            Helper.printLine();
            Scanner scanner = Helper.generateScanner();
            try {
                int val = scanner.nextInt();
                switch (val){
                    case 0:
                        menu();
                        break;
                    case 1:
                        addNewSale();
                        break;
                    case 2:
                        returnSaleItem();
                        break;
                    case 3:
                        returnSale();
                        break;
                    case 4:
                        showSales(market.getAllSales().values());
                        break;
                    case 5:
                        showSalesByDateRange();
                        break;
                    default:
                        incorrectSales();
                        break;
                }
            }catch (Exception e){
                incorrectSales();
            }
            menu();
        }
        public static void addNewSale() {
            try {
                if(!isProductEmpty()){
                    Helper.titlePrint("Add new sale");
                    List<SaleItem> properties= getSalesProperties();
                    market.addSale(properties);
                }
                else{
                    System.out.println("You dont have any product to sale! At first add product in 'Operation on products' section!");
                    Helper.sleep();
                    menu();
                }
            }catch (Exception e){
                incorrectSales();
            }
        }
        public static void returnSaleItem(){
            try {
                if(!isSalesEmpty()){
                    Helper.titlePrint("Return product from sale");
                    Scanner scanner = Helper.generateScanner();
                    System.out.println("Enter sale code:");
                    String code = scanner.next().trim();
                    checkString(code);
                    Sale removedItem= market.findSaleByCode(code);
                    if (removedItem!=null){
                        showSalesInner(removedItem);
                        int index= scanner.nextInt()-1;
                        if(!Helper.isPositive(index)) incorrectMenu();
                        System.out.println("How many products is returning?");
                        int count= scanner.nextInt();
                        if(!Helper.isPositive(count)) incorrectMenu();
                        market.returnSaleItem(removedItem,removedItem.getSaleItemsList().get(index),count);
                    }
                    else {
                        System.out.println("We didn't find this sale!");
                        System.out.println("Please enter code correctly!");
                    }
                }
                else {
                    System.out.println("Product list is empty!");
                }
            }catch (Exception e){
                incorrectProduct();
            }
        }
        public static void returnSale(){
            try {
                if(!isSalesEmpty()){
                    Helper.titlePrint("Return sale");
                    Scanner scanner = Helper.generateScanner();
                    System.out.println("Enter sale code:");
                    String code = scanner.next().trim();
                    checkString(code);
                    Sale removedItem= market.findSaleByCode(code);
                    if (removedItem!=null){
                        showSalesInner(removedItem);
                        System.out.println("Are you sure to return?(y/n)");
                        String s=scanner.next();
                        if(s.equals("y"))
                        market.returnSale(removedItem);
                    }
                    else {
                        System.out.println("We didn't find this sale!");
                        System.out.println("Please enter code correctly!");
                    }
                }
                else {
                    System.out.println("Product list is empty!");
                }
            }catch (Exception e){
                incorrectProduct();
            }
        }
        public static void showSales(Collection<Sale> collection){
            if (isEmptyCollection(collection)){
                System.out.println("Sales list is empty!");
                return;
            }
            String leftAlignFormat = "| %-43s |%-17s |%-18s |%-24s |%-17s |%n";
            System.out.format("+---------------------------------------------+------------------+-------------------+-------------------------+------------------+%n");
            System.out.format("|                     Code                    |      Price       | Count of Products |           Date          |   Return Status  |%n");
            System.out.format("+---------------------------------------------+------------------+-------------------+-------------------------+------------------+%n");
            int i = 0;
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            for (Sale sale: collection) {
                System.out.format(leftAlignFormat, (i + 1) + ". "
                                +sale.id,
                        sale.getPrice(),
                        sale.getSaleItemsList().size(),
                        sale.getDateTime().format(format),
                        sale.isReturned()
                );
                i++;
            }
            System.out.format("+---------------------------------------------+------------------+-------------------+-------------------------+------------------+%n");

        }
        public static void showSalesItems(Collection<SaleItem> collection){
            Helper.titlePrint("Select returned product");
            String leftAlignFormat = "|%-17s |%-18s |%-18s |%-18s |%-17s |%n";
            System.out.format("+------------------+-------------------+-------------------+-------------------+------------------+%n");
            System.out.format("|       Name       | Count of Products |       Price       |    Total Price    |   Return Status  |%n");
            System.out.format("+------------------+-------------------+-------------------+-------------------+------------------+%n");
            int i = 0;
            for (SaleItem sale: collection) {
                System.out.format(leftAlignFormat, (i + 1) + ". "
                                +sale.getProduct().getName(),
                        sale.getCount(),
                        sale.getCurrentPrice(),
                        sale.getCurrentPrice()* sale.getCount(),
                        sale.isReturned()
                );
                i++;
            }
            System.out.format("+------------------+-------------------+-------------------+-------------------+------------------+%n");

        }
        public static void showSalesByDateRange(){
            if (isSalesEmpty()){
                System.out.println("Sales list is empty!");
                return;
            }
            System.out.println("Enter start date (DAY-MONTH-YEAR//01-01-2021):");
            Scanner scanner = Helper.generateScanner();
            String min= scanner.next().trim();
            if(!Helper.checkDate(min)) incorrectMenu();
            System.out.println("Enter end date:");
            String max= scanner.next().trim();
            if(!Helper.checkDate(max)) incorrectMenu();
            List<Sale> list= market.getSalesByDateRange(LocalDate.parse(min),LocalDate.parse(max));
            showSales(list);
            Helper.sleep();
        }
        public static List<SaleItem> getSalesProperties()  {
            List<SaleItem> saleItemList = new LinkedList<>();
            Scanner scanner = Helper.generateScanner();
            String yes= "y";
            while (yes.equals("y")){
                System.out.println("Enter product code:");
                String code = scanner.next();
                Product product = market.findProductByCode(code);
                System.out.println("Enter count of product:");
                int count = scanner.nextInt();
                if(!Helper.isPositive(count)) incorrectSales();
                saleItemList.add(new SaleItem(product,count));
                System.out.println("Add another product?(y/n)");
                yes= scanner.next().trim();
            }
            return saleItemList;
        }
        public static void showSalesInner(Sale item){
            Collection<Sale> sale = new ArrayList<>();
            sale.add(item);
            showSales(sale);
            showSalesItems(item.getSaleItemsList());
        }
    }


    public static boolean isCategoryEmpty(){
        return market.getCategories().size()==0;
    }
    public static boolean isProductEmpty(){
        return market.getAllProducts().size()==0;
    }
    public static boolean isEmptyCollection(Collection collection){
        return collection.size()==0;
    }
    public static boolean isSalesEmpty(){
        return market.getAllSales().size()==0;
    }

    public static void incorrectMenu()  {
        Helper.incorrectNumber();
        menu();
    }
    public static void incorrectCategory()  {
        Helper.incorrectNumber();
        CategoryOperations.operationsOnCategory();
    }
    public static void incorrectProduct()  {
        Helper.incorrectNumber();
        ProductOperations.operationsOnProduct();
    }
    public static void incorrectSales()  {
        Helper.incorrectNumber();
        SalesOperations.operationsOnSales();
    }
    public static void checkString(String val){
        if(val.length()==0){
            incorrectMenu();
        }
    }
}
