package web;

import adapter.*;
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
 * Web Server để test và hiển thị Adapter Pattern
 */
public class AdapterWebServer {
    private HttpServer server;
    private int port;
    private XMLService xmlService;
    private List<ConversionLog> logs;
    
    public AdapterWebServer(int port, XMLService xmlService) {
        this.port = port;
        this.xmlService = xmlService;
        this.logs = new ArrayList<>();
    }
    
    public void logConversion(String type, String input, String output) {
        logs.add(new ConversionLog(type, input, output));
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
        
        server.createContext("/convert", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                // Test conversion
                String xmlData = xmlService.getXMLData();
                logConversion("JSON to XML", "JSON data", xmlData);
                
                String response = "Conversion completed!";
                exchange.sendResponseHeaders(200, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
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
        
        System.out.println("\n🌐 Adapter Web Server đã khởi động!");
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
        html.append("    <title>Adapter Pattern - XML/JSON Converter</title>\n");
        html.append("    <link rel='stylesheet' href='/style.css'>\n");
        html.append("</head>\n<body>\n");
        html.append("    <div class='container'>\n");
        html.append("        <h1>🔄 Adapter Pattern Demo</h1>\n");
        html.append("        <div class='info-box'>\n");
        html.append("            <p>🌐 Server: <strong>localhost:").append(port).append("</strong></p>\n");
        html.append("            <p>🔧 Pattern: <strong>Adapter Pattern with Load Balancer</strong></p>\n");
        html.append("            <p>📊 Function: <strong>XML ↔ JSON Conversion</strong></p>\n");
        html.append("        </div>\n");
        
        html.append("        <div class='conversion-demo'>\n");
        html.append("            <h2>🧪 Test Conversion</h2>\n");
        html.append("            <button onclick='testConversion()' class='btn'>🔄 Run Conversion Test</button>\n");
        html.append("        </div>\n");
        
        html.append("        <div class='logs'>\n");
        html.append("            <h2>📋 Conversion Logs</h2>\n");
        for (ConversionLog log : logs) {
            html.append("            <div class='log-item'>\n");
            html.append("                <div class='log-type'>").append(log.type).append("</div>\n");
            html.append("                <div class='log-content'>\n");
            html.append("                    <div class='code-block'>\n");
            html.append("                        <pre>").append(escapeHtml(log.output)).append("</pre>\n");
            html.append("                    </div>\n");
            html.append("                </div>\n");
            html.append("            </div>\n");
        }
        html.append("        </div>\n");
        
        html.append("    </div>\n");
        html.append("    <script>\n");
        html.append("        function testConversion() {\n");
        html.append("            fetch('/convert').then(() => location.reload());\n");
        html.append("        }\n");
        html.append("    </script>\n");
        html.append("</body>\n</html>");
        return html.toString();
    }
    
    private String escapeHtml(String str) {
        return str.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;");
    }
    
    private String generateCSS() {
        return "* { margin: 0; padding: 0; box-sizing: border-box; }\n" +
               "body {\n" +
               "    font-family: 'Segoe UI', Tahoma, sans-serif;\n" +
               "    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);\n" +
               "    min-height: 100vh;\n" +
               "    padding: 20px;\n" +
               "}\n" +
               ".container {\n" +
               "    max-width: 1200px;\n" +
               "    margin: 0 auto;\n" +
               "    background: white;\n" +
               "    border-radius: 15px;\n" +
               "    box-shadow: 0 20px 60px rgba(0,0,0,0.3);\n" +
               "    padding: 30px;\n" +
               "}\n" +
               "h1 { color: #333; text-align: center; margin-bottom: 20px; font-size: 2.5em; }\n" +
               "h2 { color: #555; margin: 20px 0 15px; }\n" +
               ".info-box {\n" +
               "    background: #fff3cd;\n" +
               "    border-left: 4px solid #ffc107;\n" +
               "    padding: 15px 20px;\n" +
               "    margin-bottom: 30px;\n" +
               "    border-radius: 5px;\n" +
               "}\n" +
               ".info-box p { margin: 5px 0; color: #555; }\n" +
               ".conversion-demo {\n" +
               "    background: #f8f9fa;\n" +
               "    padding: 20px;\n" +
               "    border-radius: 10px;\n" +
               "    margin-bottom: 30px;\n" +
               "    text-align: center;\n" +
               "}\n" +
               ".btn {\n" +
               "    padding: 12px 30px;\n" +
               "    background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);\n" +
               "    color: white;\n" +
               "    border: none;\n" +
               "    border-radius: 8px;\n" +
               "    font-size: 16px;\n" +
               "    font-weight: 600;\n" +
               "    cursor: pointer;\n" +
               "    transition: transform 0.3s;\n" +
               "}\n" +
               ".btn:hover { transform: translateY(-2px); }\n" +
               ".logs { background: #f8f9fa; padding: 20px; border-radius: 10px; }\n" +
               ".log-item {\n" +
               "    background: white;\n" +
               "    padding: 15px;\n" +
               "    margin: 10px 0;\n" +
               "    border-radius: 8px;\n" +
               "    border-left: 3px solid #f093fb;\n" +
               "}\n" +
               ".log-type {\n" +
               "    font-weight: 600;\n" +
               "    color: #f5576c;\n" +
               "    margin-bottom: 10px;\n" +
               "}\n" +
               ".code-block {\n" +
               "    background: #282c34;\n" +
               "    color: #abb2bf;\n" +
               "    padding: 15px;\n" +
               "    border-radius: 5px;\n" +
               "    overflow-x: auto;\n" +
               "}\n" +
               ".code-block pre { margin: 0; font-family: 'Courier New', monospace; font-size: 14px; }";
    }
    
    private static class ConversionLog {
        String type;
        String input;
        String output;
        
        ConversionLog(String type, String input, String output) {
            this.type = type;
            this.input = input;
            this.output = output;
        }
    }
}
