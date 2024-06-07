package mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LikedMemberNo {

    @Column(name="liked_member_no")
    private int likedMemberNo;

    protected LikedMemberNo(){}

    protected LikedMemberNo(int likedMemberNo) {
        this.likedMemberNo = likedMemberNo;
    }
}
