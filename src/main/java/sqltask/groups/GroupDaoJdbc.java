package sqltask.groups;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@SuppressWarnings("java:S1874")
@Repository
public class GroupDaoJdbc {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource ds;
    private static final String GROUP_TABLE = "groups";
    private static final String GET_BY_ID = "select * from " + GROUP_TABLE + " where group_id=?";
    private final GroupMapper groupMapper = new GroupMapper();
    private final GroupRowMapper groupRowMapper = new GroupRowMapper();


    public GroupDaoJdbc(JdbcTemplate jdbcTemplate, DataSource ds) {
        this.jdbcTemplate = jdbcTemplate;
        this.ds = ds;
    }

    public void save(Group group) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(ds).withTableName(GROUP_TABLE)
                        .usingGeneratedKeyColumns("group_id");
        jdbcInsert.execute(groupMapper.mapToInsert(group));
    }

    public Group getById(int id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, groupRowMapper, id);
    }
}
