package fooddelivery.service;

import fooddelivery.model.Rating;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class RatingService {

    private final List<Rating> ratings = new ArrayList<>();
    private final Set<String>  ratedOrderIds = new HashSet<>();   // prevent double-rating

    public boolean hasRated(String orderId) {
        return ratedOrderIds.contains(orderId);
    }

    public void addRating(Rating rating) {
        if (rating.getOrderId() != null)
            ratedOrderIds.add(rating.getOrderId());
        ratings.add(rating);
    }

    public List<Rating> getRatingsByRestaurant(String restaurantId) {
        List<Rating> result = new ArrayList<>();
        for (Rating r : ratings)
            if (restaurantId.equals(r.getRestaurantId())) result.add(r);
        return result;
    }

    public double getAverageStars(String restaurantId) {
        List<Rating> list = getRatingsByRestaurant(restaurantId);
        if (list.isEmpty()) return 0.0;
        return list.stream().mapToInt(Rating::getStars).average().orElse(0.0);
    }

    public List<Rating> getAllRatings() { return new ArrayList<>(ratings); }
}
