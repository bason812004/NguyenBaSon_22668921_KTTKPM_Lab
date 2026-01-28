package filesystem;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite trong Composite Pattern
 * Đại diện cho một thư mục có thể chứa các File và Folder khác
 */
public class Folder implements FileSystemComponent {
    private String name;
    private List<FileSystemComponent> children;
    
    public Folder(String name) {
        this.name = name;
        this.children = new ArrayList<>();
    }
    
    /**
     * Thêm một component con vào thư mục
     */
    public void add(FileSystemComponent component) {
        children.add(component);
    }
    
    /**
     * Xóa một component con khỏi thư mục
     */
    public void remove(FileSystemComponent component) {
        children.remove(component);
    }
    
    /**
     * Lấy danh sách các component con
     */
    public List<FileSystemComponent> getChildren() {
        return children;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public long getSize() {
        // Tính tổng kích thước của tất cả các thành phần con
        long totalSize = 0;
        for (FileSystemComponent child : children) {
            totalSize += child.getSize();
        }
        return totalSize;
    }
    
    @Override
    public void display(String indent) {
        System.out.println(indent + "📁 " + name + " (" + getSize() + " bytes)");
        for (FileSystemComponent child : children) {
            child.display(indent + "    ");
        }
    }
}
