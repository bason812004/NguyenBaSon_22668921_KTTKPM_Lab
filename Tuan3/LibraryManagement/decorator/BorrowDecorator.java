package decorator;

import factory.Book;

/**
 * Abstract Decorator - Lớp cơ sở cho các decorator
 */
public abstract class BorrowDecorator implements Borrowable {
    protected Borrowable wrapped;
    
    public BorrowDecorator(Borrowable wrapped) {
        this.wrapped = wrapped;
    }
    
    @Override
    public Book getBook() {
        return wrapped.getBook();
    }
    
    @Override
    public int getBorrowDays() {
        return wrapped.getBorrowDays();
    }
    
    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }
    
    @Override
    public double getCost() {
        return wrapped.getCost();
    }
}
