package factory;

/**
 * Concrete Product - Sách giấy
 */
public class PaperBook extends Book {
    private int pages;
    private String publisher;
    private int publishYear;
    
    public PaperBook(String title, String author, String genre, int pages, String publisher, int publishYear) {
        super(title, author, genre);
        this.pages = pages;
        this.publisher = publisher;
        this.publishYear = publishYear;
    }
    
    public PaperBook(String title, String author, String genre) {
        this(title, author, genre, 200, "NXB Giáo Dục", 2023);
    }
    
    public int getPages() {
        return pages;
    }
    
    public String getPublisher() {
        return publisher;
    }
    
    public int getPublishYear() {
        return publishYear;
    }
    
    @Override
    public String getType() {
        return "📖 Sách Giấy";
    }
    
    @Override
    public String getDescription() {
        return String.format("%s | %d trang | NXB: %s | Năm: %d", 
                getType(), pages, publisher, publishYear);
    }
}
