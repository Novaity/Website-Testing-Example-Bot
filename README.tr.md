# Akakçe Otomasyon & Test Süiti (SE 2226)

> 🇬🇧 **For English documentation, please [click here](README.md).**

## 📌 Proje Genel Bakış
**Akakçe Otomasyon & Test Süiti**, Türkiye'nin önde gelen fiyat karşılaştırma platformu **[Akakce.com](https://www.akakce.com/)**'un güvenilirliğini, işlevselliğini ve kararlılığını doğrulamak için tasarlanmış kapsamlı bir **Sistem Seviyesi Yazılım Test** projesidir.

**SE 2226 Yazılım Kalite Güvencesi** dersi kapsamında geliştirilen bu proje, **Selenium WebDriver** ve **JUnit 5** kullanılarak oluşturulmuş güçlü bir **Kara Kutu Test (Black-Box Testing)** stratejisi uygular. Arama algoritmaları, filtreleme mantığı ve kullanıcı hesap yönetimi gibi kritik özelliklerin farklı koşullar altında doğru çalıştığını doğrulamak için canlı prodüksiyon ortamında gerçek kullanıcı davranışlarını simüle eder.

Proje, test planlama, belgeleme ve yürütme aşamalarında **IEEE 29119** standartlarını takip eder ve tanımlanan kapsam için **%100 otomasyon kapsamına** ulaşmıştır.

---

## 📊 Test Sonuçları ve Performans Metrikleri
Sistem, aşağıdaki sonuçlarla (Haziran 2024 itibarıyla) titiz bir otomasyon test sürecinden geçmiştir:

| Metrik | Değer |
|:---|:---|
| **Toplam Test Senaryosu** | 45 |
| **Başarı Oranı** | **%95.5** (43 Geçti) |
| **Hata Oranı** | %4.5 (2 Kaldı) |
| **Otomasyon Kapsamı** | Tanımlanan kapsamın %100'ü |
| **Ortalama Test Süresi** | ~10.37 saniye/test |
| **Test Ortamı** | Chrome (Son Sürüm), Windows 10/11, Canlı Site |

> **Not:** Gözlemlenen küçük hatalar kaydedilmiş ve analiz edilmiştir; bunlar temel olarak canlı web sitesindeki anlık DOM değişikliklerine bağlanmış ve canlı site testlerinde sürekli seçici (selector) bakımının gerekliliğini vurgulamıştır.

---

## 🛠 Teknoloji Yığını
*   **Dil:** Java (JDK 17+)
*   **Otomasyon Kütüphanesi:** Selenium WebDriver
*   **Test Çerçevesi:** JUnit 5 (Jupiter)
*   **Derleme Aracı:** IntelliJ IDEA (Native Module System)
*   **Tasarım Deseni:** Modüler Otomasyon Tasarımı (Merkezi `BOT` Denetleyicisi)

---

## 🚀 Temel Özellikler ve Test Kapsamı

Proje, en kritik kullanıcı yolculuklarına odaklanmaktadır:

### 1. Kullanıcı Kimlik Doğrulama Sistemi
*   **Senaryolar:** Geçerli/Geçersiz Giriş, Şifre Maskeleme, "Beni Hatırla" Doğrulaması.
*   **Güvenlik:** Rate Limit (istek sınırlama) tespiti (Kaba kuvvet saldırı koruması doğrulaması), Oturum sürekliliği.
*   **Hata Yönetimi:** Mevcut olmayan hesaplar ve boş alanlar için hata mesajlarının doğrulanması.

### 2. Ürün Arama ve Keşif
*   **İşlevsellik:** Geçerli terimler, Yazım hataları, Markaya özgü sorgular (örn: "Apple", "Samsung").
*   **Girdi Doğrulama:** Özel karakterlerin ve boş arama sorgularının işlenmesi.
*   **Sonuç Doğruluğu:** Arama sonuçlarının sorgulanan anahtar kelimeleri içerdiğinin doğrulanması.

### 3. Gelişmiş Filtreleme Mantığı
*   **Fiyat Filtreleri:** Sınır Değer Analizi (Min/Max limitleri).
*   **Öznitelik Filtreleri:** Marka, Donanım Özellikleri ve Kategori filtrelerinin dinamik seçimi.
*   **Mantık Doğrulaması:** Filtrelerin mantıksal "VE" koşulları olarak çalıştığından emin olunması (örn: "Telefon" VE "Samsung" VE "Fiyat > 10000").

### 4. Hesap ve Sosyal Özellikler (Takip Et/Bırak)
*   **Takip Listesi Yönetimi:** Favorilere ürün ekleme, çıkarma ve toplu işlemler.
*   **Kısıt Testi:** **"Maksimum 200 ürün"** takip sınırı uyarısının tetiklendiğinin ve düzgün bir şekilde işlendiğinin doğrulanması.
*   **Misafir Erişimi:** Yetkisiz kullanıcıların kısıtlı işlemlere erişmeye çalıştığında giriş sayfasına yönlendirildiğinin doğrulanması.

### 5. Satıcı Yönlendirme ve Navigasyon
*   **Dış Bağlantılar:** "Satıcıya Git" butonlarının satıcı sayfalarını doğru şekilde yeni sekmelerde (`target="_blank"`) açtığının doğrulanması.
*   **Detay Sayfaları:** Listelemeden ürün detay görünümlerine doğru geçişin sağlanması.

---

## 📂 Mimari ve Tasarım
Proje, kodun yeniden kullanılabilirliğini ve sürdürülebilirliğini en üst düzeye çıkarmak için **Modüler Otomasyon Tasarımı** kullanır.

*   **`BOT.java` (Controller):** WebDriver için bir arayüz görevi görür ve tüm düşük seviyeli tarayıcı etkileşimlerini (tıklama, yazma, bekleme, sekme değiştirme) kapsüller. Çerez Banner'ları ve Pop-up'lar gibi standart UI desenlerini otomatik olarak yönetir.
*   **`Test Sınıfları`:** Test senaryoları için iş mantığını içerir ve BOT denetleyicisi tarafından döndürelen durum değişikliklerini doğrular (assert).

```
SE2226PROJE/
├── src/akakcebot/
│   └── BOT.java         # Merkezi Denetleyici ve Yardımcı Metotlar
├── Test/akakcebot/
│   ├── LoginTest.java   # Kimlik Doğrulama Senaryoları
│   ├── SearchTest.java  # Arama Mantığı Doğrulaması
│   ├── FilterTest.java  # Karmaşık Filtreleme Senaryoları
│   ├── FollowUnfollowTest.java # Limit ve Liste Yönetimi
│   └── OrderedTestSuite.java # Sıralı yürütme için orkestratör
```

**Önemli Teknik Uygulamalar:**
*   **Bekleme Stratejileri:** Dinamik DOM yüklenmesini yönetmek için `Thread.sleep` yerine `WebDriverWait` ve `ExpectedConditions` kullanılarak kararsız (flaky) testler önemli ölçüde azaltıldı.
*   **Dayanıklı Seçiciler (Selectors):** Canlı sitedeki küçük UI güncellemelerine karşı dayanıklı CSS/XPath seçicilerinin kullanılması.
*   **Pop-up Engelleyiciler:** Etkileşimlerden önce engelleri proaktif olarak temizleyen birleşik bir `closeCookieBannerIfExists()` yöntemi.

---

## 🏃‍♂️ Nasıl Çalıştırılır
1.  **Gereksinimler:** Java 17+, Chrome Tarayıcı.
2.  **Klonlama:** `git clone <repo-url>`
3.  **Kurulum:** Projeyi IntelliJ IDEA'da açın. Selenium ve JUnit 5 kütüphanelerinin sınıf yolunda (classpath) olduğundan emin olun.
4.  **Yürütme:** Tam regresyon süitini doğru bağımlılık sırasıyla çalıştırmak için `OrderedTestSuite.java` dosyasını çalıştırın veya izole hata ayıklama için bireysel test dosyalarını çalıştırın.

---
*Yasal Uyarı: Bu proje eğitim amaçlı olarak canlı bir prodüksiyon ortamında (`akakce.com`) gerçekleştirilmiştir. İnvazif eylemlerden (DoS, spam) kaçınılmış, yalnızca halka açık arayüz doğrulamasına odaklanılmıştır.*
