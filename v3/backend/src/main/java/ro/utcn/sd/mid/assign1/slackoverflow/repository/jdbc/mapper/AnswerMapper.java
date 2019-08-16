package ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements RowMapper<Answer> {
    @Override
    public Answer mapRow(ResultSet rs, int rowNr) throws SQLException {
        return new Answer(rs.getInt("id"),
                rs.getInt("questionId"),
                rs.getInt("userId"),
                rs.getString("text"),
                rs.getTimestamp("creationDate"),
                rs.getInt("score"));
    }
}
