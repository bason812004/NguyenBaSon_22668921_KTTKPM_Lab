package decorator;

/**
 * Concrete Decorator - Gia hạn thời gian mượn
 */
public class ExtendedBorrowDecorator extends BorrowDecorator {
    private int extraDays;
    private double extraCostPerDay;
    
    public ExtendedBorrowDecorator(Borrowable wrapped, int extraDays) {
        super(wrapped);
        this.extraDays = extraDays;
        this.extraCostPerDay = 1000.0; // 1000 VND mỗi ngày gia hạn
    }
    
    @Override
    public int getBorrowDays() {
        return super.getBorrowDays() + extraDays;
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + Gia hạn " + extraDays + " ngày";
    }
    
    @Override
    public double getCost() {
        return super.getCost() + (extraDays * extraCostPerDay);
    }
    
    public int getExtraDays() {
        return extraDays;
    }
}
