package observer;

import factory.Book;
import java.util.Arrays;
import java.util.List;

/**
 * Concrete Observer - Thành viên thư viện
 * Nhận thông báo về sách mới theo thể loại quan tâm
 */
public class LibraryMember implements LibraryObserver {
    private String name;
    private String memberId;
    private List<String> interestedGenres;
    
    public LibraryMember(String name, String memberId, String... genres) {
        this.name = name;
        this.memberId = memberId;
        this.interestedGenres = Arrays.asList(genres);
    }
    
    @Override
    public void onNewBook(Book book) {
        // Chỉ thông báo nếu sách thuộc thể loại quan tâm
        boolean isInterested = interestedGenres.stream()
                .anyMatch(genre -> book.getGenre().toLowerCase().contains(genre.toLowerCase()));
        
        if (isInterested) {
            System.out.println("      📧 " + name + " (ID: " + memberId + "): " +
                             "Có sách mới bạn quan tâm: \"" + book.getTitle() + "\" (" + book.getGenre() + ")");
        }
    }
    
    @Override
    public void onBookOverdue(Book book, String borrower) {
        // Thành viên chỉ nhận thông báo nếu họ là người mượn
        if (borrower.equals(name)) {
            System.out.println("      📧 " + name + ": Sách \"" + book.getTitle() + 
                             "\" của bạn đã quá hạn! Vui lòng trả lại thư viện.");
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getMemberId() {
        return memberId;
    }
    
    public List<String> getInterestedGenres() {
        return interestedGenres;
    }
}
