package vn.edu.fpt.elios_user_service.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ProxyAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String emailHeader = request.getHeader("X-Auth-Request-Email");
        String groupsHeader = request.getHeader("X-Auth-Request-Groups");

        log.debug("Auth Headers: email={}, groups={}", emailHeader, groupsHeader);

        if (emailHeader != null && groupsHeader != null) {

            Set<GrantedAuthority> authorities = Arrays.stream(groupsHeader.split(","))
                    .map(String::trim)
                    .filter(s -> s.startsWith("role:"))
                    .map(s -> s.replace("role:", ""))
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            CustomAuthenticationToken authentication =
                    new CustomAuthenticationToken(emailHeader, authorities);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}