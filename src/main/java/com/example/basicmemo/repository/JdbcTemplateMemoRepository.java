package com.example.basicmemo.repository;

import com.example.basicmemo.dto.MemoResponseDto;
import com.example.basicmemo.entity.Memo;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateMemoRepository implements MemoRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemoRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public MemoResponseDto saveMemo(Memo memo) {
        // INSERT Query를 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert
                .withCatalogName("memo")
                .withTableName("memo").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("title", memo.getTitle());
        parameters.put("contents", memo.getContents());

        // 저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new MemoResponseDto(key.longValue(), memo.getTitle(), memo.getContents());
    }

    @Override
    public List<MemoResponseDto> findAllMemos() {
        return jdbcTemplate.query("select * from memo", memoRowMapper());
    }

    @Override
    public Optional<Memo> findMemoById(Long id) {
        List<Memo> result = jdbcTemplate.query("select * from memo where id = ?", memoRowMapperV2(), id);
        return result.stream().findAny();
    }

    @Override
    public Memo findMemoByIdOrElseThrow(Long id) {
        List<Memo> result = jdbcTemplate.query("select * from memo where id = ?", memoRowMapperV2(), id);

        return result.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exist id = " + id));
    }

    @Override
    public int updateMemo(Long id, String title, String contents) {
        return jdbcTemplate.update("update memo set title = ?, contents = ? where id = ?", title, contents, id);
    }

    @Override
    public int updateTitle(Long id, String title) {
        return jdbcTemplate.update("update memo set title = ? where id = ?", title, id);
    }

    @Override
    public int deleteMemo(Long id) {
        return jdbcTemplate.update("delete from memo where id = ?", id);
    }

    private RowMapper<MemoResponseDto> memoRowMapper() {
        return (rs, rowNum) -> new MemoResponseDto(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("contents")
        );
    }

    private RowMapper<Memo> memoRowMapperV2() {
        return (rs, rowNum) -> new Memo(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("contents")
        );
    }
}
