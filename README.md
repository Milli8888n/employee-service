# Employee Service

Ứng dụng Spring Boot REST quản lý nhân viên FPT với H2 Database.

## Chạy ứng dụng

Yêu cầu Java 17 và Maven.

```bash
mvn spring-boot:run
```

Mở:

- Web client: http://localhost:8080
- API: http://localhost:8080/api/employees
- H2 Console: http://localhost:8080/h2-console

Thông tin H2:

- JDBC URL: `jdbc:h2:mem:employeedb`
- Username: `sa`
- Password: để trống

## Chạy test suite

```bash
mvn test
``>
