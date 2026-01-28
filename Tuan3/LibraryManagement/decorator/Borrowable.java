package decorator;

import factory.Book;

/**
 * Component interface cho Decorator Pattern
 * Đại diện cho việc mượn sách với các tính năng bổ sung
 */
public interface Borrowable {
    /**
     * Lấy sách được mượn
     */
    Book getBook();
    
    /**
     * Lấy số ngày được mượn
     */
    int getBorrowDays();
    
    /**
     * Lấy mô tả về việc mượn sách
     */
    String getDescription();
    
    /**
     * Lấy chi phí (phí mượn, phí gia hạn, v.v.)
     */
    double getCost();
}
