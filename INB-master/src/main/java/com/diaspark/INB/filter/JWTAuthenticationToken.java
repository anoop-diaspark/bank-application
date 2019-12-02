package com.diaspark.INB.filter;

import com.diaspark.INB.entity.UserPrincipal;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JWTAuthenticationToken extends AbstractAuthenticationToken {

    private final UserPrincipal userPrincipal;
    private final String token;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public JWTAuthenticationToken(Collection<? extends GrantedAuthority> authorities, UserPrincipal userPrincipal, String token) {
        super(authorities);
        this.userPrincipal = userPrincipal;
        this.token = token;
    }

    @Override
    public String getName() {
        return String.valueOf(userPrincipal.getId());
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userPrincipal;
    }
}
