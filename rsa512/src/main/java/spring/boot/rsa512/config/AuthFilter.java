package spring.boot.rsa512.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import spring.boot.rsa512.model.User;
import spring.boot.rsa512.repository.UserRepository;
import spring.boot.rsa512.service.JwtService;

@Component
@RequiredArgsConstructor
public class AuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @SuppressWarnings({"null", "UseSpecificCatch"})
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Log đường dẫn request
        System.out.println("[AuthFilter] Request path: " + request.getServletPath());

        // Bỏ qua filter cho các endpoint thuộc /api/auth (ví dụ: login, register)
        if (request.getServletPath().startsWith("/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy header Authorization
        String authHeader = request.getHeader("Authorization");

        // Nếu không có token hoặc không bắt đầu bằng "Bearer " thì bỏ qua xác thực
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy token (bỏ "Bearer ")
        String token = authHeader.substring(7);

        try {
            // Kiểm tra token hợp lệ
            if (!jwtService.isTokenValid(token)) {
                // Token không hợp lệ
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token không hợp lệ hoặc hết hạn");
                return;
            }

            // Lấy username từ token
            String username = jwtService.extractUsername(token);
            System.out.println("[AuthFilter] Token hợp lệ, username: " + username);

            // Tìm user theo username
            User user = userRepository.findByUsername(username).orElse(null);

            if (user == null) {
                // Không tìm thấy user tương ứng
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User không tồn tại");
                return;
            }

            UserDetails userDetails = org.springframework.security.core.userdetails.User
    .withUsername(user.getUsername())
    .password(user.getPassword()) // không quan trọng vì không xác thực ở đây
    .authorities("ROLE_" + user.getRole()) // thêm role đúng chuẩn Spring
    .build();

UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
    userDetails,
    null,
    userDetails.getAuthorities()
);

SecurityContextHolder.getContext().setAuthentication(authentication);


        } catch (Exception e) {
            // Lỗi khi xử lý token
            System.out.println("[AuthFilter] Lỗi xác thực JWT: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Lỗi xác thực JWT");
            return;
        }

        // Cho phép request tiếp tục đi qua filter chain
        filterChain.doFilter(request, response);
    }
}
