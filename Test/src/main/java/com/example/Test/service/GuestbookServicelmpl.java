package com.example.Test.service;


import com.example.Test.DTO.GuestbookDTO;
import com.example.Test.DTO.PageRequestDTO;
import com.example.Test.DTO.PageResultDTO;
import com.example.Test.entity.Guestbook;
import com.example.Test.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor //의존성 자동 주입, 클래스 선언시에 자동으로 주입
public class GuestbookServicelmpl implements GuestBookService{

    private final GuestbookRepository repository; //반드시 final로 선언
    //jpa처리를 위해서 GuestbookRepository를 주입

    @Override
    public Long register(GuestbookDTO dto){
        log.info("DTO -----------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);

        repository.save(entity); //save를 통해서 저장, 저장된 후에 엔티티가 가지는 gno값을 반환

        return null;
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
}
