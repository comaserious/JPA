package mapping.section01.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/*필기.
*  클래스 선언부 위에 위치해서 JPA 에서 사용되는 엔티티 클래스임을 표시한다
*  이는 해당 클래스와 DB 의 테이블 매핑을 의미한다
*  - 기본 생성자는 필수로 작성해야 한다.
*  - final 클래스, enum, interface, inner class 에서는 사용할 수 없다
*  - 저장할 필드에 final 을 사용해서는 안된다*/
@Entity(name ="entityMember")
@Table(name="tbl_member", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"member_id","phone"})

})
public class Member {

    /*필기.
    *  @Column 속성
    *  - name : 매핑할 테이블의 컬럼 이름 지정
    *  - nullable : null 값 허용 여부 설정 = not null 제약조건에 해당한다. 기본값은 true 즉 null 값을 허용한다
    *  - unique : 컬럼의 유일성 제약 조건  기본값은 false 즉 유일성을 부여 하지 않는다, 두개 이상의 컬럼에 unique 제약 조건을 설정하기 위해서는
    *             @Table 어노테이션 속성에 uniqueContraints 속성을 추가해야한다
    *  - columnDefinition : 직접 컬럼의 DDL 을 지정할 수 있다
    *  - length : 문자열의 길이 = String 타입에서만 사용 (default: 255)
    *  */

    @Id
    @Column(name = "member_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberNo;

    @Column(name="member_id", nullable = false, unique = true, columnDefinition = "varchar(10)")
    private String memberId;

    @Column(name = "member_pwd", nullable = false)
    private String memberPwd;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "phone", unique = true)
    private String phone;

    @Column(name = "address", length = 900)
    private String address;

    @Column(name = "enroll_date")
    private LocalDateTime enrollDate;

    /*필기.
    *  @Enumerated
    *  - enum 타입을 매핑 하기 위해서 사용한다
    *  - ORDINAL : Enum 타입을 순서로 매핑 ( 필드가 작성된 순서대로 위에서 아래로 ) 장점 : DB 에 저장되는 데이터의 크가 작다; 단점 : 그 값이 무엇을 의미하는지 알기 쉽지 않다
    *  - STRING : Enum 타입을 문자열로 매핑*/
    @Column(name = "member_role")
    @Enumerated(EnumType.STRING)
    // 정해진 값을 받고 싶을때 그외의 값은 초기화가 불가능하게 만들기 위해서 Enum 을 사용한다
    private MemberRole memberRole;

    @Column(name="status",columnDefinition = "char(1) default 'Y'")
    private String status;

    protected Member(){}

    public Member(String memberId, String memberPwd, String memberName, String phone, String address, LocalDateTime enrollDate, MemberRole memberRole, String status) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberName = memberName;
        this.phone = phone;
        this.address = address;
        this.enrollDate = enrollDate;
        this.memberRole = memberRole;
        this.status = status;
    }


}
