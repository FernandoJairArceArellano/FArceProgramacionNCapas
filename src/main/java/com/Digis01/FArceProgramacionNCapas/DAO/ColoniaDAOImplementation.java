package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Colonia;
import com.Digis01.FArceProgramacionNCapas.ML.Result;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements IColoniaDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result ColoniaByIdMunicipio(int IdMunicipio) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL ColoniaByIdMunicipio(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, IdMunicipio);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);

                callableStatement.execute();
                ResultSet resulSet = (ResultSet) callableStatement.getObject(2);

                result.objects = new ArrayList<>();
                while (resulSet.next()) {
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resulSet.getInt("IdColonia"));
                    colonia.setNombre(resulSet.getString("Nombre"));
                    colonia.setCodigoPostal(resulSet.getString("CodigoPostal"));

                    result.objects.add(colonia);
                }
                return 1;
            });
            result.correct = true;
        } catch (Exception ex) {
            result.correct = true;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}