package IMat;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Erik on 2017-02-27.
 */
public class ShoppingList {
    private String name;
    private String description;
    private List<ShoppingItem> items;

    private static final List<ShoppingList> allLists = getSampleShoppingLists();

    private static final PropertyChangeSupport observers = new PropertyChangeSupport(new ShoppingList(null, null, null));

    public static void addObserver(PropertyChangeListener observer) {
        observers.addPropertyChangeListener(observer);
    }

    public static void removeObserver(PropertyChangeListener observer) {
        observers.removePropertyChangeListener(observer);
    }

    public static void notifyShoppingListChanged() {
        observers.firePropertyChange("ShoppingList Changed", null, null);
    }


    public static List<ShoppingList> getAllShoppingLists() {
        return allLists;
    }

    public static void addNewShoppingList(ShoppingList a) {

        allLists.add(0, a);
        notifyShoppingListChanged();
    }

    public static void removeShoppingList(ShoppingList a) {
        allLists.remove(a);
        notifyShoppingListChanged();
    }

    public ShoppingList(String name, String description, List<ShoppingItem> items) {
        this.name = name;
        this.description = description;
        this.items = items;
    }

    public static List<ShoppingList> getSampleShoppingLists() {
        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        List<Product> allItems = dataHandler.getProducts();
        List<ShoppingList> result = new ArrayList<>();
        result.add(new ShoppingList("Börjes Kakor", "Tips från min roliga kompis Börje.", getRandomItems(allItems, 5)));
        result.add(new ShoppingList("Veckolistan", "Det vanliga jag brukar köpa.", getRandomItems(allItems, 7)));
        result.add(new ShoppingList("Emils Hamburgare", "Favoriten som alltid går hem.", getRandomItems(allItems, 3)));
        result.add(new ShoppingList("Thai gryta", "God Thai-gryta. Färsk riven ingefära.", getRandomItems(allItems, 10)));
        result.add(new ShoppingList("Köttfärsås", "Mormors goda köttfärsås & spaghetti.", getRandomItems(allItems, 12)));
        return result;
    }

    private static List<ShoppingItem> getRandomItems(List<Product> products, int n) {
        List<ShoppingItem> items = new ArrayList<>(n);
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int k = r.nextInt(products.size());
            items.add(new ShoppingItem(products.get(k), r.nextInt(6)+1));
        }
        return items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ShoppingItem> getItems() {
        return items;
    }

//    public String getPriceString() {
//        double price = Math.round(getPrice() * 100.0) / 100.0;
//        String s = String.valueOf(price).replaceAll("\\.", ",");
//        int p1 = (int) ((price*100) % 10);
//        if (p1 == 0) {
//            s = s + "0";
//        }
//        s = s + " :-";
//        return s;
//    }
    public double getPrice() {
        if (items == null) {
            return 0.0;
        }
        double sum = 0.0;
        for (ShoppingItem item : items) {
            sum = sum + item.getTotal();
        }
        return sum;
    }

    public void setItems(List<ShoppingItem> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShoppingList that = (ShoppingList) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        return items != null ? items.equals(that.items) : that.items == null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }

    public String getShortIngredients() {
        if (items == null || items.isEmpty()) {
            return "";
        }
        String s = "";
        for (int i = 0; i < items.size(); i++) {
            if (i == 0) {
                s += items.get(i).getProduct().getName();
            } else {
                s += ", " + items.get(i).getProduct().getName();
            }
        }
        return s;
    }
}
