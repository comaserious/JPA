package mapping.section03.compositekey.subsection01.embeddedid;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_like")
// like 는 예약어이다 따라서 like 테이블은 만들수 없다
// 그렇기 때문에 테이블의 이름은 유의해서 작성하자
public class Like {

    /*세트로 묶어둔 memberNo, bookNo 를 ID(pk)로서 사용하겠다*/
    @EmbeddedId
    private LikedCompositeKey likeInfo;

    protected Like(){}

    public Like(LikedCompositeKey likeInfo) {
        this.likeInfo = likeInfo;
    }


}
