package com.example.basicmemo.repository;

import com.example.basicmemo.entity.Memo;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoRepositoryImpl implements MemoRepository {

    // 자료구조가 DB 역할 수행
    private final Map<Long, Memo> memoStore = new HashMap<>();

    @Override
    public Memo saveMemo(Memo memo) {

        Long memoId = memoStore.isEmpty() ? 1 : Collections.max(memoStore.keySet()) + 1;
        memo.setId(memoId);

        memoStore.put(memoId, memo);

        return memo;
    }

    @Override
    public List<Memo> findAllMemos() {
        return new ArrayList<>(memoStore.values());
    }

    @Override
    public Memo findMemoById(Long id) {
        return memoStore.get(id);
    }

    @Override
    public void deleteMemo(Long id) {
        memoStore.remove(id);
    }
}
