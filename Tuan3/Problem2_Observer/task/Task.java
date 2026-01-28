package task;

import java.util.ArrayList;
import java.util.List;

/**
 * Subject trong Observer Pattern - Đại diện cho một công việc (Task)
 * Khi trạng thái thay đổi, tất cả các thành viên nhóm sẽ được thông báo
 */
public class Task {
    private String name;
    private String status;
    private List<TaskObserver> observers;
    
    // Các trạng thái có thể của Task
    public static final String STATUS_TODO = "TODO";
    public static final String STATUS_IN_PROGRESS = "IN_PROGRESS";
    public static final String STATUS_REVIEW = "REVIEW";
    public static final String STATUS_DONE = "DONE";
    public static final String STATUS_BLOCKED = "BLOCKED";
    
    public Task(String name) {
        this.name = name;
        this.status = STATUS_TODO;
        this.observers = new ArrayList<>();
    }
    
    /**
     * Đăng ký một thành viên nhóm để nhận thông báo
     */
    public void attach(TaskObserver observer) {
        observers.add(observer);
        System.out.println("   ✅ Đã thêm thành viên vào theo dõi task: " + name);
    }
    
    /**
     * Hủy đăng ký thành viên
     */
    public void detach(TaskObserver observer) {
        observers.remove(observer);
        System.out.println("   ❌ Đã xóa thành viên khỏi theo dõi task: " + name);
    }
    
    /**
     * Thông báo cho tất cả thành viên khi có thay đổi
     */
    public void notifyObservers() {
        System.out.println("\n   📢 Thông báo đến " + observers.size() + " thành viên:");
        for (TaskObserver observer : observers) {
            observer.onTaskUpdated(name, status);
        }
    }
    
    /**
     * Cập nhật trạng thái task và thông báo cho observers
     */
    public void setStatus(String newStatus) {
        String oldStatus = this.status;
        this.status = newStatus;
        
        String emoji = getStatusEmoji(newStatus);
        System.out.println("\n   🔄 Task '" + name + "': " + oldStatus + " → " + emoji + " " + newStatus);
        
        notifyObservers();
    }
    
    private String getStatusEmoji(String status) {
        switch (status) {
            case STATUS_TODO: return "📋";
            case STATUS_IN_PROGRESS: return "🔨";
            case STATUS_REVIEW: return "👀";
            case STATUS_DONE: return "✅";
            case STATUS_BLOCKED: return "🚫";
            default: return "❓";
        }
    }
    
    public String getName() {
        return name;
    }
    
    public String getStatus() {
        return status;
    }
}
