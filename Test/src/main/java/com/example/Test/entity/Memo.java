package com.example.Test.entity;

import lombok.*;

import jakarta.persistence.*;
import lombok.ToString;

@Entity //엔티티 객체라는 것을 의미함
@Table(name="tbl_memo") // 어떠한 테이블로 생성할 것인지에 대한 정보를 담기 위한 어노테이션
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
    @Id //자동으로 생성되는 번호 사용하기 위한 어노테이션
    @GeneratedValue(strategy = GenerationType.IDENTITY) //PK를 자동으로 생성하고자 할때 사용함(키 생성 전략)
    private Long mno;

    @Column(length = 200, nullable = false) //다양한 속성을 지정할 수 있다
    private String memoText;
}
