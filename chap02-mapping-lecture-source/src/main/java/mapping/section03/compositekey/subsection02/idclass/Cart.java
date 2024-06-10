package mapping.section03.compositekey.subsection02.idclass;

import jakarta.persistence.*;

@Entity
@Table(name= "tbl_cart")
@IdClass(CartCompositeKey.class)
public class Cart {

    @Id
    @Column(name="cart_owner")
    private int cartOwner;

    @Id
    @Column(name="added_book")
    private int addedBook;

    @Column(name="quantity")
    private int quantity;

    protected Cart(){}

    public Cart(int cartOwner, int addedBook, int quantity) {

        this.cartOwner = cartOwner;
        this.addedBook = addedBook;
        this.quantity = quantity;
    }

    public int getCartOwner() {
        return cartOwner;
    }

    public int getAddedBook() {
        return addedBook;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartOwner=" + cartOwner +
                ", addedBook=" + addedBook +
                ", quantity=" + quantity +
                '}';
    }

    // Entity 는 DB 처럼 취급 하기 때문에 데이터를 함부로 집어 넣는건 지양한다 따라서 웬만하면 setter 를 사용하지 않고 DTO 를 거쳐서 넣는걸 지향한다
}
