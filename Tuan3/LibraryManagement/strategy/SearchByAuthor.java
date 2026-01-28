package strategy;

import factory.Book;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Strategy - Tìm kiếm theo tác giả
 */
public class SearchByAuthor implements SearchStrategy {
    
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getStrategyName() {
        return "Tìm theo Tác Giả";
    }
}
