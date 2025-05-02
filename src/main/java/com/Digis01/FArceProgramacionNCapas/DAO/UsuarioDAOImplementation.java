package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Colonia;
import com.Digis01.FArceProgramacionNCapas.ML.Direccion;
import com.Digis01.FArceProgramacionNCapas.ML.Estado;
import com.Digis01.FArceProgramacionNCapas.ML.Municipio;
import com.Digis01.FArceProgramacionNCapas.ML.Pais;
import com.Digis01.FArceProgramacionNCapas.ML.Result;
import com.Digis01.FArceProgramacionNCapas.ML.Rol;
import com.Digis01.FArceProgramacionNCapas.ML.Usuario;
import com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.sql.CallableStatement;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

// Logica de base de datos
@Repository
public class UsuarioDAOImplementation implements IUsuarioDAO {

    @Autowired //Inyección dependencias (field, contructor, setter) JDBCTemplate
    private JdbcTemplate jdbcTemplate; // conexión directa

    @Autowired
    private EntityManager entityManager;

    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            jdbcTemplate.execute("{CALL UsuarioGetAll(?)}", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    int idUsuario = resultSet.getInt("IdUsuario");
                    if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        usuarioDireccion.Usuario = new Usuario();
                        usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));
                        usuarioDireccion.Usuario.setUsername(resultSet.getString("UserName"));
                        usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                        usuarioDireccion.Usuario.setPassword(resultSet.getString("Password"));
                        java.sql.Date fechaNacimiento = resultSet.getDate("FNacimiento");
                        usuarioDireccion.Usuario.setFNacimiento(fechaNacimiento);
                        usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo").charAt(0));
                        usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                        usuarioDireccion.Usuario.setNCelular(resultSet.getString("NCelular"));
                        usuarioDireccion.Usuario.setCURP(resultSet.getString("CURP"));
                        usuarioDireccion.Usuario.Rol = new Rol();
                        usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));
                        usuarioDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle") != null ? resultSet.getString("Calle") : "Sin dirección registrada");
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior") != null ? resultSet.getString("NumeroInterior") : "");
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior") != null ? resultSet.getString("NumeroExterior") : "");
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        usuarioDireccion.Direcciones.add(direccion);
                        result.objects.add(usuarioDireccion);
                    }
                }
                result.correct = true;
                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }

        return result;
    }

    @Override
    public Result GetAllJPA() {
        Result result = new Result();

        try {
            TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Usuario> queryUsuarios
                    = entityManager.createQuery("FROM Usuario ORDER BY IdUsuario", com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class);

            List<com.Digis01.FArceProgramacionNCapas.JPA.Usuario> usuarios = queryUsuarios.getResultList();
            result.objects = new ArrayList<>();

            for (com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA : usuarios) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                Usuario usuario = new Usuario(); // Crear instancia

                // Mapear propiedades del usuario
                usuario.setIdUsuario(usuarioJPA.getIdUsuario());
                usuario.setNombre(usuarioJPA.getNombre());
                usuario.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
                usuario.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
                usuario.setImagen(usuarioJPA.getImagen());
                usuario.setUsername(usuarioJPA.getUsername());
                usuario.setEmail(usuarioJPA.getEmail());
                usuario.setPassword(usuarioJPA.getPassword());
                usuario.setFNacimiento(usuarioJPA.getFNacimiento());
                usuario.setSexo(usuarioJPA.getSexo());
                usuario.setTelefono(usuarioJPA.getTelefono());
                usuario.setNCelular(usuarioJPA.getNCelular());
                usuario.setCURP(usuarioJPA.getCURP());
                usuario.setStatus(usuarioJPA.getStatus());

                // Mapear Rol
                if (usuarioJPA.getRol() != null) {
                    Rol rol = new Rol();
                    rol.setIdRol(usuarioJPA.getRol().getIdRol());
                    usuario.setRol(rol);
                }

                usuarioDireccion.Usuario = usuario;

                TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> queryDireccion = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :IdUsuario", com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class);
                queryDireccion.setParameter("IdUsuario", usuario.getIdUsuario());

                List<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> direcionesJPA = queryDireccion.getResultList();

                usuarioDireccion.Direcciones = new ArrayList<>();

                for (com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccionJPA : direcionesJPA) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(direccionJPA.getIdDireccion());
                    direccion.setCalle(direccionJPA.getCalle());
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior());
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior());
                    direccion.Colonia = new Colonia();

                    direccion.Colonia.setIdColonia(direccionJPA.Colonia.getIdColonia());

                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setNombre(direccionJPA.Colonia.Municipio.getNombre());

                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setNombre(direccionJPA.Colonia.Municipio.Estado.getNombre());

                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.Colonia.Municipio.Estado.Pais.getNombre());

                    usuarioDireccion.Direcciones.add(direccion);
                }

                result.objects.add(usuarioDireccion);
            }

            result.correct = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = "Error al obtener los usuarios con JPA.";
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Add(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

//        try{
//            if (!imagenFile.isEmpty()) {
//                byte[] bytes = imagenFile.getBytes();
//                String imgBase64 = Base64.getEncoder().encodeToString(bytes);
//                usuarioDireccion.Usuario.setImagen(imgBase64);
//            }
//        } catch(Exception ex){
//            
//        }
        try {
            jdbcTemplate.execute("CALL UsuarioDireccionAdd(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, usuarioDireccion.Usuario.getNombre());
                callableStatement.setString(2, usuarioDireccion.Usuario.getApellidoPaterno());
                callableStatement.setString(3, usuarioDireccion.Usuario.getApellidoMaterno());

                callableStatement.setString(4, usuarioDireccion.Usuario.getImagen());
                // Si la imagen es null
//                if (usuarioDireccion.Usuario.getImagen() != null) {
//                    callableStatement.setString(4, usuarioDireccion.Usuario.getImagen());
//                } else {
//                    callableStatement.setNull(4, Types.VARCHAR);
//                }
                callableStatement.setDate(5, new java.sql.Date(usuarioDireccion.Usuario.getFNacimiento().getTime()));
                callableStatement.setString(6, usuarioDireccion.Usuario.getNCelular());
                callableStatement.setInt(7, usuarioDireccion.Usuario.Rol.getIdRol());
                callableStatement.setString(8, usuarioDireccion.Usuario.getCURP());
                callableStatement.setString(9, usuarioDireccion.Usuario.getUsername());
                callableStatement.setString(10, usuarioDireccion.Usuario.getEmail());
                callableStatement.setString(11, usuarioDireccion.Usuario.getPassword());
                callableStatement.setString(12, String.valueOf(usuarioDireccion.Usuario.getSexo()));
                callableStatement.setString(13, usuarioDireccion.Usuario.getTelefono());
                callableStatement.setString(14, usuarioDireccion.Direccion.getCalle());
                callableStatement.setString(15, usuarioDireccion.Direccion.getNumeroExterior());
                callableStatement.setString(16, usuarioDireccion.Direccion.getNumeroInterior());
                callableStatement.setInt(17, usuarioDireccion.Direccion.Colonia.getIdColonia());

                callableStatement.setInt(18, usuarioDireccion.Usuario.getStatus());

                callableStatement.executeUpdate();
                //System.out.println("Filas afectadas: " + rowAffected);
                result.correct = true;

                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Transactional
    @Override
    public Result AddJPA(UsuarioDireccion usuarioDireccion) {
        Result result = new Result();

        try {
            // Llenado de datos UsuarioJPA con UsuarioML
            com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();
            usuarioJPA.setNombre(usuarioDireccion.Usuario.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioDireccion.Usuario.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioDireccion.Usuario.getApellidoMaterno());
            usuarioJPA.setFNacimiento(usuarioDireccion.Usuario.getFNacimiento());
            usuarioJPA.setNCelular(usuarioDireccion.Usuario.getNCelular());
            usuarioJPA.Rol = new com.Digis01.FArceProgramacionNCapas.JPA.Rol();
            usuarioJPA.Rol.setIdRol(usuarioDireccion.Usuario.Rol.getIdRol());
            usuarioJPA.setCURP(usuarioDireccion.Usuario.getCURP());
            usuarioJPA.setUsername(usuarioDireccion.Usuario.getUsername());
            usuarioJPA.setEmail(usuarioDireccion.Usuario.getEmail());
            usuarioJPA.setPassword(usuarioDireccion.Usuario.getPassword());
            usuarioJPA.setSexo(usuarioDireccion.Usuario.getSexo());
            usuarioJPA.setTelefono(usuarioDireccion.Usuario.getTelefono());
            usuarioJPA.setImagen(usuarioDireccion.Usuario.getImagen());
            usuarioJPA.setStatus(usuarioDireccion.Usuario.getStatus());

            entityManager.persist(usuarioJPA);

            // Insercion de direccion al usuario
            com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccionJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Direccion();
            direccionJPA.setCalle(usuarioDireccion.Direccion.getCalle());
            direccionJPA.setNumeroInterior(usuarioDireccion.Direccion.getNumeroInterior());
            direccionJPA.setNumeroExterior(usuarioDireccion.Direccion.getNumeroExterior());
            direccionJPA.Colonia = new com.Digis01.FArceProgramacionNCapas.JPA.Colonia();
            direccionJPA.Colonia.setIdColonia(usuarioDireccion.Direccion.Colonia.getIdColonia());
            direccionJPA.Usuario = usuarioJPA;

            entityManager.persist(direccionJPA);

            System.out.println("");

            result.correct = true;
        } catch (Exception ex) {
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result direccionesByIdUsuario(int IdUsuario) {
        Result result = new Result();

        try {

            jdbcTemplate.execute("CALL DireccionesByIdUsuario(?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {

                callableStatement.registerOutParameter(1, Types.REF_CURSOR);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.setInt(3, IdUsuario);

                callableStatement.execute();

                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

                ResultSet resultSetUsuario = (ResultSet) callableStatement.getObject(1);

                if (resultSetUsuario.next()) {
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSetUsuario.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombre(resultSetUsuario.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSetUsuario.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSetUsuario.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setUsername(resultSetUsuario.getNString("Username"));
                    usuarioDireccion.Usuario.setEmail(resultSetUsuario.getString("Email"));
                    usuarioDireccion.Usuario.setPassword(resultSetUsuario.getString("Password"));
                    Date fechaNacimiento = resultSetUsuario.getDate("FNacimiento");
                    usuarioDireccion.Usuario.setFNacimiento(fechaNacimiento != null ? new Date(fechaNacimiento.getTime()) : null);
                    String sexo = resultSetUsuario.getString("Sexo");
                    usuarioDireccion.Usuario.setSexo(sexo != null && !sexo.isEmpty() ? sexo.charAt(0) : null);
                    usuarioDireccion.Usuario.setTelefono(resultSetUsuario.getString("Telefono"));
                    usuarioDireccion.Usuario.setNCelular(resultSetUsuario.getString("NCelular"));
                    usuarioDireccion.Usuario.setCURP(resultSetUsuario.getString("CURP"));
                }

                ResultSet resultSetDireccion = (ResultSet) callableStatement.getObject(2);

                usuarioDireccion.Direcciones = new ArrayList();

                while (resultSetDireccion.next()) {

                    Direccion direccion = new Direccion();

                    direccion.setIdDireccion(resultSetDireccion.getInt("IdDireccion"));
                    direccion.setCalle(resultSetDireccion.getString("Calle"));
                    direccion.setNumeroExterior(resultSetDireccion.getString("NumeroExterior"));
                    direccion.setNumeroInterior(resultSetDireccion.getString("NumeroInterior"));
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(resultSetDireccion.getInt("IdColonia"));
                    direccion.Colonia.setNombre(resultSetDireccion.getString("NombreColonia"));

                    usuarioDireccion.Direcciones.add(direccion);
                }

                result.object = usuarioDireccion;
                result.correct = true;
                return 1;
            });

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result DireccionesByIdUsuarioJPA(int IdUsuario) {
        Result result = new Result();

        try {
            UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
            com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = entityManager.find(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, IdUsuario);

            if (usuarioJPA != null) {
                // Mapear de JPA a modelo ML
                Usuario usuarioML = new Usuario();
                usuarioML.setIdUsuario(usuarioJPA.getIdUsuario());
                usuarioML.setNombre(usuarioJPA.getNombre());
                usuarioML.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
                usuarioML.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
                usuarioML.setUsername(usuarioJPA.getUsername());
                usuarioML.setEmail(usuarioJPA.getEmail());
                usuarioML.setPassword(usuarioJPA.getPassword());
                usuarioML.setFNacimiento(usuarioJPA.getFNacimiento());
                usuarioML.setSexo(usuarioJPA.getSexo());
                usuarioML.setTelefono(usuarioJPA.getTelefono());
                usuarioML.setNCelular(usuarioJPA.getNCelular());
                usuarioML.setCURP(usuarioJPA.getCURP());

                // Rellenar Rol
                Rol rolML = new Rol();
                rolML.setIdRol(usuarioJPA.Rol.getIdRol());
                usuarioML.Rol = rolML;

                usuarioDireccion.Usuario = usuarioML;

                // Obtener direcciones asociadas al usuario
                Query query = entityManager.createQuery("FROM Direccion WHERE Usuario.IdUsuario = :IdUsuario",
                        com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class);
                query.setParameter("IdUsuario", IdUsuario);
                List<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> direccionesJPA = query.getResultList();

                List<Direccion> direccionesML = new ArrayList<>();

                for (com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccionJPA : direccionesJPA) {
                    Direccion direccionML = new Direccion();
                    direccionML.setIdDireccion(direccionJPA.getIdDireccion());
                    direccionML.setCalle(direccionJPA.getCalle());
                    direccionML.setNumeroExterior(direccionJPA.getNumeroExterior());
                    direccionML.setNumeroInterior(direccionJPA.getNumeroInterior());

                    Colonia coloniaML = new Colonia();
                    coloniaML.setIdColonia(direccionJPA.Colonia.getIdColonia());
                    coloniaML.setNombre(direccionJPA.Colonia.getNombre());

                    direccionML.Colonia = coloniaML;

                    direccionesML.add(direccionML);
                }

                usuarioDireccion.Direcciones = direccionesML;

                result.object = usuarioDireccion;
            } else {
                result.correct = false;
                result.errorMessage = "No se encontró el usuario con ID: " + IdUsuario;
            }

            result.correct = true;
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetById(int IdUsuario) {
        Result result = new Result();
        try {
            //Error en esta linea
            jdbcTemplate.execute("CALL UsuarioGetById(?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, IdUsuario);
                callableStatement.registerOutParameter(2, Types.REF_CURSOR);
                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);

                if (resultSet.next()) {
                    UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                    usuarioDireccion.Usuario = new Usuario();
                    usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                    usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                    usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                    usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                    usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));
                    usuarioDireccion.Usuario.setUsername(resultSet.getString("Username"));
                    usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                    usuarioDireccion.Usuario.setPassword(resultSet.getString("Password"));
                    java.sql.Date fechaNacimiento = resultSet.getDate("FNacimiento");
                    usuarioDireccion.Usuario.setFNacimiento(fechaNacimiento);
                    String sexo = resultSet.getString("Sexo");
                    usuarioDireccion.Usuario.setSexo(sexo != null && !sexo.isEmpty() ? sexo.charAt(0) : null);
                    usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                    usuarioDireccion.Usuario.setNCelular(resultSet.getString("NCelular"));
                    usuarioDireccion.Usuario.setCURP(resultSet.getString("CURP"));
                    usuarioDireccion.Usuario.Rol = new Rol();
                    usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                    usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));

                    result.object = usuarioDireccion;
                }

                result.correct = true;
                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result GetByIdJPA(int IdUsuario) {
        Result result = new Result();

        try {
            // Busqueda usuario por ID
            com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = entityManager.find(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, IdUsuario);

            if (usuarioJPA != null) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                Usuario usuarioML = new Usuario();

                usuarioML.setIdUsuario(usuarioJPA.getIdUsuario());
                usuarioML.setNombre(usuarioJPA.getNombre());
                usuarioML.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
                usuarioML.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
                usuarioML.setImagen(usuarioJPA.getImagen());
                usuarioML.setUsername(usuarioJPA.getUsername());
                usuarioML.setEmail(usuarioJPA.getEmail());
                usuarioML.setPassword(usuarioJPA.getPassword());
                usuarioML.setFNacimiento(usuarioJPA.getFNacimiento());
                usuarioML.setSexo(usuarioJPA.getSexo());
                usuarioML.setTelefono(usuarioJPA.getTelefono());
                usuarioML.setNCelular(usuarioJPA.getNCelular());
                usuarioML.setCURP(usuarioJPA.getCURP());

                // Rellenar Rol
                Rol rolML = new Rol();
                rolML.setIdRol(usuarioJPA.Rol.getIdRol());
                usuarioML.Rol = rolML;

                usuarioDireccion.Usuario = usuarioML;

                result.object = usuarioDireccion;
                result.correct = true;

            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado con Id:" + IdUsuario;
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result Update(Usuario usuario) {
        Result result = new Result();
        try {
            jdbcTemplate.execute("CALL UsuarioUpdate (?,?,?,?,?,?,?,?,?,?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getNombre());
                callableStatement.setString(3, usuario.getApellidoPaterno());
                callableStatement.setString(4, usuario.getApellidoMaterno());
                callableStatement.setString(5, usuario.getImagen());
                callableStatement.setString(6, usuario.getUsername());
                callableStatement.setString(7, usuario.getEmail());
                callableStatement.setString(8, usuario.getPassword());
                callableStatement.setDate(9, new java.sql.Date(usuario.getFNacimiento().getTime()));
                callableStatement.setString(10, String.valueOf(usuario.getSexo()));
                callableStatement.setString(11, usuario.getNCelular());
                callableStatement.setString(12, usuario.getTelefono());
                callableStatement.setString(13, usuario.getCURP());
                callableStatement.setInt(14, usuario.Rol.getIdRol());

                int rowsAffected = callableStatement.executeUpdate();

                if (rowsAffected > 0) {
                    result.correct = true;
                } else {
                    result.correct = false;
                    result.errorMessage = "Error en la actualización";
                }

                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result UpdateJPA(Usuario usuario) {
        Result result = new Result();

        try {
            com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = entityManager.find(
                    com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, usuario.getIdUsuario());
            if (usuarioJPA != null) {
                // Actualizar los campos
                usuarioJPA.setNombre(usuario.getNombre());
                usuarioJPA.setApellidoPaterno(usuario.getApellidoPaterno());
                usuarioJPA.setApellidoMaterno(usuario.getApellidoMaterno());

                if (usuario.getImagen() != null && !usuario.getImagen().isEmpty()) {
                    usuarioJPA.setImagen(usuario.getImagen());
                }

                usuarioJPA.setUsername(usuario.getUsername());
                usuarioJPA.setEmail(usuario.getEmail());
                usuarioJPA.setPassword(usuario.getPassword());
                usuarioJPA.setFNacimiento(usuario.getFNacimiento());
                usuarioJPA.setSexo(usuario.getSexo());
                usuarioJPA.setTelefono(usuario.getTelefono());
                usuarioJPA.setNCelular(usuario.getNCelular());
                usuarioJPA.setCURP(usuario.getCURP());

                // Rol
                com.Digis01.FArceProgramacionNCapas.JPA.Rol rolJPA = entityManager.find(
                        com.Digis01.FArceProgramacionNCapas.JPA.Rol.class,
                        usuario.getRol().getIdRol()
                );
                usuarioJPA.setRol(rolJPA);

                // Guardar cambios
                entityManager.merge(usuarioJPA);

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado con Id: " + usuario.getIdUsuario();
            }

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Override
    public Result UpdateStatus(Usuario usuario) {
        Result result = new Result();

        try {
            int rowsAffected = jdbcTemplate.update(connection -> {
                CallableStatement callableStatement = connection.prepareCall("{CALL UsuarioUpdateStatus(?, ?)}");
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setInt(2, usuario.getStatus());
                return callableStatement;
            });

            if (rowsAffected > 0) {
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "No se actualizó ningún registro.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result UpdateStatusJPA(Usuario usuario) {
        Result result = new Result();
        try {
            com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA
                    = entityManager.find(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, usuario.getIdUsuario());
            if (usuarioJPA != null) {
                System.out.println("Usuario encontrado con Id: " + usuario.getIdUsuario());
                // Actualizar campo de Status
                usuarioJPA.setStatus(usuario.getStatus());
                entityManager.merge(usuarioJPA);
                //entityManager.flush();

                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "Usuario no encontrado con Id: " + usuario.getIdUsuario();
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    public Result GetAllDinamico(Usuario usuario) {
        Result result = new Result();

        try {
            jdbcTemplate.execute("CALL GetAllDinamicoDesdeVistaUsuario(?,?,?,?,?)", (CallableStatementCallback<Integer>) callableStatement -> {
                callableStatement.setString(1, usuario.getNombre());
                callableStatement.setString(2, usuario.getApellidoPaterno());
                callableStatement.setString(3, usuario.getApellidoMaterno());
                callableStatement.setInt(4, usuario.Rol.getIdRol());
                callableStatement.registerOutParameter(5, Types.REF_CURSOR);

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);

                result.objects = new ArrayList<>();

                while (resultSet.next()) {

                    int idUsuario = resultSet.getInt("IdUsuario");
                    if (!result.objects.isEmpty() && idUsuario == ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Usuario.getIdUsuario()) {
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        ((UsuarioDireccion) (result.objects.get(result.objects.size() - 1))).Direcciones.add(direccion);
                    } else {
                        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                        usuarioDireccion.Usuario = new Usuario();
                        usuarioDireccion.Usuario.setIdUsuario(resultSet.getInt("IdUsuario"));
                        usuarioDireccion.Usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuarioDireccion.Usuario.setApellidoPaterno(resultSet.getString("ApellidoPaterno"));
                        usuarioDireccion.Usuario.setApellidoMaterno(resultSet.getString("ApellidoMaterno"));
                        usuarioDireccion.Usuario.setImagen(resultSet.getString("Imagen"));
                        usuarioDireccion.Usuario.setUsername(resultSet.getString("UserName"));
                        usuarioDireccion.Usuario.setEmail(resultSet.getString("Email"));
                        usuarioDireccion.Usuario.setPassword(resultSet.getString("Password"));
                        java.sql.Date fechaNacimiento = resultSet.getDate("FNacimiento");
                        usuarioDireccion.Usuario.setFNacimiento(fechaNacimiento);
                        usuarioDireccion.Usuario.setSexo(resultSet.getString("Sexo").charAt(0));
                        usuarioDireccion.Usuario.setTelefono(resultSet.getString("Telefono"));
                        usuarioDireccion.Usuario.setNCelular(resultSet.getString("NCelular"));
                        usuarioDireccion.Usuario.setCURP(resultSet.getString("CURP"));
                        usuarioDireccion.Usuario.Rol = new Rol();
                        usuarioDireccion.Usuario.Rol.setIdRol(resultSet.getInt("IdRol"));
                        usuarioDireccion.Usuario.Rol.setNombre(resultSet.getString("NombreRol"));
                        usuarioDireccion.Direcciones = new ArrayList<>();
                        Direccion direccion = new Direccion();
                        direccion.setIdDireccion(resultSet.getInt("IdDireccion"));
                        direccion.setCalle(resultSet.getString("Calle") != null ? resultSet.getString("Calle") : "Sin dirección registrada");
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior") != null ? resultSet.getString("NumeroInterior") : "");
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior") != null ? resultSet.getString("NumeroExterior") : "");
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.setIdColonia(resultSet.getInt("IdColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IdMunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IdEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IdPais"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        usuarioDireccion.Direcciones.add(direccion);
                        result.objects.add(usuarioDireccion);
                    }
                }
                result.correct = true;
                return 1;
            });
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
            result.objects = null;
        }

        return result;
    }

    @Override
    public Result GetAllDinamicoJPA(Usuario usuario) {
        Result result = new Result();

        try {

            // Construccion de query
            String jpql = "SELECT u FROM Usuario u WHERE 1=1";

            // Validacion de datos que se recibe segun los filtros
            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                jpql += " AND u.Nombre LIKE :nombre";
            }

            if (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().isEmpty()) {
                jpql += " AND u.ApellidoPaterno LIKE :apellidoPaterno";
            }

            if (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isEmpty()) {
                jpql += " AND u.ApellidoMaterno LIKE :apellidoMaterno";
            }

            if (usuario.getRol() != null && usuario.getRol().getIdRol() > 0) {
                jpql += " AND u.Rol.IdRol = :idRol";
            }

            // Filtro de status
            if (usuario.getStatus() >= 0) {
                if (usuario.getStatus() == 0) {
                    jpql += " AND u.Status = 1"; // Mostrar solo activos (Status=1 en BD)
                } else if (usuario.getStatus() == 1) {
                    jpql += " AND u.Status = 0"; // Mostrar dados de baja (Status=0 en BD)
                }
            }

            // Ordenar los resultados
            jpql += " ORDER BY u.IdUsuario";

            // Creaccion de queery con armado
            TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Usuario> query
                    = entityManager.createQuery(jpql, com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class);

            // Asignar parámetros
            if (usuario.getNombre() != null && !usuario.getNombre().isEmpty()) {
                query.setParameter("nombre", "%" + usuario.getNombre() + "%");
            }

            if (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().isEmpty()) {
                query.setParameter("apellidoPaterno", "%" + usuario.getApellidoPaterno() + "%");
            }

            if (usuario.getApellidoMaterno() != null && !usuario.getApellidoMaterno().isEmpty()) {
                query.setParameter("apellidoMaterno", "%" + usuario.getApellidoMaterno() + "%");
            }

            if (usuario.getRol() != null && usuario.getRol().getIdRol() > 0) {
                query.setParameter("idRol", usuario.getRol().getIdRol());
            }

            List<com.Digis01.FArceProgramacionNCapas.JPA.Usuario> usuariosJPA = query.getResultList();
            result.objects = new ArrayList<>();

            // Procesar datos de cada usuario
            for (com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA : usuariosJPA) {
                UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
                Usuario user = new Usuario();

                // Mapear datos del usuario
                user.setIdUsuario(usuarioJPA.getIdUsuario());
                user.setNombre(usuarioJPA.getNombre());
                user.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
                user.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
                user.setImagen(usuarioJPA.getImagen());
                user.setUsername(usuarioJPA.getUsername());
                user.setEmail(usuarioJPA.getEmail());
                user.setPassword(usuarioJPA.getPassword());
                user.setFNacimiento(usuarioJPA.getFNacimiento());
                user.setSexo(usuarioJPA.getSexo());
                user.setTelefono(usuarioJPA.getTelefono());
                user.setNCelular(usuarioJPA.getNCelular());
                user.setCURP(usuarioJPA.getCURP());
                user.setStatus(usuarioJPA.getStatus());

                if (usuarioJPA.getRol() != null) {
                    Rol rol = new Rol();
                    rol.setIdRol(usuarioJPA.getRol().getIdRol());
                    rol.setNombre(usuarioJPA.getRol().getNombre());
                    user.setRol(rol);
                }

                usuarioDireccion.Usuario = user;

                // Cargar de Direcciones
                TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> queryDireccion
                        = entityManager.createQuery("FROM Direccion d WHERE d.Usuario.IdUsuario = :IdUsuario", com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class);
                queryDireccion.setParameter("IdUsuario", user.getIdUsuario());

                List<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> direccionesJPA = queryDireccion.getResultList();

                usuarioDireccion.Direcciones = new ArrayList<>();

                for (com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccionJPA : direccionesJPA) {
                    Direccion direccion = new Direccion();
                    direccion.setIdDireccion(direccionJPA.getIdDireccion());
                    direccion.setCalle(direccionJPA.getCalle() != null ? direccionJPA.getCalle() : "Sin dirección registrada");
                    direccion.setNumeroExterior(direccionJPA.getNumeroExterior() != null ? direccionJPA.getNumeroExterior() : "");
                    direccion.setNumeroInterior(direccionJPA.getNumeroInterior() != null ? direccionJPA.getNumeroInterior() : "");

                    direccion.Colonia = new Colonia();
                    direccion.Colonia.setIdColonia(direccionJPA.getColonia().getIdColonia());
                    direccion.Colonia.setNombre(direccionJPA.getColonia().getNombre());
                    direccion.Colonia.setCodigoPostal(direccionJPA.getColonia().getCodigoPostal());

                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.setNombre(direccionJPA.getColonia().getMunicipio().getNombre());

                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.setNombre(direccionJPA.getColonia().getMunicipio().getEstado().getNombre());

                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(direccionJPA.getColonia().getMunicipio().getEstado().getPais().getNombre());

                    usuarioDireccion.Direcciones.add(direccion);
                }

                result.objects.add(usuarioDireccion);
            }

            result.correct = true;

        } catch (Exception ex) {
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = "Error al obtener usuarios dinámicos con JPA.";
            result.ex = ex;
        }

        return result;
    }

    @Transactional
    @Override
    public Result DeleteJPA(int IdUsuario) {
        Result result = new Result();

        try {
            // Buscar el usuario por su ID
            com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuario
                    = entityManager.find(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, IdUsuario);

            if (usuario != null) {
                // Buscar y eliminar direcciones asociadas al usuario
                TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> queryDirecciones = entityManager.createQuery(
                        "FROM Direccion WHERE Usuario.IdUsuario = :idUsuario", com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class);
                queryDirecciones.setParameter("idUsuario", IdUsuario);
                List<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> direcciones = queryDirecciones.getResultList();

                for (com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccion : direcciones) {
                    if (!entityManager.contains(direccion)) {
                        direccion = entityManager.merge(direccion);
                    }
                    entityManager.remove(direccion);
                }

                // Eliminar el usuario después de eliminar sus direcciones
                if (!entityManager.contains(usuario)) {
                    usuario = entityManager.merge(usuario);
                }
                entityManager.remove(usuario);

                result.correct = true;
                result.errorMessage = ("Usuario y sus direcciones eliminados correctamente.");
            } else {
                result.correct = false;
                result.errorMessage = ("Usuario no encontrado.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            result.correct = false;
            result.errorMessage = ("Error al eliminar el usuario con JPA.");
            result.ex = ex;
        }

        return result;
    }

}
