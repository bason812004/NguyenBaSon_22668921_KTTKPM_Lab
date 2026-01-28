package factory;

/**
 * Factory Method Pattern - Tạo các loại sách khác nhau
 */
public class BookFactory {
    
    // Các loại sách hỗ trợ
    public static final String TYPE_PAPER = "PAPER";
    public static final String TYPE_EBOOK = "EBOOK";
    public static final String TYPE_AUDIO = "AUDIO";
    
    /**
     * Factory Method - Tạo sách dựa trên loại
     */
    public static Book createBook(String type, String title, String author, String genre) {
        if (type == null) {
            throw new IllegalArgumentException("Loại sách không được null");
        }
        
        switch (type.toUpperCase()) {
            case TYPE_PAPER:
                return new PaperBook(title, author, genre);
            case TYPE_EBOOK:
                return new EBook(title, author, genre);
            case TYPE_AUDIO:
                return new AudioBook(title, author, genre);
            default:
                throw new IllegalArgumentException("Loại sách không hợp lệ: " + type);
        }
    }
    
    /**
     * Tạo sách giấy với đầy đủ thông tin
     */
    public static PaperBook createPaperBook(String title, String author, String genre, 
                                            int pages, String publisher, int publishYear) {
        return new PaperBook(title, author, genre, pages, publisher, publishYear);
    }
    
    /**
     * Tạo sách điện tử với đầy đủ thông tin
     */
    public static EBook createEBook(String title, String author, String genre, 
                                    double fileSize, String format) {
        return new EBook(title, author, genre, fileSize, format);
    }
    
    /**
     * Tạo sách nói với đầy đủ thông tin
     */
    public static AudioBook createAudioBook(String title, String author, String genre, 
                                            int durationMinutes, String narrator) {
        return new AudioBook(title, author, genre, durationMinutes, narrator);
    }
}
