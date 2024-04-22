package floatandlearnswimschool;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReviewManager {

    public Scanner option;
    private BookingManager bookingManager;
    private TimetableManager timetableManager;
    private Learner learner;
    private String ratingId;
    private String bookingId;
    private String trainerId;
    private String lessonId;
    private String learnerId;
    private String review;
    private int rating;
    public List<ReviewManager> reviews;

    public String getRatingId() {
        return ratingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getLearnerId() {
        return learnerId;
    }

    public String getReview() {
        return review;
    }

    public int getRating() {
        return rating;
    }

    public void setBookingManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    public void setTimetableManager(TimetableManager timetableManager) {
        this.timetableManager = timetableManager;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public ReviewManager(String ratingId, String bookingId, String trainerId, String lessonId, String learnerId, String review, int rating) {
        this.ratingId = ratingId;
        this.bookingId = bookingId;
        this.trainerId = trainerId;
        this.lessonId = lessonId;
        this.learnerId = learnerId;
        this.review = review;
        this.rating = rating;
    }

    //create default constructor
    public ReviewManager() {
        option = new Scanner(System.in);
        reviews = new ArrayList<>();
    }

    //get reviews of user
    public List<ReviewManager> getReviews(String learnerId, String trainerId, String month) {
        String lessonMonth;
        //create list to return filter review
        List<ReviewManager> filterReview = new ArrayList<>();
        for (ReviewManager reviewManager : reviews) {
            lessonMonth = timetableManager.getLessonById(reviewManager.getLessonId()).getLessonDate().substring(3, 6);
            if (!learnerId.isEmpty()) {
                if (reviewManager.getLearnerId().equalsIgnoreCase(learnerId)) {
                    filterReview.add(reviewManager);
                }
            } else if (!trainerId.isEmpty()) {
                if (reviewManager.getTrainerId().equalsIgnoreCase(trainerId) && lessonMonth.equalsIgnoreCase(month)) {
                    filterReview.add(reviewManager);
                }
            } else {
                filterReview.add(reviewManager);
            }
        }
        return filterReview;
    }

    //show review 
    public void showReviews(String learnerId, String trainerId, String month) {
        //get filterReview
        List<ReviewManager> filterReviews = getReviews(learnerId, trainerId, month);
        //check filter review not empty
        if (!filterReviews.isEmpty()) {
            //print heading
            System.out.println("..........................................................................................................................."
                    + "...........................................................................");
            System.out.printf(": %-12s : %-12s : %-12s : %-15s : %-15s : %-15s : %-15s : %-12s : %-13s : %-10s : %-30s : \n",
                    "Review Id", "Booking Id", "Lesson Id", "Lesson Title", "Learner Name", "Trainer Name", "Lesson Date", "Lesson Day", "Time", "Rating", "Reviews");
            System.out.println("................................................................................................................."
                    + ".....................................................................................");
            for (ReviewManager review1 : filterReviews) {
                //get lesson object to get lesson information
                Lesson lesson = timetableManager.getLessonById(review1.getLessonId());
                System.out.printf(": %-12s : %-12s : %-12s : %-15s : %-15s : %-15s : %-15s : %-12s : %-13s : %-10s : %-30s : \n",
                        review1.getRatingId(), review1.getBookingId(), review1.getLessonId(), lesson.getLessonTitle(),
                        learner.getLearnerById(review1.getLearnerId()).getLearnerName(), new Trainer().getTrainerById(review1.getTrainerId()).getFullName(),
                        lesson.getLessonDate(), lesson.getLessonDay(), lesson.getLessonTime(), review1.getRating(), review1.getReview());

            }
            System.out.println("................................................................................................................."
                    + ".....................................................................................");
        } else {
            System.out.println("Sorry ! no reviews available");
        }

    }

    //get review from user
    public ReviewManager learnerReviews(String bookingId,String review,String ratingInput) {
        String ratingId;
        if (reviews.size() >= 0 && reviews.size() <= 9) {
            ratingId = "RID0" + (reviews.size() + 1);
        } else {
            ratingId = "RID" + (reviews.size() + 1);
        }
        
        // Show review options
        System.out.println("\nLearner Review\n");
        System.out.println("1. Very Dissatisfied");
        System.out.println("2. Dissatisfied");
        System.out.println("3. Ok");
        System.out.println("4. Satisfied");
        System.out.println("5. Very Satisfied\n");
        // get learner review
        System.out.print("Enter your review for this lesson: ");
        review = option.nextLine().trim();
        // Check if review is empty
        if (review.isEmpty()) {
            System.out.println("Invalid input! Review field cannot be empty.");
            return null;
        } else {
            //get learner rating
            System.out.print("Enter your rating for this lesson (1-5): ");
            ratingInput = option.nextLine().trim();
            // Check if rating input is empty
            if (ratingInput.isEmpty()) {
                System.out.println("Invalid input! Rating field cannot be empty.");
                return null;
            } else {
                if (ratingInput.matches("\\d+")) {
                    rating = Integer.parseInt(ratingInput);
                    // Check if rating is within valid range
                    if (rating >= 1 && rating <= 5) {
                        //create reviewManaggement to return
                        //get booking object by id
                        BookingManager booking = bookingManager.getBookingsById(bookingId);
                        String lessonId = booking.getLessonId();
                        String learnerId = booking.getLearnerId();
                        String trainerId = new TimetableManager().getLessonById(lessonId).getTrainerId();
                        ReviewManager review1 = new ReviewManager(ratingId, bookingId, trainerId, lessonId, learnerId, review, rating);
                        //add review
                        return review1;
                    } else {
                        System.out.println("Invalid input! Please enter a rating between 1 and 5.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a rating between 1 and 5.");
                }

            }
        }
        return null;
    }

}
