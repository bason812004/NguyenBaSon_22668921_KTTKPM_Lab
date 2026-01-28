package ui;

/**
 * Component interface trong Composite Pattern cho UI
 * Đại diện cho các thành phần giao diện người dùng
 */
public interface UIComponent {
    /**
     * Render/hiển thị component
     */
    void render();
    
    /**
     * Lấy tên của component
     */
    String getName();
}
