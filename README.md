# Akakçe Automation & Testing Suite (SE 2226)

> 🇹🇷 **Türkçe dokümantasyon için [buraya tıklayın](README.tr.md).**

## 📌 Project Overview
**Akakce Automation & Testing Suite** is a robust **System-Level Software Testing** project designed to validate the reliability, functionality, and responsiveness of **[Akakce.com](https://www.akakce.com/)**, Turkey's leading price comparison platform.

Developed as part of the **SE 2226 Software Quality Assurance** course, this project implements a comprehensive **Black-Box Testing** strategy using **Selenium WebDriver** and **JUnit 5**. It simulates real-world user behaviors on the live production environment to ensure critical features—such as search algorithms, filtering logic, and user account management—perform under varying conditions.

The project follows **IEEE 29119** standards for test planning, documentation, and execution, achieving a **100% automation coverage** for the scope defined.

---

## 📊 Test Results & Performance Metrics
The system underwent rigorous automation testing with the following outcomes (as of June 2024):

| Metric | Value |
|:---|:---|
| **Total Test Cases** | 45 |
| **Pass Rate** | **95.5%** (43 Passed) |
| **Fail Rate** | 4.5% (2 Failed) |
| **Automation Coverage** | 100% of defined scope |
| **Average Test Duration** | ~10.37 seconds per test |
| **Test Environment** | Chrome (Latest), Windows 10/11, Live Production Site |

> **Note:** The minor failures observed were logged and analyzed, primarily attributed to transient DOM changes on the live website, highlighting the need for continuous selector maintenance in live-site testing.

---

## 🛠 Technology Stack
*   **Language:** Java (JDK 17+)
*   **Automation Library:** Selenium WebDriver
*   **Test Framework:** JUnit 5 (Jupiter)
*   **Build Tool:** IntelliJ IDEA (Native Module System)
*   **Design Pattern:** Modular Automation Design (Centralized `BOT` Controller)

---

## 🚀 Key Features & Test Scope

The project focuses on the most critical user journeys:

### 1. User Authentication System
*   **Scenarios:** Valid/Invalid Login, Password Masking, "Remember Me" Token Validation.
*   **Security:** Rate Limit detection (Brute-force protection verification), Session persistence.
*   **Error Handling:** Validation of error messages for non-existent accounts and empty fields.

### 2. Product Search & Discovery
*   **Functionality:** Valid terms, Typos, Brand-specific queries (e.g., "Apple", "Samsung").
*   **Input Validation:** Handling of special characters and empty search queries.
*   **Result Accuracy:** Verifying that search results contain the queried keywords.

### 3. Advanced Filtering Logic
*   **Price Filters:** Boundary Value Analysis (Min/Max limits).
*   **Attribute Filters:** Dynamic selection of Brand, Hardware Specs, and Category filters.
*   **Logic Verification:** Ensuring filters act as logical "AND" conditions (e.g., "Phone" AND "Samsung" AND "Price > 10000").

### 4. Account & Social Features (Follow/Unfollow)
*   **Watchlist Management:** Adding items to favorites, removing them, and bulk actions.
*   **Constraint Testing:** Verifying the **"Max 200 items"** follow limit popup is triggered and handled gracefully.
*   **Guest Access:** Ensuring unauthorized users are redirected to login when attempting restricted actions.

### 5. Vendor Redirection & Navigation
*   **External Linking:** Verifying that "Go to Seller" buttons correctly open vendor pages in new tabs (`target="_blank"`).
*   **Detail Pages:** Correct navigation from listing to product detail views.

---

## 📂 Architecture & Design
The project utilizes a **Modular Automation Design** to maximize code reusability and maintainability.

*   **`BOT.java` (Controller):** Acts as a facade for the WebDriver, encapsulating all low-level browser interactions (click, type, wait, switch tab). It handles standard UI patterns like Cookie Banners and Popups automatically.
*   **`Test Classes`:** Contains the business logic for test scenarios, asserting state changes returned by the BOT controller.

```
SE2226PROJE/
├── src/akakcebot/
│   └── BOT.java         # Centralized Controller & Helper Methods
├── Test/akakcebot/
│   ├── LoginTest.java   # Authentication Scenarios
│   ├── SearchTest.java  # Search Logic Verification
│   ├── FilterTest.java  # Complex Filtering Scenarios
│   ├── FollowUnfollowTest.java # Limit & List Management
│   └── OrderedTestSuite.java # Orchestrator for ordered execution
```

**Key Technical Implementations:**
*   **Wait Strategies:** Replaced `Thread.sleep` with `WebDriverWait` and `ExpectedConditions` to handle dynamic DOM loading, significantly reducing flaky tests.
*   **Resilient Selectors:** Usage of robust CSS/XPath selectors to survive minor UI updates on the live site.
*   **Popup Interceptors:** A unified method `closeCookieBannerIfExists()` that proactively clears obstructions before interactions.

---

## 🏃‍♂️ How to Run
1.  **Prerequisites:** Java 17+, Chrome Browser.
2.  **Clone:** `git clone <repo-url>`
3.  **Setup:** Open in IntelliJ IDEA. Ensure Selenium and JUnit 5 libraries are in the classpath.
4.  **Execute:** Run `OrderedTestSuite.java` to execute the full regression suite in the correct dependency order, or run individual test files for isolated debugging.

---
*Disclaimer: This project was conducted for educational purposes on a live production environment (`akakce.com`). Care was taken to avoid invasive actions (DoS, spam), focusing only on public interface validation.*
