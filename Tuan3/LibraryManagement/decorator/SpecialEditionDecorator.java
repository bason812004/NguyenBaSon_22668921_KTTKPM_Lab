package decorator;

/**
 * Concrete Decorator - Phiên bản đặc biệt
 * Cho phép mượn sách với phiên bản đặc biệt (bìa cứng, có chữ ký, v.v.)
 */
public class SpecialEditionDecorator extends BorrowDecorator {
    private String editionType;
    private double specialCost;
    
    public static final String HARDCOVER = "Bìa cứng";
    public static final String SIGNED = "Có chữ ký tác giả";
    public static final String LIMITED = "Phiên bản giới hạn";
    public static final String COLLECTORS = "Phiên bản sưu tầm";
    
    public SpecialEditionDecorator(Borrowable wrapped, String editionType) {
        super(wrapped);
        this.editionType = editionType;
        this.specialCost = calculateSpecialCost(editionType);
    }
    
    private double calculateSpecialCost(String type) {
        switch (type) {
            case HARDCOVER:
                return 5000.0;
            case SIGNED:
                return 20000.0;
            case LIMITED:
                return 15000.0;
            case COLLECTORS:
                return 30000.0;
            default:
                return 10000.0;
        }
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + " + editionType;
    }
    
    @Override
    public double getCost() {
        return super.getCost() + specialCost;
    }
    
    public String getEditionType() {
        return editionType;
    }
}
