package com.Digis01.FArceProgramacionNCapas;

import com.Digis01.FArceProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.DireccionDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.PaisDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.UsuarioDAOImplementation;
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
import jakarta.persistence.TypedQuery;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MockitoTest {

    @InjectMocks
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @InjectMocks
    private DireccionDAOImplementation direccionDAOImplementation;

    @InjectMocks
    private PaisDAOImplementation paisDAOImplementation;

    @InjectMocks
    private EstadoDAOImplementation estadoDAOImplementation;

    @InjectMocks
    private MunicipioDAOImplementation municipioDAOImplementation;

    @InjectMocks
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private EntityManager entityManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    // Usuario
    // ---> SELECT
    // Correcto
    @Test
    public void testGetAllMockito() throws SQLException {
        ResultSet rsMock = mock(ResultSet.class);   // Funciona como un cursor que se mueve a través de las filas
        Mockito.when(rsMock.next()).thenReturn(true, false);

        // Usuario
        Mockito.when(rsMock.getInt("IdUsuario")).thenReturn(1);
        Mockito.when(rsMock.getString("NombreUsuario")).thenReturn("Juan");
        Mockito.when(rsMock.getString("ApellidoPaterno")).thenReturn("Pérez");
        Mockito.when(rsMock.getString("ApellidoMaterno")).thenReturn("García");
        Mockito.when(rsMock.getString("Imagen")).thenReturn("base64img");
        Mockito.when(rsMock.getString("UserName")).thenReturn("juan123");
        Mockito.when(rsMock.getString("Email")).thenReturn("juan@example.com");
        Mockito.when(rsMock.getString("Password")).thenReturn("securePassword");
        Mockito.when(rsMock.getDate("FNacimiento")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
        Mockito.when(rsMock.getString("Sexo")).thenReturn("H");
        Mockito.when(rsMock.getString("Telefono")).thenReturn("12345678");
        Mockito.when(rsMock.getString("NCelular")).thenReturn("9876543210");
        Mockito.when(rsMock.getString("CURP")).thenReturn("CURP1234");
        Mockito.when(rsMock.getInt("IdRol")).thenReturn(1);
        Mockito.when(rsMock.getString("NombreRol")).thenReturn("Administrador");

        // Dirección
        Mockito.when(rsMock.getInt("IdDireccion")).thenReturn(100);
        Mockito.when(rsMock.getString("Calle")).thenReturn("Av. Siempre Viva");
        Mockito.when(rsMock.getString("NumeroInterior")).thenReturn("2B");
        Mockito.when(rsMock.getString("NumeroExterior")).thenReturn("742");

        // Colonia
        Mockito.when(rsMock.getInt("IdColonia")).thenReturn(10);
        Mockito.when(rsMock.getString("NombreColonia")).thenReturn("Centro");
        Mockito.when(rsMock.getString("CodigoPostal")).thenReturn("12345");

        // Municipio
        when(rsMock.getInt("IdMunicipio")).thenReturn(20);
        Mockito.when(rsMock.getString("NombreMunicipio")).thenReturn("MunicipioX");

        // Estado
        Mockito.when(rsMock.getInt("IdEstado")).thenReturn(30);
        Mockito.when(rsMock.getString("NombreEstado")).thenReturn("EstadoY");

        // País
        Mockito.when(rsMock.getInt("IdPais")).thenReturn(40);
        Mockito.when(rsMock.getString("NombrePais")).thenReturn("México");

        // Simulación del CallableStatementCallback
        Mockito.when(jdbcTemplate.execute(anyString(), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    CallableStatement cs = mock(CallableStatement.class);
                    Mockito.when(cs.getObject(anyInt())).thenReturn(rsMock);
                    return callback.doInCallableStatement(cs);
                });

        // Ejecutar método
        Result result = usuarioDAOImplementation.GetAll();

        // Validaciones
        Assertions.assertTrue(result.correct, "El result.correct viene false");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertFalse(result.objects.isEmpty(), "result.objects esta vacío");

        // Comparar datos
        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.objects.get(0);
        Assertions.assertEquals("Juan", usuarioDireccion.Usuario.getNombre());
        Assertions.assertEquals("Pérez", usuarioDireccion.Usuario.getApellidoPaterno());
        Assertions.assertEquals("García", usuarioDireccion.Usuario.getApellidoMaterno());
        Assertions.assertEquals("juan@example.com", usuarioDireccion.Usuario.getEmail());
        Assertions.assertEquals("12345678", usuarioDireccion.Usuario.getTelefono());
        Assertions.assertEquals("base64img", usuarioDireccion.Usuario.getImagen());
        Assertions.assertEquals("juan123", usuarioDireccion.Usuario.getUsername());
        Assertions.assertEquals("securePassword", usuarioDireccion.Usuario.getPassword());
        Assertions.assertEquals(1, usuarioDireccion.Usuario.Rol.getIdRol());
        Assertions.assertEquals("Administrador", usuarioDireccion.Usuario.Rol.getNombre());

        Direccion direccion = usuarioDireccion.Direcciones.get(0);
        Assertions.assertEquals("Av. Siempre Viva", direccion.getCalle());
        Assertions.assertEquals("2B", direccion.getNumeroInterior());
        Assertions.assertEquals("742", direccion.getNumeroExterior());
        Assertions.assertEquals("Centro", direccion.Colonia.getNombre());
        Assertions.assertEquals("12345", direccion.Colonia.getCodigoPostal());
        Assertions.assertEquals("MunicipioX", direccion.Colonia.Municipio.getNombre());
        Assertions.assertEquals("EstadoY", direccion.Colonia.Municipio.Estado.getNombre());
        Assertions.assertEquals("México", direccion.Colonia.Municipio.Estado.Pais.getNombre());
    }

    // Correcto
    @Test
    public void testGetAllJPAMockito() {
        com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuario1 = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();
        usuario1.setIdUsuario(1);
        usuario1.setNombre("Fernando");
        usuario1.setApellidoPaterno("Arce");

        com.Digis01.FArceProgramacionNCapas.JPA.Rol rol1 = new com.Digis01.FArceProgramacionNCapas.JPA.Rol();
        rol1.setIdRol(1);
        usuario1.setRol(rol1);

        com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuario2 = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();
        usuario2.setIdUsuario(2);
        usuario2.setNombre("Jair");
        usuario2.setApellidoPaterno("Arellano");

        com.Digis01.FArceProgramacionNCapas.JPA.Rol rol2 = new com.Digis01.FArceProgramacionNCapas.JPA.Rol();
        rol2.setIdRol(1);
        usuario2.setRol(rol2);

        List<com.Digis01.FArceProgramacionNCapas.JPA.Usuario> usuariosSimulados = List.of(usuario1, usuario2);

        com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccion1 = new com.Digis01.FArceProgramacionNCapas.JPA.Direccion();
        direccion1.setCalle("Av. Reforma");
        direccion1.setNumeroInterior("Centro");
        direccion1.setNumeroExterior("100");

        com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccion2 = new com.Digis01.FArceProgramacionNCapas.JPA.Direccion();
        direccion2.setCalle("Calle 5");
        direccion2.setNumeroInterior("Industrial");
        direccion2.setNumeroExterior("202");

        TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Usuario> usuarioQuery = mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class)))
                .thenReturn(usuarioQuery);
        Mockito.when(usuarioQuery.getResultList()).thenReturn(usuariosSimulados);

        TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> direccionQuery = mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(contains("FROM Direccion"), eq(com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class)))
                .thenReturn(direccionQuery);
        Mockito.when(direccionQuery.setParameter(eq("IdUsuario"), anyInt())).thenReturn(direccionQuery);
        Mockito.when(direccionQuery.getResultList()).thenReturn(List.of());

        Result result = usuarioDAOImplementation.GetAllJPA();

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
        Assertions.assertEquals(2, result.objects.size());

        // Verificaciones
        Mockito.verify(entityManager, times(1)).createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class));
        Mockito.verify(usuarioQuery).getResultList();

        Mockito.verify(entityManager, times(2)).createQuery(contains(
                "FROM Direccion"), eq(com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class));
        Mockito.verify(direccionQuery, times(2)).setParameter(eq("IdUsuario"), anyInt());
        Mockito.verify(direccionQuery, times(2)).getResultList();
    }

    @Test
    public void testGetAllDinamicoMockito() throws Exception {
        ResultSet rsMock = mock(ResultSet.class);
        CallableStatement csMock = mock(CallableStatement.class);
        Mockito.when(rsMock.next()).thenReturn(true, false);

        // Mock de datos del ResultSet
        Mockito.when(rsMock.getInt("IdUsuario")).thenReturn(1);
        Mockito.when(rsMock.getString("NombreUsuario")).thenReturn("Fernando Jair");
        Mockito.when(rsMock.getString("ApellidoPaterno")).thenReturn("Arce");
        Mockito.when(rsMock.getString("ApellidoMaterno")).thenReturn("Arellano");
        Mockito.when(rsMock.getString("UserName")).thenReturn("FArellano");
        Mockito.when(rsMock.getString("Email")).thenReturn("fernandoArceArelleno@gmail.com");
        Mockito.when(rsMock.getString("Password")).thenReturn("PassSecurity");
        Mockito.when(rsMock.getDate("FNacimiento")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
        Mockito.when(rsMock.getString("Sexo")).thenReturn("M");
        Mockito.when(rsMock.getString("Telefono")).thenReturn("12345678");
        Mockito.when(rsMock.getString("NCelular")).thenReturn("9876543210");
        Mockito.when(rsMock.getString("CURP")).thenReturn("CURP1234");
        Mockito.when(rsMock.getInt("IdRol")).thenReturn(1);
        Mockito.when(rsMock.getString("NombreRol")).thenReturn("Administrador");
        Mockito.when(rsMock.getString("Imagen")).thenReturn("base64img");

        Mockito.when(rsMock.getInt("IdDireccion")).thenReturn(100);
        Mockito.when(rsMock.getString("Calle")).thenReturn("Av. Siempre Viva");
        Mockito.when(rsMock.getString("NumeroInterior")).thenReturn("2B");
        Mockito.when(rsMock.getString("NumeroExterior")).thenReturn("742");

        Mockito.when(rsMock.getInt("IdColonia")).thenReturn(10);
        Mockito.when(rsMock.getString("NombreColonia")).thenReturn("Centro");
        Mockito.when(rsMock.getString("CodigoPostal")).thenReturn("12345");

        Mockito.when(rsMock.getInt("IdMunicipio")).thenReturn(20);
        Mockito.when(rsMock.getString("NombreMunicipio")).thenReturn("MunicipioX");

        Mockito.when(rsMock.getInt("IdEstado")).thenReturn(30);
        Mockito.when(rsMock.getString("NombreEstado")).thenReturn("EstadoY");

        Mockito.when(rsMock.getInt("IdPais")).thenReturn(40);
        Mockito.when(rsMock.getString("NombrePais")).thenReturn("México");

        Mockito.when(csMock.getObject(5)).thenReturn(rsMock);

        // Mock de jdbcTemplate
        Mockito.when(jdbcTemplate.execute(anyString(), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    return callback.doInCallableStatement(csMock);
                });

        Usuario usuario = new Usuario();
        usuario.setNombre("Fernando Jair");
        usuario.setApellidoPaterno("Arce");
        usuario.setApellidoMaterno("Arellano");
        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);

        Result result = usuarioDAOImplementation.GetAllDinamico(usuario);

        // Asserts
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNull(result.ex);
        Assertions.assertNotNull(result.objects);
        Assertions.assertEquals(1, result.objects.size());

        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.objects.get(0);
        Assertions.assertEquals("Fernando Jair", usuarioDireccion.Usuario.getNombre());
        Assertions.assertEquals("Arce", usuarioDireccion.Usuario.getApellidoPaterno());
        Assertions.assertEquals("Arellano", usuarioDireccion.Usuario.getApellidoMaterno());
        Assertions.assertEquals("fernandoArceArelleno@gmail.com", usuarioDireccion.Usuario.getEmail());
        Assertions.assertEquals("base64img", usuarioDireccion.Usuario.getImagen());
        Assertions.assertEquals("FArellano", usuarioDireccion.Usuario.getUsername());
        Assertions.assertEquals("PassSecurity", usuarioDireccion.Usuario.getPassword());
        Assertions.assertEquals("12345678", usuarioDireccion.Usuario.getTelefono());
        Assertions.assertEquals("9876543210", usuarioDireccion.Usuario.getNCelular());
        Assertions.assertEquals("CURP1234", usuarioDireccion.Usuario.getCURP());
        Assertions.assertEquals(1, usuarioDireccion.Usuario.Rol.getIdRol());
        Assertions.assertEquals("Administrador", usuarioDireccion.Usuario.Rol.getNombre());

        Direccion direccion = usuarioDireccion.Direcciones.get(0);
        Assertions.assertEquals("Av. Siempre Viva", direccion.getCalle());
        Assertions.assertEquals("2B", direccion.getNumeroInterior());
        Assertions.assertEquals("742", direccion.getNumeroExterior());
        Assertions.assertEquals("Centro", direccion.Colonia.getNombre());
        Assertions.assertEquals("12345", direccion.Colonia.getCodigoPostal());
        Assertions.assertEquals("MunicipioX", direccion.Colonia.Municipio.getNombre());
        Assertions.assertEquals("EstadoY", direccion.Colonia.Municipio.Estado.getNombre());
        Assertions.assertEquals("México", direccion.Colonia.Municipio.Estado.Pais.getNombre());

        // Verificaciones con Mockito
        verify(jdbcTemplate).execute(anyString(), any(CallableStatementCallback.class));
        verify(csMock).setString(1, "Fernando Jair");
        verify(csMock).setString(2, "Arce");
        verify(csMock).setString(3, "Arellano");
        verify(csMock).setInt(4, 1);
        verify(csMock).registerOutParameter(5, Types.REF_CURSOR);
        verify(csMock).execute();
        verify(csMock).getObject(5);
        verify(rsMock, atLeastOnce()).next();
        verify(rsMock).getString("NombreUsuario");
        verify(rsMock).getString("ApellidoPaterno");
        verify(rsMock).getString("ApellidoMaterno");
        verify(rsMock).getString("Email");
        verify(rsMock).getString("Imagen");
        verify(rsMock).getString("UserName");
    }

    @Test
    public void testGetAllDinamicoJPAMockito() {
        com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();
        usuarioJPA.setIdUsuario(1);
        usuarioJPA.setNombre("Juan");
        usuarioJPA.setApellidoPaterno("Pérez");
        usuarioJPA.setApellidoMaterno("García");
        usuarioJPA.setImagen("base64img");
        usuarioJPA.setUsername("juan123");
        usuarioJPA.setEmail("juan@example.com");
        usuarioJPA.setPassword("securePassword");
        usuarioJPA.setFNacimiento(new java.util.Date());
        usuarioJPA.setSexo('H');
        usuarioJPA.setTelefono("12345678");
        usuarioJPA.setNCelular("9876543210");
        usuarioJPA.setCURP("CURP1234");
        usuarioJPA.setStatus(1);
        com.Digis01.FArceProgramacionNCapas.JPA.Rol rolJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Rol();
        rolJPA.setIdRol(1);
        rolJPA.setNombre("Administrador");
        usuarioJPA.setRol(rolJPA);

        // Simular la consulta de usuarios
        TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Usuario> usuarioQuery = mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class)))
                .thenReturn(usuarioQuery);
        Mockito.when(usuarioQuery.setParameter(anyString(), any())).thenReturn(usuarioQuery);
        Mockito.when(usuarioQuery.getResultList()).thenReturn(List.of(usuarioJPA));

        // Simular la consulta de direcciones
        TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> direccionQuery = mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(contains("FROM Direccion"), eq(com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class)))
                .thenReturn(direccionQuery);
        Mockito.when(direccionQuery.setParameter(eq("IdUsuario"), anyInt())).thenReturn(direccionQuery);
        Mockito.when(direccionQuery.getResultList()).thenReturn(List.of());

        // Crear una instancia de Usuario para la prueba
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellidoPaterno("Pérez");
        usuario.setApellidoMaterno("García");
        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);
        usuario.setStatus(0); // Mostrar solo activos

        // Ejecutar el método
        Result result = usuarioDAOImplementation.GetAllDinamicoJPA(usuario);

        // Validaciones
        Assertions.assertNotNull(result, "El objeto result no debe ser nulo");
        Assertions.assertNull(result.ex, "No debe haber excepciones");
        Assertions.assertNull(result.errorMessage, "No debe haber mensajes de error");
        Assertions.assertTrue(result.correct, "El resultado debe ser correcto");
        Assertions.assertEquals(1, result.objects.size(), "Debe haber un objeto en la lista");

        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.objects.get(0);
        Assertions.assertEquals("Juan", usuarioDireccion.Usuario.getNombre());
        Assertions.assertEquals("Pérez", usuarioDireccion.Usuario.getApellidoPaterno());
        Assertions.assertEquals("García", usuarioDireccion.Usuario.getApellidoMaterno());
        Assertions.assertEquals("juan@example.com", usuarioDireccion.Usuario.getEmail());
        Assertions.assertEquals("12345678", usuarioDireccion.Usuario.getTelefono());
        Assertions.assertEquals("base64img", usuarioDireccion.Usuario.getImagen());
        Assertions.assertEquals("juan123", usuarioDireccion.Usuario.getUsername());
        Assertions.assertEquals("securePassword", usuarioDireccion.Usuario.getPassword());
        Assertions.assertEquals(1, usuarioDireccion.Usuario.Rol.getIdRol());
        Assertions.assertEquals("Administrador", usuarioDireccion.Usuario.Rol.getNombre());
    }

    // ---> INSERT
    @Test
    public void testdireccionesByIdUsuarioMockito() throws SQLException {
        int userId = 1;

        // Mocks
        ResultSet mockUsuarioResultSet = Mockito.mock(ResultSet.class);
        ResultSet mockDireccionesResultSet = Mockito.mock(ResultSet.class);
        CallableStatement mockCallable = Mockito.mock(CallableStatement.class);

        Mockito.when(mockCallable.getObject(1)).thenReturn(mockUsuarioResultSet);
        Mockito.when(mockCallable.getObject(2)).thenReturn(mockDireccionesResultSet);

        // Simular datos del usuario
        Mockito.when(mockUsuarioResultSet.next()).thenReturn(true);
        Mockito.when(mockUsuarioResultSet.getInt("IdUsuario")).thenReturn(userId);
        Mockito.when(mockUsuarioResultSet.getString("NombreUsuario")).thenReturn("Fernando");
        Mockito.when(mockUsuarioResultSet.getString("ApellidoPaterno")).thenReturn("Arce");
        Mockito.when(mockUsuarioResultSet.getString("ApellidoMaterno")).thenReturn("Arellano");
        Mockito.when(mockUsuarioResultSet.getNString("Username")).thenReturn("FArceArellano");
        Mockito.when(mockUsuarioResultSet.getString("Email")).thenReturn("fernando.aa@gmail.com");
        Mockito.when(mockUsuarioResultSet.getString("Password")).thenReturn("123456");
        Mockito.when(mockUsuarioResultSet.getDate("FNacimiento")).thenReturn(new java.sql.Date(System.currentTimeMillis()));
        Mockito.when(mockUsuarioResultSet.getString("Sexo")).thenReturn("M");
        Mockito.when(mockUsuarioResultSet.getString("Telefono")).thenReturn("1234567890");
        Mockito.when(mockUsuarioResultSet.getString("NCelular")).thenReturn("0987654321");
        Mockito.when(mockUsuarioResultSet.getString("CURP")).thenReturn("AEAF020530HMCRRRA8");

        // Simular datos de direcciones
        Mockito.when(mockDireccionesResultSet.next()).thenReturn(true, false);
        Mockito.when(mockDireccionesResultSet.getInt("IdDireccion")).thenReturn(10);
        Mockito.when(mockDireccionesResultSet.getString("Calle")).thenReturn("Calle Fantasma");
        Mockito.when(mockDireccionesResultSet.getString("NumeroExterior")).thenReturn("123");
        Mockito.when(mockDireccionesResultSet.getString("NumeroInterior")).thenReturn("B");
        Mockito.when(mockDireccionesResultSet.getInt("IdColonia")).thenReturn(5);
        Mockito.when(mockDireccionesResultSet.getString("NombreColonia")).thenReturn("Colonia Centro");

        Mockito.when(jdbcTemplate.execute(Mockito.anyString(), Mockito.any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<Integer> callback = invocation.getArgument(1);
                    return callback.doInCallableStatement(mockCallable);
                });

        Result result = usuarioDAOImplementation.direccionesByIdUsuario(userId);

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
        Assertions.assertEquals("Fernando", usuarioDireccion.Usuario.getNombre());
        Assertions.assertEquals(1, usuarioDireccion.Direcciones.size());
        Assertions.assertEquals("Calle Fantasma", usuarioDireccion.Direcciones.get(0).getCalle());
    }

    @Test
    public void testdireccionesByIdUsuarioJPAMockito() {
        int userId = 1;

        // Mock Usuario JPA
        com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();
        usuarioJPA.setIdUsuario(userId);
        usuarioJPA.setNombre("Fernando");
        usuarioJPA.setApellidoPaterno("Arce");
        usuarioJPA.setApellidoMaterno("Arellano");
        usuarioJPA.setUsername("FArceArellano");
        usuarioJPA.setEmail("fernando.aa@gmail.com");
        usuarioJPA.setPassword("123456");
        usuarioJPA.setFNacimiento(new java.sql.Date(System.currentTimeMillis()));
        usuarioJPA.setSexo('M');
        usuarioJPA.setTelefono("1234567890");
        usuarioJPA.setNCelular("0987654321");
        usuarioJPA.setCURP("AEAF020530HMCRRRA8");

        com.Digis01.FArceProgramacionNCapas.JPA.Rol rol = new com.Digis01.FArceProgramacionNCapas.JPA.Rol();
        rol.setIdRol(1);
        usuarioJPA.Rol = rol;

        // Mock Dirección JPA
        com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccionJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Direccion();
        direccionJPA.setIdDireccion(10);
        direccionJPA.setCalle("Calle Fantasma");
        direccionJPA.setNumeroExterior("123");
        direccionJPA.setNumeroInterior("B");
        com.Digis01.FArceProgramacionNCapas.JPA.Colonia colonia = new com.Digis01.FArceProgramacionNCapas.JPA.Colonia();
        colonia.setIdColonia(5);
        colonia.setNombre("Colonia Centro");
        direccionJPA.Colonia = colonia;

        List<com.Digis01.FArceProgramacionNCapas.JPA.Direccion> direcciones = List.of(direccionJPA);

        Mockito.when(entityManager.find(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, userId))
                .thenReturn(usuarioJPA);

        TypedQuery mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(mockQuery.getResultList()).thenReturn(direcciones);
        Mockito.when(entityManager.createQuery(Mockito.anyString(), Mockito.eq(com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class)))
                .thenReturn(mockQuery);

        Result result = usuarioDAOImplementation.DireccionesByIdUsuarioJPA(userId);

        Assertions.assertTrue(result.correct);
        Assertions.assertNotNull(result.object);
        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
        Assertions.assertEquals("Fernando", usuarioDireccion.Usuario.getNombre());
        Assertions.assertEquals(1, usuarioDireccion.Direcciones.size());
        Assertions.assertEquals("Calle Fantasma", usuarioDireccion.Direcciones.get(0).getCalle());
    }

    @Test
    public void testGetByIdMockito() throws SQLException {
        // Arrange: Simulación del ResultSet con datos esperados
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.when(mockResultSet.next()).thenReturn(true);
        Mockito.when(mockResultSet.getInt("IdUsuario")).thenReturn(1);
        Mockito.when(mockResultSet.getString("NombreUsuario")).thenReturn("Fernando Jair");
        Mockito.when(mockResultSet.getString("ApellidoPaterno")).thenReturn("Arce");
        Mockito.when(mockResultSet.getString("ApellidoMaterno")).thenReturn("Arellano");
        Mockito.when(mockResultSet.getString("Imagen")).thenReturn("imagen.jpg");
        Mockito.when(mockResultSet.getString("Username")).thenReturn("FArceArellano");
        Mockito.when(mockResultSet.getString("Email")).thenReturn("fernando.aa@gmail.com");
        Mockito.when(mockResultSet.getString("Password")).thenReturn("Contraseña123");
        Mockito.when(mockResultSet.getDate("FNacimiento")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
        Mockito.when(mockResultSet.getString("Sexo")).thenReturn("M");
        Mockito.when(mockResultSet.getString("Telefono")).thenReturn("5559242886");
        Mockito.when(mockResultSet.getString("NCelular")).thenReturn("5540742014");
        Mockito.when(mockResultSet.getString("CURP")).thenReturn("AEAF020530HMCRRRA8");
        Mockito.when(mockResultSet.getInt("IdRol")).thenReturn(1);
        Mockito.when(mockResultSet.getString("NombreRol")).thenReturn("Administrador");

        // Simular llamada a jdbcTemplate.execute
        Mockito.when(jdbcTemplate.execute(Mockito.anyString(), Mockito.any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<Integer> callback = invocation.getArgument(1);
                    CallableStatement mockCallable = Mockito.mock(CallableStatement.class);

                    // Simulación del REF_CURSOR
                    Mockito.when(mockCallable.getObject(2)).thenReturn(mockResultSet);

                    return callback.doInCallableStatement(mockCallable);
                });

        // Act
        Result result = usuarioDAOImplementation.GetById(1);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.ex);
        Assertions.assertNull(result.errorMessage);
        Assertions.assertNotNull(result.object);
        Assertions.assertInstanceOf(UsuarioDireccion.class, result.object);

        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
        Assertions.assertEquals(1, usuarioDireccion.Usuario.getIdUsuario());
        Assertions.assertEquals("Fernando Jair", usuarioDireccion.Usuario.getNombre());
        Assertions.assertEquals("Arce", usuarioDireccion.Usuario.getApellidoPaterno());
        Assertions.assertEquals("Arellano", usuarioDireccion.Usuario.getApellidoMaterno());
        Assertions.assertEquals("imagen.jpg", usuarioDireccion.Usuario.getImagen());
        Assertions.assertEquals("FArceArellano", usuarioDireccion.Usuario.getUsername());
        Assertions.assertEquals("fernando.aa@gmail.com", usuarioDireccion.Usuario.getEmail());
        Assertions.assertEquals("Contraseña123", usuarioDireccion.Usuario.getPassword());
        //Assertions.assertEquals(new java.util.Date(12051973L), usuarioDireccion.Usuario.getFNacimiento());
        Assertions.assertEquals('M', usuarioDireccion.Usuario.getSexo());
        Assertions.assertEquals("5559242886", usuarioDireccion.Usuario.getTelefono());
        Assertions.assertEquals("5540742014", usuarioDireccion.Usuario.getNCelular());
        Assertions.assertEquals("AEAF020530HMCRRRA8", usuarioDireccion.Usuario.getCURP());
        Assertions.assertEquals(1, usuarioDireccion.Usuario.getRol().getIdRol());
        Assertions.assertEquals("Administrador", usuarioDireccion.Usuario.getRol().getNombre());

        // Verificar ejecución del procedimiento
        Mockito.verify(jdbcTemplate, Mockito.times(1))
                .execute(Mockito.eq("CALL UsuarioGetById(?,?)"), Mockito.any(CallableStatementCallback.class));
    }

    @Test
    public void testGetByIdJPAMockito() {
        com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();
        usuarioJPA.setIdUsuario(1);
        usuarioJPA.setNombre("Fernando Jair");
        usuarioJPA.setApellidoPaterno("Arce");
        usuarioJPA.setApellidoMaterno("Arellano");
        usuarioJPA.setUsername("FArceArellano");
        usuarioJPA.setEmail("fernando.aa@gmail.com");
        usuarioJPA.setPassword("Contraseña123");
        usuarioJPA.setSexo('M');
        usuarioJPA.setTelefono("5559242886");
        usuarioJPA.setNCelular("5540742014");
        usuarioJPA.setCURP("AEAF020530HMCRRRA8");
        usuarioJPA.setImagen("imagen.jpg");
        usuarioJPA.setFNacimiento(new java.util.Date(12051973L));

        com.Digis01.FArceProgramacionNCapas.JPA.Rol rolJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Rol();
        rolJPA.setIdRol(1);
        usuarioJPA.Rol = rolJPA;

        Mockito.when(entityManager.find(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, 1)).thenReturn(usuarioJPA);

        Result result = usuarioDAOImplementation.GetByIdJPA(1);

        Assertions.assertNotNull(result, "El resultado no debe ser nulo");
        Assertions.assertTrue(result.correct, "Se esperaba un resultado correcto");
        Assertions.assertNull(result.errorMessage, "No debe haber mensaje de error");
        Assertions.assertNull(result.ex, "No debe haber excepción");

        Assertions.assertNotNull(result.object, "El objeto devuelto no debe ser nulo");
        Assertions.assertInstanceOf(com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion.class, result.object);

        UsuarioDireccion usuarioDireccion = (UsuarioDireccion) result.object;
        Assertions.assertEquals(1, usuarioDireccion.Usuario.getIdUsuario());
        Assertions.assertEquals("Fernando Jair", usuarioDireccion.Usuario.getNombre());
        Assertions.assertEquals("Arce", usuarioDireccion.Usuario.getApellidoPaterno());
        Assertions.assertEquals("Arellano", usuarioDireccion.Usuario.getApellidoMaterno());
        Assertions.assertEquals("FArceArellano", usuarioDireccion.Usuario.getUsername());
        Assertions.assertEquals("fernando.aa@gmail.com", usuarioDireccion.Usuario.getEmail());
        Assertions.assertEquals("Contraseña123", usuarioDireccion.Usuario.getPassword());
        Assertions.assertEquals('M', usuarioDireccion.Usuario.getSexo());
        Assertions.assertEquals("5559242886", usuarioDireccion.Usuario.getTelefono());
        Assertions.assertEquals("5540742014", usuarioDireccion.Usuario.getNCelular());
        Assertions.assertEquals("AEAF020530HMCRRRA8", usuarioDireccion.Usuario.getCURP());
        Assertions.assertEquals("imagen.jpg", usuarioDireccion.Usuario.getImagen());
        Assertions.assertEquals(new java.util.Date(12051973L), usuarioDireccion.Usuario.getFNacimiento());
        Assertions.assertEquals(1, usuarioDireccion.Usuario.getRol().getIdRol());

        // Verificar llamada a entityManager
        Mockito.verify(entityManager, Mockito.times(1)).find(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, 1);
    }

    @Test
    public void testUsuarioAddMockito() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setNombre("Juan");
        usuarioDireccion.Usuario.setApellidoPaterno("Pérez");
        usuarioDireccion.Usuario.setApellidoMaterno("Gómez");
        usuarioDireccion.Usuario.setImagen("base64image");
        usuarioDireccion.Usuario.setFNacimiento(new java.util.Date(12051973L));
        usuarioDireccion.Usuario.setNCelular("1234567890");
        usuarioDireccion.Usuario.setCURP("XXXX000000HDF");
        usuarioDireccion.Usuario.setUsername("juanp");
        usuarioDireccion.Usuario.setEmail("juan@test.com");
        usuarioDireccion.Usuario.setPassword("123456");
        usuarioDireccion.Usuario.setSexo('H');
        usuarioDireccion.Usuario.setTelefono("12345678");
        usuarioDireccion.Usuario.setStatus(1);
        usuarioDireccion.Usuario.Rol = new Rol();
        usuarioDireccion.Usuario.Rol.setIdRol(1);

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Av. Principal");
        usuarioDireccion.Direccion.setNumeroExterior("100");
        usuarioDireccion.Direccion.setNumeroInterior("10A");
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(1);

        // Simular el comportamiento del encoder y jdbcTemplate
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("hashed123");

        Mockito.when(jdbcTemplate.execute(Mockito.anyString(), Mockito.any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    CallableStatement cs = Mockito.mock(CallableStatement.class);
                    //Mockito.doNothing().when(cs).executeUpdate();
                    return callback.doInCallableStatement(cs);
                });

        Result result = usuarioDAOImplementation.Add(usuarioDireccion);

        // Verificaciones
        Assertions.assertNotNull(result, "El objeto result está nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepción");
        Assertions.assertNull(result.errorMessage, "Se envió un mensaje de error");
        Assertions.assertTrue(result.correct, "La operación no fue exitosa");

        // Verificar que encode se llamó
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("123456");

        // Verificar que se llamó a jdbcTemplate.execute
        Mockito.verify(jdbcTemplate, Mockito.times(1)).execute(Mockito.anyString(), Mockito.any(CallableStatementCallback.class));
    }

    // Correcto
    @Test
    public void testUsuarioAddJPAMockito() {
        com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setNombre("Juan");
        usuarioDireccion.Usuario.setApellidoPaterno("Pérez");
        usuarioDireccion.Usuario.setApellidoMaterno("Gómez");
        usuarioDireccion.Usuario.setFNacimiento(new java.util.Date(12051973L));
        usuarioDireccion.Usuario.setNCelular("1234567890");
        usuarioDireccion.Usuario.setCURP("XXXX000000HDF");
        usuarioDireccion.Usuario.setUsername("juanp");
        usuarioDireccion.Usuario.setEmail("juan@test.com");
        usuarioDireccion.Usuario.setPassword("123456");
        usuarioDireccion.Usuario.setSexo('H');
        usuarioDireccion.Usuario.setTelefono("12345678");
        usuarioDireccion.Usuario.setStatus(1);
        usuarioDireccion.Usuario.Rol = new Rol();
        usuarioDireccion.Usuario.Rol.setIdRol(1);

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Av. Principal");
        usuarioDireccion.Direccion.setNumeroExterior("100");
        usuarioDireccion.Direccion.setNumeroInterior("10A");
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(1);

        // Configurar mocks para la contraseña
        Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("hashed123");

        // Capturar los datos y hacer algo
        Mockito.doNothing().when(entityManager).persist(Mockito.any());

        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);

        // Verificaciones
        Assertions.assertNotNull(result, "El objeto result está nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepción");
        Assertions.assertNull(result.errorMessage, "Se envió un mensaje de error");
        Assertions.assertTrue(result.correct, "La operación no fue exitosa");

        // Verificar persistencias
        Mockito.verify(entityManager, Mockito.times(1))
                .persist(Mockito.any(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class));
        Mockito.verify(entityManager, Mockito.times(1))
                .persist(Mockito.any(com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class));
    }

    // ---> UPDATE
    @Test
    public void testUpdateUsuarioMockito() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Juan");
        usuario.setApellidoPaterno("Pérez");
        usuario.setApellidoMaterno("García");
        usuario.setImagen("imagen.jpg");
        usuario.setUsername("juan123");
        usuario.setEmail("juan@example.com");
        usuario.setPassword("12345");
        usuario.setFNacimiento(new java.util.Date(12051973L));
        usuario.setSexo('M');
        usuario.setNCelular("1234567890");
        usuario.setTelefono("0987654321");
        usuario.setCURP("CURP1234");
        Rol rol = new Rol();
        rol.setIdRol(2);
        usuario.setRol(rol);

        Mockito.when(passwordEncoder.encode("12345")).thenReturn("hashed123");

        Mockito.when(jdbcTemplate.execute(anyString(), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<Integer> callback = invocation.getArgument(1);
                    CallableStatement mockCallable = mock(CallableStatement.class);
                    Mockito.when(mockCallable.executeUpdate()).thenReturn(1);
                    return callback.doInCallableStatement(mockCallable);
                });

        Result result = usuarioDAOImplementation.Update(usuario);

        Assertions.assertTrue(result.correct);

        Mockito.verify(jdbcTemplate, Mockito.times(1)).execute(Mockito.anyString(), Mockito.any(CallableStatementCallback.class));
    }

    @Test
    public void testUpdateJPAUsuarioMockito() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        // Configurar el usuario
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan");
        usuario.setApellidoPaterno("Pérez");
        usuario.setApellidoMaterno("Gómez");
        usuario.setFNacimiento(new java.util.Date(12051973L));
        usuario.setNCelular("1234567890");
        usuario.setCURP("XXXX000000HDF");
        usuario.setUsername("juanp");
        usuario.setEmail("juan@test.com");
        usuario.setPassword("123456");
        usuario.setSexo('H');
        usuario.setTelefono("12345678");
        usuario.setImagen("base64img");
        usuario.setStatus(1);

        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);

        // Encriptar contraseña simulada
        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("hashed123");

        // Configurar la dirección
        Direccion direccion = new Direccion();
        direccion.setCalle("Av. Principal");
        direccion.setNumeroExterior("100");
        direccion.setNumeroInterior("10A");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1);
        direccion.setColonia(colonia);

        usuarioDireccion.setUsuario(usuario);
        usuarioDireccion.setDireccion(direccion);

        // Simular persistencia sin errores
        doNothing().when(entityManager).persist(any());

        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);

        // Assert
        Assertions.assertNotNull(result, "El objeto result está nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepción");
        Assertions.assertNull(result.errorMessage, "Se envió un mensaje de error");
        Assertions.assertTrue(result.correct, "La operación no fue exitosa");

        // Verificar que se intentó persistir
        Mockito.verify(entityManager, atLeastOnce()).persist(any());
    }

    @Test
    public void testUpdateStatusMockito() {
        com.Digis01.FArceProgramacionNCapas.ML.Usuario usuario = new com.Digis01.FArceProgramacionNCapas.ML.Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Fernando Jair");
        usuario.setApellidoPaterno("Arce");
        usuario.setStatus(0); // Se quiere cambiar a 0

        // Simulación de llamada exitosa al procedimiento almacenado
        Mockito.when(jdbcTemplate.update(any(PreparedStatementCreator.class)))
                .thenReturn(1); // Simula que se actualizó 1 fila

        Result result = usuarioDAOImplementation.UpdateStatus(usuario);

        Assertions.assertNotNull(result, "El objeto result no debe ser nulo");
        Assertions.assertTrue(result.correct, "La actualización del status no se realizó correctamente");
        Assertions.assertNull(result.errorMessage, "No debe haber mensaje de error");
        Assertions.assertNull(result.ex, "No debe haber excepción");
        Assertions.assertNull(result.object, "El objeto result.object debe ser nulo");
        Assertions.assertNull(result.objects, "El objeto result.objects debe ser nulo");

        // Verifica que se ejecutó el procedimiento almacenado una vez
        Mockito.verify(jdbcTemplate, Mockito.times(1)).update(any(PreparedStatementCreator.class));
    }

    @Test
    public void testUpdateStatusJPAMockito() {
        com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioSimulado = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();
        usuarioSimulado.setIdUsuario(1);
        usuarioSimulado.setNombre("Fernando Jair");
        usuarioSimulado.setApellidoMaterno("Arce");
        usuarioSimulado.setSexo('M');
        usuarioSimulado.setStatus(1); // Su estado actual es 1
        usuarioSimulado.Rol = new com.Digis01.FArceProgramacionNCapas.JPA.Rol();

        // Usuario que se recibe para actualizar el status
        com.Digis01.FArceProgramacionNCapas.ML.Usuario usuario = new com.Digis01.FArceProgramacionNCapas.ML.Usuario();
        usuario.setIdUsuario(1);
        usuario.setStatus(0); // Se quiere cambiar a 0

        // Simulación del entityManager
        Mockito.when(entityManager.find(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class, 1)).thenReturn(usuarioSimulado);

        Result result = usuarioDAOImplementation.UpdateStatusJPA(usuario);

        Assertions.assertNotNull(result, "El objeto result no debe ser nulo");
        Assertions.assertTrue(result.correct, "La actualización del status no se realizó correctamente");
        Assertions.assertNull(result.errorMessage, "No debe haber mensaje de error");
        Assertions.assertNull(result.ex, "No debe haber excepción");
        Assertions.assertEquals(0, usuarioSimulado.getStatus(), "El status del usuario no fue actualizado correctamente");
        Assertions.assertNull(result.object);
        Assertions.assertNull(result.objects);

        // Verificar que merge fue llamado
        Mockito.verify(entityManager, Mockito.times(1)).merge(usuarioSimulado);
    }

    // ---> DELETE
    @Test
    public void testDeleteJPAMockito() {
        com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();

        usuarioJPA.setIdUsuario(1000);
        usuarioJPA.setNombre("Juan");
        usuarioJPA.setApellidoPaterno("Pérez");
        usuarioJPA.setApellidoMaterno("Gómez");
        usuarioJPA.setFNacimiento(new java.util.Date(12051973L));
        usuarioJPA.setNCelular("1234567890");
        usuarioJPA.setCURP("XXXX000000HDF");
        usuarioJPA.setUsername("juanp");
        usuarioJPA.setEmail("juan@test.com");
        usuarioJPA.setPassword("123456");
        usuarioJPA.setSexo('H');
        usuarioJPA.setTelefono("12345678");
        usuarioJPA.setStatus(1);
//        usuarioJPA.Rol = new Rol();
//        usuarioJPA.Rol.setIdRol(1);

        com.Digis01.FArceProgramacionNCapas.JPA.Direccion direccionJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Direccion();
        direccionJPA.setIdDireccion(1);
        direccionJPA.setCalle("Av. Principal");
        direccionJPA.setNumeroExterior("100");
        direccionJPA.setNumeroInterior("10A");
        direccionJPA.setUsuario(usuarioJPA);
//        direccionJPA.Colonia = new Colonia();
//        direccionJPA.Colonia.setIdColonia(1);

//        Mockito.when(passwordEncoder.encode(anyString())).thenReturn("hashed123");
        // Mock: encontrar usuario
        Mockito.when(entityManager.find(eq(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class), eq(1000)))
                .thenReturn(usuarioJPA);

        // Mock: direcciones asociadas
        TypedQuery mockQuery = Mockito.mock(TypedQuery.class);
        Mockito.when(entityManager.createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class)))
                .thenReturn(mockQuery);
        Mockito.when(mockQuery.setParameter(eq("idUsuario"), eq(1000))).thenReturn(mockQuery);
        Mockito.when(mockQuery.getResultList()).thenReturn(List.of(direccionJPA));

        // Mock merge y remove
        Mockito.when(entityManager.merge(any())).thenAnswer(i -> i.getArguments()[0]);
        Mockito.doNothing().when(entityManager).remove(any());

        Result result = usuarioDAOImplementation.DeleteJPA(1000);

        Assertions.assertNotNull(result, "El objeto result no debe ser nulo");
        Assertions.assertTrue(result.correct, "La eliminación del usuario y sus direcciones no se realizó correctamente");
        Assertions.assertNull(result.object, "El campo result.object debe ser nulo");
        Assertions.assertNull(result.objects, "El campo result.objects debe ser nulo");
        Assertions.assertNull(result.ex, "No debe haber excepción en result.ex");
        Assertions.assertEquals("Usuario y sus direcciones eliminados correctamente.", result.errorMessage);

        Mockito.verify(entityManager, Mockito.times(1)).find(eq(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class), eq(1000));
        Mockito.verify(entityManager, Mockito.times(2)).remove(any()); // 1 dirección + 1 usuario
    }

    // Direccion
    // Catalogo PAIS, ESTADO, MUNICIPIO, COLONIA
    @Test
    public void testGetAllPaisMockito() throws SQLException {
        // Mock de ResultSet
        ResultSet rsMock = mock(ResultSet.class);
        Mockito.when(rsMock.next()).thenReturn(true, true, false); // Dos registros

        Mockito.when(rsMock.getInt("IdPais")).thenReturn(1).thenReturn(2);
        Mockito.when(rsMock.getString("Nombre")).thenReturn("México").thenReturn("Colombia");

        // Mock del CallableStatement
        CallableStatement csMock = mock(CallableStatement.class);
        Mockito.when(csMock.getObject(1)).thenReturn(rsMock);

        // Simulación del jdbcTemplate
        Mockito.when(jdbcTemplate.execute(eq("{CALL PaisGetAll(?)}"), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    return callback.doInCallableStatement(csMock);
                });

        // Ejecutar el método
        Result result = paisDAOImplementation.GetAll();

        // Verificaciones
        Assertions.assertNotNull(result, "El objeto result está nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepción");
        Assertions.assertNull(result.errorMessage, "Se envió un mensaje de error");
        Assertions.assertTrue(result.correct, "La consulta no fue exitosa");
        Assertions.assertEquals(2, result.objects.size(), "Número incorrecto de países");

        Pais pais1 = (Pais) result.objects.get(0);
        Pais pais2 = (Pais) result.objects.get(1);

        Assertions.assertEquals(1, pais1.getIdPais());
        Assertions.assertEquals("México", pais1.getNombre());

        Assertions.assertEquals(2, pais2.getIdPais());
        Assertions.assertEquals("Colombia", pais2.getNombre());

        // Verificación de que se llamó al procedimiento
        Mockito.verify(jdbcTemplate, times(1)).execute(eq("{CALL PaisGetAll(?)}"), any(CallableStatementCallback.class));
    }

    @Test
    public void testGetAllPaisJPAMockito() {
        // Pais simulado 1
        com.Digis01.FArceProgramacionNCapas.JPA.Pais pais = new com.Digis01.FArceProgramacionNCapas.JPA.Pais();
        pais.setIdPais(1);
        pais.setNombre("México");

        // Pais simulado 2
        com.Digis01.FArceProgramacionNCapas.JPA.Pais pais2 = new com.Digis01.FArceProgramacionNCapas.JPA.Pais();
        pais2.setIdPais(2);
        pais2.setNombre("Colombia");

        List<com.Digis01.FArceProgramacionNCapas.JPA.Pais> paisesSimulados = List.of(pais, pais2);

        TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Pais> paisQuery = mock(TypedQuery.class);

        Mockito.when(entityManager.createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Pais.class)))
                .thenReturn(paisQuery);

        Mockito.when(paisQuery.getResultList()).thenReturn(paisesSimulados);

        Result result = paisDAOImplementation.GetAllJPA();

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
        Assertions.assertEquals(2, result.objects.size());

        // Verificaciones
        Mockito.verify(entityManager, times(1)).createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Pais.class));
        Mockito.verify(paisQuery).getResultList();

        Mockito.verify(entityManager, times(1)).createQuery(contains(
                "FROM Pais ORDER BY IdPais"), eq(com.Digis01.FArceProgramacionNCapas.JPA.Pais.class));
    }

    @Test
    public void testEstadoByIdPaisMockito() throws SQLException {
        // Simulación del ResultSet
        ResultSet resultSetMock = mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true, true, false); // 2 registros

        Mockito.when(resultSetMock.getInt("IdEstado")).thenReturn(1).thenReturn(2);
        Mockito.when(resultSetMock.getString("Nombre")).thenReturn("Jalisco").thenReturn("Sonora");

        // Simulación del CallableStatement
        CallableStatement callableStatementMock = mock(CallableStatement.class);
        Mockito.when(callableStatementMock.getObject(2)).thenReturn(resultSetMock);

        // Simulación del jdbcTemplate.execute
        Mockito.when(jdbcTemplate.execute(eq("CALL EstadoByIdPais(?,?)"), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    return callback.doInCallableStatement(callableStatementMock);
                });

        // Ejecutar método
        Result result = estadoDAOImplementation.EstadoByIdPais(1);

        // Validaciones
        Assertions.assertNotNull(result, "El resultado no debe ser nulo");
        Assertions.assertTrue(result.correct, "El resultado debe ser correcto");
        Assertions.assertNull(result.ex, "No debe haber excepción");
        Assertions.assertNull(result.errorMessage, "No debe haber mensaje de error");
        Assertions.assertEquals(2, result.objects.size(), "Debe haber 2 estados");

        Estado estado1 = (Estado) result.objects.get(0);
        Estado estado2 = (Estado) result.objects.get(1);

        Assertions.assertEquals(1, estado1.getIdEstado());
        Assertions.assertEquals("Jalisco", estado1.getNombre());

        Assertions.assertEquals(2, estado2.getIdEstado());
        Assertions.assertEquals("Sonora", estado2.getNombre());

        // Verificación de ejecución
        Mockito.verify(jdbcTemplate, times(1)).execute(eq("CALL EstadoByIdPais(?,?)"), any(CallableStatementCallback.class));
    }

    @Test
    public void testEstadoByIdPaisJPAMockito() {
        // Crear objetos de prueba
        com.Digis01.FArceProgramacionNCapas.JPA.Pais pais = new com.Digis01.FArceProgramacionNCapas.JPA.Pais();
        pais.setIdPais(1);

        com.Digis01.FArceProgramacionNCapas.JPA.Estado estado1 = new com.Digis01.FArceProgramacionNCapas.JPA.Estado();
        estado1.setIdEstado(1);
        estado1.setNombre("México");
        estado1.setPais(pais);

        com.Digis01.FArceProgramacionNCapas.JPA.Estado estado2 = new com.Digis01.FArceProgramacionNCapas.JPA.Estado();
        estado2.setIdEstado(2);
        estado2.setNombre("Culiacan");
        estado2.setPais(pais);

        // Mock del TypedQuery
        TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Estado> estadoQuery = mock(TypedQuery.class);

        // Simular comportamiento del EntityManager
        Mockito.when(entityManager.createQuery(
                contains("FROM Estado e WHERE e.Pais.IdPais = :idPais ORDER BY e.IdEstado"),
                eq(com.Digis01.FArceProgramacionNCapas.JPA.Estado.class))
        ).thenReturn(estadoQuery);

        Mockito.when(estadoQuery.setParameter(eq("idPais"), eq(1))).thenReturn(estadoQuery);
        Mockito.when(estadoQuery.getResultList()).thenReturn(List.of(estado1, estado2));

        // Ejecutar método
        Result result = estadoDAOImplementation.EstadoByIdPaisJPA(1);

        // Verificaciones
        Assertions.assertNotNull(result, "El objeto result está nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepción");
        Assertions.assertNull(result.errorMessage, "Se envió un mensaje de error");
        Assertions.assertTrue(result.correct, "La consulta no fue exitosa");
        Assertions.assertEquals(2, result.objects.size(), "El número de estados es incorrecto");

        Mockito.verify(entityManager, times(1)).createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Estado.class));
        Mockito.verify(estadoQuery, times(1)).setParameter("idPais", 1);
        Mockito.verify(estadoQuery, times(1)).getResultList();
    }

    @Test
    public void testMunicipioByIdEstadoMockito() throws SQLException {
        // Simulación del ResultSet
        ResultSet resultSetMock = mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true, true, false); // 2 registros

        Mockito.when(resultSetMock.getInt("IdMunicipio")).thenReturn(1).thenReturn(2);
        Mockito.when(resultSetMock.getString("Nombre")).thenReturn("Chicoloapan").thenReturn("Texcoco");

        // Simulación del CallableStatement
        CallableStatement callableStatementMock = mock(CallableStatement.class);
        Mockito.when(callableStatementMock.getObject(2)).thenReturn(resultSetMock);

        // Simulación del jdbcTemplate.execute
        Mockito.when(jdbcTemplate.execute(eq("CALL MunicipioByIdEstado(?,?)"), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    return callback.doInCallableStatement(callableStatementMock);
                });

        // Ejecutar método
        Result result = municipioDAOImplementation.MunicipioByIdEstado(1);

        // Validaciones
        Assertions.assertNotNull(result, "El resultado no debe ser nulo");
        Assertions.assertTrue(result.correct, "El resultado debe ser correcto");
        Assertions.assertNull(result.ex, "No debe haber excepción");
        Assertions.assertNull(result.errorMessage, "No debe haber mensaje de error");
        Assertions.assertEquals(2, result.objects.size(), "Debe haber 2 municipios");

        Municipio municipio1 = (Municipio) result.objects.get(0);
        Municipio municipio2 = (Municipio) result.objects.get(1);

        Assertions.assertEquals(1, municipio1.getIdMunicipio());
        Assertions.assertEquals("Chicoloapan", municipio1.getNombre());

        Assertions.assertEquals(2, municipio2.getIdMunicipio());
        Assertions.assertEquals("Texcoco", municipio2.getNombre());

        // Verificación de ejecución
        Mockito.verify(jdbcTemplate, times(1)).execute(eq("CALL MunicipioByIdEstado(?,?)"), any(CallableStatementCallback.class));
    }

    @Test
    public void testMunicipioByIdEstadoJPAMockito() {
        // Crear objetos de prueba
        com.Digis01.FArceProgramacionNCapas.JPA.Estado estado = new com.Digis01.FArceProgramacionNCapas.JPA.Estado();
        estado.setIdEstado(1);

        com.Digis01.FArceProgramacionNCapas.JPA.Municipio municipio1 = new com.Digis01.FArceProgramacionNCapas.JPA.Municipio();
        municipio1.setIdMunicipio(1);
        municipio1.setNombre("Chicoloapan");
        municipio1.setEstado(estado);

        com.Digis01.FArceProgramacionNCapas.JPA.Municipio municipio2 = new com.Digis01.FArceProgramacionNCapas.JPA.Municipio();
        municipio2.setIdMunicipio(2);
        municipio2.setNombre("Texcoco");
        municipio2.setEstado(estado);

        // Mock del TypedQuery
        TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Municipio> municipioQuery = mock(TypedQuery.class);

        // Simular comportamiento del EntityManager
        Mockito.when(entityManager.createQuery(
                contains("FROM Municipio m WHERE m.Estado.IdEstado = :idEstado ORDER BY m.IdMunicipio"),
                eq(com.Digis01.FArceProgramacionNCapas.JPA.Municipio.class))
        ).thenReturn(municipioQuery);

        Mockito.when(municipioQuery.setParameter(eq("idEstado"), eq(1))).thenReturn(municipioQuery);
        Mockito.when(municipioQuery.getResultList()).thenReturn(List.of(municipio1, municipio2));

        // Ejecutar método
        Result result = municipioDAOImplementation.MunicipioByIdEstadoJPA(1);

        // Verificaciones
        Assertions.assertNotNull(result, "El objeto result está nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepción");
        Assertions.assertNull(result.errorMessage, "Se envió un mensaje de error");
        Assertions.assertTrue(result.correct, "La consulta no fue exitosa");
        Assertions.assertEquals(2, result.objects.size(), "El número de estados es incorrecto");

        Mockito.verify(entityManager, times(1)).createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Municipio.class));
        Mockito.verify(municipioQuery, times(1)).setParameter("idEstado", 1);
        Mockito.verify(municipioQuery, times(1)).getResultList();
    }

    @Test
    public void testColoniaByIdMunicipioMockito() throws SQLException {
        // Simulación del ResultSet
        ResultSet resultSetMock = mock(ResultSet.class);
        Mockito.when(resultSetMock.next()).thenReturn(true, true, false); // 2 registros

        Mockito.when(resultSetMock.getInt("IdColonia")).thenReturn(1).thenReturn(2);
        Mockito.when(resultSetMock.getString("Nombre")).thenReturn("Geo").thenReturn("Ara 3");

        // Simulación del CallableStatement
        CallableStatement callableStatementMock = mock(CallableStatement.class);
        Mockito.when(callableStatementMock.getObject(2)).thenReturn(resultSetMock);

        // Simulación del jdbcTemplate.execute
        Mockito.when(jdbcTemplate.execute(eq("CALL ColoniaByIdMunicipio(?,?)"), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    return callback.doInCallableStatement(callableStatementMock);
                });

        // Ejecutar método
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipio(1);

        // Validaciones
        Assertions.assertNotNull(result, "El resultado no debe ser nulo");
        Assertions.assertTrue(result.correct, "El resultado debe ser correcto");
        Assertions.assertNull(result.ex, "No debe haber excepción");
        Assertions.assertNull(result.errorMessage, "No debe haber mensaje de error");
        Assertions.assertEquals(2, result.objects.size(), "Debe haber 2 colonias");

        Colonia colonia1 = (Colonia) result.objects.get(0);
        Colonia colonia2 = (Colonia) result.objects.get(1);

        Assertions.assertEquals(1, colonia1.getIdColonia());
        Assertions.assertEquals("Geo", colonia1.getNombre());

        Assertions.assertEquals(2, colonia2.getIdColonia());
        Assertions.assertEquals("Ara 3", colonia2.getNombre());

        // Verificación de ejecución
        Mockito.verify(jdbcTemplate, times(1)).execute(eq("CALL ColoniaByIdMunicipio(?,?)"), any(CallableStatementCallback.class));
    }

    @Test
    public void testColoniaByIdMunicipioJPAMockito() {
        // Crear objetos de prueba
        com.Digis01.FArceProgramacionNCapas.JPA.Municipio municipio = new com.Digis01.FArceProgramacionNCapas.JPA.Municipio();
        municipio.setIdMunicipio(1);

        com.Digis01.FArceProgramacionNCapas.JPA.Colonia colonia1 = new com.Digis01.FArceProgramacionNCapas.JPA.Colonia();
        colonia1.setIdColonia(1);
        colonia1.setNombre("Geo");
        colonia1.setMunicipio(municipio);

        com.Digis01.FArceProgramacionNCapas.JPA.Colonia colonia2 = new com.Digis01.FArceProgramacionNCapas.JPA.Colonia();
        colonia2.setIdColonia(2);
        colonia2.setNombre("Ara 3");
        colonia2.setMunicipio(municipio);

        // Mock del TypedQuery
        TypedQuery<com.Digis01.FArceProgramacionNCapas.JPA.Colonia> coloniaQuery = mock(TypedQuery.class);

        // Simular comportamiento del EntityManager
        Mockito.when(entityManager.createQuery(
                contains("FROM Colonia c WHERE c.Municipio.IdMunicipio = :idMunicipio"),
                eq(com.Digis01.FArceProgramacionNCapas.JPA.Colonia.class))
        ).thenReturn(coloniaQuery);

        Mockito.when(coloniaQuery.setParameter(eq("idMunicipio"), eq(1))).thenReturn(coloniaQuery);
        Mockito.when(coloniaQuery.getResultList()).thenReturn(List.of(colonia1, colonia2));

        // Ejecutar método
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipioJPA(1);

        // Verificaciones
        Assertions.assertNotNull(result, "El objeto result está nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepción");
        Assertions.assertNull(result.errorMessage, "Se envió un mensaje de error");
        Assertions.assertTrue(result.correct, "La consulta no fue exitosa");
        Assertions.assertEquals(2, result.objects.size(), "El número de estados es incorrecto");

        Mockito.verify(entityManager, times(1)).createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Colonia.class));
        Mockito.verify(coloniaQuery, times(1)).setParameter("idMunicipio", 1);
        Mockito.verify(coloniaQuery, times(1)).getResultList();
    }
}
