package task;

/**
 * Observer interface cho Task Observer Pattern
 * Các thành viên nhóm sẽ implement interface này để nhận thông báo
 */
public interface TaskObserver {
    /**
     * Được gọi khi trạng thái task thay đổi
     * @param taskName Tên task
     * @param status Trạng thái mới
     */
    void onTaskUpdated(String taskName, String status);
}
