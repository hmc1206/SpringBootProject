package com.example.Test.DTO;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//jpa를 이욯아는 Repository에서는 페이지 처리 결과를 Page<Entity>타입으로 변환하게 됨
//따라서 서비스 계층에서 이를 처리하기 위해서도 별도의 클래스를 만들어서 처리해야 함
// - Page<Entity>의 엔티티 객체들을 DTO 객체로 변환해서 자료구조로 담아 주어야 함
// - 화면 출력에 필요한 정보들을 구성해주어야 함
@Data
public class PageResultDTO<DTO, EN> { //다양한 곳에서 사용할 수 있도록 제네릭 타입을 이용해서 DTO와 EN이라는 타입을 지정
    //DTO List
    private List<DTO> dtoList;

    //초 페이지 번호
    private int totalPage;

    //현재 페이지 번호
    private int page;
    //목록 사이즈
    private int size;

    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;

    //이전, 다음
    private boolean prev, next;

    //페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN, DTO > fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());

        totalPage = result.getTotalPages();

        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){

        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        //temp end page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;

        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;

        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start,end).boxed().collect(Collectors.toList());
    }
}
