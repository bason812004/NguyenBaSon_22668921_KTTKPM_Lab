package task;

/**
 * Concrete Observer - Thành viên nhóm theo dõi task
 */
public class TeamMember implements TaskObserver {
    private String name;
    private String role;
    
    public TeamMember(String name, String role) {
        this.name = name;
        this.role = role;
    }
    
    @Override
    public void onTaskUpdated(String taskName, String status) {
        System.out.println("      → " + name + " (" + role + ") nhận thông báo: Task '" + taskName + "' = " + status);
    }
    
    public String getName() {
        return name;
    }
    
    public String getRole() {
        return role;
    }
}
