package fooddelivery.model;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    private List<MenuItem> items = new ArrayList<>();

    public Menu() {}

    public List<MenuItem> getItems()            { return items; }
    public void           setItems(List<MenuItem> items) { this.items = items; }

    public void addItem(MenuItem item)          { items.add(item); }
    public void removeItem(String itemId)       { items.removeIf(i -> i.getId().equals(itemId)); }
}
