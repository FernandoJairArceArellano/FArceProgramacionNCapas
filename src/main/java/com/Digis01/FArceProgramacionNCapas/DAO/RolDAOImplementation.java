package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Result;
import com.Digis01.FArceProgramacionNCapas.ML.Rol;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOImplementation implements IRolDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            result.object = jdbcTemplate.execute("{CALL RolGetAll(?)}", (CallableStatementCallback<List<Rol>>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                List<Rol> roles = new ArrayList<>();
                while (resultSet.next()) {
                    Rol rol = new Rol();
                    rol.setIdRol(resultSet.getInt("IdRol"));
                    rol.setNombre(resultSet.getString("Nombre"));
                    roles.add(rol);
                }
                result.correct = true;
                return roles;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
