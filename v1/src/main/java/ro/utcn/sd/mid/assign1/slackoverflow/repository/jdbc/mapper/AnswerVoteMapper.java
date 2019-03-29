package ro.utcn.sd.mid.assign1.slackoverflow.repository.jdbc.mapper;

import org.springframework.jdbc.core.RowMapper;
import ro.utcn.sd.mid.assign1.slackoverflow.entity.AnswerVote;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerVoteMapper implements RowMapper<AnswerVote> {
    @Override
    public AnswerVote mapRow(ResultSet rs, int rowNr) throws SQLException {
        return new AnswerVote(rs.getInt("id"),
                rs.getInt("answerId"),
                rs.getInt("userId"),
                rs.getBoolean("voteType"));
    }
}
