package decorator;

import factory.Book;

/**
 * Concrete Component - Mượn sách cơ bản
 */
public class BasicBorrow implements Borrowable {
    private Book book;
    private int baseDays;
    private double baseCost;
    
    public BasicBorrow(Book book) {
        this.book = book;
        this.baseDays = 14; // 2 tuần mặc định
        this.baseCost = 0.0; // Miễn phí mượn cơ bản
    }
    
    public BasicBorrow(Book book, int baseDays) {
        this.book = book;
        this.baseDays = baseDays;
        this.baseCost = 0.0;
    }
    
    @Override
    public Book getBook() {
        return book;
    }
    
    @Override
    public int getBorrowDays() {
        return baseDays;
    }
    
    @Override
    public String getDescription() {
        return "Mượn cơ bản: \"" + book.getTitle() + "\"";
    }
    
    @Override
    public double getCost() {
        return baseCost;
    }
}
