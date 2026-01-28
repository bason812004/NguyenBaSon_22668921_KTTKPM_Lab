package factory;

/**
 * Concrete Product - Sách điện tử (EBook)
 */
public class EBook extends Book {
    private double fileSize; // MB
    private String format;   // PDF, EPUB, MOBI
    private String downloadUrl;
    
    public EBook(String title, String author, String genre, double fileSize, String format) {
        super(title, author, genre);
        this.fileSize = fileSize;
        this.format = format;
        this.downloadUrl = "https://library.example.com/ebooks/" + getIsbn();
    }
    
    public EBook(String title, String author, String genre) {
        this(title, author, genre, 5.0, "PDF");
    }
    
    public double getFileSize() {
        return fileSize;
    }
    
    public String getFormat() {
        return format;
    }
    
    public String getDownloadUrl() {
        return downloadUrl;
    }
    
    @Override
    public String getType() {
        return "💻 Sách Điện Tử";
    }
    
    @Override
    public String getDescription() {
        return String.format("%s | %.1f MB | Định dạng: %s", 
                getType(), fileSize, format);
    }
}
