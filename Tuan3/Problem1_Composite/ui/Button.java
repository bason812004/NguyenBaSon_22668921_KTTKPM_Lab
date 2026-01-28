package ui;

/**
 * Leaf trong Composite Pattern - Nút bấm
 */
public class Button implements UIComponent {
    private String name;
    private String label;
    
    public Button(String name, String label) {
        this.name = name;
        this.label = label;
    }
    
    @Override
    public void render() {
        System.out.println("  [Button: " + label + "]");
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public String getLabel() {
        return label;
    }
}
