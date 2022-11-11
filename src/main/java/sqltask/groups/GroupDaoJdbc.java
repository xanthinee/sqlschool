package sqltask.groups;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.*;

@SuppressWarnings("java:S1874")
@Repository
public class GroupDaoJdbc implements GroupDAO {

    private final JdbcTemplate jdbcTemplate;
    private static final String GROUP_TABLE = "groups";
    private static final String STUDENTS_TABLE = "students";
    private static final String GET_BY_ID = "select * from " + GROUP_TABLE + " where group_id=?";
    private static final String SAVE_GROUP = "insert into " + GROUP_TABLE + " values(default, ?)";
    private static final String DELETE_BY_ID = "delete from " + GROUP_TABLE + " where group_id = ?";
    private static final String DELETE_ALL = "delete from " + GROUP_TABLE;
    private static final String GET_ALL = "select * from " + GROUP_TABLE;
    private static final String COMPARE_GROUPS = "select g.group_id, g.group_name from " + GROUP_TABLE +
            " g left join " + STUDENTS_TABLE + " s on g.group_id = s.group_id " +
            "where g.group_id <> ? group by g.group_id, g.group_name " +
            "having count(s.student_id) <= (select count(s2.student_id) from "
            + STUDENTS_TABLE + " s2 where s2.group_id = ?)";
    private final GroupRowMapper groupRowMapper = new GroupRowMapper();

    public GroupDaoJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Group group) {
        jdbcTemplate.update(SAVE_GROUP, group.getName());
    }
    @Override
    public void deleteById(int id) {
        jdbcTemplate.update(DELETE_BY_ID, id);
    }

    @Override
    public Group getById(int id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, groupRowMapper, id);
    }

    @Override
    public void saveAll(List<Group> groups) {
        jdbcTemplate.batchUpdate(SAVE_GROUP, groups, groups.size(),
                (PreparedStatement ps, Group group) -> ps.setString(1, group.getName()));
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.execute(DELETE_ALL);
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(GET_ALL, groupRowMapper);
    }

    @Override
    public List<Group> compareGroups(int groupID) {
        return jdbcTemplate.query(COMPARE_GROUPS, groupRowMapper, groupID, groupID);
    }
}
