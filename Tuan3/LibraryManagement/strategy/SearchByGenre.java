package strategy;

import factory.Book;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Concrete Strategy - Tìm kiếm theo thể loại
 */
public class SearchByGenre implements SearchStrategy {
    
    @Override
    public List<Book> search(List<Book> books, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return books.stream()
                .filter(book -> book.getGenre().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }
    
    @Override
    public String getStrategyName() {
        return "Tìm theo Thể Loại";
    }
}
