package com.example.SpringBoot_Backend_Sercurity.Security_Config;

import com.example.SpringBoot_Backend_Sercurity.DTO.Reponse.InstrospectReponse;
import com.example.SpringBoot_Backend_Sercurity.DTO.Request.InstrospectRequest;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret}")
    private  String jwtSecret ;
    @Value("${jwt.expiration}")
    private  long jwtExpirationMs ;

    public String generateToken(Authentication authentication) {
        User userPrincipal =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        // Lấy authorities của người dùng dưới dạng List<String>
        List<String> authorities = userPrincipal.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        try {
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(userPrincipal.getUsername()) // Set subject là tên người dùng
                    .claim("authorities", authorities) // Thêm quyền vào token
                    .issuer("Security-springboot") // ten project
                    .issueTime(new Date()) // Thời gian phát hành
                    .expirationTime(new Date(System.currentTimeMillis() + jwtExpirationMs))
                    .build();

            // Tạo JWT và ký bằng HMAC
            SignedJWT signedJWT = new SignedJWT(
                    new JWSHeader(JWSAlgorithm.HS512), // Sử dụng HS512 làm thuật toán ký
                    claimsSet
            );

            // Ký JWT
            signedJWT.sign(new MACSigner(jwtSecret.getBytes()));

            return signedJWT.serialize(); // Trả về JWT đã ký
        } catch (Exception e) {
            throw new RuntimeException("Cannot generate token", e);
        }
    }

    // Lay username tu token
    public String getUserNameByToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token) ;
            return signedJWT.getJWTClaimsSet().getSubject() ;
        } catch (ParseException e) {
            throw new RuntimeException("Invalid token : " +e );
        }
    }

    // Lay roles tu token
    public List<String> getRolesFromToken(String token) {
        try{
            SignedJWT signedJWT = SignedJWT.parse(token) ;
            return (List<String>) signedJWT.getJWTClaimsSet().getClaim("authorities");
        } catch (Exception e) {
            return List.of();
        }
    }

    // Kiem tra token
    public InstrospectReponse introspect (InstrospectRequest instrospectRequest) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(instrospectRequest.getToken());

            // Kiểm tra chữ ký
            JWSVerifier verifier = new MACVerifier(jwtSecret.getBytes());
            if (!signedJWT.verify(verifier)) {
                return new InstrospectReponse(false);
            }

            // Kiểm tra hạn token
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            return new InstrospectReponse(expiration.after(new Date()));
        } catch (Exception e) {
            return new InstrospectReponse(false);
        }
    }

}
