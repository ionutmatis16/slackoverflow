package ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuestionMapper implements RowMapper<Question> {
    @Override
    public Question mapRow(ResultSet rs, int rowNr) throws SQLException {
        return new Question(rs.getInt("id"),
                rs.getInt("userId"),
                rs.getString("title"),
                rs.getString("text"),
                rs.getTimestamp("creationDate"),
                new ArrayList<>(),
                rs.getInt("score"));
    }
}
