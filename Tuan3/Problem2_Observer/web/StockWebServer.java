package web;

import stock.*;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Web Server để hiển thị Stock notifications trên trình duyệt
 */
public class StockWebServer {
    private HttpServer server;
    private int port;
    private List<StockUpdateInfo> updates;
    private List<Stock> stocks;
    
    public StockWebServer(int port) {
        this.port = port;
        this.updates = new ArrayList<>();
        this.stocks = new ArrayList<>();
    }
    
    public void addStock(Stock stock) {
        stocks.add(stock);
    }
    
    public void recordUpdate(String stockName, double price, String observer) {
        updates.add(new StockUpdateInfo(stockName, price, observer));
    }
    
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String html = generateHTML();
                byte[] response = html.getBytes(StandardCharsets.UTF_8);
                
                exchange.getResponseHeaders().add("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            }
        });
        
        server.createContext("/style.css", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String css = generateCSS();
                byte[] response = css.getBytes(StandardCharsets.UTF_8);
                
                exchange.getResponseHeaders().add("Content-Type", "text/css; charset=UTF-8");
                exchange.sendResponseHeaders(200, response.length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(response);
                os.close();
            }
        });
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("\n🌐 Stock Web Server đã khởi động!");
        System.out.println("📍 Truy cập: http://localhost:" + port);
    }
    
    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }
    
    private String generateHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang='vi'>\n<head>\n");
        html.append("    <meta charset='UTF-8'>\n");
        html.append("    <meta http-equiv='refresh' content='2'>\n"); // Auto refresh every 2s
        html.append("    <title>Stock Observer - Real-time Monitoring</title>\n");
        html.append("    <link rel='stylesheet' href='/style.css'>\n");
        html.append("</head>\n<body>\n");
        html.append("    <div class='container'>\n");
        html.append("        <h1>📈 Stock Price Monitor</h1>\n");
        html.append("        <div class='info-box'>\n");
        html.append("            <p>🌐 Server: <strong>localhost:").append(port).append("</strong></p>\n");
        html.append("            <p>🔄 Pattern: <strong>Observer Pattern with Load Balancer</strong></p>\n");
        html.append("            <p>⏱️ Auto-refresh: <strong>Every 2 seconds</strong></p>\n");
        html.append("        </div>\n");
        
        // Stock cards
        html.append("        <div class='stocks-grid'>\n");
        for (Stock stock : stocks) {
            html.append("            <div class='stock-card'>\n");
            html.append("                <h2>").append(stock.getName()).append("</h2>\n");
            html.append("                <div class='price'>$").append(String.format("%.2f", stock.getPrice())).append("</div>\n");
            html.append("                <div class='observers'>👥 ").append(stock.getObserverCount()).append(" Observers</div>\n");
            html.append("            </div>\n");
        }
        html.append("        </div>\n");
        
        // Updates timeline
        html.append("        <div class='timeline'>\n");
        html.append("            <h2>📊 Recent Updates</h2>\n");
        int displayCount = Math.min(updates.size(), 20);
        for (int i = updates.size() - 1; i >= updates.size() - displayCount && i >= 0; i--) {
            StockUpdateInfo info = updates.get(i);
            html.append("            <div class='update-item'>\n");
            html.append("                <span class='stock-name'>").append(info.stockName).append("</span>\n");
            html.append("                <span class='price'>$").append(String.format("%.2f", info.price)).append("</span>\n");
            html.append("                <span class='observer'>→ ").append(info.observer).append("</span>\n");
            html.append("            </div>\n");
        }
        html.append("        </div>\n");
        
        html.append("    </div>\n</body>\n</html>");
        return html.toString();
    }
    
    private String generateCSS() {
        return "* { margin: 0; padding: 0; box-sizing: border-box; }\n" +
               "body {\n" +
               "    font-family: 'Segoe UI', Tahoma, sans-serif;\n" +
               "    background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);\n" +
               "    min-height: 100vh;\n" +
               "    padding: 20px;\n" +
               "}\n" +
               ".container {\n" +
               "    max-width: 1400px;\n" +
               "    margin: 0 auto;\n" +
               "    background: white;\n" +
               "    border-radius: 15px;\n" +
               "    box-shadow: 0 20px 60px rgba(0,0,0,0.3);\n" +
               "    padding: 30px;\n" +
               "}\n" +
               "h1 {\n" +
               "    color: #1e3c72;\n" +
               "    text-align: center;\n" +
               "    margin-bottom: 20px;\n" +
               "    font-size: 2.5em;\n" +
               "}\n" +
               ".info-box {\n" +
               "    background: #e8f4f8;\n" +
               "    border-left: 4px solid #1e3c72;\n" +
               "    padding: 15px 20px;\n" +
               "    margin-bottom: 30px;\n" +
               "    border-radius: 5px;\n" +
               "}\n" +
               ".info-box p { margin: 5px 0; color: #555; }\n" +
               ".stocks-grid {\n" +
               "    display: grid;\n" +
               "    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));\n" +
               "    gap: 20px;\n" +
               "    margin-bottom: 30px;\n" +
               "}\n" +
               ".stock-card {\n" +
               "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
               "    color: white;\n" +
               "    padding: 25px;\n" +
               "    border-radius: 10px;\n" +
               "    box-shadow: 0 5px 15px rgba(0,0,0,0.2);\n" +
               "    transition: transform 0.3s;\n" +
               "}\n" +
               ".stock-card:hover { transform: translateY(-5px); }\n" +
               ".stock-card h2 { margin-bottom: 15px; font-size: 1.5em; }\n" +
               ".price {\n" +
               "    font-size: 2em;\n" +
               "    font-weight: bold;\n" +
               "    margin: 10px 0;\n" +
               "}\n" +
               ".observers { opacity: 0.9; margin-top: 10px; }\n" +
               ".timeline {\n" +
               "    background: #f8f9fa;\n" +
               "    padding: 20px;\n" +
               "    border-radius: 10px;\n" +
               "}\n" +
               ".timeline h2 {\n" +
               "    color: #333;\n" +
               "    margin-bottom: 15px;\n" +
               "}\n" +
               ".update-item {\n" +
               "    background: white;\n" +
               "    padding: 12px 15px;\n" +
               "    margin: 8px 0;\n" +
               "    border-radius: 5px;\n" +
               "    border-left: 3px solid #667eea;\n" +
               "    display: flex;\n" +
               "    justify-content: space-between;\n" +
               "    align-items: center;\n" +
               "}\n" +
               ".stock-name {\n" +
               "    font-weight: 600;\n" +
               "    color: #1e3c72;\n" +
               "    min-width: 100px;\n" +
               "}\n" +
               ".update-item .price {\n" +
               "    color: #28a745;\n" +
               "    font-weight: bold;\n" +
               "    font-size: 1em;\n" +
               "    margin: 0;\n" +
               "}\n" +
               ".observer {\n" +
               "    color: #666;\n" +
               "    font-size: 0.9em;\n" +
               "}";
    }
    
    private static class StockUpdateInfo {
        String stockName;
        double price;
        String observer;
        
        StockUpdateInfo(String stockName, double price, String observer) {
            this.stockName = stockName;
            this.price = price;
            this.observer = observer;
        }
    }
}
