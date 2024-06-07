package mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class LikedCompositeKey {

    @Embedded
    private LikedBookNo likedBookNo;
    @Embedded
    private LikedMemberNo memberNo;

    protected LikedCompositeKey(){}

    protected LikedCompositeKey(LikedBookNo likedBookNo, LikedMemberNo memberNo) {
        this.likedBookNo = likedBookNo;
        this.memberNo = memberNo;
    }
}
