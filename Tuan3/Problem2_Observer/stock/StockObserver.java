package stock;

/**
 * Observer interface cho Stock Observer Pattern
 * Các nhà đầu tư sẽ implement interface này để nhận thông báo
 */
public interface StockObserver {
    /**
     * Được gọi khi giá cổ phiếu thay đổi
     * @param stockName Tên cổ phiếu
     * @param price Giá mới
     */
    void update(String stockName, double price);
}
