# E-Commerce Spring boot project
# Watch Store
## Giới thiệu
- Đồ án giữa kì được xây dựng với Spring boot(Backend) và HTML,CSS,JS(Frontend)
- Nguyên tắc phát triển phần mềm:
  + KISS (Keep It Simple, Stupid): Giữ mã nguồn đơn giản và dễ hiểu, tránh phức tạp hóa quá mức.
  + DRY (Don't Repeat Yourself): Tránh lặp lại mã nguồn, thay vào đó, sử dụng các hàm hoặc lớp tái sử dụng được.
- Mô Hình
  + Waterfall: Mô hình phát triển phần mềm theo từng bước tuần tự (phân tích, thiết kế, phát triển, kiểm thử). Mỗi bước phải hoàn thành trước khi bước tiếp theo bắt đầu.
  + Model-View-Controller (MVC): Ứng dụng tuân theo mô hình MVC, trong đó:
    + Model: Đại diện cho dữ liệu và logic nghiệp vụ.
    + View: Đại diện cho giao diện người dùng.
    + Controller: Xử lý các yêu cầu từ người dùng và trả về kết quả thích hợp.
  + Singleton Pattern: Áp dụng trong các dịch vụ như kết nối cơ sở dữ liệu để đảm bảo chỉ tạo ra một thể hiện duy nhất trong suốt ứng dụng.
  + Xác thực và phân quyền người dùng sủ dụng Spring Security trong Backend.
- Thực tiễn phát triển phần mềm:
  + Continuous Integration (CI): Tích hợp mã nguồn vào hệ thống thường xuyên (ngày hoặc thậm chí nhiều lần trong ngày) để phát hiện sớm lỗi và đảm bảo rằng phần mềm luôn sẵn sàng để triển khai.
  + Continuous Delivery (CD): Tự động hóa quá trình triển khai phần mềm để phần mềm có thể được phát hành vào bất kỳ lúc nào.
- ERD diagram:
![ERD-Diagram](https://github.com/user-attachments/assets/7aeb64ac-cc99-40a8-9634-7e2f7ef1f739)
- Cấu trúc thư mục:
![Screenshot 2024-11-09 151031](https://github.com/user-attachments/assets/5f9fda55-b8ae-48b3-9ae0-ae4c58d2f2da)
- Giải thích cấu trúc thư mục:
  + /controller: Chứa các controller API xử lý các yêu cầu HTTP và trả về phản hồi.
  + /repository: Quản lý việc truy cập vào cơ sở dữ liệu, thường sử dụng Spring Data JPA
  + /service: Chứa các dịch vụ xử lý logic nghiệp vụ chính của ứng dụng.
  + /resources: Chứa các tệp cấu hình, tài nguyên tĩnh và template.
  + /entity: chứa các thuộc tính của các đối tượng trong đồ án
  + /untils: chứa phương thức dùng để tải lên hình ảnh
  + /DAO: chứa cấu hình cho đối tượng User và Role
  + /configuration: chứa các tệp liên quan đến bảo bật, xác thực và phân quyền cho người dùng.
  ## Các bước để chạy đồ án trên máy tính cục bộ:
  - Sử dụng git để tải đồ án về máy: git clone https://github.com/TDKhanhMinh/Midterm-Java.git
  - Chạy Mysql với port 3307
  - Thực hiện đoạn SQL script đã được cung cấp(e-commerce-new-data-script.sql)
    + ![Screenshot 2024-11-09 154103](https://github.com/user-attachments/assets/4a1ef5a5-eba3-45ee-95ae-e46fc395ee10)
  - Sau khi chạy đoạn SQL script trên, dữ liệu sẽ được thêm vào mặc định 1 người dùng là admin(admin@admin.com,admin)
  - Người dùng mở trình duyệt và nhập localhost:8080
  - Sau đó đăng nhập bằng tên tài khoản và mật khẩu đã được cung cấp để tiếp tục(bắt buộc phải thực hiện bước này đầu tiên)


  
