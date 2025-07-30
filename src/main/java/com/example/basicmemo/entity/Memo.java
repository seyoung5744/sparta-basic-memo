package com.example.basicmemo.entity;

import com.example.basicmemo.dto.MemoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Memo {

    private Long id;
    private String title;
    private String contents;

    public void update(MemoRequestDto request) {
        this.title = request.getTitle();
        this.contents = request.getContents();
    }
}
