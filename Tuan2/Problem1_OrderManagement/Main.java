public class Main {
    public static void main(String[] args) {
        System.out.println("=== HỆ THỐNG QUẢN LÝ ĐƠN HÀNG ===\n");

        // ========== STATE PATTERN ==========
        System.out.println("--- STATE PATTERN: Quản lý trạng thái đơn hàng ---");
        Order order1 = new Order("ORD001", 350000);
        
        System.out.println("\n1. Đơn hàng mới tạo:");
        order1.process(); // New -> Processing
        
        System.out.println("\n2. Đơn hàng đang xử lý:");
        order1.process(); // Processing actions
        
        System.out.println("\n3. Giao hàng:");
        order1.deliver(); // Processing -> Delivered
        
        System.out.println("\n4. Cập nhật đơn đã giao:");
        order1.process(); // Delivered actions
        
        System.out.println("\n5. Thử hủy đơn đã giao:");
        order1.cancel(); // Cannot cancel delivered order

        // Try cancelling a processing order
        System.out.println("\n6. Tạo đơn hàng mới và hủy khi đang xử lý:");
        Order order2 = new Order("ORD002", 200000);
        order2.process(); // New -> Processing
        order2.cancel(); // Processing -> Cancelled

        // ========== STRATEGY PATTERN ==========
        System.out.println("\n\n--- STRATEGY PATTERN: Phương thức vận chuyển ---");
        
        Order order3 = new Order("ORD003", 600000);
        
        ShippingStrategy standardShipping = new StandardShipping();
        ShippingStrategy expressShipping = new ExpressShipping();
        ShippingStrategy freeShipping = new FreeShipping();
        
        System.out.println("\nĐơn hàng #" + order3.getOrderId() + " - Giá trị: " + order3.getAmount() + " VND");
        
        System.out.println("\n1. " + standardShipping.getShippingMethod());
        System.out.println("   Phí vận chuyển: " + standardShipping.calculateShippingCost(order3.getAmount()) + " VND");
        
        System.out.println("\n2. " + expressShipping.getShippingMethod());
        System.out.println("   Phí vận chuyển: " + expressShipping.calculateShippingCost(order3.getAmount()) + " VND");
        
        System.out.println("\n3. " + freeShipping.getShippingMethod());
        System.out.println("   Phí vận chuyển: " + freeShipping.calculateShippingCost(order3.getAmount()) + " VND");

        // ========== DECORATOR PATTERN ==========
        System.out.println("\n\n--- DECORATOR PATTERN: Tùy chỉnh đơn hàng ---");
        
        OrderComponent basicOrder = new BasicOrder(300000, "Đơn hàng cơ bản");
        System.out.println("\n1. " + basicOrder.getDescription());
        System.out.println("   Tổng chi phí: " + basicOrder.getCost() + " VND");
        
        OrderComponent giftWrappedOrder = new GiftWrapDecorator(basicOrder);
        System.out.println("\n2. " + giftWrappedOrder.getDescription());
        System.out.println("   Tổng chi phí: " + giftWrappedOrder.getCost() + " VND");
        
        OrderComponent fullyDecoratedOrder = new InsuranceDecorator(
            new PriorityProcessingDecorator(
                new GiftWrapDecorator(basicOrder)
            )
        );
        System.out.println("\n3. " + fullyDecoratedOrder.getDescription());
        System.out.println("   Tổng chi phí: " + fullyDecoratedOrder.getCost() + " VND");

        // ========== COMBINATION EXAMPLE ==========
        System.out.println("\n\n--- KẾT HỢP CÁC PATTERN ---");
        System.out.println("\nTạo đơn hàng hoàn chỉnh với tất cả các tính năng:");
        
        // Create order with State
        Order finalOrder = new Order("ORD004", 450000);
        System.out.println("Đơn hàng #" + finalOrder.getOrderId());
        
        // Add decorations
        OrderComponent decoratedFinalOrder = new InsuranceDecorator(
            new GiftWrapDecorator(
                new BasicOrder(finalOrder.getAmount(), "Điện thoại Samsung Galaxy")
            )
        );
        System.out.println("Chi tiết: " + decoratedFinalOrder.getDescription());
        
        // Choose shipping strategy
        ShippingStrategy chosenShipping = new ExpressShipping();
        double shippingCost = chosenShipping.calculateShippingCost(decoratedFinalOrder.getCost());
        System.out.println("Phương thức: " + chosenShipping.getShippingMethod());
        System.out.println("Phí ship: " + shippingCost + " VND");
        
        double totalCost = decoratedFinalOrder.getCost() + shippingCost;
        System.out.println("TỔNG THANH TOÁN: " + totalCost + " VND");
        
        // Process order through states
        System.out.println("\nXử lý đơn hàng:");
        finalOrder.process();
        finalOrder.deliver();

        System.out.println("\n=== KẾT THÚC ===");
    }
}
