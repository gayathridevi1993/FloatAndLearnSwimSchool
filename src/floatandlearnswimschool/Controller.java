package floatandlearnswimschool;

import java.util.Scanner;

public class Controller {

    public Scanner option;
    private Learner learner;
    private Trainer trainer;
    private ReviewManager reviewManager;
    private BookingManager bookingManager;
    private ReportManager reportManager;
    private TimetableManager timetableManager;

    public Scanner getOption() {
        return option;
    }

    public Learner getLearner() {
        return learner;
    }

    public Trainer getTrainer() {
        return trainer;
    }

    public ReviewManager getReviewManager() {
        return reviewManager;
    }

    public BookingManager getBookingManager() {
        return bookingManager;
    }

    public ReportManager getReportManager() {
        return reportManager;
    }

    public TimetableManager getTimetableManager() {
        return timetableManager;
    }

    public Controller() {
        option = new Scanner(System.in);
        learner = new Learner();
        bookingManager = new BookingManager();
        timetableManager = new TimetableManager();
        trainer = new Trainer();
        reviewManager = new ReviewManager();
        reportManager = new ReportManager();
        bookingManager.setLearner(learner);
        bookingManager.setReviewManager(reviewManager);
        reviewManager.setBookingManager(bookingManager);
        reviewManager.setLearner(learner);
        reviewManager.setTimetableManager(timetableManager);
        learner.setBooking(bookingManager);
        bookingManager.setTimetableManager(timetableManager);
        reportManager.setBookingManager(bookingManager);
        reportManager.setLearner(learner);
        reportManager.setTimetableManager(timetableManager);
        reportManager.setReviewManager(reviewManager);
        timetableManager.setBookingManager(bookingManager);
        timetableManager.setLearner(learner);

    }

    //main menu
    public void mainMenu() {
        String preference;
        System.out.println("\n.................................");
        System.out.println("\tController Menu");
        System.out.println(".................................");
        do {
            //show details to select preference
            System.out.println("\nA. Manage Learners");
            System.out.println("B. View All Bookings");
            System.out.println("C. View All Timetable");
            System.out.println("D. View All Reviews");
            System.out.println("E. Join a Swim Lesson");
            System.out.println("F. Generate Reports");
            System.out.println("G. Exit");
            System.out.print("Enter your preference : ");
            preference = option.nextLine().trim();
            if (preference.equalsIgnoreCase("A")) {
                manageLearners();
            } else if (preference.equalsIgnoreCase("B")) {
                //show bookings 
                bookingManager.showBookings("");
            } else if (preference.equalsIgnoreCase("C")) {
                //show all time
                timetableManager.filterLessons = timetableManager.lessons;
                timetableManager.showLessons();
                timetableManager.filterLesson("");
            } else if (preference.equalsIgnoreCase("D")) {
                reviewManager.showReviews("", "", "");
            } else if (preference.equalsIgnoreCase("E")) {
                bookingManager.bookingMenu();
            } else if (preference.equalsIgnoreCase("F")) {
               reportManager.filterReport();
            } else if (preference.equalsIgnoreCase("G")) {
                System.out.println("Exiting the application. Thank you for using our system!");
            } else {
                System.out.println("* Invalid choice. Please enter a valid option (A-G).");
            }
        } while (!preference.equalsIgnoreCase("G"));

    }

    //manager learner
    public void manageLearners() {
        String preference1;
        do {
            //show heading
            System.out.println("\n\tManage Learner");
            System.out.println(".................................\n");
            //show option to select
            System.out.println("A. View all learners");
            System.out.println("B. Add new Learners");
            System.out.println("C. Return");
            System.out.print("Enter your preference : ");
            preference1 = option.nextLine().trim();
            //call functionality according to user enter preferece
            if (preference1.equalsIgnoreCase("A")) {
                learner.showLearners();
            } else if (preference1.equalsIgnoreCase("B")) {
                addNewLearner();
            } else if (preference1.equalsIgnoreCase("C")) {
                return;
            } else {
                System.out.println("Invalid input ! you entered an incorrect preference");
            }
        } while (!preference1.equalsIgnoreCase("C"));
    }

    //add new student
    public void addNewLearner() {
        String learnerName;
        String learnerEmail;
        String learnerContact;
        String learnerGender;
        String learnerPassword;
        String age;
        int learnerAge;
        String level;
        int learnerLevel;
        System.out.println("\n\tAdd new learner");
        System.out.println(".................................\n");
        //get name from user
        System.out.print("Enter learner name : ");
        learnerName = option.nextLine().trim();
        //validation name is not empty
        if (learnerName.isEmpty()) {
            System.out.println("Invalid input ! name field cannot be empty");
            return;
        } else {
            //valiation of valid name
            if (learnerName.matches("[0-9]+")) {
                System.out.println("Invalid input ! you entered an incorrect learner name");
                return;
            } else {
                //validation of duplicate name
                if (learner.duplicateName(learnerName)) {
                    System.out.println("Invalid input! This learner name already exists.");
                    return;
                }
            }
        }
        //get email from user
        System.out.print("Enter learner email : ");
        learnerEmail = option.nextLine().trim();
        //validation email is not empty
        if (learnerEmail.isEmpty()) {
            System.out.println("Invalid input ! email field cannot be empty");
            return;
        } else {
            //validation of valid email
            if (!learnerEmail.matches("^(.+)@(\\S+)$")) {
                System.out.println("Invalid input ! you entered an incorrect learner email");
                return;
            } else {
                //validation of duplicate name
                if (learner.duplicateEmail(learnerEmail)) {
                    System.out.println("Invalid input ! entered learner email id already exist");
                    return;
                }
            }
        }
        //get contact from user
        System.out.print("Enter learner contact : ");
        learnerContact = option.nextLine().trim();
        //validation of contact not empty
        if (learnerContact.isEmpty()) {
            System.out.println("Invalid input ! contact field cannot be empty");
            return;
        } else {
            //validation of valid cona
            if (!learnerContact.matches("[0-9]+")) {
                System.out.println("Invalid input ! you entered an incorrect learner contact");
                return;
            } else {
                if (learner.duplicateContact(learnerContact)) {
                    System.out.println("Invalid input ! entered learner contact already exist");
                    return;
                }
            }
        }
        //show gender to select
        System.out.println("A. Male");
        System.out.println("B. Female");
        System.out.print("Select one learner gender : ");
        learnerGender = option.nextLine().trim();
        //validation of gender is not empty
        if (learnerGender.isEmpty()) {
            System.out.println("Invalid input ! contact field cannot be empty");
            return;
        } else {
            //set gender according to selection
            if (learnerGender.equalsIgnoreCase("A")) {
                learnerGender = "Male";
            } else if (learnerGender.equalsIgnoreCase("B")) {
                learnerGender = "Female";
            } else {
                System.out.println("Invalid input ! you entered an incorrect learner gender");
                return;
            }
        }
        //get password from user
        System.out.print("Enter learner password : ");
        learnerPassword = option.nextLine().trim();
        //validation of passw
        if (learnerPassword.isEmpty()) {
            System.out.println("Invalid input ! contact field cannot be empty");
            return;
        }
        System.out.print("Enter learner level (1 to 5 ) : ");
        level = option.nextLine().trim();
        if (level.isEmpty()) {
            System.out.println("Invalid input ! level field cannot be empty");
            return;
        } else {
            if (!level.matches("[1-5]")) {
                System.out.println("Invalid input ! learner level should be in between 1 to 5");
                return;
            } else {
                learnerLevel = Integer.parseInt(level);
            }
        }
        System.out.print("Enter learner age ( 4 to 11): ");
        age = option.nextLine().trim();
        if (age.isEmpty()) {
            System.out.println("Invalid input ! age field cannot be empty");
            return;
        } else {
            if (!age.matches("[0-9]+")) {
                System.out.println("Invalid input ! you entered an incorrect learner age");
                return;
            } else {
                learnerAge = Integer.parseInt(age);
                if (!(learnerAge >= 4 && learnerAge <= 11)) {
                    System.out.println("Invalid input ! learner age should be in between 4 to 11");
                    return;
                }
            }
        }
        //save new learners details in list
        boolean addNewLearner = learner.saveLearners("", learnerName, learnerEmail, learnerAge, learnerLevel, learnerContact, learnerPassword, learnerGender);
        if (addNewLearner) {
            System.out.println("\nLearner added successfully");
        }

    }

}
