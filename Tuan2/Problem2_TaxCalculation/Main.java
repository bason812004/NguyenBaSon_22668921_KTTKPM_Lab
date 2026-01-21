public class Main {
    public static void main(String[] args) {
        System.out.println("=== HỆ THỐNG TÍNH THUẾ SẢN PHẨM ===\n");

        // ========== STATE PATTERN ==========
        System.out.println("--- STATE PATTERN: Trạng thái thuế sản phẩm ---");
        
        Product laptop = new Product("Laptop Dell XPS", 20000000);
        System.out.println("\n1. Sản phẩm: " + laptop.getName());
        System.out.println("   Giá gốc: " + laptop.getBasePrice() + " VND");
        System.out.println("   " + laptop.getTaxInfo());
        System.out.println("   Thuế: " + laptop.calculateTax() + " VND");
        System.out.println("   Tổng giá: " + laptop.getTotalPrice() + " VND");

        laptop.setTaxState(new VATTaxState());
        System.out.println("\n   Thay đổi sang " + laptop.getTaxInfo());
        System.out.println("   Thuế: " + laptop.calculateTax() + " VND");
        System.out.println("   Tổng giá: " + laptop.getTotalPrice() + " VND");

        Product luxuryCar = new Product("Xe Mercedes S-Class", 3000000000.0);
        luxuryCar.setTaxState(new LuxuryTaxState());
        System.out.println("\n2. Sản phẩm: " + luxuryCar.getName());
        System.out.println("   Giá gốc: " + luxuryCar.getBasePrice() + " VND");
        System.out.println("   " + luxuryCar.getTaxInfo());
        System.out.println("   Thuế: " + luxuryCar.calculateTax() + " VND");
        System.out.println("   Tổng giá: " + luxuryCar.getTotalPrice() + " VND");

        Product cigarette = new Product("Thuốc lá Marlboro", 50000);
        cigarette.setTaxState(new ExciseTaxState());
        System.out.println("\n3. Sản phẩm: " + cigarette.getName());
        System.out.println("   Giá gốc: " + cigarette.getBasePrice() + " VND");
        System.out.println("   " + cigarette.getTaxInfo());
        System.out.println("   Thuế: " + cigarette.calculateTax() + " VND");
        System.out.println("   Tổng giá: " + cigarette.getTotalPrice() + " VND");

        // ========== STRATEGY PATTERN ==========
        System.out.println("\n\n--- STRATEGY PATTERN: Chiến lược tính thuế ---");
        
        double productPrice1 = 500000;
        double productPrice2 = 2000000;
        double productPrice3 = 8000000;

        TaxCalculationStrategy flatTax = new FlatTaxStrategy(0.10);
        TaxCalculationStrategy progressiveTax = new ProgressiveTaxStrategy();
        TaxCalculationStrategy importTax = new ImportTaxStrategy();

        System.out.println("\n1. Sản phẩm nội địa giá: " + productPrice1 + " VND");
        System.out.println("   " + flatTax.getStrategyName() + ": " + flatTax.calculateTax(productPrice1) + " VND");
        System.out.println("   " + progressiveTax.getStrategyName() + ": " + progressiveTax.calculateTax(productPrice1) + " VND");

        System.out.println("\n2. Sản phẩm nội địa giá: " + productPrice2 + " VND");
        System.out.println("   " + flatTax.getStrategyName() + ": " + flatTax.calculateTax(productPrice2) + " VND");
        System.out.println("   " + progressiveTax.getStrategyName() + ": " + progressiveTax.calculateTax(productPrice2) + " VND");

        System.out.println("\n3. Sản phẩm nội địa giá: " + productPrice3 + " VND");
        System.out.println("   " + flatTax.getStrategyName() + ": " + flatTax.calculateTax(productPrice3) + " VND");
        System.out.println("   " + progressiveTax.getStrategyName() + ": " + progressiveTax.calculateTax(productPrice3) + " VND");

        System.out.println("\n4. Sản phẩm nhập khẩu giá: " + productPrice2 + " VND");
        System.out.println("   " + importTax.getStrategyName() + ": " + importTax.calculateTax(productPrice2) + " VND");

        // ========== DECORATOR PATTERN ==========
        System.out.println("\n\n--- DECORATOR PATTERN: Thêm các lớp thuế ---");
        
        TaxableProduct phone = new SimpleProduct("iPhone 15 Pro", 30000000);
        System.out.println("\n1. " + phone.getDescription());
        System.out.println("   Giá: " + phone.getPrice() + " VND");

        TaxableProduct phoneWithVAT = new VATDecorator(phone);
        System.out.println("\n2. " + phoneWithVAT.getDescription());
        System.out.println("   Giá: " + phoneWithVAT.getPrice() + " VND");

        TaxableProduct importedLuxuryWatch = new LuxuryTaxDecorator(
            new ImportTaxDecorator(
                new VATDecorator(
                    new SimpleProduct("Đồng hồ Rolex nhập khẩu", 500000000)
                )
            )
        );
        System.out.println("\n3. " + importedLuxuryWatch.getDescription());
        System.out.println("   Giá ban đầu: 500,000,000 VND");
        System.out.println("   Giá cuối cùng: " + importedLuxuryWatch.getPrice() + " VND");

        TaxableProduct alcoholProduct = new ExciseTaxDecorator(
            new VATDecorator(
                new SimpleProduct("Rượu Hennessy XO", 3000000)
            )
        );
        System.out.println("\n4. " + alcoholProduct.getDescription());
        System.out.println("   Giá ban đầu: 3,000,000 VND");
        System.out.println("   Giá cuối cùng: " + alcoholProduct.getPrice() + " VND");

        // ========== COMBINATION EXAMPLE ==========
        System.out.println("\n\n--- KẾT HỢP CÁC PATTERN ---");
        System.out.println("\nTính thuế cho nhiều sản phẩm với các trạng thái và chiến lược khác nhau:");
        
        // Product 1: Domestic electronic with state and decorator
        Product tv = new Product("Smart TV Samsung", 15000000);
        tv.setTaxState(new VATTaxState());
        
        TaxableProduct tvWithDecorators = new VATDecorator(
            new SimpleProduct(tv.getName(), tv.getBasePrice())
        );
        
        System.out.println("\n1. " + tv.getName());
        System.out.println("   Giá gốc: " + tv.getBasePrice() + " VND");
        System.out.println("   State Pattern - " + tv.getTaxInfo() + ": " + tv.calculateTax() + " VND");
        System.out.println("   Decorator Pattern - " + tvWithDecorators.getDescription());
        System.out.println("   Giá cuối (Decorator): " + tvWithDecorators.getPrice() + " VND");
        
        // Use strategy to calculate tax
        TaxCalculationStrategy strategy = new ProgressiveTaxStrategy();
        System.out.println("   Strategy Pattern - " + strategy.getStrategyName() + ": " + 
                         strategy.calculateTax(tv.getBasePrice()) + " VND");

        System.out.println("\n=== KẾT THÚC ===");
    }
}
