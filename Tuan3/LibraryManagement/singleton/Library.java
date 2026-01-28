package singleton;

import factory.Book;
import observer.LibraryObserver;
import strategy.SearchContext;
import strategy.SearchStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton Pattern - Quản lý thư viện
 * Đảm bảo chỉ có một instance duy nhất của Library trong hệ thống
 */
public class Library {
    // Instance duy nhất (Singleton)
    private static Library instance;
    
    // Danh sách sách trong thư viện
    private List<Book> books;
    
    // Danh sách observers (Observer Pattern)
    private List<LibraryObserver> observers;
    
    // Context tìm kiếm (Strategy Pattern)
    private SearchContext searchContext;
    
    /**
     * Private constructor - ngăn tạo instance từ bên ngoài
     */
    private Library() {
        this.books = new ArrayList<>();
        this.observers = new ArrayList<>();
        this.searchContext = new SearchContext();
        System.out.println("   📚 Library được khởi tạo (Singleton)");
    }
    
    /**
     * Lấy instance duy nhất của Library (Thread-safe với double-checked locking)
     */
    public static Library getInstance() {
        if (instance == null) {
            synchronized (Library.class) {
                if (instance == null) {
                    instance = new Library();
                }
            }
        }
        return instance;
    }
    
    // ===================== BOOK MANAGEMENT =====================
    
    /**
     * Thêm sách mới và thông báo cho observers
     */
    public void addBook(Book book) {
        books.add(book);
        System.out.println("   ➕ Đã thêm sách: " + book.getTitle());
        notifyNewBook(book);
    }
    
    /**
     * Xóa sách khỏi thư viện
     */
    public void removeBook(Book book) {
        if (books.remove(book)) {
            System.out.println("   ➖ Đã xóa sách: " + book.getTitle());
        }
    }
    
    /**
     * Lấy danh sách tất cả sách
     */
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }
    
    /**
     * Lấy số lượng sách
     */
    public int getBookCount() {
        return books.size();
    }
    
    // ===================== OBSERVER PATTERN =====================
    
    /**
     * Đăng ký observer
     */
    public void attach(LibraryObserver observer) {
        observers.add(observer);
    }
    
    /**
     * Hủy đăng ký observer
     */
    public void detach(LibraryObserver observer) {
        observers.remove(observer);
    }
    
    /**
     * Thông báo khi có sách mới
     */
    public void notifyNewBook(Book book) {
        System.out.println("\n   📢 Thông báo sách mới đến " + observers.size() + " người theo dõi:");
        for (LibraryObserver observer : observers) {
            observer.onNewBook(book);
        }
    }
    
    /**
     * Thông báo khi sách quá hạn
     */
    public void notifyOverdue(Book book, String borrower) {
        System.out.println("\n   📢 Thông báo sách quá hạn:");
        for (LibraryObserver observer : observers) {
            observer.onBookOverdue(book, borrower);
        }
    }
    
    // ===================== STRATEGY PATTERN =====================
    
    /**
     * Thiết lập chiến lược tìm kiếm
     */
    public void setSearchStrategy(SearchStrategy strategy) {
        searchContext.setStrategy(strategy);
    }
    
    /**
     * Tìm kiếm sách với chiến lược hiện tại
     */
    public List<Book> searchBooks(String keyword) {
        return searchContext.executeSearch(books, keyword);
    }
    
    /**
     * Hiển thị tất cả sách trong thư viện
     */
    public void displayAllBooks() {
        System.out.println("\n   📚 Danh sách sách trong thư viện (" + books.size() + " cuốn):");
        System.out.println("   ─────────────────────────────────────────────────────");
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println("   " + (i + 1) + ". " + book.getDescription());
            System.out.println("      Tiêu đề: " + book.getTitle());
            System.out.println("      Tác giả: " + book.getAuthor());
            System.out.println("      Thể loại: " + book.getGenre());
            System.out.println();
        }
    }
    
    /**
     * Reset library (chỉ dùng cho testing)
     */
    public void reset() {
        books.clear();
        observers.clear();
        searchContext = new SearchContext();
    }
}
