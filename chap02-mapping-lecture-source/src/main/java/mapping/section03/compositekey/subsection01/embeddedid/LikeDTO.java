package mapping.section03.compositekey.subsection01.embeddedid;

public class LikeDTO {

    private int likedBookNo;
    private int likedMemberNo;

    protected  LikeDTO(){}

    public LikeDTO(int likedBookNo, int likedMemberNo) {
        this.likedBookNo = likedBookNo;
        this.likedMemberNo = likedMemberNo;
    }

    public int getLikedMemberNo() {
        return likedMemberNo;
    }

    public void setLikedMemberNo(int likedMemberNo) {
        this.likedMemberNo = likedMemberNo;
    }

    public int getLikedBookNo() {
        return likedBookNo;
    }

    public void setLikedBookNo(int likedBookNo) {
        this.likedBookNo = likedBookNo;
    }

    @Override
    public String toString() {
        return "LikeDTO{" +
                "likedBookNo=" + likedBookNo +
                ", likedMemberNo=" + likedMemberNo +
                '}';
    }
}
