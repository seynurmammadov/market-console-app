package market.code;

import market.code.tuple.*;

import java.util.*;

import static market.code.Helper.*;

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
        titlePrint("Market");
        System.out.println("- 1 Operation on categories");
        System.out.println("- 2 Operation on products");
        System.out.println("- 3 Operation on sales");
        System.out.println("- 0 Get out of the system");
        printLine();
        Scanner scanner = generateScanner();
        try {
            int val = scanner.nextInt();
            switch (val){
                case 1:
                    operationsOnCategory();
                    break;
                case 2:
                    operationsOnProduct();
                    break;
                case 3:
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
    public static void operationsOnCategory() {
        titlePrint("Operations on categories");
        System.out.println("-1 Add new category");
        if(!isCategoryEmpty()){
            System.out.println("-2 Edit category");
            System.out.println("-3 Delete category");
            System.out.println("-4 Show all categories");
        }
        System.out.println("-0 Back");
        printLine();
        Scanner scanner = generateScanner();
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
                    sleep();
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
        titlePrint("Add new category");
        System.out.println("Enter category name:");
        Scanner scanner = generateScanner();
        String val = scanner.nextLine();
        market.addCategory(val);
    }
    public static void editCategory()  {
        if (isCategoryEmpty()){
            System.out.println("Category is empty!");
            return;
        }
        titlePrint("Select category");
        showCategories();
        Scanner scanner = generateScanner();
        int index= scanner.nextInt()-1;
        System.out.println("Enter category name:");
        String val = scanner.next();
        market.editCategory(val,index);
    }
    public static void deleteCategory()  {
        if (isCategoryEmpty()){
            System.out.println("Category is empty!");
            return;
        }
        titlePrint("Select category");
        showCategories();
        Scanner scanner = generateScanner();
        int index= scanner.nextInt()-1;
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
    //Product menu and CRUD
    public static void operationsOnProduct()  {
        titlePrint("Operations on product");
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
        printLine();
        Scanner scanner = generateScanner();
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
                    sleep();
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
                titlePrint("Add new product");
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
                    titlePrint("Edit product");
                    Scanner scanner = generateScanner();
                    System.out.println("Enter product code:");
                    String code = scanner.next();
                    if (market.findProductByCode(code)!=null){
                        FourTuple<String,Category,Integer,Float> properties= getProductProperties();
                        market.editProduct(code,properties.first,properties.second,properties.three,properties.four);
                    }
                    else {
                        System.out.println("We didn't find this product!");
                        System.out.println("Please enter code correctly!");
                        editProduct();
                    }
                }
                else{
                    noticeAddingCategory();
                }
            }
            else {
                System.out.println("Product list is empty!");
                return;
            }
        }catch (Exception e){
            incorrectProduct();
        }
    }
    public static void deleteProduct(){
        try {
            if(!isProductEmpty()){
                    titlePrint("Delete product");
                    Scanner scanner = generateScanner();
                    System.out.println("Enter product code:");
                    String code = scanner.nextLine();
                    if (market.findProductByCode(code)!=null){
                        market.deleteProduct(code);
                        System.out.println(market.getAllProducts());
                    }
                    else {
                        System.out.println("We didn't find this product!");
                        System.out.println("Please enter code correctly!");
                        deleteProduct();
                    }
            }
            else {
                System.out.println("Product list is empty!");
                return;
            }
        }catch (Exception e){
            incorrectProduct();
        }
    }
    public static void showProducts(Collection<Product> collection){
        if (isProductEmpty(collection)){
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
        showCategories();
        Scanner scanner = generateScanner();
        int index= scanner.nextInt()-1;
        List<Product> list= market.getProdByCat(index);
        showProducts(list);
        sleep();
    }
    public static void showProdByPriceLenght(){
        if (isProductEmpty()){
            System.out.println("Product list is empty!");
            return;
        }
        System.out.println("Enter minimum price:");
        Scanner scanner = generateScanner();
        float min= scanner.nextFloat();
        System.out.println("Enter maximum price:");
        if(!isPositive(min)) incorrectProduct();
        float max= scanner.nextFloat();
        if(!isPositive(max)) incorrectProduct();
        List<Product> list= market.getProdByPriceRange(min,max);
        showProducts(list);
        sleep();
    }
    public static void searchByName(){
        if (isProductEmpty()){
            System.out.println("Product list is empty!");
            return;
        }
        System.out.println("Enter searching product name:");
        Scanner scanner = generateScanner();
        String regex= scanner.next();
        List<Product> list= market.getProdByRegex(regex);
        showProducts(list);
        sleep();
    }


    public static FourTuple<String,Category,Integer,Float> getProductProperties()  {
        Scanner scanner = generateScanner();
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
        titlePrint("Select category");
        showCategories();
        Category category = market.getCategories().get(scanner.nextInt()-1);
        System.out.println("Enter count of product:");
        int count = scanner.nextInt();
        if(!isPositive(count)) incorrectProduct();
        System.out.println("Enter price of product:");
        float price = scanner.nextFloat();
        if(!isPositive(price)) incorrectProduct();
        return new FourTuple<>(name,category,count,price);
    }

    public static void noticeAddingCategory() {
        System.out.println("Before adding a product you need to add at least one category!");
        System.out.println("Do you want add new category? (y/n)");
        Scanner scanner = generateScanner();
        String val = scanner.nextLine();
        switch (val){
            case "y":
                addNewCategory();
                break;
            case "n":
                System.out.println("We return you to the main page!");
                sleep();
                menu();
            default:
                incorrectProduct();
        }
    }

    public static boolean isCategoryEmpty(){
        return market.getCategories().size()==0;
    }
    public static boolean isProductEmpty(){
        return market.getAllProducts().size()==0;
    }
    public static boolean isProductEmpty(Collection collection){
        return collection.size()==0;
    }
    public static void incorrectMenu()  {
        incorrectNumber();
        menu();
    }
    public static void incorrectCategory()  {
        incorrectNumber();
        operationsOnCategory();
    }
    public static void incorrectProduct()  {
        incorrectNumber();
        operationsOnProduct();
    }
}
