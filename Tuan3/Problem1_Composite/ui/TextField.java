package ui;

/**
 * Leaf trong Composite Pattern - Trường nhập văn bản
 */
public class TextField implements UIComponent {
    private String name;
    private String placeholder;
    
    public TextField(String name, String placeholder) {
        this.name = name;
        this.placeholder = placeholder;
    }
    
    @Override
    public void render() {
        System.out.println("  [TextField: " + placeholder + "]");
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public String getPlaceholder() {
        return placeholder;
    }
}
