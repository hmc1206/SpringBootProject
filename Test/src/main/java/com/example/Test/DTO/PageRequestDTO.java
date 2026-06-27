package com.example.Test.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

//화면에서 전달되는 page라는 파라미터와 size라는 파라미터를 수집하는 역할
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;
    private String type;
    private String keyword;

    //페이지 번호 등은 기본값을 가지는 것이 좋기 때문에 1과 10라는 값을 이용
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1, size, sort);
        //나중에 수정의 여지가 있기는 하지만 , jpa를 이용하는 경우에는 페이지 번호가 0부터 시작한다는 점을 감안해서 1페이지의 경우 0이 될 수 있도록 page -1을 하는 형태로 작성
    }
}
