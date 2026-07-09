package com.uca.pncparcialfinalhotel.services.impl;

import com.uca.pncparcialfinalhotel.domain.dto.request.LoginRequest;
import com.uca.pncparcialfinalhotel.domain.dto.request.RefreshTokenRequest;
import com.uca.pncparcialfinalhotel.domain.dto.response.AuthResponse;
import com.uca.pncparcialfinalhotel.security.JwtService;
import com.uca.pncparcialfinalhotel.security.UserDetailsServiceImpl;
import com.uca.pncparcialfinalhotel.services.AuthService;
import com.uca.pncparcialfinalhotel.shared.exception.HotelException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());

        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        return new AuthResponse(
                accessToken,
                refreshToken,
                userDetails.getUsername(),
                userDetails.getAuthorities().iterator().next().getAuthority()
        );
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        String token = request.refreshToken();

        try {
            // Validamos que sea refresh token y no access token
            if (!jwtService.isRefreshToken(token)) {
                throw HotelException.unauthorized("El token proporcionado no es un refresh token");
            }

            String username = jwtService.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtService.isTokenValid(token, userDetails)) {
                throw HotelException.unauthorized("Refresh token inválido o expirado");
            }

            String newAccessToken = jwtService.generateAccessToken(userDetails);
            String newRefreshToken = jwtService.generateRefreshToken(userDetails);

            return new AuthResponse(
                    newAccessToken,
                    newRefreshToken,
                    userDetails.getUsername(),
                    userDetails.getAuthorities().iterator().next().getAuthority()
            );

        } catch (HotelException ex) {
            throw ex;
        } catch (Exception ex) {
            throw HotelException.unauthorized("Refresh token inválido o expirado");
        }
    }
}