package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Colonia;
import com.Digis01.FArceProgramacionNCapas.ML.Direccion;
import com.Digis01.FArceProgramacionNCapas.ML.Result;
import com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion;
import java.sql.ResultSet;
import java.sql.Types;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements IDireccionDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Result GetById(int IdDireccion) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL DireccionById(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {

                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));

                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                    direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));

                    direccion.Colonia.Municipio = new com.Digis01.FArceProgramacionNCapas.ML.Municipio();
                    direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                    direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));

                    direccion.Colonia.Municipio.Estado = new com.Digis01.FArceProgramacionNCapas.ML.Estado();
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                    direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));

                    direccion.Colonia.Municipio.Estado.Pais = new com.Digis01.FArceProgramacionNCapas.ML.Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                    result.object = direccion;
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
    public Result DireccionAdd(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL DireccionAdd(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, usuarioDireccion.Usuario.getIdUsuario());
                callableStatement.setString(2, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(3, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(4, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(5, usuarioDireccion.Direccion.Colonia.getIdColonia());

                int rowAffected = callableStatement.executeUpdate();
                System.out.println("Filas afectadas: " + rowAffected);
                result.correct = rowAffected > 0 ? true : false;

                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            ex.printStackTrace();
        }

        return result;
    }

    @Override
    public Result UpdateById(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL DireccionUpdateById(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, usuarioDireccion.Direccion.getIdDireccion());
                callableStatement.setString(2, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(3, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setString(4, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setInt(5, usuarioDireccion.Direccion.Colonia.getIdColonia());

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMessage = "Error en la actualización";
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
