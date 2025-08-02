package com.example.basicmemo.dto;

import com.example.basicmemo.entity.Memo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemoResponseDto {

    private Long id;
    private String title;
    private String contents;

    public MemoResponseDto(Memo memo) {
        this(memo.getId(), memo.getTitle(), memo.getContents());
    }
}
