import singleton.Library;
import factory.*;
import strategy.*;
import observer.*;
import decorator.*;

import java.util.List;

/**
 * Demo class cho hệ thống quản lý thư viện
 * Minh họa 5 Design Patterns: Singleton, Factory Method, Strategy, Observer, Decorator
 */
public class LibraryDemo {
    
    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║           HỆ THỐNG QUẢN LÝ THƯ VIỆN - LIBRARY MANAGEMENT             ║");
        System.out.println("║     (Singleton + Factory + Strategy + Observer + Decorator)          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
        
        // Demo 1: Singleton Pattern
        demoSingletonPattern();
        
        // Demo 2: Factory Method Pattern
        demoFactoryPattern();
        
        // Demo 3: Observer Pattern
        demoObserverPattern();
        
        // Demo 4: Strategy Pattern
        demoStrategyPattern();
        
        // Demo 5: Decorator Pattern
        demoDecoratorPattern();
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    KẾT THÚC DEMO THÀNH CÔNG!                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════╝");
    }
    
    /**
     * Demo 1: Singleton Pattern
     */
    private static void demoSingletonPattern() {
        System.out.println("\n═══════════════════════════════════════════════════════════════════════");
        System.out.println("  1️⃣  SINGLETON PATTERN - Đảm bảo chỉ có một Library");
        System.out.println("═══════════════════════════════════════════════════════════════════════\n");
        
        Library library1 = Library.getInstance();
        Library library2 = Library.getInstance();
        Library library3 = Library.getInstance();
        
        System.out.println("\n   🔍 Kiểm tra Singleton:");
        System.out.println("      library1 == library2: " + (library1 == library2));
        System.out.println("      library2 == library3: " + (library2 == library3));
        System.out.println("      ✅ Tất cả đều trỏ đến cùng một instance!");
    }
    
    /**
     * Demo 2: Factory Method Pattern
     */
    private static void demoFactoryPattern() {
        System.out.println("\n═══════════════════════════════════════════════════════════════════════");
        System.out.println("  2️⃣  FACTORY METHOD PATTERN - Tạo các loại sách khác nhau");
        System.out.println("═══════════════════════════════════════════════════════════════════════\n");
        
        Library library = Library.getInstance();
        library.reset(); // Reset để demo
        
        System.out.println("   📖 Tạo sách bằng Factory:\n");
        
        // Tạo các loại sách khác nhau
        Book paperBook1 = BookFactory.createPaperBook(
            "Clean Code", "Robert C. Martin", "Lập Trình", 
            464, "Prentice Hall", 2008
        );
        
        Book paperBook2 = BookFactory.createPaperBook(
            "Design Patterns", "Gang of Four", "Lập Trình",
            395, "Addison-Wesley", 1994
        );
        
        Book ebook = BookFactory.createEBook(
            "Java Programming", "John Smith", "Lập Trình",
            15.5, "PDF"
        );
        
        Book audioBook = BookFactory.createAudioBook(
            "The Pragmatic Programmer", "Andy Hunt", "Lập Trình",
            480, "Professional Narrator"
        );
        
        // Sử dụng factory method đơn giản
        Book novel = BookFactory.createBook(
            BookFactory.TYPE_PAPER, 
            "Sherlock Holmes", "Arthur Conan Doyle", "Trinh Thám"
        );
        
        // Hiển thị thông tin
        System.out.println("   " + paperBook1.getDescription());
        System.out.println("   " + paperBook2.getDescription());
        System.out.println("   " + ebook.getDescription());
        System.out.println("   " + audioBook.getDescription());
        System.out.println("   " + novel.getDescription());
    }
    
    /**
     * Demo 3: Observer Pattern
     */
    private static void demoObserverPattern() {
        System.out.println("\n═══════════════════════════════════════════════════════════════════════");
        System.out.println("  3️⃣  OBSERVER PATTERN - Thông báo khi có sách mới/quá hạn");
        System.out.println("═══════════════════════════════════════════════════════════════════════\n");
        
        Library library = Library.getInstance();
        library.reset();
        
        // Tạo observers
        LibraryStaff staff1 = new LibraryStaff("Nguyễn Văn Quản Lý", "Thủ thư trưởng");
        LibraryStaff staff2 = new LibraryStaff("Trần Thị Nhân Viên", "Nhân viên");
        
        LibraryMember member1 = new LibraryMember("Lê Văn Đọc Giả", "M001", "Lập Trình", "Công nghệ");
        LibraryMember member2 = new LibraryMember("Phạm Thị Sinh Viên", "M002", "Trinh Thám", "Văn Học");
        
        // Đăng ký observers
        System.out.println("   📌 Đăng ký observers:");
        library.attach(staff1);
        library.attach(staff2);
        library.attach(member1);
        library.attach(member2);
        System.out.println("   ✅ Đã đăng ký 4 observers (2 nhân viên, 2 thành viên)\n");
        
        // Thêm sách mới - sẽ trigger notification
        System.out.println("   📚 Thêm sách mới vào thư viện:");
        
        Book programmingBook = BookFactory.createBook(
            BookFactory.TYPE_PAPER,
            "Effective Java", "Joshua Bloch", "Lập Trình"
        );
        library.addBook(programmingBook);
        
        Book mysteryBook = BookFactory.createBook(
            BookFactory.TYPE_EBOOK,
            "Murder on the Orient Express", "Agatha Christie", "Trinh Thám"
        );
        library.addBook(mysteryBook);
        
        // Thông báo sách quá hạn
        System.out.println("\n   ⏰ Giả lập sách quá hạn:");
        library.notifyOverdue(programmingBook, "Phạm Thị Sinh Viên");
    }
    
    /**
     * Demo 4: Strategy Pattern
     */
    private static void demoStrategyPattern() {
        System.out.println("\n═══════════════════════════════════════════════════════════════════════");
        System.out.println("  4️⃣  STRATEGY PATTERN - Các chiến lược tìm kiếm sách");
        System.out.println("═══════════════════════════════════════════════════════════════════════\n");
        
        Library library = Library.getInstance();
        library.reset();
        
        // Thêm một số sách để tìm kiếm
        library.addBook(BookFactory.createBook(BookFactory.TYPE_PAPER, 
            "Clean Code", "Robert Martin", "Lập Trình"));
        library.addBook(BookFactory.createBook(BookFactory.TYPE_PAPER, 
            "Clean Architecture", "Robert Martin", "Lập Trình"));
        library.addBook(BookFactory.createBook(BookFactory.TYPE_EBOOK, 
            "The Pragmatic Programmer", "Andy Hunt", "Lập Trình"));
        library.addBook(BookFactory.createBook(BookFactory.TYPE_AUDIO, 
            "Sherlock Holmes", "Arthur Conan Doyle", "Trinh Thám"));
        library.addBook(BookFactory.createBook(BookFactory.TYPE_PAPER, 
            "Harry Potter", "J.K. Rowling", "Văn Học"));
            
        System.out.println();
        
        // Tìm theo tên
        System.out.println("\n   🔍 Tìm kiếm với các chiến lược khác nhau:\n");
        
        library.setSearchStrategy(new SearchByName());
        List<Book> result1 = library.searchBooks("Clean");
        printSearchResult(result1);
        
        // Tìm theo tác giả
        library.setSearchStrategy(new SearchByAuthor());
        List<Book> result2 = library.searchBooks("Robert");
        printSearchResult(result2);
        
        // Tìm theo thể loại
        library.setSearchStrategy(new SearchByGenre());
        List<Book> result3 = library.searchBooks("Lập Trình");
        printSearchResult(result3);
    }
    
    private static void printSearchResult(List<Book> books) {
        System.out.println("      📋 Tìm thấy " + books.size() + " kết quả:");
        for (Book book : books) {
            System.out.println("         • " + book.getTitle() + " - " + book.getAuthor());
        }
        System.out.println();
    }
    
    /**
     * Demo 5: Decorator Pattern
     */
    private static void demoDecoratorPattern() {
        System.out.println("\n═══════════════════════════════════════════════════════════════════════");
        System.out.println("  5️⃣  DECORATOR PATTERN - Tính năng bổ sung khi mượn sách");
        System.out.println("═══════════════════════════════════════════════════════════════════════\n");
        
        // Tạo một cuốn sách
        Book book = BookFactory.createPaperBook(
            "Design Patterns", "Gang of Four", "Lập Trình",
            395, "Addison-Wesley", 1994
        );
        
        System.out.println("   📖 Sách: " + book.getTitle() + " - " + book.getAuthor());
        System.out.println();
        
        // Mượn cơ bản
        System.out.println("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("   📋 Kịch bản 1: Mượn cơ bản");
        Borrowable basicBorrow = new BasicBorrow(book);
        printBorrowDetails(basicBorrow);
        
        // Mượn với gia hạn
        System.out.println("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("   📋 Kịch bản 2: Mượn + Gia hạn 7 ngày");
        Borrowable extendedBorrow = new ExtendedBorrowDecorator(new BasicBorrow(book), 7);
        printBorrowDetails(extendedBorrow);
        
        // Mượn phiên bản đặc biệt
        System.out.println("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("   📋 Kịch bản 3: Mượn phiên bản bìa cứng");
        Borrowable specialBorrow = new SpecialEditionDecorator(
            new BasicBorrow(book), 
            SpecialEditionDecorator.HARDCOVER
        );
        printBorrowDetails(specialBorrow);
        
        // Kết hợp nhiều decorator
        System.out.println("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("   📋 Kịch bản 4: Mượn + Gia hạn 14 ngày + Phiên bản có chữ ký");
        Borrowable premiumBorrow = new SpecialEditionDecorator(
            new ExtendedBorrowDecorator(
                new BasicBorrow(book), 
                14
            ),
            SpecialEditionDecorator.SIGNED
        );
        printBorrowDetails(premiumBorrow);
        
        // Kịch bản đầy đủ nhất
        System.out.println("   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("   📋 Kịch bản 5: VIP - Gia hạn 30 ngày + Phiên bản sưu tầm");
        Borrowable vipBorrow = new SpecialEditionDecorator(
            new ExtendedBorrowDecorator(
                new BasicBorrow(book), 
                30
            ),
            SpecialEditionDecorator.COLLECTORS
        );
        printBorrowDetails(vipBorrow);
    }
    
    private static void printBorrowDetails(Borrowable borrow) {
        System.out.println();
        System.out.println("      📝 Mô tả: " + borrow.getDescription());
        System.out.println("      📅 Số ngày mượn: " + borrow.getBorrowDays() + " ngày");
        System.out.println("      💰 Chi phí: " + String.format("%,.0f", borrow.getCost()) + " VND");
        System.out.println();
    }
}
