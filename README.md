# Akakce Website Testing Bot

## 🇺🇸 English (American English)

### Project Overview

This project is an **automated website testing bot** developed for **akakce.com**, a popular price comparison website. It uses **Java**, **Selenium WebDriver**, and **JUnit 5** to test critical user flows such as login, search, filtering, following products, price comparison, and redirection to seller pages.

The project is designed as a **test automation framework**, not a production bot. Its primary goal is to verify that core functionalities of the website work correctly for both logged-in and guest users.

---

### Technologies Used

* **Java 17+**
* **Selenium WebDriver**
* **JUnit 5 (Jupiter)**
* **ChromeDriver**
* **Maven / Gradle compatible structure**

---

### Project Structure

```
novaity-website-testing-example-bot/
├── src/
│   └── akakcebot/
│       └── BOT.java
└── Test/
    └── akakcebot/
        ├── SearchTest.java
        ├── LoginTest.java
        ├── FilterTest.java
        ├── FollowUnfollowTest.java
        ├── PriceCompRedirectTest.java
        └── OrderedTestSuite.java
```

---

### Core Class: `BOT.java`

`BOT` is the **main helper class** that wraps Selenium operations.

Main responsibilities:

* Browser initialization and teardown
* Login & logout operations
* Product search
* Follow / unfollow products
* Filtering by price, brand, and features
* Handling popups and cookies
* Price comparison page navigation
* Tab and redirect handling

This class is reused across all test classes.

---

### Test Classes

#### 🔍 SearchTest

Tests the search functionality:

* Valid product search
* Empty search behavior
* Special character handling
* Case-insensitive search
* Partial keyword matching
* Typo tolerance
* Long input validation
* Brand-based search

---

#### 🔐 LoginTest

Tests authentication features:

* Successful login
* Incorrect password handling
* Empty credentials
* Remember Me (cookie persistence)
* Password masking
* Redirect after login
* Enter key login
* Non-existing account validation
* Rate limiting after multiple failures

---

#### 🧰 FilterTest

Tests product filtering:

* Price range filtering
* Brand filtering
* Feature filtering
* Combined filters
* No-result scenarios

---

#### ⭐ FollowUnfollowTest

Tests follow system:

* Follow product
* Prevent duplicate follows
* Follow without login redirect
* Follow persistence after logout/login
* Multi-follow feature
* Follow limit popup (200+ products)
* Unfollow single product
* Unfollow all products

---

#### 💰 PriceCompRedirectTest

Tests price comparison page:

* Visibility for logged-in and guest users
* Price sorting (ascending)
* Seller link validation
* Currency format validation
* Free shipping label detection
* Redirect to seller in new tab

---

### Running Tests

You can run all tests together using:

* `OrderedTestSuite`
* Or run individual test classes

Make sure that:

* Chrome browser is installed
* ChromeDriver version matches your Chrome version
* Selenium dependencies are properly added

---

### Requirements

**System Requirements:**

* Windows / macOS / Linux
* Google Chrome (latest stable recommended)
* Internet connection

**Software Requirements:**

* Java JDK **17 or higher**
* Maven or Gradle (optional but recommended)
* ChromeDriver (must match installed Chrome version)

**Libraries:**

* Selenium WebDriver
* JUnit 5 (Jupiter)

---

### Setup

1. **Clone the repository**

```bash
git clone https://github.com/novaity/website-testing-example-bot.git
cd website-testing-example-bot
```

2. **Install Java**
   Ensure Java is installed:

```bash
java -version
```

3. **Setup ChromeDriver**

* Download ChromeDriver compatible with your Chrome version
* Add it to your system PATH **or** place it in the project root

4. **Install dependencies**
   If using Maven:

```bash
mvn clean install
```

---

### How to Run

#### Run All Tests

You can run all tests using Maven:

```bash
mvn test
```

Or directly by running:

* `OrderedTestSuite.java`

#### Run Individual Tests

You can also run tests individually:

* `SearchTest`
* `LoginTest`
* `FilterTest`
* `FollowUnfollowTest`
* `PriceCompRedirectTest`

Each test will automatically:

* Launch Chrome
* Execute scenarios
* Close the browser

---

### Environment Configuration (.env)

To avoid hardcoding sensitive or environment-specific data (such as test credentials), this project supports a `.env`-style configuration approach.

#### Example `.env` file

Create a file named `.env` in the project root directory:

```env
# Test Account Credentials
TEST_EMAIL=testmailtesting@gmail.com
TEST_PASSWORD=123456789Test

# Browser Configuration
BROWSER=chrome
CHROME_DRIVER_PATH=./chromedriver.exe

# Timeouts (seconds)
IMPLICIT_WAIT=10
EXPLICIT_WAIT=10
```

> ⚠️ **Important:** Do NOT commit `.env` files to version control.

---

### Using `.env` Values in Code (Conceptual)

Environment variables can be accessed in Java using:

```java
String email = System.getenv("TEST_EMAIL");
String password = System.getenv("TEST_PASSWORD");
```

This approach improves:

* Security
* Environment portability
* CI/CD compatibility

---

### Notes

* This project is for **educational and testing purposes only**
* Hardcoded test credentials are used for demo/testing
* Website UI changes may break selectors

---

## 🇹🇷 Türkçe

### Proje Özeti

Bu proje, **akakce.com** sitesi için geliştirilmiş bir **otomatik web test botudur**. **Java**, **Selenium WebDriver** ve **JUnit 5** kullanılarak, sitenin temel kullanıcı akışlarının doğru çalışıp çalışmadığını test eder.

Bu bir **test otomasyon projesidir**, gerçek kullanım veya scraping amacıyla tasarlanmamıştır.

---

### Kullanılan Teknolojiler

* **Java 17+**
* **Selenium WebDriver**
* **JUnit 5 (Jupiter)**
* **ChromeDriver**
* **Maven / Gradle uyumlu yapı**

---

### Proje Yapısı

```
novaity-website-testing-example-bot/
├── src/
│   └── akakcebot/
│       └── BOT.java
└── Test/
    └── akakcebot/
        ├── SearchTest.java
        ├── LoginTest.java
        ├── FilterTest.java
        ├── FollowUnfollowTest.java
        ├── PriceCompRedirectTest.java
        └── OrderedTestSuite.java
```

---

### Ana Sınıf: `BOT.java`

`BOT` sınıfı Selenium işlemlerini merkezi olarak yöneten ana yardımcı sınıftır.

Sağladığı özellikler:

* Tarayıcı başlatma ve kapatma
* Giriş / çıkış işlemleri
* Ürün arama
* Ürün takip / takibi bırakma
* Fiyat, marka ve özellik filtreleme
* Cookie ve popup yönetimi
* Fiyat karşılaştırma sayfası işlemleri
* Yeni sekme ve yönlendirme kontrolü

---

### Test Sınıfları

#### 🔍 SearchTest (Arama Testleri)

* Geçerli ürün arama
* Boş arama davranışı
* Özel karakter kontrolü
* Büyük/küçük harf duyarsızlığı
* Kısmi eşleşme
* Yazım hatası toleransı
* Uzun arama girdileri
* Marka bazlı arama

---

#### 🔐 LoginTest (Giriş Testleri)

* Başarılı giriş
* Hatalı şifre kontrolü
* Boş alan kontrolü
* Beni hatırla (cookie testi)
* Şifre alanı gizleme
* Giriş sonrası yönlendirme
* Enter tuşu ile giriş
* Kayıtsız kullanıcı testi
* Rate limit testi

---

#### 🧰 FilterTest (Filtre Testleri)

* Fiyat aralığı filtresi
* Marka filtresi
* Özellik filtresi
* Kombine filtreler
* Sonuçsuz filtre senaryoları

---

#### ⭐ FollowUnfollowTest (Takip Testleri)

* Ürün takip etme
* Çift takip engeli
* Girişsiz takip yönlendirmesi
* Oturum sonrası takip kalıcılığı
* Çoklu takip
* 200+ takip limiti kontrolü
* Tekli takip bırakma
* Tümünü takipten çıkarma

---

#### 💰 PriceCompRedirectTest (Fiyat Karşılaştırma Testleri)

* Misafir ve girişli kullanıcı görünürlüğü
* Fiyat sıralama kontrolü
* Satıcı link doğrulama
* Para birimi formatı
* Ücretsiz kargo etiketi
* Yeni sekmede yönlendirme

---

### Testleri Çalıştırma

* `OrderedTestSuite` ile tüm testleri çalıştırabilirsiniz
* Veya testleri tek tek çalıştırabilirsiniz

Gereksinimler:

* Chrome yüklü olmalı
* ChromeDriver sürümü uyumlu olmalı
* Selenium bağımlılıkları ekli olmalı

---

### Gereksinimler

**Sistem Gereksinimleri:**

* Windows / macOS / Linux
* Google Chrome (tercihen güncel sürüm)
* İnternet bağlantısı

**Yazılım Gereksinimleri:**

* Java JDK **17 veya üzeri**
* Maven veya Gradle (önerilir)
* ChromeDriver (Chrome sürümü ile uyumlu olmalı)

**Kütüphaneler:**

* Selenium WebDriver
* JUnit 5 (Jupiter)

---

### Kurulum (Setup)

1. **Projeyi klonlayın**

```bash
git clone https://github.com/novaity/website-testing-example-bot.git
cd website-testing-example-bot
```

2. **Java kurulumunu kontrol edin**

```bash
java -version
```

3. **ChromeDriver ayarlayın**

* Chrome sürümünüze uygun ChromeDriver indirin
* Sistem PATH içine ekleyin veya proje dizinine koyun

4. **Bağımlılıkları yükleyin**
   Maven kullanıyorsanız:

```bash
mvn clean install
```

---

### Nasıl Çalıştırılır (How to Run)

#### Tüm Testleri Çalıştırma

```bash
mvn test
```

Veya doğrudan:

* `OrderedTestSuite.java`

#### Tekil Test Çalıştırma

* `SearchTest`
* `LoginTest`
* `FilterTest`
* `FollowUnfollowTest`
* `PriceCompRedirectTest`

Her test sırasında:

* Chrome otomatik açılır
* Test senaryoları çalışır
* Tarayıcı otomatik kapanır

---

### Ortam Değişkenleri (.env)

Hassas veya ortama bağlı bilgilerin (test hesabı, driver yolu vb.) kod içine gömülmemesi için `.env` tabanlı yapı önerilir.

#### Örnek `.env` Dosyası

Proje kök dizininde `.env` adında bir dosya oluşturun:

```env
# Test Hesabı Bilgileri
TEST_EMAIL=testmailtesting@gmail.com
TEST_PASSWORD=123456789Test

# Tarayıcı Ayarları
BROWSER=chrome
CHROME_DRIVER_PATH=./chromedriver.exe

# Zaman Aşımı Ayarları (saniye)
IMPLICIT_WAIT=10
EXPLICIT_WAIT=10
```

> ⚠️ **Uyarı:** `.env` dosyasını kesinlikle GitHub’a eklemeyin.

---

### Java İçinden `.env` Kullanımı (Mantıksal)

Java tarafında ortam değişkenleri şu şekilde okunur:

```java
String email = System.getenv("TEST_EMAIL");
String password = System.getenv("TEST_PASSWORD");
```

Bu yapı:

* Güvenliği artırır
* Ortamlar arası taşınabilirlik sağlar
* CI/CD süreçlerine uygundur

---

### Notlar

* Proje **eğitim ve test amaçlıdır**
* Test hesap bilgileri demo amaçlıdır
* Site arayüzü değişirse testler bozulabilir

---

✅ README.md başarıyla oluşturuldu.
