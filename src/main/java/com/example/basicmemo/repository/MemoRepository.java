package com.example.basicmemo.repository;

import com.example.basicmemo.entity.Memo;

import java.util.List;

public interface MemoRepository {
    Memo saveMemo(Memo memo);

    List<Memo> findAllMemos();

    Memo findMemoById(Long id);

    void deleteMemo(Long id);
}
