package filesystem;

/**
 * Component interface trong Composite Pattern
 * Đại diện cho cả File và Folder trong hệ thống quản lý thư mục
 */
public interface FileSystemComponent {
    /**
     * Lấy tên của component
     */
    String getName();
    
    /**
     * Lấy kích thước của component (bytes)
     * Với File: trả về kích thước file
     * Với Folder: trả về tổng kích thước các thành phần con
     */
    long getSize();
    
    /**
     * Hiển thị thông tin component với định dạng thụt đầu dòng
     * @param indent Chuỗi thụt đầu dòng để hiển thị cấu trúc cây
     */
    void display(String indent);
}
