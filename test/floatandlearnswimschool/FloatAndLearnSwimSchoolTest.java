package floatandlearnswimschool;

import java.util.List;
import java.util.Scanner;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FloatAndLearnSwimSchoolTest {

    private Controller controller;
    private BookingManager bookingManager;
    private TimetableManager timetableManager;
    private ReviewManager reviewManager;
    private Learner learner;

    public FloatAndLearnSwimSchoolTest() {
        this.controller = new Controller();
        this.bookingManager = controller.getBookingManager();
        this.timetableManager = controller.getTimetableManager();
        this.learner = controller.getLearner();
        this.reviewManager = controller.getReviewManager();
    }

    

    @Test
    // Test duplicate booking
    public void testADuplicateBooking() {
        bookingManager.learnerId = "SID01";
        String lessonId = "LID01";
        System.out.println("\nTest description : duplicate booking\n");
         System.out.println("Learner name : "+learner.getLearnerById("SID01").getLearnerName());
        System.out.println("Lesson id  : "+lessonId+"\n");
        boolean result = bookingManager.duplicateBooking(lessonId);
        if (result) {
            System.out.println("Invalid input ! This lesson is already booked");
        }
        assertFalse(result);
    }

    @Test
    // Test validBookingId
    public void testBValidBookingId() {
         bookingManager.learnerId = "SID01";
        String bookingId = "BID01";
        System.out.println("\nTest description : valid booking id\n");
        System.out.println("Booking Id : "+bookingId);
        System.out.println("Learner name : "+learner.getLearnerById("SID01").getLearnerName());
        boolean result = bookingManager.validBookingId(bookingId);
        assertFalse(result);
    }

    @Test
    // Test cancelledLesson
    public void testCancelledLesson() {
        bookingManager.learnerId = "SID02";
        String bookingId = "BID01";
        String lessonId=null;
        int expSeat=4;
        int seat=0;
        String status = null;
        String expStatus="Cancelled";
        boolean result = false;
        System.out.println("\nTest description : cancelled lesson\n");
        System.out.println("Learner name : "+learner.getLearnerById("SID02").getLearnerName());
        System.out.println("Booking Id : "+bookingId);
        
        //default booking for cancel
        BookingManager booking = new BookingManager("BID01", "SID02", "LID35", "Booked");
        bookingManager.bookings.add(booking);
        //update seat
        timetableManager.getLessonById("LID35").setSeats(timetableManager.getLessonById("LID35").getSeats()-1);
        if (bookingManager.validBookingId(bookingId)) {
            bookingManager.option = new Scanner(bookingId);
            result = bookingManager.cancelledLesson();
            status=bookingManager.getBookingsById(bookingId).getStatus();
            lessonId=bookingManager.getBookingsById(bookingId).getLessonId();
            seat=timetableManager.getLessonById(lessonId).getSeats();
        }
        assertTrue(result);
        assertEquals(status,expStatus);
        assertEquals(expSeat,seat);
    }

    @Test
    // Test attendLessons
    public void testDAttendLessons() {
        boolean result = false;
        bookingManager.learnerId = "SID01";
        String bookingId = "BID02";
        String status = null;
        String expStatus="Attended";
        int expSeat=4;
        int seat=0;
        System.out.println("\nTest description : attendeded lesson");
        System.out.println("Learner name : "+learner.getLearnerById("SID01").getLearnerName());
        System.out.println("Booking id : "+bookingId);
        //by default booking for attend lesson
        BookingManager booking = new BookingManager("BID02", "SID01", "LID01", "Booked");
        bookingManager.bookings.add(booking);
        //update seat
        timetableManager.getLessonById("LID01").setSeats(timetableManager.getLessonById("LID01").getSeats()-1);
        
        if (bookingManager.validBookingId(bookingId)) {
            bookingManager.option = new Scanner(bookingId + "\nGreat lesson!\n5");
            reviewManager.option = new Scanner("Best\n5");
            // Mocking method call
            result = bookingManager.attendLessons();
            status=bookingManager.getBookingsById(bookingId).getStatus();
            System.out.println();
            //print attend lesson
            bookingManager.selectedBookings(bookingId);
             seat=timetableManager.getLessonById("LID01").getSeats();
        }
        assertTrue(result);
        assertEquals(status,expStatus);
        assertEquals(expSeat,seat);
    }

    @Test
    //test filter timetable
    public void testFilterByDay() {
        String day = "Monday";
        System.out.println("\nTest description : filter lesson by monday");
        System.out.println("Day : "+day);
        List<Lesson> result = timetableManager.filterByDay(day);
        assertNotNull(result);
        //show filter timetabl
        timetableManager.filterLessons = result;
        timetableManager.showLessons();
    }

    @Test
    //test learner login
    public void testLearnerLogin() {
        String contact = "041235618123";
        String password = "pass01";
        System.out.println("\nTest description : learner Login");
        System.out.println("Contact : "+contact);
        System.out.println("Password : "+password);
        learner.option = new Scanner(contact+"\n"+password);
        String learnerId = learner.learnerLogin();
        assertNotNull(learnerId);

    }
}
