package floatandlearnswimschool;

public class Lesson {
    // Private attributes
    private String lessonId;
    private String lessonTitle;
    private String lessonDate;
    private String lessonDay;
    private int vacancies;
    private int lessonLevel;
    private String trainerId;
    private String equipmentNeeded;
    private String lessonPrice;
    private String lessonTime;

    // Constructor
    public Lesson(String lessonId, String lessonTitle, String lessonDate, String lessonDay, int vacancies,
            int lessonLevel, String trainerId, String equipmentNeeded, String lessonPrice, String lessonTime) {
        // Initialize attributes with provided values
        this.lessonId = lessonId;
        this.lessonTitle = lessonTitle;
        this.lessonDate = lessonDate;
        this.lessonDay = lessonDay;
        this.vacancies = vacancies;
        this.lessonLevel = lessonLevel;
        this.trainerId = trainerId;
        this.equipmentNeeded = equipmentNeeded;
        this.lessonPrice = lessonPrice;
        this.lessonTime = lessonTime;
    }

    // Getter and setter methods
    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonTitle() {
        return lessonTitle;
    }

    public void setLessonTitle(String lessonTitle) {
        this.lessonTitle = lessonTitle;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getLessonDay() {
        return lessonDay;
    }

    public void setLessonDay(String lessonDay) {
        this.lessonDay = lessonDay;
    }

    public int getSeats() {
        return vacancies;
    }

    public void setSeats(int vacancies) {
        this.vacancies = vacancies;
    }

    public int getLessonLevel() {
        return lessonLevel;
    }

    public void setLessonLevel(int lessonLevel) {
        this.lessonLevel = lessonLevel;
    }

    public String getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(String trainerId) {
        this.trainerId = trainerId;
    }

    public String getEquipmentNeeded() {
        return equipmentNeeded;
    }

    public void setEquipmentNeeded(String equipmentNeeded) {
        this.equipmentNeeded = equipmentNeeded;
    }

    public String getLessonPrice() {
        return lessonPrice;
    }

    public void setLessonPrice(String lessonPrice) {
        this.lessonPrice = lessonPrice;
    }

    public String getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(String lessonTime) {
        this.lessonTime = lessonTime;
    }

}
