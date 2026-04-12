package fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private List<MenuItem> items = new ArrayList<>();

    public Menu() {}

    public List<MenuItem> getItems() { return items; }
    public void setItems(List<MenuItem> items) { this.items = items; }

    public void addItem(MenuItem item) {
        items.add(item);
    }

    public void removeItem(String itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
    }

    // filter by category
    public List<MenuItem> getItemsByCategory(String category) {
        List<MenuItem> filtered = new ArrayList<>();
        for (MenuItem item : items) {
            if (item.getCategory() != null &&
                    item.getCategory().equalsIgnoreCase(category)) {
                filtered.add(item);
            }
        }
        return filtered;
    }
}