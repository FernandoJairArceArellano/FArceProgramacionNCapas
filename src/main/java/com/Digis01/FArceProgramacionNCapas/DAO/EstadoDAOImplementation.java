package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Estado;
import com.Digis01.FArceProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements IEstadoDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result EstadoByIdPais(int IdPais) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL EstadoByIdPais(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, IdPais);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resulSet = (ResultSet) callableStatement.getObject(2);

                result.objects = new ArrayList<>();
                while (resulSet.next()) {
                    Estado estado = new Estado();
                    estado.setIdEstado(resulSet.getInt("IdEstado"));
                    estado.setNombre(resulSet.getString("Nombre"));

                    result.objects.add(estado);
                }
                return 1;
            });
            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}