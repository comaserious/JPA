package mapping.section03.compositekey.subsection02.idclass;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartRegistService {

    private CartRepository cartRepository;

    @Autowired
    public CartRegistService(CartRepository cartRepository){
        this.cartRepository=cartRepository;
    }

    @Transactional
    public void addItemToCart(CartDTO cartDTO) {

        Cart newCart = new Cart(cartDTO.getCartOwnerMemberNo(),cartDTO.getAddedBookNo(),cartDTO.getQuantity());

        cartRepository.save(newCart);
    }
}
