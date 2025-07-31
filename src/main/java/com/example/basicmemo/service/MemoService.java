package com.example.basicmemo.service;

import com.example.basicmemo.dto.MemoRequestDto;
import com.example.basicmemo.dto.MemoResponseDto;

import java.util.List;

public interface MemoService {

    MemoResponseDto saveMemo(MemoRequestDto dto);

    List<MemoResponseDto> findAllMemos();

    MemoResponseDto findMemoById(Long id);

    MemoResponseDto updateMemo(Long id, String title, String contents);

    MemoResponseDto updateTitle(Long id, String title, String contents);

    void deleteMemo(Long id);
}
