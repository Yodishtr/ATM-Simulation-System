package com.yodishtr.ATM.Simulator.Service;

import com.yodishtr.ATM.Simulator.Entity.Card;
import com.yodishtr.ATM.Simulator.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class CardDetail implements UserDetails {

    private final Card card;
    private final User user;

    public CardDetail(Card card, User user) {
        this.card = card;
        this.user = user;
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
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.user.getRole().name()));
        return authorities;
    }

    public Card getCard() {
        return card;
    }
}
