package ${pPackage}.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * @author ${author}
 * @date ${currTime?datetime}
 * @desc
 */
public abstract class BaseController {

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;
}
