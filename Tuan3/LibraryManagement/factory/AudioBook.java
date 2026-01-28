package factory;

/**
 * Concrete Product - Sách nói (AudioBook)
 */
public class AudioBook extends Book {
    private int durationMinutes;
    private String narrator;
    private String language;
    
    public AudioBook(String title, String author, String genre, int durationMinutes, String narrator) {
        super(title, author, genre);
        this.durationMinutes = durationMinutes;
        this.narrator = narrator;
        this.language = "Tiếng Việt";
    }
    
    public AudioBook(String title, String author, String genre) {
        this(title, author, genre, 300, "Narrator AI");
    }
    
    public int getDurationMinutes() {
        return durationMinutes;
    }
    
    public String getDurationFormatted() {
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        return String.format("%dh %dm", hours, minutes);
    }
    
    public String getNarrator() {
        return narrator;
    }
    
    public String getLanguage() {
        return language;
    }
    
    @Override
    public String getType() {
        return "🎧 Sách Nói";
    }
    
    @Override
    public String getDescription() {
        return String.format("%s | Thời lượng: %s | Người đọc: %s", 
                getType(), getDurationFormatted(), narrator);
    }
}
