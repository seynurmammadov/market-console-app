package market.code;

import market.code.tuple.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


public class Main {
    static Market market= new Market();
    public static void main(String[] args)  {
        //Adding data
        {
            List<Product> products = new ArrayList<>();
            for(int i=0; i<10;i++){
                market.addCategory("Category"+(i+1));
            }
            for(int i=0; i<30;i++){
                market.addProduct("Product"+(i+1),market.getCategories().get(Generator.generateNumber(0,10)), Generator.generateNumber(5,1000),Generator.generateNumber(1,300));
            }
            for (Product pro: market.getAllProducts().values() ) {
                products.add(pro);
            }
            for(int i=0; i<Generator.generateNumber(20,40);i++){
                List<SaleItem> saleItem = new LinkedList<>();
                for (int j=0; j<Generator.generateNumber(2,12);j++){
                    SaleItem saleItem1 = new SaleItem(products.get(Generator.generateNumber(1,30)),Generator.generateNumber(1,40));
                    saleItem.add(saleItem1);
                }
                market.addSale(saleItem);
            }
            for (Sale sale :market.getAllSales().values()){
                long minDay = LocalDate.of(2017, 1, 1).toEpochDay();
                long maxDay = LocalDate.of(2021, 12, 31).toEpochDay();
                long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
                LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
                sale.setDateTime(randomDate.atStartOfDay());
            }
        }
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
            if(!isEmptyCollection(market.getCategories())){
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
                }
                if(!isEmptyCollection(market.getCategories())){
                    switch (val){
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
                    }
                }
            }catch (Exception e){
                incorrectMenu();
            }
            menu();
        }
        public static void addNewCategory() {
            Helper.titlePrint("Add new category");
            System.out.println("Enter category name:");
            String val = checkString();
            market.addCategory(val);
            Helper.sleep();
        }
        public static void editCategory()  {
            Helper.titlePrint("Select category");
            showCategories();
            int index = checkIndex();
            System.out.println("Enter category name:");
            String val = checkString();
            market.editCategory(val,index);
        }
        public static void deleteCategory()  {
            Helper.titlePrint("Select category");
            showCategories();
            int index = checkIndex();
            market.deleteCategory(index);
        }
        public static void showCategories(){
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
            if(!isEmptyCollection(market.getAllProducts())) {
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
                }
                if(!isEmptyCollection(market.getAllProducts())){
                    switch (val){
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
                            showProdByPriceRange();
                            break;
                        case 7:
                            searchByName();
                            break;
                    }
                }
            }catch (Exception e){
                incorrectMenu();
            }
            menu();
        }
        public static void addNewProduct() {
            try {
                if(!isEmptyCollection(market.getCategories())){
                    Helper.titlePrint("Add new product");
                    FourTuple<String,Category,Integer,Float> properties= getProductProperties();
                    market.addProduct(properties.first,properties.second,properties.three,properties.four);
                    Helper.sleep();
                }
                else{
                    noticeAddingCategory();
                }
            }catch (Exception e){
                incorrectMenu();
            }
        }
        public static void editProduct()   {
            try {
                if(!isEmptyCollection(market.getCategories())){
                    Helper.titlePrint("Edit product");
                    System.out.println("Enter product code:");
                    String code = checkString();
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
            }catch (Exception e){
                incorrectMenu();
            }
        }
        public static void deleteProduct(){
            try {
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
            }catch (Exception e){
                incorrectMenu();
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
            if (isEmptyCollection(market.getCategories())){
                System.out.println("Product or Category lists is empty!");
                return;
            }
            CategoryOperations.showCategories();
            int index = checkIndex();
            List<Product> list= market.getProdByCat(index);
            showProducts(list);
            Helper.sleep();
        }
        public static void showProdByPriceRange(){
            TwoTuple<Float,Float> minMax= getMinMaxPrices();
            List<Product> list= market.getProdByPriceRange(minMax.first,minMax.second);
            showProducts(list);
            Helper.sleep();
        }
        public static void searchByName(){
            System.out.println("Enter searching product name:");
            String regex= checkString();
            List<Product> list= market.getProdByRegex(regex);
            showProducts(list);
            Helper.sleep();
        }
        public static FourTuple<String,Category,Integer,Float> getProductProperties()  {
            Scanner scanner = Helper.generateScanner();
            System.out.println("Enter product name:");
            String name = checkString();
            Helper.titlePrint("Select category");
            CategoryOperations.showCategories();
            Category category = market.getCategories().get(scanner.nextInt()-1);
            System.out.println("Enter count of product:");
            int count = checkNumber();
            System.out.println("Enter price of product:");
            float price = checkFloat();
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
                    incorrectMenu();
            }
        }
    }
    //Sales menu and CRUD
    static class SalesOperations{
        //Menu
        public static void operationsOnSales()  {
            Helper.titlePrint("Operations on sales");
            System.out.println("- 1 Add new sale");
            if(!isEmptyCollection(market.getAllSales())) {
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
                }
                if(!isEmptyCollection(market.getAllSales())){
                    switch (val){
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
                        case 6:
                            showSalesByPriceRange();
                            break;
                        case 7:
                            showSalesByDate();
                            break;
                        case 8:
                            showSalesByCode();
                            break;
                    }
                }
            }catch (Exception e){
                incorrectMenu();
            }
            menu();
        }
        public static void addNewSale() {
            try {
                if(!isEmptyCollection(market.getAllProducts())){
                    Helper.titlePrint("Add new sale");
                    List<SaleItem> properties= getSalesProperties();
                    market.addSale(properties);
                    Helper.sleep();
                }
                else{
                    System.out.println("You dont have any product to sale! At first add product in 'Operation on products' section!");
                    Helper.sleep();
                    menu();
                }
            }catch (Exception e){
                incorrectMenu();
            }
        }
        public static void returnSaleItem(){
            try {
                Helper.titlePrint("Return product from sale");
                System.out.println("Enter sale code:");
                String code = checkString();
                Sale removedItem= market.findSaleByCode(code);
                if (removedItem!=null){
                    showSalesInner(removedItem);
                    int index= checkIndex();
                    System.out.println("How many products is returning?");
                    int count= checkNumber();
                    market.returnSaleItem(removedItem,removedItem.getSaleItemsList().get(index),count);
                }
                else {
                    System.out.println("We didn't find this sale!");
                    System.out.println("Please enter code correctly!");
                }
            }catch (Exception e){
                incorrectMenu();
            }
        }
        public static void returnSale(){
            try {
                Helper.titlePrint("Return sale");
                Scanner scanner = Helper.generateScanner();
                System.out.println("Enter sale code:");
                String code = checkString();
                Sale removedItem= market.findSaleByCode(code);
                if (removedItem!=null){
                    showSalesInner(removedItem);
                    System.out.println("Are you sure to return?(y/n)");
                    String s=scanner.nextLine();
                    if(s.equals("y")){
                        market.returnSale(removedItem);
                    }
                }
                else {
                    System.out.println("We didn't find this sale!");
                    System.out.println("Please enter code correctly!");
                }
            }catch (Exception e){
                incorrectMenu();
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
            Helper.titlePrint("Products");
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
            System.out.println("Enter start date (DAY-MONTH-YEAR//01-01-2021 12:50)");
            String min= checkString();
            System.out.println("Enter end date:");
            String max= checkString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            List<Sale> list= market.getSalesByDateRange(LocalDateTime.parse(min,formatter),LocalDateTime.parse(max,formatter));
            showSales(list);
            Helper.sleep();
        }
        public static void showSalesByPriceRange(){
            TwoTuple<Float,Float> minMax= getMinMaxPrices();
            List<Sale> list= market.getSalesByPriceRange(minMax.first,minMax.second);
            showSales(list);
            Helper.sleep();
        }
        public static void showSalesByDate(){
            System.out.println("Enter date (DAY-MONTH-YEAR//01-01-2021)");
            Scanner scanner = Helper.generateScanner();
            String date= scanner.nextLine();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            List<Sale> list= market.getSalesByDate(LocalDate.parse(date,formatter));
            showSales(list);
            Helper.sleep();
        }
        public static void showSalesByCode(){
            System.out.println("Enter sale code:");
            String code = checkString();
            Sale sale= market.findSaleByCode(code);
            if (sale!=null){
                showSalesInner(sale);
            }
            else {
                System.out.println("We didn't find this sale!");
                System.out.println("Please enter code correctly!");
            }
            Helper.sleep();
        }
        public static List<SaleItem> getSalesProperties()  {
            List<SaleItem> saleItemList = new LinkedList<>();
            Scanner scanner = Helper.generateScanner();
            String yes= "y";
            while (yes.equals("y")){
                System.out.println("Enter product code:");
                String code = scanner.nextLine();
                Product product = market.findProductByCode(code);
                System.out.println("Enter count of product:");
                int count = checkNumber();
                saleItemList.add(new SaleItem(product,count));
                System.out.println("Add another product?(y/n)");
                yes= scanner.nextLine().trim();
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

    public static boolean isEmptyCollection(Collection collection){
        return collection.size()==0;
    }
    public static boolean isEmptyCollection(Map collection){
        return collection.size()==0;
    }
    public static void incorrectMenu()  {
        Helper.incorrectNumber();
        menu();
    }
    public static void checkString(String val){
        if(val.length()==0){
            incorrectMenu();
        }
    }
    public static TwoTuple<Float,Float> getMinMaxPrices(){
        System.out.println("Enter minimum price:");
        Scanner scanner = Helper.generateScanner();
        float min= scanner.nextFloat();
        System.out.println("Enter maximum price:");
        if(!Helper.isPositive(min)) incorrectMenu();
        float max= scanner.nextFloat();
        if(!Helper.isPositive(max)) incorrectMenu();
        return new TwoTuple<>(min, max);
    }
    public static String checkString(){
        Scanner scanner = Helper.generateScanner();
        String val = scanner.nextLine().trim();
        checkString(val);
        return val;
    }
    public static int checkIndex() {
        Scanner scanner = Helper.generateScanner();
        int index= scanner.nextInt()-1;
        if(!Helper.isPositive(index)) incorrectMenu();
        return index;
    }
    public static int checkNumber() {
        Scanner scanner = Helper.generateScanner();
        int num= scanner.nextInt();
        if(!Helper.isPositive(num)) incorrectMenu();
        return num;
    }
    public static float checkFloat() {
        Scanner scanner = Helper.generateScanner();
        float num= scanner.nextFloat();
        if(!Helper.isPositive(num)) incorrectMenu();
        return num;
    }
}
