package spring.boot.rsa512.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.util.Date;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    private static final Path PRIVATE_KEY_PATH = Path.of("ec_private.key");
    private static final Path PUBLIC_KEY_PATH = Path.of("ec_public.key");

    private ECPublicKey publicKey;
    private ECPrivateKey privateKey;

    public JwtService() {
        try {
            if (Files.exists(PRIVATE_KEY_PATH) && Files.exists(PUBLIC_KEY_PATH)) {
                // TODO: implement load keys from files (PEM or DER)
                // Bạn có thể viết hàm load key từ file ở đây
                // hiện tại tạm để throw lỗi để bạn biết cần triển khai
                throw new RuntimeException("Key loading from file chưa triển khai");
            } else {
                generateAndSaveKeys();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize EC keys", e);
        }
    }

    private void generateAndSaveKeys() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp521r1"); // ES512
        keyPairGenerator.initialize(ecSpec);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        this.privateKey = (ECPrivateKey) keyPair.getPrivate();
        this.publicKey = (ECPublicKey) keyPair.getPublic();

        System.out.println("EC Key Pair generated successfully.");

        // TODO: save keys to file để tái sử dụng (định dạng PEM hoặc DER)
        // Bạn có thể dùng BouncyCastle hoặc thư viện khác để xuất PEM
    }

   public String generateToken(String username, String role) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60); // 1 giờ

    return Jwts.builder()
            .setSubject(username)
            .claim("role", role) // 👈 Thêm role vào payload
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(privateKey, SignatureAlgorithm.ES512)
            .compact();
}


    @SuppressWarnings("UseSpecificCatch")
    public String validateTokenAndGetUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            throw new RuntimeException("Invalid or expired JWT token");
        }
    }

    public ECPublicKey getPublicKey() {
        return publicKey;
    }

    public ECPrivateKey getPrivateKey() {
        return privateKey;
    }

  public boolean isTokenValid(String token) {
    try {
        Jwts.parserBuilder()
            .setSigningKey(publicKey)
            .build()
            .parseClaimsJws(token); // Nếu không exception là token hợp lệ
        return true;
    } catch (Exception e) {
        System.out.println("[JwtService] Token không hợp lệ: " + e.getMessage());
        return false;
    }
}


    public String extractUsername(String token) {
    try {
        return Jwts.parserBuilder()
                .setSigningKey(publicKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    } catch (Exception e) {
        throw new RuntimeException("Không thể trích xuất username từ token", e);
    }
}

}
