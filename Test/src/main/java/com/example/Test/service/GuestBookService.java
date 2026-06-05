package com.example.Test.service;

import com.example.Test.DTO.GuestbookDTO;
import com.example.Test.DTO.PageRequestDTO;
import com.example.Test.DTO.PageResultDTO;
import com.example.Test.entity.Guestbook;

public interface GuestBookService {
    Long register(GuestbookDTO dto);

    GuestbookDTO read(Long gno);

    void remove(Long gno);

    void modify(GuestbookDTO dto);

    //PageRequestDTO를 파라미터로 PageResultDTO를 리턴 타입으로 사용하는 getList()를 설계하고
    //엔티티 객체를 DTO 객체로 변환하는 entityToDto()를 정의
    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto){
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    default GuestbookDTO entityToDto(Guestbook entity){
        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();
        return dto;
    }
}
