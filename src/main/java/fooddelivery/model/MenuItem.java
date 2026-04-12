package fooddelivery.model;

public class MenuItem {

    private String  id;
    private String  name;
    private String  description;
    private double  price;
    private String  category;
    private boolean available = true;   // NEW
    private int     preparationTime;    // minutes

    public MenuItem() {}

    public int  getPreparationTime()                    { return preparationTime; }
    public void setPreparationTime(int preparationTime) { this.preparationTime = preparationTime; }

    public String getId()                  { return id; }
    public void   setId(String id)         { this.id = id; }

    public String getName()                { return name; }
    public void   setName(String name)     { this.name = name; }

    public String getDescription()                     { return description; }
    public void   setDescription(String description)   { this.description = description; }

    public double getPrice()               { return price; }
    public void   setPrice(double price)   { this.price = price; }

    public String getCategory()                { return category; }
    public void   setCategory(String category) { this.category = category; }

    public boolean isAvailable()               { return available; }          // NEW
    public void    setAvailable(boolean avail) { this.available = avail; }    // NEW

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem item = (MenuItem) o;
        return id != null && id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
