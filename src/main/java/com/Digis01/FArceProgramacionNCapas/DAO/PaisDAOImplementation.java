package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Pais;
import com.Digis01.FArceProgramacionNCapas.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaisDAOImplementation implements IPaisDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            result.object = jdbcTemplate.execute("{CALL PaisGetAll(?)}", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();
                while (resultSet.next()) {
                    Pais pais = new Pais();
                    pais.setIdPais(resultSet.getInt("IdPais"));
                    pais.setNombre(resultSet.getString("Nombre"));
                    result.objects.add(pais);
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

    @Override
    public Result GetAllJPA() {
        Result result = new Result();
        try {
            // Consulta JPQL
            TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Pais> query
                    = entityManager.createQuery("FROM Pais ORDER BY IdPais", com.Digis01.FArceProgramacionNCapas.JPA.Pais.class);

            List<com.Digis01.FArceProgramacionNCapas.JPA.Pais> paisesJPA = query.getResultList();
            result.objects = new ArrayList<>();

            for (com.Digis01.FArceProgramacionNCapas.JPA.Pais paisJPA : paisesJPA) {
                Pais pais = new Pais();
                pais.setIdPais(paisJPA.getIdPais());
                pais.setNombre(paisJPA.getNombre());
                result.objects.add(pais);
            }

            result.correct = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

}
