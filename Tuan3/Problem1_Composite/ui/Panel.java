package ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite trong Composite Pattern - Panel chứa các component khác
 */
public class Panel implements UIComponent {
    private String name;
    private List<UIComponent> children;
    
    public Panel(String name) {
        this.name = name;
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
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║ Panel: " + name);
        System.out.println("╠══════════════════════════════════╣");
        for (UIComponent child : children) {
            child.render();
        }
        System.out.println("╚══════════════════════════════════╝");
    }
    
    @Override
    public String getName() {
        return name;
    }
}
