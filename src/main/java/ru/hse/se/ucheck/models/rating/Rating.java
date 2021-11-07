package ru.hse.se.ucheck.models.rating;

public class Rating {

    private long ratingsSum = 0;
    private long ratingsCount = 0;

    public double getAverage() {
        return ratingsCount == 0 ? 0 : ((double) ratingsSum) / ratingsCount;
    }

    public void applyReview(Review review) {
        ratingsSum += review.getValue();
        ratingsCount += 1;
    }

}
