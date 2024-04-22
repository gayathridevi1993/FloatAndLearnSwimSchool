package floatandlearnswimschool;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class Trainer {

    private String trainerId;
    private String fullName;
    private String email;
    private String contact;
    private String gender;
    private String dateOfBirth;
    public List<Trainer> trainers;

    public String getTrainerId() {
        return trainerId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public Trainer(String trainerId, String fullName, String email, String contact, String gender, String dateOfBirth) {
        this.trainerId = trainerId;
        this.fullName = fullName;
        this.email = email;
        this.contact = contact;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Trainer() {
        trainers = new ArrayList<>();
        addDefaultTrainers();
    }

    //add default trainers
    public void addDefaultTrainers() {
        Reader reader = null;
        try {
            //read data from text file
            reader = new FileReader("src\\floatandlearnswimschool\\Details\\trainers");
            //ignore heading
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader().withSkipHeaderRecord(true));
            for (CSVRecord record : parser) {
                //get all details
                String trainerId = record.get(0);
                String fullName = record.get(1);
                String email = record.get(2);
                String contact = record.get(3);
                String gender = record.get(4);
                String dateOfBirth = record.get(5);

                //create Trainer object
                Trainer trainer = new Trainer(trainerId, fullName, email, contact, gender, dateOfBirth);
                //add trainer details
                trainers.add(trainer);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Trainer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //show trainers
    public void showTrainers() {
        System.out.println("Trainer Details:");
        //check trainers is not empty
        if (!trainers.isEmpty()) {
            //print heading
            System.out.println("................................................................................................"
                    + "................");
            System.out.printf(": %-8s : %-20s : %-25s : %-15s : %-10s : %-15s : \n", "ID", "Full Name", "Email", "Contact", "Gender", "Date of Birth");
            System.out.println("................................................................................................"
                    + "................");
            //print details
            for (Trainer trainer : trainers) {
                System.out.printf(": %-8s : %-20s : %-25s : %-15s : %-10s : %-15s :\n", trainer.getTrainerId(), trainer.getFullName(),
                        trainer.getEmail(), trainer.getContact(), trainer.getGender(), trainer.getDateOfBirth());

            }
            System.out.println("................................................................................................"
                    + "................");
        }
    }
    
    //get Trainer by id
    public Trainer getTrainerById(String trainerId) {
        for (Trainer trainer : trainers) {
            if (trainer.getTrainerId().equals(trainerId)) {
                return trainer;
            }
        }
        // Return null if trainer id not found
        return null;
    }
    
    //trainer id exist
    public boolean trainerExist(String trainerId) {
        for (Trainer trainer : trainers) {
            if (trainer.getTrainerId().equalsIgnoreCase(trainerId)) {
                return true;
            }
        }
        return false;
    }
}
