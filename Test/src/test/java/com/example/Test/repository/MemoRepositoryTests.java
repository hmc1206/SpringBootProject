package com.example.Test.repository;


import com.example.Test.entity.Memo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {
    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
        //testClass() 메서드는 MemoRepository 인터페이스 타입의 실제 객체가 어떤 것인지 확인
    }


    //한번에 여러 개의 엔티티 객체를 저장
    @Test
    public void testInsertDummies() {
        IntStream.rangeClosed(1,100).forEach(i -> { //100개의 Memo 객체 생성
            Memo memo = Memo.builder().memoText("Sample..."+i).build(); //memoText()는 Not Null 조건이므로 반드시 값을 넣어줘야 함
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){
        Long mno = 100L;

        //조회 작업
        //findById()의 경우 java.util 패키지의 Optional 타입으로 반환되기 때문에 한번 더 결과가 존재하는지를 체크하는 형태로 작성하게 됨
        Optional<Memo> result = memoRepository.findById(mno);

        System.out.println("===============================");
        if(result.isPresent()){

            Memo memo = result.get();

            System.out.println(memo);

        }else{

            System.out.println("데이터 없음");
        }
    }

    //트랜잭션 처리를 위해 사용하는 어노테이션
    @Transactional
    @Test
    public void testSelect2(){
        Long mno = 100L;

        Memo memo = memoRepository.getOne(mno);

        System.out.println("==============================");

        System.out.println(memo);
    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();

        //수정작업은 등록 작업과 동일하게 save()를 이용
        //해당 번호를 select 쿼리로 Memo객체를 확인하고 update를 함
        //객체가 없으면 insert를 함
        System.out.println(memoRepository.save(memo));
    }

//    @Test
//    public void testDelete() {
//        Long mno = 100L;
          //deleteById()의 리턴 타입은 void이고 만일 해당 데이터가 존재하지 않으면 예외를 발생
          //이것도 마찬가지고 select 이후에 처리
//        memoRepository.deleteById(mno);
//    }

    @Test
    public void testPageDefault(){
        //리턴 타입이 org.springframework.data.domain.Page
        Pageable pageable = PageRequest.of(0,10);

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println(result);

        System.out.println("----------------------------------------------");

        System.out.println("Total Pages : " + result.getTotalPages());
        System.out.println("Total Count : " + result.getTotalElements());

        System.out.println("Page Number : " + result.getNumber());
        System.out.println("Page Size   : " + result.getSize());
        System.out.println("has next page?  : " + result.hasNext());
        System.out.println("first Page? : " + result.isFirst());

        System.out.println("----------------------------------------------");

        for(Memo memo : result.getContent()){
            System.out.println(memo);
        }
    }

    @Test
    public void testSort() {
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2); //and를 이용해서 연결

        Pageable pageable = PageRequest.of(0, 10 , sortAll); //결합된 정렬 조건 사용

        Page<Memo> result = memoRepository.findAll(pageable);

        System.out.println("----------------------------------------------");
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods(){
        //mno 값이 70부터 80사이의 객체들을 구함
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        for(Memo memo : list) {
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPageable() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("mno").descending());

        Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);

        result.get().forEach(memo -> System.out.println(memo));
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods() {
        memoRepository.deleteMemoByMnoLessThan(10L);
    }
}
