package floatandlearnswimschool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Learner {

    private BookingManager bookingManager;
    public Scanner option;
    private String learnerId;
    private String learnerName;
    private String learnerEmail;
    private int learnerAge;
    private int learnerLevel;
    private String learnerContact;
    private String learnerPassword;
    private String learnerGender;
    public List<Learner> learners;

    /**
     * @return the learnerId
     */
    public String getLearnerId() {
        return learnerId;
    }

    /**
     * @param learnerId the learnerId to set
     */
    public void setLearnerId(String learnerId) {
        this.learnerId = learnerId;
    }

    /**
     * @return the learnerName
     */
    public String getLearnerName() {
        return learnerName;
    }

    /**
     * @param learnerName the learnerName to set
     */
    public void setLearnerName(String learnerName) {
        this.learnerName = learnerName;
    }

    /**
     * @return the learnerEmail
     */
    public String getLearnerEmail() {
        return learnerEmail;
    }

    /**
     * @param learnerEmail the learnerEmail to set
     */
    public void setLearnerEmail(String learnerEmail) {
        this.learnerEmail = learnerEmail;
    }

    /**
     * @return the learnerAge
     */
    public int getLearnerAge() {
        return learnerAge;
    }

    /**
     * @param learnerAge the learnerAge to set
     */
    public void setLearnerAge(int learnerAge) {
        this.learnerAge = learnerAge;
    }

    /**
     * @return the learnerLevel
     */
    public int getLearnerLevel() {
        return learnerLevel;
    }

    /**
     * @param learnerLevel the learnerLevel to set
     */
    public void setLearnerLevel(int learnerLevel) {
        this.learnerLevel = learnerLevel;
    }

    /**
     * @return the learnerContact
     */
    public String getLearnerContact() {
        return learnerContact;
    }

    /**
     * @param learnerContact the learnerContact to set
     */
    public void setLearnerContact(String learnerContact) {
        this.learnerContact = learnerContact;
    }

    /**
     * @return the learnerPassword
     */
    public String getLearnerPassword() {
        return learnerPassword;
    }

    /**
     * @param learnerPassword the learnerPassword to set
     */
    public void setLearnerPassword(String learnerPassword) {
        this.learnerPassword = learnerPassword;
    }

    /**
     * @return the learnerGender
     */
    public String getLearnerGender() {
        return learnerGender;
    }

    /**
     * @param learnerGender the learnerGender to set
     */
    public void setLearnerGender(String learnerGender) {
        this.learnerGender = learnerGender;
    }

    public void setBooking(BookingManager bookingManager) {
        this.bookingManager = bookingManager;
    }

    //create constructor to add in list
    public Learner(String learnerId, String learnerName, String learnerEmail, int learnerAge, int learnerLevel, String learnerContact, String learnerPassword, String learnerGender) {
        this.learnerId = learnerId;
        this.learnerName = learnerName;
        this.learnerEmail = learnerEmail;
        this.learnerAge = learnerAge;
        this.learnerLevel = learnerLevel;
        this.learnerContact = learnerContact;
        this.learnerPassword = learnerPassword;
        this.learnerGender = learnerGender;
    }

    //create constructor
    public Learner() {
        this.learners = new ArrayList<>();
        option = new Scanner(System.in);
        addDefaultLearners();
    }

    //add defalt learners
    public void addDefaultLearners() {
        Reader reader = null;
        try {
            //read data from text file
            reader = new FileReader("src\\floatandlearnswimschool\\Details\\learners");
            //ignore heading
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord(true));
            for (CSVRecord record : parser) {
                //get all details
                String learnerId = record.get(0);
                String learnerName = record.get(1);
                String learnerEmail = record.get(2);
                int learnerAge = Integer.parseInt(record.get(3));
                String learnerContact = record.get(4);
                String learnerPassword = record.get(5);
                String learnerGender = record.get(6);
                int learnerLevel = Integer.parseInt(record.get(7));
                //save learners details
                saveLearners(learnerId, learnerName, learnerEmail, learnerAge, learnerLevel, learnerContact, learnerPassword, learnerGender);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //save learner details
    public boolean saveLearners(String learnerId,String learnerName, String learnerEmail, int learnerAge, int learnerLevel, String learnerContact, String learnerPassword, String learnerGender) {
       //set learner id if null
        if(learnerId.isEmpty()){
           learnerId = "LID" + (learners.size() + 1);
       }
        //create Learner object
        Learner learner = new Learner(learnerId, learnerName, learnerEmail, learnerAge, learnerLevel, learnerContact, learnerPassword, learnerGender);
        //add learner details
        learners.add(learner);
        //validation of learner name add in learners list and return
        return learners.get(learners.size()-1).getLearnerName().equalsIgnoreCase(learnerName);

    }

    //show learners details
    public void showLearners() {
        System.out.println("\nLearner Details:");
        //check learners is not empty
        if (!learners.isEmpty()) {
            //print heading
            System.out.println(".........................................................................................................."
                    + "................");
            System.out.printf(": %-8s : %-20s : %-25s : %-15s : %-10s : %-10s : %-10s : \n", "Learner Id", "Full Name", "Email", "Contact", "Gender", "Age", "Level");
            System.out.println(".........................................................................................................."
                    + "................");
            //print details
            for (Learner learner : learners) {
                System.out.printf(": %-10s : %-20s : %-25s : %-15s : %-10s : %-10s : %-10s : \n", learner.getLearnerId(), learner.getLearnerName(),
                        learner.getLearnerEmail(), learner.getLearnerContact(), learner.getLearnerGender(), learner.getLearnerAge(), " Level " + learner.getLearnerLevel());

            }
            System.out.println(".........................................................................................................."
                    + "................");
        }
    }
    
    //learner profile
    public void learnerProfile(String learnerId) {
        System.out.println("\n.................................");
        System.out.println("\tMy Profile");
        System.out.println(".................................");
        for (Learner learner : learners) {
            if (learner.getLearnerId().equalsIgnoreCase(learnerId)) {
                System.out.println("\nId      : " + learner.getLearnerId());
                System.out.println("Name    : " + learner.getLearnerName());
                System.out.println("Level   : " + learner.getLearnerLevel());
                System.out.println("Email   : " + learner.getLearnerEmail());
                System.out.println("Contact : " + learner.getLearnerContact());
                System.out.println("Age     : " + learner.getLearnerAge());
                System.out.println("Gender : " + learner.getLearnerGender());

            }
        }
        System.out.println(".................................\n");
    }

    //learner login
    public String learnerLogin() {
        String contact;
        String password;
        String learnerId = null;
        System.out.println("\n\tLearner Login");
        System.out.println(".................................\n");
        //get email from user
        System.out.print("Enter Contact : ");
        contact = option.nextLine().trim();
        //validation of email id
        if (contact.isEmpty() || !contact.matches("\\d+")) {
            System.out.println("Invalid input ! incorrect email id ");
            return null;
        } else {
            //get email from user
            System.out.print("Enter Password : ");
            password = option.nextLine().trim();
            //validation password is not empty
            if (password.isEmpty()) {
                System.out.println("Invalid input ! incorrect password ");
            } else {
                for (Learner learner : learners) {
                    if (learner.getLearnerContact().equalsIgnoreCase(contact) && learner.getLearnerPassword().equalsIgnoreCase(password)) {
                        learnerId = learner.getLearnerId();
                    }
                }
            }
        }
        //print message if login successfull
        if (learnerId != null) {
            System.out.println("\nLearner login successfully\n");
            return learnerId;
        }else{
            //print message if incorrect contact or password
            System.out.println("Invalid input ! incorrect contact or password ");
             return null;
        }
    }
    
    //get learner details by id
    public Learner getLearnerById(String learnerId) {
        for (Learner learner : learners) {
            if (learner.getLearnerId().equals(learnerId)) {
                return learner;
            }
        }
        // Return null if learner id not found
        return null;
    }

    //validation of duplicate name
    public boolean duplicateName(String name) {
        for (Learner learner : learners) {
            if (learner.getLearnerName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        //return false if don't fint duplicate name
        return false;
    }

    //validation of duplicate contact
    public boolean duplicateContact(String contact) {
        for (Learner learner : learners) {
            if (learner.getLearnerContact().equalsIgnoreCase(contact)) {
                return true;
            }
        }
        //return false if don't fint duplicate contact
        return false;
    }

    //validation of duplicate email
    public boolean duplicateEmail(String email) {
        for (Learner learner : learners) {
            if (learner.getLearnerEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        //return false if don't fint duplicate email
        return false;
    }

}
