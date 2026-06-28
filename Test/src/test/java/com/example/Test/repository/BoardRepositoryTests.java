package com.example.Test.repository;

import com.example.Test.entity.Board;
import com.example.Test.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardRepositoryTests {
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Member member = Member.builder().email("user"+i+"@aaa.com").build();

            Board board = Board.builder()
                    .title("Title..."+i)
                    .content("Content..."+i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    //지연 로딩 방식으로 로딩하기 때문에 board테이블만 가져와서 출력하는 건 문제가 없지만
    //.getWriter()에서 문제가 발생 => 데이터베이스의 연결이 끝난 상태
    //"no session"이라는 메시지 에러 해결을 위한 어노테이션 => "@Transactional"
    //@Transactional => 하나의 트랙잭션으로 처리하라는 의미(필요할 때 다시 데이터베이스와 연결이 생성됨)
    @Transactional
    @Test
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(100L); //데이터베이스에서 존재하는 번호

        Board board = result.get();

        System.out.println(board);
        System.out.println(board.getWriter());
    }

    //엔티티 클래스 내부에 연관관계가 있는 경우
    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[])result;

        System.out.println("---------------------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardWithReply() {
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object[] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }
}
