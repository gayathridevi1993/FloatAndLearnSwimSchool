package floatandlearnswimschool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReportManager {

    private Scanner option;
    private BookingManager bookingManager;
    private ReviewManager reviewManager;
    private TimetableManager timetableManager;
    private Learner learner;

    public void setBookingManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    public void setReviewManager(ReviewManager reviewManager) {
        this.reviewManager = reviewManager;
    }

    public void setTimetableManager(TimetableManager timetableManager) {
        this.timetableManager = timetableManager;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    //create constructor
    public ReportManager() {
        option = new Scanner(System.in);
    }

    //filter report
    public void filterReport() {
        String preference;
        String month;
        do {
            //show option to select
            System.out.println("\nA. Trainer ");
            System.out.println("B. Learner");
            System.out.println("C. Return");
            //get preference to generate report
            System.out.print("Enter your Preference to generate report : ");
            preference = option.nextLine().trim();
            if (preference.equalsIgnoreCase("A") || preference.equalsIgnoreCase("B")) {
                //get month to generate report
                System.out.print("Enter month name to generate report : ");
                month = option.nextLine().trim();
                //validation of month not empty
                if (validMonth(month) != null) {
                    if (preference.equalsIgnoreCase("A")) {
                        //generate traier report
                        generateTrainerReport(month);
                    } else if (preference.equalsIgnoreCase("B")) {
                        //generate l
                        generateLearnerReport(month);
                    }
                }
            } else if (preference.equalsIgnoreCase("c")) {
                return;
            } else {
                System.out.println("Invalid input ! you entered an incorrect month name");
            }
        } while (!preference.equalsIgnoreCase("C"));
    }

    //generate learner report
    public void generateLearnerReport(String month) {
        boolean reportAvailable = false;
        try {
            //print details in file
            PrintWriter outputStream = new PrintWriter("src\\floatandlearnswimschool\\Details\\learnerReport");
            //print heading
            outputStream.println(".................................");
            outputStream.println("Learner report of month : " + month);
            outputStream.println(".................................\n");
            //get booking details by learner id
            for (Learner learner1 : learner.learners) {
                List<Integer> bookingDetails = calculateLearnerBookings(learner1.getLearnerId(), month);
                //validation of booking details is not empty
                if (!bookingDetails.isEmpty()) {
                    if (!(bookingDetails.get(0) == 0 && bookingDetails.get(1) == 0 && bookingDetails.get(2) == 0)) {
                        outputStream.println("\nId              : " + learner1.getLearnerId());
                        outputStream.println("Name            : " + learner1.getLearnerName());
                        outputStream.println("Level           : " + learner1.getLearnerLevel());
                        outputStream.println("Email           : " + learner1.getLearnerEmail());
                        outputStream.println("Contact         : " + learner1.getLearnerContact());
                        outputStream.println("Age             : " + learner1.getLearnerAge());
                        outputStream.println("Gender          : " + learner1.getLearnerGender());
                        outputStream.println("Total bookings  : " + bookingDetails.get(0));
                        outputStream.println("total attends   : " + bookingDetails.get(1));
                        outputStream.println("total cancelled : " + bookingDetails.get(2));
                        outputStream.println("..............................................................................................................................."
                                + ".......................................................................");
                        outputStream.println(String.format(": %-10s : %-10s : %-15s : %-8s : %-12s : %-12s : %-12s : %-12s : %-10s : %-13s : %-22s : %-10s : %-10s : ",
                                "Booking Id", "Lesson Id", "Lesson Title", "Status", "Lesson Level", "Learner Name", "Learner Level", "Date", "Time", "Trainer Name", "Equipment Needed", "Day", "Lesson Fees"));
                        outputStream.println("..............................................................................................................................."
                                + ".......................................................................");
                        //print details
                        for (BookingManager booking : bookingManager.getLearnerBookings(learner1.getLearnerId())) {
                            //get lesson object from bookings
                            Lesson lesson = timetableManager.getLessonById(booking.getLessonId());
                            //get learner object from bookings
                            Learner learner2 = learner.getLearnerById(booking.getLearnerId());
                            outputStream.println(String.format(": %-10s : %-10s : %-15s : %-8s : %-12s : %-12s : %-13s : %-12s : %-10s : %-13s : %-20s : %-10s : %-10s  :",
                                    booking.getBookingId(), lesson.getLessonId(), lesson.getLessonTitle(), booking.getStatus(), " Level " + lesson.getLessonLevel(), learner2.getLearnerName(), "  Level " + learner2.getLearnerLevel(), lesson.getLessonDate(),
                                    lesson.getLessonTime(), new Trainer().getTrainerById(lesson.getTrainerId()).getFullName(),
                                    lesson.getEquipmentNeeded(), lesson.getLessonDay(), lesson.getLessonPrice()));
                        }
                        outputStream.println("..............................................................................................................................."
                                + ".......................................................................");
                        reportAvailable = true;
                    }
                    
                }

            }
            outputStream.close();

            // Print success message if report is available
            if (reportAvailable) {
                System.out.println("Success! Learner report has been downloaded in the details package");
            } else {
                System.out.println("Sorry! No learners reports available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //generate trainer report
    public void generateTrainerReport(String month) {
        boolean reportAvailable = false;
        try {
            //print details in file
            PrintWriter outputStream = new PrintWriter("src\\floatandlearnswimschool\\Details\\trainerReport");
            //print heading
            outputStream.println(".................................");
            outputStream.println("Trainer report of month : " + month);
            outputStream.println(".................................\n");
            for (Trainer trainer : new Trainer().trainers) {
                double averageRatings = calcualteTrainerRating(trainer.getTrainerId(),month);
                if (averageRatings != 0) {
                    outputStream.println("\nId                : " + trainer.getTrainerId());
                    outputStream.println("Name              : " + trainer.getFullName());
                    outputStream.println("Email             : " + trainer.getEmail());
                    outputStream.println("Contact           : " + trainer.getContact());
                    outputStream.println("Age               : " + trainer.getDateOfBirth());
                    outputStream.println("Gender            : " + trainer.getGender());
                    outputStream.println("Average ratings   : " + averageRatings);
                    outputStream.println("................................................................................................................."
                    + ".....................................................................................");
                    outputStream.println(String.format(": %-12s : %-12s : %-12s : %-15s : %-15s : %-15s : %-15s : %-12s : %-13s : %-10s : %-30s : ",
                            "Review Id", "Booking Id", "Lesson Id", "Lesson Title", "Learner Name", "Trainer Name", "Lesson Date", "Lesson Day", "Time", "Rating", "Reviews"));
                    outputStream.println("................................................................................................................."
                    + ".....................................................................................");
                    //get trainer review list
                    List<ReviewManager> filterReviews = reviewManager.getReviews("", trainer.getTrainerId(), month);
                    for (ReviewManager review1 : filterReviews) {
                        //get lesson object to get lesson information
                        Lesson lesson = timetableManager.getLessonById(review1.getLessonId());
                        outputStream.println(String.format(": %-12s : %-12s : %-12s : %-15s : %-15s : %-15s : %-15s : %-12s : %-13s : %-10s : %-30s : ",
                                review1.getRatingId(), review1.getBookingId(), review1.getLessonId(), lesson.getLessonTitle(),
                                learner.getLearnerById(review1.getLearnerId()).getLearnerName(), new Trainer().getTrainerById(review1.getTrainerId()).getFullName(),
                                lesson.getLessonDate(), lesson.getLessonDay(), lesson.getLessonTime(), review1.getRating(), review1.getReview()));

                    }
                    outputStream.println("................................................................................................................."
                    + ".....................................................................................");
                
                reportAvailable=true;
                }
            }
            outputStream.close();
            // Print success message if report is available
            if (reportAvailable) {
                System.out.println("Success ! Trainer report has been downloaded in the details package");
            } else {
                System.out.println("Sorry! No trainers reports available.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //calculate trainer average rating
    public double calcualteTrainerRating(String trainerId,String month) {
        double totalRating = 0;
        int totalLessons = 0;
        double avgRating = 0;
        String lessonMonth;
        for (ReviewManager review1 : reviewManager.reviews) {
            lessonMonth = timetableManager.getLessonById(review1.getLessonId()).getLessonDate().substring(3, 6);
            if (review1.getTrainerId().equalsIgnoreCase(trainerId)&&lessonMonth.equalsIgnoreCase(month)) {
                totalRating += review1.getRating();
                totalLessons++;

            }
        }
        if (totalRating != 0 && totalLessons != 0) {
            avgRating = totalRating / totalLessons;
        }
        return avgRating;
    }

    //calculate learner boookings
    public List<Integer> calculateLearnerBookings(String learnerId, String month) {
        int totalBookings = 0;
        int totalCancels = 0;
        int totalAttends = 0;
        String lessonMonth;
        //create list to store learner bookings 
        List<Integer> learnerBookings = new ArrayList<>();
        for (BookingManager booking : bookingManager.bookings) {
            lessonMonth = timetableManager.getLessonById(booking.getLessonId()).getLessonDate().substring(3, 6);
            if (booking.getLearnerId().equalsIgnoreCase(learnerId) && lessonMonth.equalsIgnoreCase(month.substring(0, 3))) {
                if (booking.getStatus().equalsIgnoreCase("Booked")) {
                    totalBookings++;
                } else if (booking.getStatus().equalsIgnoreCase("Attended")) {
                    totalAttends++;
                } else if (booking.getStatus().equalsIgnoreCase("Cancelled")) {
                    totalCancels++;
                }
            }
        }
       
            learnerBookings.add(totalBookings);
            learnerBookings.add(totalAttends);
            learnerBookings.add(totalCancels);
       
        return learnerBookings;
    }

    //validation of valid month name
    public String validMonth(String selectMonth) {
        String[] months = new String[]{"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
        for (String month1 : months) {
            if (month1.equalsIgnoreCase(selectMonth)
                    || month1.substring(0, 3).equalsIgnoreCase(selectMonth)) {
                return month1;
            }
        }
        return null;
    }

}
