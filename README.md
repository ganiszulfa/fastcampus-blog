# Welcome

Selamat datang!
Repo ini adalah pelengkap course 1 dan 2.
Ini adalah aplikasi blog sederhana yang dibangun menggunakan Java 21, Spring Boot, dan Maven.
Aplikasi ini memiliki fitur untuk membuat, mengedit, dan menghapus posting blog, autentikasi pengguna, dan lainnya.

## Table of Contents

- [Technologies](#technologies)
- [Installation](#installation)
- [Usage](#usage)
- [FAQ](#faq)

## Technologies

- **Java**: 21
- **Framework**: Spring Boot
- **Build Tool**: Maven
- **Database**: MySQL 8
- **Cache**: Redis
- **Containerization**: Docker
- **Container Management**: Rancher Desktop
- **IDE**: IntelliJ IDEA

## Installation

1. **Clone the Repository**:

   ```bash
   git clone git@github.com:ganiszulfa/fastcampus-blog.git
   cd fastcampus-blog
   ```

2. **Run MySQL & Redis**:

   - Go to `docker-compose.yml` and run all containers.

3. **Configure MySQL and secret**:

   - Pergi ke http://localhost:3307/ and login with `root` and `example`
   - Di PHPMyAdmin, buat database: `blog` atau sesuaikan dengan value di `application-dev.properties`
   - Tambahkan file `secret.properties` isinya sesuaikan dengan `SecretProperties.java`

3. **Configure Run Configs**:
   - Buka Run Configurations di Intellij (run -> edit configurations)
   - Set environment variables dengan `SPRING_PROFILES_ACTIVE=dev`
   - Ok dan apply

## Usage

- Jika sudah di configure di IDE, cukup jalankan dengan klik run di menu bar atas. Atau SHIFT+F10 untuk shortcutnya.
- Bisa diakses di `http://localhost:8080`.

## FAQ

**Q**: Saya dapet error `org.springframework.beans.factory.BeanDefinitionStoreException: I/O failure while processing configuration class [com.fastcampus.blog.properties.SecretProperties]`

**A**: Tambahkan file `src/main/resources/secret.properties` isinya sesuaikan dengan `src/main/java/com/fastcampus/blog/properties/SecretProperties.java`

**Q**: Saya dapet error `org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource`

**A**: Pastikan container mysql sudah jalan.
