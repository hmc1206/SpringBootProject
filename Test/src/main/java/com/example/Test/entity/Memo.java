package com.example.Test.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.ToString;

@Entity //엔티티 객체라는 것을 의미함 또한, Entity가 있는 클래스는 옵션에 따라서 자동으로 테이블을 생성할 수도 있다
@Table(name="tbl_memo") // 어떠한 테이블로 생성할 것인지에 대한 정보를 담기 위한 어노테이션
@ToString
@Getter  //Getter 메서드를 생성
@Builder //객체를 생성할 수 있게 처리함
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    @Id //자동으로 생성되는 번호 사용하기 위한 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK를 자동으로 생성하고자 할때 사용함(키 생성 전략), 'auto increment'를 기본으로 사용해서 새로운 레코드가 기록될 때마다 다른 번호를 가질 수 있도록 처리함
    private Long mno;

    @Column(length = 200, nullable = false) //다양한 속성을 지정할 수 있다(nullable, name,length 등을 이용해서 데이터베이스의 칼럼에 필요한 정보를 제공함)
    private String memoText;
}
