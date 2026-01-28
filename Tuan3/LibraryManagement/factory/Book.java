package factory;

/**
 * Abstract Product trong Factory Method Pattern
 * Lớp trừu tượng đại diện cho các loại sách trong thư viện
 */
public abstract class Book {
    protected String title;
    protected String author;
    protected String genre;
    protected String isbn;
    
    public Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = generateISBN();
    }
    
    private String generateISBN() {
        return "ISBN-" + System.currentTimeMillis() % 1000000;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public String getIsbn() {
        return isbn;
    }
    
    /**
     * Mô tả chi tiết về sách - mỗi loại sách sẽ có mô tả khác nhau
     */
    public abstract String getDescription();
    
    /**
     * Lấy loại sách
     */
    public abstract String getType();
    
    @Override
    public String toString() {
        return getType() + ": " + title + " - " + author + " (" + genre + ")";
    }
}
