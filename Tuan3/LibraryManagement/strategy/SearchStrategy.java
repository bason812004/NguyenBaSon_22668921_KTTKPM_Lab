package strategy;

import factory.Book;
import java.util.List;

/**
 * Strategy interface cho việc tìm kiếm sách
 */
public interface SearchStrategy {
    /**
     * Tìm kiếm sách theo từ khóa
     * @param books Danh sách sách để tìm kiếm
     * @param keyword Từ khóa tìm kiếm
     * @return Danh sách sách phù hợp
     */
    List<Book> search(List<Book> books, String keyword);
    
    /**
     * Lấy tên chiến lược tìm kiếm
     */
    String getStrategyName();
}
