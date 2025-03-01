# Spring Security Configuration

## Overview
This project uses **Spring Security** to restrict access to specific pages in a Spring Boot application. The goal is to allow unrestricted access to some pages while requiring authentication for others.

---







### 2. Create a Security Configuration Class
Create a new Java class inside the `com.spring.security` package named `Config.java`. This class will configure Spring Security to:

✅ Allow unrestricted access to `/`, `/cart`, and `/login`.
🔒 Require authentication for `/checkout`.



#### Explanation:
- `@Configuration` and `@EnableWebSecurity`: Enables Spring Security.
- `SecurityFilterChain` configures the authentication rules.
- `requestMatchers("/", "/cart", "/login").permitAll()`: These pages are accessible to all users.
- `requestMatchers("/checkout").authenticated()`: Users must log in to access `/checkout`.
- `formLogin()`: Sets up a login page at `/login` and redirects to `/` upon success.
- `logout()`: Logs out the user and redirects to `/`.

---

## Testing the Security Configuration
1. **Run the Spring Boot Application.**
2. **Access `http://localhost:8080/` → No authentication needed.**
3. **Access `http://localhost:8080/cart` → No authentication needed.**
4. **Access `http://localhost:8080/login` → Custom login page appears.**
5. **Access `http://localhost:8080/checkout` → Redirects to login page if not logged in.**
6. **Log in using the username & password (e.g., `admin` / `admin123`).**
7. **After login, you can access `/checkout`.**

---

## Future Enhancements
- Add **user roles** (admin, user, etc.).
- Use a **database** to store user credentials instead of hardcoded properties.
- Implement **JWT-based authentication** for better security.
