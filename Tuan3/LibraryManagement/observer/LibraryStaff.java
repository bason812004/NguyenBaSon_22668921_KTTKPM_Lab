package observer;

import factory.Book;

/**
 * Concrete Observer - Nhân viên thư viện
 * Nhận thông báo về tất cả sách mới và sách quá hạn
 */
public class LibraryStaff implements LibraryObserver {
    private String name;
    private String position;
    
    public LibraryStaff(String name, String position) {
        this.name = name;
        this.position = position;
    }
    
    @Override
    public void onNewBook(Book book) {
        System.out.println("      👤 " + name + " (" + position + "): Đã ghi nhận sách mới - " + book.getTitle());
    }
    
    @Override
    public void onBookOverdue(Book book, String borrower) {
        System.out.println("      ⚠️ " + name + " (" + position + "): Sách \"" + book.getTitle() + 
                          "\" quá hạn! Người mượn: " + borrower);
    }
    
    public String getName() {
        return name;
    }
    
    public String getPosition() {
        return position;
    }
}
