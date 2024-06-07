package mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LikedBookNo {
    @Column(name="liked_book_no")
    private int likedBookNo;

    protected LikedBookNo(){}

    protected LikedBookNo(int likedBookNo) {
        this.likedBookNo = likedBookNo;
    }


}
