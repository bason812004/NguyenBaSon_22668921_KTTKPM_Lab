package strategy;

import factory.Book;
import java.util.ArrayList;
import java.util.List;

/**
 * Context class cho Strategy Pattern
 * Sử dụng strategy để thực hiện tìm kiếm
 */
public class SearchContext {
    private SearchStrategy strategy;
    
    public SearchContext() {
        // Mặc định tìm theo tên
        this.strategy = new SearchByName();
    }
    
    public SearchContext(SearchStrategy strategy) {
        this.strategy = strategy;
    }
    
    /**
     * Thay đổi chiến lược tìm kiếm
     */
    public void setStrategy(SearchStrategy strategy) {
        this.strategy = strategy;
        System.out.println("   🔄 Đã chuyển sang chiến lược: " + strategy.getStrategyName());
    }
    
    /**
     * Thực hiện tìm kiếm với chiến lược hiện tại
     */
    public List<Book> executeSearch(List<Book> books, String keyword) {
        if (strategy == null) {
            System.out.println("   ⚠️ Chưa chọn chiến lược tìm kiếm!");
            return new ArrayList<>();
        }
        
        System.out.println("   🔍 Tìm kiếm: \"" + keyword + "\" với chiến lược: " + strategy.getStrategyName());
        return strategy.search(books, keyword);
    }
    
    /**
     * Lấy tên chiến lược hiện tại
     */
    public String getCurrentStrategyName() {
        return strategy != null ? strategy.getStrategyName() : "Chưa chọn";
    }
}
