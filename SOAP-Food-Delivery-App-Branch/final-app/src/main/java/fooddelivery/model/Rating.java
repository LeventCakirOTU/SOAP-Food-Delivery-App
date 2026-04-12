package fooddelivery.model;

public class Rating {

    private String id;
    private String customerId;
    private String orderId;
    private String restaurantId;
    private int    stars;          // 1-5
    private String comment;

    public Rating() {}
    public String getId()              { return id; }
    public void   setId(String id)     { this.id = id; }
    public String getCustomerId()              { return customerId; }
    public void   setCustomerId(String cid)    { this.customerId = cid; }
    public String getOrderId()             { return orderId; }
    public void   setOrderId(String oid)   { this.orderId = oid; }
    public String getRestaurantId()            { return restaurantId; }
    public void   setRestaurantId(String rid)  { this.restaurantId = rid; }
    public int  getStars()             { return stars; }
    public void setStars(int stars)    { this.stars = Math.max(1, Math.min(5, stars)); }
    public String getComment()                 { return comment; }
    public void   setComment(String comment)   { this.comment = comment; }
}
