package com.example.basicmemo.service;

import com.example.basicmemo.dto.MemoRequestDto;
import com.example.basicmemo.dto.MemoResponseDto;
import com.example.basicmemo.entity.Memo;
import com.example.basicmemo.repository.MemoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Annotation @Service는 @Component와 같다, Spring Bean으로 등록한다는 뜻.
 * Spring Bean으로 등록되면 다른 클래스에서 주입하여 사용할 수 있다.
 * 명시적으로 Service Layer 라는것을 나타낸다.
 * 비지니스 로직을 수행한다.
 */
@Service
public class MemoServiceImpl implements MemoService {

    private final MemoRepository memoRepository;

    public MemoServiceImpl(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }

    @Override
    public MemoResponseDto saveMemo(MemoRequestDto dto) {

        // 요청 받은 데이터로 Memo 객체 생성, ID 없음
        Memo memo = new Memo(dto.getTitle(), dto.getContents());

        return memoRepository.saveMemo(memo);
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return memoRepository.findAllMemos();
    }

    @Override
    public MemoResponseDto findMemoById(Long id) {
        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);
        return new MemoResponseDto(memo);
    }

    @Transactional
    @Override
    public MemoResponseDto updateMemo(Long id, String title, String contents) {

        // 필수값 검증
        if (title == null || contents == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title and content are required values.");
        }

        int updatedRow = memoRepository.updateMemo(id, title, contents);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);
        return new MemoResponseDto(memo);
    }

    @Transactional
    @Override
    public MemoResponseDto updateTitle(Long id, String title, String contents) {
        // 필수값 검증
        if (title == null || contents != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The title is a required value.");
        }

        int updatedRow = memoRepository.updateTitle(id, title);

        // NPE 방지
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }

        Memo memo = memoRepository.findMemoByIdOrElseThrow(id);
        return new MemoResponseDto(memo);
    }

    @Override
    public void deleteMemo(Long id) {
        int deletedRow = memoRepository.deleteMemo(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id);
        }
    }
}
