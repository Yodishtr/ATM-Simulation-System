package Service;

import Entity.Card;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class CardDetail implements UserDetails {

    private final Card card;

    public CardDetail(Card card) {
        this.card = card;
    }

    @Override
    public String getUsername() {
        return card.getCardNumber().toString();
    }

    @Override
    public String getPassword() {
        return card.getPin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return card.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return card.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return card.isActive();
    }

    @Override
    public boolean isEnabled() {
        return card.isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }
}
