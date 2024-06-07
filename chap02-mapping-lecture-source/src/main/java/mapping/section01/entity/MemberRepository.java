package mapping.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    /*Entity 매니저를 주입받아 영속성 컨텍스트에 우리가
    * 만든 Entity 를 관리 할 수 있도록 한다*/
    @PersistenceContext
    private EntityManager manager;


    public void save(Member member) {

        manager.persist(member);
    }

    public String findNameById(String memberId) {

        String query = "select m.memberName from entityMember m where m.memberId = '"+memberId+"'";

        return manager.createQuery(query,String.class).getSingleResult();
    }
}
