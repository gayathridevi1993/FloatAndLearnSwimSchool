package floatandlearnswimschool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class TimetableManager implements Iterable<Lesson> {

    public Scanner option;
    private Learner learner;
    private BookingManager bookingManager;
    private int minimumSeats = 1;
    private int maximumSeats = 4;
    public List<Lesson> lessons;
    public List<Lesson> filterLessons;

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public void setBookingManager(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    public TimetableManager() {
        this.option = new Scanner(System.in);
        this.lessons = new ArrayList<>();
        this.filterLessons = new ArrayList<>();
        addDefaultLessons();
    }

    //read data from lesson file
    public void addDefaultLessons() {
        Reader reader = null;
        try {
            //read data from text file
            reader = new FileReader("src\\floatandlearnswimschool\\Details\\lesson");
            //ignore heading
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord(true));
            for (CSVRecord record : parser) {
                //get all details
                String lessonId = record.get(0);
                String lessonTitle = record.get(1);
                String lessonDay = record.get(2);
                String lessonDate = record.get(3);
                String lessonTime = record.get(4);
                String trainerId = record.get(5);
                String equipmentNeeded = record.get(6);
                String lessonCharges = record.get(7);
                int lessonLevel = Integer.parseInt(record.get(8));
                int seats = Integer.parseInt(record.get(9));

                //create Lesson object
                Lesson lesson = new Lesson(lessonId, lessonTitle, lessonDate, lessonDay, seats, lessonLevel, trainerId, equipmentNeeded, lessonCharges, lessonTime);
                //add lesson details
                lessons.add(lesson);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TimetableManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TimetableManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //filter timetable
    public String filterLesson(String action) {
        String filter = null;
        String preference = null;
        String selectLesson = null;
        String trainerId;
        String day;
        String level;

        //filterLesson empty
        if (filterLessons.isEmpty()) {
            filterLessons = lessons;
            showLessons();
        }
        //get input to filter timetable
        do {
            if (!action.isEmpty()) {
                System.out.println("A. Select lesson");
                System.out.println("B. Filter timetable");
                System.out.println("C. Return");
            } else {
                System.out.println("A. Filter timetable");
                System.out.println("B. Return");
            }
            //take preference from user
            System.out.print("Enter your preference : ");
            preference = option.nextLine().trim();
            //take action according to user preference
            if (preference.equalsIgnoreCase("A") && !action.isEmpty()) {
                //print message according to status
                if (action.equalsIgnoreCase("Booking")) {
                    System.out.print("Enter lesson id to book : ");
                    selectLesson = option.nextLine().trim();
                } else if (action.equalsIgnoreCase("Change")) {
                    System.out.print("Enter lesson id to chnage : ");
                    selectLesson = option.nextLine().trim();
                }
                //validaton of valid lesson selection
                if (selectLesson == null || !lessonExist(selectLesson)) {
                    System.out.println("\nInvalid input! lesson does not exist\n ");
                    selectLesson = null;
                    preference = "C";
                } else {
                    //validation of lesson level
                    if (validateLessonLevel(selectLesson)) {
                        //validation of lesson has available seat to book
                        if (availableSeats(selectLesson)) {
                            //validate user not enter duplicate booking
                            if (!bookingManager.duplicateBooking(selectLesson)) {
                                preference = "C";
                            } else {
                                System.out.println("Invalid input! Duplicate lesson ID entered.");
                                selectLesson = null;
                                preference = "C";
                            }
                        } else {
                            System.out.println("Invalid input! This lesson is fully booked.");
                            selectLesson = null;
                            preference = "C";
                        }

                    } else {
                        System.out.println("Invalid input! Lesson level doesn't match your level.");

                        selectLesson = null;
                        preference = "C";

                    }
                }
            } else if (preference.equalsIgnoreCase("B") && !action.isEmpty() || preference.equalsIgnoreCase("A")) {
                //filte timetable
                System.out.println("A. Filter by trainer");
                System.out.println("B. Filter by level");
                System.out.println("C. Filter by day");
                System.out.println("D. All lessons");
                System.out.print("Please select a filter option:");
                filter = option.nextLine().trim();
                if (filter.equalsIgnoreCase("A")) {
                    //show trainers to select 
                    new Trainer().showTrainers();
                    System.out.print("Enter trainer id to view lesson timetable : ");
                    trainerId = option.nextLine().trim();
                    if (trainerId.isEmpty() || !new Trainer().trainerExist(trainerId)) {
                        System.out.println("\nInvalid input! You entered an invalid trainer id\n ");
                    } else {
                        //filter lesson by trainer
                        filterLessons = filterByTrainer(trainerId);
                        showLessons();
                    }
                } else if (filter.equalsIgnoreCase("B")) {
                    System.out.print("Enter level to view lesson timetable : ");
                    level = option.nextLine().trim();
                    if (level.isEmpty() || !level.matches("[1-5]")) {
                        System.out.println("\nInvalid input! You entered an incorrect level\n ");
                    } else {

                        //filter lesson by trainer
                        filterLessons = filterByLevel(Integer.parseInt(level));
                        showLessons();
                    }
                } else if (filter.equalsIgnoreCase("C")) {
                    System.out.print("Enter day (Monday/Wednesday/friday/Saturday) to view lesson timetable : ");
                    day = option.nextLine().trim();
                    if (day.isEmpty() || selectDay(day) == null) {
                        System.out.println("\nInvalid input! You entered an invalid day \n ");
                    } else {
                        //filter lesson by day
                        filterLessons = filterByDay(selectDay(day));
                        showLessons();
                    }
                } else if (filter.equalsIgnoreCase("D")) {
                    filterLessons=lessons;
                    showLessons();

                } else {
                    System.out.println("\nInvalid input! You enter an invalid option\n ");
                }
            } else if (preference.equalsIgnoreCase("C") && !action.isEmpty() || preference.equalsIgnoreCase("B")) {
                return null;

            } else {
                System.out.println("\nInvalid input! You enter an invalid option\n ");

            }
        } while (!preference.equalsIgnoreCase("C"));
        return selectLesson;

    }

    //show timetable
    public void showLessons() {
        System.out.println("Timetable Details:");
        //validation filterlesson not empty
        if (!filterLessons.isEmpty()) {
            System.out.println("...................................................................................................."
                    + ".................................................................");
            System.out.printf(": %-10s : %-20s : %-12s : %-12s : %-12s : %-15s : %-25s : %-12s : %-8s : %-8s : \n",
                    "Lesson ID", "Lesson Title", "Day", "Date", "Time", "Trainer Name", "Equipment Needed", "Fees", "Level", "Capacity");
            System.out.println("...................................................................................................."
                    + ".................................................................");
            //print details
            for (Lesson lesson : filterLessons) {
                System.out.printf(": %-10s : %-20s : %-12s : %-12s : %-12s : %-15s : %-25s : %-12s : %-8s : %-8s : \n",
                        lesson.getLessonId(), lesson.getLessonTitle(), lesson.getLessonDay(),
                        lesson.getLessonDate(), lesson.getLessonTime(), new Trainer().getTrainerById(lesson.getTrainerId()).getFullName(),
                        lesson.getEquipmentNeeded(), lesson.getLessonPrice(), " Level " + lesson.getLessonLevel(), lesson.getSeats());
            }
            System.out.println("...................................................................................................."
                    + ".................................................................");
        }
    }

    // Filter by day
    public List<Lesson> filterByDay(String day) {
        List<Lesson> filteredLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getLessonDay().equalsIgnoreCase(day)) {
                filteredLessons.add(lesson);
            }
        }
        //return filter lesson
        return filteredLessons;

    }

    // Filter by grade level
    public List<Lesson> filterByLevel(int gradeLevel) {
        List<Lesson> filteredLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getLessonLevel() == gradeLevel) {
                filteredLessons.add(lesson);
            }
        }
        //return filter lesson
        return filteredLessons;
    }

    // Filter by trainer
    public List<Lesson> filterByTrainer(String trainer) {
        List<Lesson> filteredLessons = new ArrayList<>();
        for (Lesson lesson : lessons) {
            if (lesson.getTrainerId().equalsIgnoreCase(trainer)) {
                filteredLessons.add(lesson);
            }
        }
        //return filter lesson
        return filteredLessons;
    }

    //get lesson details by id
    public Lesson getLessonById(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equalsIgnoreCase(lessonId)) {
                return lesson;
            }
        }
        //return null object if lesson id not validate
        return null;
    }

    //validation of lesson exist
    public boolean lessonExist(String lessonId) {
        for (Lesson lesson : lessons) {
            if (lesson.getLessonId().equalsIgnoreCase(lessonId)) {
                return true;
            }
        }
        return false;
    }

    //validation of lesson level
    public boolean validateLessonLevel(String lessonId) {
        //get learner id from booking manager class
        String learnerId = bookingManager.getLearnerId();
        //get learner level by learner id
        int learnerLevel = learner.getLearnerById(learnerId).getLearnerLevel();
        //get lesson level by id
        int lessonLevel = getLessonById(lessonId).getLessonLevel();
        //return validatio result of lesson level and learner level
        return learnerLevel == lessonLevel || (learnerLevel + 1) == lessonLevel;
    }

    //validation clas have capacity to book lesson
    public boolean availableSeats(String lessonId) {
        int lessonSeat = getLessonById(lessonId).getSeats();
        //validation lesson seat not greater than 4 and lesson than 1
        return lessonSeat >= minimumSeats && lessonSeat <= maximumSeats;
    }

    //select day
    public String selectDay(String selectDay) {
        String[] days = {"Monday", "Wednesday", "Friday", "Saturday"};
        for (String day : days) {
            if (day.equalsIgnoreCase(selectDay) || day.substring(0, 3).equalsIgnoreCase(selectDay)) {
                return day;
            }
        }
        return null;
    }

    @Override
    public Iterator<Lesson> iterator() {
        return new TimetableIterator();
    }

    //create inner class
    private class TimetableIterator implements Iterator<Lesson> {

        private int currentIndex;

        public TimetableIterator() {
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < lessons.size();
        }

        @Override
        public Lesson next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more elements in the timetable.");
            }
            return lessons.get(currentIndex++);
        }
    }
}
