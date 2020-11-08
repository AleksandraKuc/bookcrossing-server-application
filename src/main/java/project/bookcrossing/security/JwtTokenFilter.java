package project.bookcrossing.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import project.bookcrossing.exception.CustomException;

// We should use OncePerRequestFilter since we are doing a database call, there is no point in doing this more than once
public class JwtTokenFilter extends OncePerRequestFilter {

	private JwtTokenProvider jwtTokenProvider;

	public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
		System.out.println("doFilterInternal");
		String token = jwtTokenProvider.resolveToken(httpServletRequest);
		try {
			System.out.println("check token");
			if (token != null && jwtTokenProvider.validateToken(token)) {
				System.out.println("1");
				Authentication auth = jwtTokenProvider.getAuthentication(token);
				System.out.println("2");
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (CustomException ex) {
			System.out.println("błąd");
			//this is very important, since it guarantees the user is not authenticated at all
			SecurityContextHolder.clearContext();
			httpServletResponse.sendError(ex.getHttpStatus().value(), ex.getMessage());
			return;
		}
		System.out.println("done?");
		filterChain.doFilter(httpServletRequest, httpServletResponse);
	}

}

