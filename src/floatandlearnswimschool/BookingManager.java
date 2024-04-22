package floatandlearnswimschool;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BookingManager {

    public Scanner option;
    private TimetableManager timetableManager;
    private ReviewManager reviewManager;
    private Learner learner;
    public String learnerId;
    private String bookingId;
    private String lessonId;
    private String status;
    public List<BookingManager> bookings;

    public String getLearnerId() {
        return learnerId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getStatus() {
        return status;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public void setTimetableManager(TimetableManager timetableManager) {
        this.timetableManager = timetableManager;
    }

    public void setReviewManager(ReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    //create bookingManger constructor to add booking details
    public BookingManager(String bookingId, String learnerId, String lessonId, String status) {
        this.bookingId = bookingId;
        this.learnerId = learnerId;
        this.lessonId = lessonId;
        this.status = status;
    }

    //create default constructor
    public BookingManager() {
        bookings = new ArrayList<>();
        option = new Scanner(System.in);
    }

    //booking menu
    public void bookingMenu() {
        String preference = null;
        //learner login to get learner id

        learnerId = learner.learnerLogin();

        //open booking menu if learner id is not nulll
        if (learnerId != null) {
            System.out.println("\n.................................");
            System.out.println("\tLearner Dashboard ");
            System.out.println(".................................\n");
            do {
                System.out.println("\nA. My Profile");
                System.out.println("B. Book a Lesson");
                System.out.println("C. View My Bookings");
                System.out.println("D. View My Reviews");
                System.out.println("E. Change Lesson");
                System.out.println("F. Attend Lesson");
                System.out.println("G. Cancel Lesson");
                System.out.println("H. Signout");
                System.out.print("\nEnter your preference (A-H): ");
                preference = option.nextLine().trim();
                if (preference.equalsIgnoreCase("A")) {
                    learner.learnerProfile(learnerId);
                } else if (preference.equalsIgnoreCase("B")) {
                    boolean booking = bookingLesson();
                    if (booking) {
                        System.out.println("\nLesson successfully booked!");
                    }
                } else if (preference.equalsIgnoreCase("C")) {
                    showBookings(learnerId);

                } else if (preference.equalsIgnoreCase("D")) {
                    reviewManager.showReviews(learnerId, "", "");
                } else if (preference.equalsIgnoreCase("E")) {
                    boolean change = changeLessons();
                    if (change) {
                        System.out.println("\nLesson successfully changed");
                    }
                } else if (preference.equalsIgnoreCase("F")) {
                    boolean attend = attendLessons();
                    if (attend) {
                        System.out.println("Lesson successfully attended");
                    }
                } else if (preference.equalsIgnoreCase("G")) {
                    cancelledLesson();
                } else if (preference.equalsIgnoreCase("H")) {
                    System.out.println("You signed out successfully");
                } else {
                    System.out.println("Invalid input ! you entered incorrect preference");
                }
            } while (!preference.equalsIgnoreCase("H"));
        }
    }

    //get booking details by learners
    public List<BookingManager> getLearnerBookings(String learnerId) {
        //create list to return
        List<BookingManager> learnerBookings = new ArrayList<>();
        for (BookingManager booking : bookings) {
            if (!learnerId.isEmpty()) {
                if (booking.getLearnerId().equalsIgnoreCase(learnerId)) {
                    learnerBookings.add(booking);
                }
            } else {
                learnerBookings.add(booking);
            }
        }
        return learnerBookings;
    }

    //show all bookings
    public void showBookings(String learnerId) {
        //validation filterlesson not empty
        if (!getLearnerBookings(learnerId).isEmpty()) {
             System.out.println("\nBooking Details:");
            System.out.println("..............................................................................................................................."
                    + ".......................................................................");
            System.out.printf(": %-10s : %-10s : %-15s : %-10s : %-12s : %-12s : %-12s : %-12s : %-10s : %-13s : %-22s : %-10s : %-10s : \n",
                    "Booking Id", "Lesson Id", "Lesson Title", "Status", "Lesson Level", "Learner Name", "Learner Level", "Date", "Time", "Trainer Name", "Equipment Needed", "Day", "Lesson Fees");
            System.out.println("................................................................................................................."
                    + ".....................................................................................");
            //print details
            for (BookingManager booking : getLearnerBookings(learnerId)) {
                //get lesson object from bookings
                Lesson lesson = timetableManager.getLessonById(booking.getLessonId());
                //get learner object from bookings
                Learner learner1 = learner.getLearnerById(booking.getLearnerId());
                System.out.printf(": %-10s : %-10s : %-15s : %-10s : %-12s : %-12s : %-13s : %-12s : %-10s : %-13s : %-21s : %-10s : %-10s  : \n",
                        booking.getBookingId(), lesson.getLessonId(), lesson.getLessonTitle(), booking.getStatus(), " Level " + lesson.getLessonLevel(), learner1.getLearnerName(), "  Level " + learner1.getLearnerLevel(), lesson.getLessonDate(),
                        lesson.getLessonTime(), new Trainer().getTrainerById(lesson.getTrainerId()).getFullName(),
                        lesson.getEquipmentNeeded(), lesson.getLessonDay(), lesson.getLessonPrice());
            }

            System.out.println("................................................................................................................................"
                    + "......................................................................");
        } else {
            System.out.println("Sorry ! no bookings available");
        }
    }

    //booking lesson
    public boolean bookingLesson() {
        String status = "Booked";
        String bookingId = null;
        if (bookings.size() >= 0 && bookings.size() <= 9) {
            bookingId = "BID0" + (bookings.size() + 1);
        } else {
            bookingId = "BID" + (bookings.size() + 1);
        }

        //show all timetable by default
        timetableManager.filterLessons = timetableManager.lessons;
        timetableManager.showLessons();
        String lessonId = timetableManager.filterLesson("Booking");
        if (lessonId != null) {
            //create booking Manager object ot booking lesson
            BookingManager booking = new BookingManager(bookingId, learnerId, lessonId, status);
            bookings.add(booking);
            //update seat after lesson booking
            //get lesson object to update seat
            Lesson lesson = timetableManager.getLessonById(lessonId);
            lesson.setSeats(lesson.getSeats() - 1);
            return true;
        }
        return false;
    }

    //change lesson
    public boolean changeLessons() {
        String status = "Changed";
        String bookingId;
        //validation of learner bookings not empty
        if (!getLearnerBookings(learnerId).isEmpty()) {
            //show bookings details
            showBookings(learnerId);
            System.out.print("Enter booking id to chnage lesson : ");
            bookingId = option.nextLine().trim();
            if (validBookingId(bookingId)) {
                //select new lesson to chnage
                //show all timetable by default
                timetableManager.filterLessons = timetableManager.lessons;
                timetableManager.showLessons();
                String lessonId = timetableManager.filterLesson("Change");
                if (lessonId != null) {
                    //update lessson after change
                    //get booking object to update details
                    BookingManager booking = getBookingsById(bookingId);
                    //get previous lessonid to update seat
                    String previousLessonId = booking.getLessonId();
                    //update previous lesson id seat
                    timetableManager.getLessonById(previousLessonId).setSeats(timetableManager.getLessonById(previousLessonId).getSeats() + 1);
                    //update lesson
                    booking.setLessonId(lessonId);
                    //update new lesson id seats
                    System.out.println("lesson seat lesson id : " + lessonId);
                    timetableManager.getLessonById(lessonId).setSeats(timetableManager.getLessonById(lessonId).getSeats() - 1);
                    //update booking status
                    booking.setStatus(status);
                    return true;
                }
            }
        } else {
            System.out.println("Invalid input! No bookings available to change.");
            return false;
        }
        return false;
    }

    //attend lessons
    public boolean attendLessons() {
        String status = "Attended";
        String bookingId;
        //validation of learner bookings not empty
        if (!getLearnerBookings(learnerId).isEmpty()) {
            //show bookings details
            showBookings(learnerId);
            System.out.print("Enter booking id to attend lesson : ");
            bookingId = option.nextLine().trim();
            if (validBookingId(bookingId)) {
                //show selected booking
                System.out.println("\n\tAttended Lesson Details");
                System.out.println(".........................................");
                selectedBookings(bookingId);
                //get rating and review
                ReviewManager learnerReview = reviewManager.learnerReviews(bookingId, "", "");
                if (learnerReview != null) {
                    //save review 
                    reviewManager.reviews.add(learnerReview);
                    //update booking status
                    getBookingsById(bookingId).setStatus(status);
                    //update lesson seat
                    Lesson lesson = timetableManager.getLessonById(getBookingsById(bookingId).getLessonId());
                    lesson.setSeats(lesson.getSeats() + 1);
                    //update learner level
                    Learner learner1 = learner.getLearnerById(getBookingsById(bookingId).getLearnerId());
                    int lessonLevel = lesson.getLessonLevel();
                    int learnerLevel = learner1.getLearnerLevel();
                    if (lessonLevel > learnerLevel) {
                        learner1.setLearnerLevel(lessonLevel);
                    }
                    return true;
                } else {
                    return false;
                }

            }
        } else {
            System.out.println("Invalid input! No bookings available to attend.");
            return false;
        }
        return false;
    }

    //cancell lesson 
    public boolean cancelledLesson() {
        String status = "Cancelled";
        String bookingId;
        //validation of learner bookings not empty
        if (!getLearnerBookings(learnerId).isEmpty()) {
            //show bookings details
            showBookings(learnerId);
            System.out.print("Enter booking id to cancel lesson : ");
            bookingId = option.nextLine().trim();
            if (validBookingId(bookingId)) {
                //update booking status
                getBookingsById(bookingId).setStatus(status);
                //get lesson id to update seat
                String lessonId = getBookingsById(bookingId).getLessonId();
                //update lesson seat
                timetableManager.getLessonById(lessonId).setSeats(timetableManager.getLessonById(lessonId).getSeats() + 1);
                System.out.println("\nLesson successfully cancelled");
                //show selected booking
                System.out.println("\n\tCancelled Lesson Details");
                System.out.println(".........................................");
                selectedBookings(bookingId);
                return true;
            }
        } else {
            System.out.println("Invalid input! No bookings available to cancel.");
            return false;
        }
        return false;

    }

    //show selected booking details
    public void selectedBookings(String bookingId) {
        for (BookingManager booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                System.out.println("..............................................................................................................................."
                        + ".......................................................................");
                System.out.printf(": %-10s : %-10s : %-15s : %-8s : %-12s : %-12s : %-12s : %-12s : %-10s : %-13s : %-22s : %-10s : %-10s : \n",
                        "Booking Id", "Lesson Id", "Lesson Title", "Status", "Lesson Level", "Learner Name", "Learner Level", "Date", "Time", "Trainer Name", "Equipment Needed", "Day", "Lesson Fees");
                System.out.println("................................................................................................................."
                        + ".....................................................................................");
                //get lesson object from bookings
                Lesson lesson = timetableManager.getLessonById(booking.getLessonId());
                //get learner object from bookings
                Learner learner1 = learner.getLearnerById(booking.getLearnerId());
                System.out.printf(": %-10s : %-10s : %-15s : %-8s : %-12s : %-12s : %-13s : %-12s : %-10s : %-13s : %-20s : %-10s : %-10s  : \n",
                        booking.getBookingId(), lesson.getLessonId(), lesson.getLessonTitle(), booking.getStatus(), " Level " + lesson.getLessonLevel(), learner1.getLearnerName(), "  Level " + learner1.getLearnerLevel(), lesson.getLessonDate(),
                        lesson.getLessonTime(), new Trainer().getTrainerById(lesson.getTrainerId()).getFullName(),
                        lesson.getEquipmentNeeded(), lesson.getLessonDay(), lesson.getLessonPrice());

                System.out.println("................................................................................................................."
                        + ".....................................................................................");

            }
        }
    }

    //get booking object by id
    public BookingManager getBookingsById(String bookingId) {
        for (BookingManager booking : bookings) {
            if (booking.getBookingId().equalsIgnoreCase(bookingId)) {
                return booking;
            }
        }
        //return null if booking id not found in bookings
        return null;
    }

    //validation of booking id
    public boolean validBookingId(String bookingId) {
        if (getBookingsById(bookingId) != null) {
            //print error message if booking already atteded
            if (getBookingsById(bookingId).getStatus().equalsIgnoreCase("Attended")) {
                System.out.println("Invalid input! Booking already attended by you.");
                return false;
                //print error message if booking alrady cancelled
            } else if (getBookingsById(bookingId).getStatus().equalsIgnoreCase("Cancelled")) {
                System.out.println("Invalid input! Booking already cancelled by you.");
                return false;
            } else {
                //return true if found valid booking id
                return true;
            }

        } else {
            System.out.println("Invalid input ! bookingId does not exist");
            return false;
        }
    }

    //validation of duplicate booking
    public boolean duplicateBooking(String lessonId) {
        for (BookingManager booking : bookings) {
            if (booking.getLearnerId().equalsIgnoreCase(learnerId) && booking.getLessonId().equalsIgnoreCase(lessonId)) {
                if (!(booking.getStatus().equalsIgnoreCase("Cancelled") || booking.getStatus().equalsIgnoreCase("Attended"))) {
                    return true;
                }
            }
        }
        return false;
    }

}
