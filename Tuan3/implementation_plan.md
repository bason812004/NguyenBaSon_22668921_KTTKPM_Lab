# Design Patterns Implementation Plan - Tuan3

Xây dựng hệ thống áp dụng các Design Patterns cho bài tập tuần 3 môn Kiến trúc Thiết kế Phần mềm.

---

## Proposed Changes

### Problem 1: Composite Design Pattern

> **Bài toán**: Quản lý thư mục/tập tin theo mô hình cây và các thành phần UI (nút bấm, hộp thoại, thanh điều hướng).

#### UML Diagram - File System

```mermaid
classDiagram
    class FileSystemComponent {
        <<interface>>
        +getName() String
        +getSize() long
        +display(indent: String) void
    }
    
    class File {
        -name: String
        -size: long
        +getName() String
        +getSize() long
        +display(indent: String) void
    }
    
    class Folder {
        -name: String
        -children: List~FileSystemComponent~
        +getName() String
        +getSize() long
        +display(indent: String) void
        +add(component: FileSystemComponent) void
        +remove(component: FileSystemComponent) void
    }
    
    FileSystemComponent <|.. File
    FileSystemComponent <|.. Folder
    Folder o-- FileSystemComponent : contains
```

#### UML Diagram - UI Components

```mermaid
classDiagram
    class UIComponent {
        <<interface>>
        +render() void
        +getName() String
    }
    
    class Button {
        -name: String
        +render() void
        +getName() String
    }
    
    class TextField {
        -name: String
        -placeholder: String
        +render() void
        +getName() String
    }
    
    class Panel {
        -name: String
        -children: List~UIComponent~
        +render() void
        +getName() String
        +add(component: UIComponent) void
        +remove(component: UIComponent) void
    }
    
    class Dialog {
        -title: String
        -children: List~UIComponent~
        +render() void
        +getName() String
        +add(component: UIComponent) void
    }
    
    UIComponent <|.. Button
    UIComponent <|.. TextField
    UIComponent <|.. Panel
    UIComponent <|.. Dialog
    Panel o-- UIComponent : contains
    Dialog o-- UIComponent : contains
```

#### Files Structure

```
Tuan3/
└── Problem1_Composite/
    ├── filesystem/
    │   ├── FileSystemComponent.java
    │   ├── File.java
    │   └── Folder.java
    ├── ui/
    │   ├── UIComponent.java
    │   ├── Button.java
    │   ├── TextField.java
    │   ├── Panel.java
    │   └── Dialog.java
    └── CompositeDemo.java
```

---

### Problem 2: Observer Design Pattern

> **Bài toán 1**: Thông báo cho nhà đầu tư khi giá cổ phiếu thay đổi.  
> **Bài toán 2**: Thông báo cho thành viên nhóm khi trạng thái công việc thay đổi.

#### UML Diagram - Stock Observer

```mermaid
classDiagram
    class StockObserver {
        <<interface>>
        +update(stockName: String, price: double) void
    }
    
    class Stock {
        -name: String
        -price: double
        -observers: List~StockObserver~
        +attach(observer: StockObserver) void
        +detach(observer: StockObserver) void
        +notifyObservers() void
        +setPrice(price: double) void
        +getPrice() double
    }
    
    class Investor {
        -name: String
        +update(stockName: String, price: double) void
    }
    
    StockObserver <|.. Investor
    Stock o-- StockObserver : notifies
```

#### UML Diagram - Task Observer

```mermaid
classDiagram
    class TaskObserver {
        <<interface>>
        +onTaskUpdated(taskName: String, status: String) void
    }
    
    class Task {
        -name: String
        -status: String
        -observers: List~TaskObserver~
        +attach(observer: TaskObserver) void
        +detach(observer: TaskObserver) void
        +notifyObservers() void
        +setStatus(status: String) void
    }
    
    class TeamMember {
        -name: String
        +onTaskUpdated(taskName: String, status: String) void
    }
    
    TaskObserver <|.. TeamMember
    Task o-- TaskObserver : notifies
```

#### Files Structure

```
Tuan3/
└── Problem2_Observer/
    ├── stock/
    │   ├── StockObserver.java
    │   ├── Stock.java
    │   └── Investor.java
    ├── task/
    │   ├── TaskObserver.java
    │   ├── Task.java
    │   └── TeamMember.java
    └── ObserverDemo.java
```

---

### Problem 3: Adapter Design Pattern

> **Bài toán**: Chuyển đổi giữa định dạng JSON và XML.

#### UML Diagram

```mermaid
classDiagram
    class XMLService {
        <<interface>>
        +getXMLData() String
        +processXMLData(xmlData: String) void
    }
    
    class JSONService {
        +getJSONData() String
        +processJSONData(jsonData: String) void
    }
    
    class XMLToJSONAdapter {
        -jsonService: JSONService
        +getXMLData() String
        +processXMLData(xmlData: String) void
        -convertJSONToXML(json: String) String
        -convertXMLToJSON(xml: String) String
    }
    
    XMLService <|.. XMLToJSONAdapter
    XMLToJSONAdapter --> JSONService : adapts
```

#### Files Structure

```
Tuan3/
└── Problem3_Adapter/
    ├── XMLService.java
    ├── JSONService.java
    ├── XMLToJSONAdapter.java
    └── AdapterDemo.java
```

---

### Library Management System (Main Project)

> Áp dụng 5 Design Patterns: Singleton, Factory Method, Strategy, Observer, Decorator

#### Architecture Overview

```mermaid
flowchart TB
    subgraph Singleton
        Library["Library (Singleton)"]
    end
    
    subgraph Factory
        BookFactory --> PaperBook
        BookFactory --> EBook
        BookFactory --> AudioBook
    end
    
    subgraph Strategy
        SearchContext --> SearchByName
        SearchContext --> SearchByAuthor
        SearchContext --> SearchByGenre
    end
    
    subgraph Observer
        Library -->|notifies| LibraryObserver
        LibraryObserver --> Staff
        LibraryObserver --> Member
    end
    
    subgraph Decorator
        BorrowedBook --> ExtendedBorrow
        BorrowedBook --> SpecialEdition
    end
    
    Library --> BookFactory
    Library --> SearchContext
```

#### UML Diagram - Singleton Pattern

```mermaid
classDiagram
    class Library {
        -instance: Library$
        -books: List~Book~
        -observers: List~LibraryObserver~
        -Library()
        +getInstance() Library$
        +addBook(book: Book) void
        +removeBook(book: Book) void
        +getBooks() List~Book~
        +attach(observer: LibraryObserver) void
        +notifyNewBook(book: Book) void
    }
```

#### UML Diagram - Factory Method Pattern

```mermaid
classDiagram
    class Book {
        <<abstract>>
        #title: String
        #author: String
        #genre: String
        +getTitle() String
        +getAuthor() String
        +getGenre() String
        +getDescription() String*
    }
    
    class PaperBook {
        -pages: int
        +getDescription() String
    }
    
    class EBook {
        -fileSize: double
        -format: String
        +getDescription() String
    }
    
    class AudioBook {
        -duration: int
        -narrator: String
        +getDescription() String
    }
    
    class BookFactory {
        +createBook(type: String, title: String, author: String, genre: String) Book$
    }
    
    Book <|-- PaperBook
    Book <|-- EBook
    Book <|-- AudioBook
    BookFactory ..> Book : creates
```

#### UML Diagram - Strategy Pattern

```mermaid
classDiagram
    class SearchStrategy {
        <<interface>>
        +search(books: List~Book~, keyword: String) List~Book~
    }
    
    class SearchByName {
        +search(books: List~Book~, keyword: String) List~Book~
    }
    
    class SearchByAuthor {
        +search(books: List~Book~, keyword: String) List~Book~
    }
    
    class SearchByGenre {
        +search(books: List~Book~, keyword: String) List~Book~
    }
    
    class SearchContext {
        -strategy: SearchStrategy
        +setStrategy(strategy: SearchStrategy) void
        +executeSearch(books: List~Book~, keyword: String) List~Book~
    }
    
    SearchStrategy <|.. SearchByName
    SearchStrategy <|.. SearchByAuthor
    SearchStrategy <|.. SearchByGenre
    SearchContext o-- SearchStrategy
```

#### UML Diagram - Observer Pattern (Library)

```mermaid
classDiagram
    class LibraryObserver {
        <<interface>>
        +onNewBook(book: Book) void
        +onBookOverdue(book: Book, borrower: String) void
    }
    
    class LibraryStaff {
        -name: String
        +onNewBook(book: Book) void
        +onBookOverdue(book: Book, borrower: String) void
    }
    
    class LibraryMember {
        -name: String
        -interestedGenres: List~String~
        +onNewBook(book: Book) void
        +onBookOverdue(book: Book, borrower: String) void
    }
    
    LibraryObserver <|.. LibraryStaff
    LibraryObserver <|.. LibraryMember
```

#### UML Diagram - Decorator Pattern

```mermaid
classDiagram
    class Borrowable {
        <<interface>>
        +getBook() Book
        +getBorrowDays() int
        +getDescription() String
        +getCost() double
    }
    
    class BasicBorrow {
        -book: Book
        -baseDays: int
        +getBook() Book
        +getBorrowDays() int
        +getDescription() String
        +getCost() double
    }
    
    class BorrowDecorator {
        <<abstract>>
        #wrapped: Borrowable
        +getBook() Book
        +getBorrowDays() int
        +getDescription() String
        +getCost() double
    }
    
    class ExtendedBorrowDecorator {
        -extraDays: int
        +getBorrowDays() int
        +getDescription() String
        +getCost() double
    }
    
    class SpecialEditionDecorator {
        +getDescription() String
        +getCost() double
    }
    
    Borrowable <|.. BasicBorrow
    Borrowable <|.. BorrowDecorator
    BorrowDecorator <|-- ExtendedBorrowDecorator
    BorrowDecorator <|-- SpecialEditionDecorator
    BorrowDecorator o-- Borrowable : wraps
```

#### Files Structure

```
Tuan3/
└── LibraryManagement/
    ├── singleton/
    │   └── Library.java
    ├── factory/
    │   ├── Book.java
    │   ├── PaperBook.java
    │   ├── EBook.java
    │   ├── AudioBook.java
    │   └── BookFactory.java
    ├── strategy/
    │   ├── SearchStrategy.java
    │   ├── SearchByName.java
    │   ├── SearchByAuthor.java
    │   ├── SearchByGenre.java
    │   └── SearchContext.java
    ├── observer/
    │   ├── LibraryObserver.java
    │   ├── LibraryStaff.java
    │   └── LibraryMember.java
    ├── decorator/
    │   ├── Borrowable.java
    │   ├── BasicBorrow.java
    │   ├── BorrowDecorator.java
    │   ├── ExtendedBorrowDecorator.java
    │   └── SpecialEditionDecorator.java
    └── LibraryDemo.java
```

---

## Architectural Characteristics

| Characteristic | Applied In | Description |
|---------------|------------|-------------|
| **Modularity** | All patterns | Mỗi pattern được tổ chức trong package riêng |
| **Extensibility** | Factory, Strategy, Decorator | Dễ dàng thêm loại sách, chiến lược tìm kiếm, tính năng mượn mới |
| **Loose Coupling** | Observer, Adapter | Giảm sự phụ thuộc giữa các thành phần |
| **Single Responsibility** | All patterns | Mỗi class có một nhiệm vụ duy nhất |
| **Open/Closed** | All patterns | Mở rộng mà không cần sửa đổi code hiện tại |

---

## Verification Plan

### Automated Testing

Chạy file demo để kiểm tra từng pattern:

```bash
# Compile and run Problem 1 - Composite
cd d:/nam4\ hk2/KTTKPM/TH/Tuan3/Problem1_Composite
javac -d out filesystem/*.java ui/*.java CompositeDemo.java
java -cp out CompositeDemo

# Compile and run Problem 2 - Observer
cd d:/nam4\ hk2/KTTKPM/TH/Tuan3/Problem2_Observer
javac -d out stock/*.java task/*.java ObserverDemo.java
java -cp out ObserverDemo

# Compile and run Problem 3 - Adapter
cd d:/nam4\ hk2/KTTKPM/TH/Tuan3/Problem3_Adapter
javac -d out *.java
java -cp out AdapterDemo

# Compile and run Library Management System
cd d:/nam4\ hk2/KTTKPM/TH/Tuan3/LibraryManagement
javac -d out singleton/*.java factory/*.java strategy/*.java observer/*.java decorator/*.java LibraryDemo.java
java -cp out LibraryDemo
```

### Manual Verification

1. **Composite Pattern**: Kiểm tra cây thư mục hiển thị đúng cấu trúc, tổng kích thước folder đúng
2. **Observer Pattern**: Kiểm tra các observer nhận được thông báo khi subject thay đổi
3. **Adapter Pattern**: Kiểm tra chuyển đổi JSON ↔ XML thành công
4. **Library System**: Kiểm tra tất cả chức năng: thêm sách, tìm kiếm, mượn sách với decorator
