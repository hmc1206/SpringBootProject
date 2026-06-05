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

import java.util.Optional;
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

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO){
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<Guestbook> result = repository.findAll(pageable);

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public GuestbookDTO read(Long gno) { //read()기능을 구현
        Optional<Guestbook> result = repository.findById(gno);
        //findById()를 통해서 엔티티 객체를 가져옴

        return result.isPresent()? entityToDto(result.get()) : null;
        //entityToDto()를 이용해서 엔티티 객체를 DTO로 변환해서 반환
    }

    @Override
    public void remove(Long gno){
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        //업데이트 하는 항목은 '제목' '내용'
        Optional<Guestbook> result = repository.findById(dto.getGno());

        if(result.isPresent()){
            Guestbook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }
}
