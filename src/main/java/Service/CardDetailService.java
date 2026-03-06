package Service;

import Entity.Card;
import Repository.CardRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

public class CardDetailService implements UserDetailsService {

    private CardRepository cardRepository;

    public CardDetailService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Card> optionalCard = cardRepository.findByCardNumber(UUID.fromString(username));
        if (optionalCard.isEmpty()){
            throw new UsernameNotFoundException(username);
        }
        return new CardDetail(optionalCard.get());
    }
}
