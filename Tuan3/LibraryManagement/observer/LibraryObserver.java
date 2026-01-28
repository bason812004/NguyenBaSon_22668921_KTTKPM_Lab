package observer;

import factory.Book;

/**
 * Observer interface cho Library Observer Pattern
 * Các đối tượng quan tâm (nhân viên, thành viên) sẽ implement interface này
 */
public interface LibraryObserver {
    /**
     * Thông báo khi có sách mới
     */
    void onNewBook(Book book);
    
    /**
     * Thông báo khi sách quá hạn mượn
     */
    void onBookOverdue(Book book, String borrower);
}
