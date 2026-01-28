package ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite trong Composite Pattern - Hộp thoại chứa các component
 */
public class Dialog implements UIComponent {
    private String title;
    private List<UIComponent> children;
    
    public Dialog(String title) {
        this.title = title;
        this.children = new ArrayList<>();
    }
    
    public void add(UIComponent component) {
        children.add(component);
    }
    
    public void remove(UIComponent component) {
        children.remove(component);
    }
    
    public List<UIComponent> getChildren() {
        return children;
    }
    
    @Override
    public void render() {
        System.out.println("┌────────────────────────────────────┐");
        System.out.println("│ Dialog: " + title);
        System.out.println("├────────────────────────────────────┤");
        for (UIComponent child : children) {
            child.render();
        }
        System.out.println("└────────────────────────────────────┘");
    }
    
    @Override
    public String getName() {
        return title;
    }
}
