package mapping.section03.compositekey.subsection01.embeddedid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LikeBookService {

    @Autowired
    private LikeBookRepository likeBookRepository;


    @Transactional
    public void generateLikeBook(LikeDTO likeDTO) {

        Like like = new Like(new LikedCompositeKey(new LikedBookNo(likeDTO.getLikedBookNo()),
                new LikedMemberNo(likeDTO.getLikedMemberNo())));

        likeBookRepository.save(like);
    }
}
