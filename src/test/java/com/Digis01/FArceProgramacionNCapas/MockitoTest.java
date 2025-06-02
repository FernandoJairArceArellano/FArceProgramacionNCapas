package com.Digis01.FArceProgramacionNCapas;

import com.Digis01.FArceProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.DireccionDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.PaisDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.ML.Colonia;
import com.Digis01.FArceProgramacionNCapas.ML.Direccion;
import com.Digis01.FArceProgramacionNCapas.ML.Municipio;
import com.Digis01.FArceProgramacionNCapas.ML.Result;
import com.Digis01.FArceProgramacionNCapas.ML.Rol;
import com.Digis01.FArceProgramacionNCapas.ML.Usuario;
import com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
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
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public void testGetAll() throws Exception {
        ResultSet rsMock = mock(ResultSet.class);   // Funciona como un cursor que se mueve a través de las filas
        when(rsMock.next()).thenReturn(true, false);

        // Usuario
        when(rsMock.getInt("IdUsuario")).thenReturn(1);
        when(rsMock.getString("NombreUsuario")).thenReturn("Juan");
        when(rsMock.getString("ApellidoPaterno")).thenReturn("Pérez");
        when(rsMock.getString("ApellidoMaterno")).thenReturn("García");
        when(rsMock.getString("Imagen")).thenReturn("base64img");
        when(rsMock.getString("UserName")).thenReturn("juan123");
        when(rsMock.getString("Email")).thenReturn("juan@example.com");
        when(rsMock.getString("Password")).thenReturn("securePassword");
        when(rsMock.getDate("FNacimiento")).thenReturn(new java.sql.Date(new java.util.Date().getTime()));
        when(rsMock.getString("Sexo")).thenReturn("H");
        when(rsMock.getString("Telefono")).thenReturn("12345678");
        when(rsMock.getString("NCelular")).thenReturn("9876543210");
        when(rsMock.getString("CURP")).thenReturn("CURP1234");
        when(rsMock.getInt("IdRol")).thenReturn(1);
        when(rsMock.getString("NombreRol")).thenReturn("Administrador");

        // Dirección
        when(rsMock.getInt("IdDireccion")).thenReturn(100);
        when(rsMock.getString("Calle")).thenReturn("Av. Siempre Viva");
        when(rsMock.getString("NumeroInterior")).thenReturn("2B");
        when(rsMock.getString("NumeroExterior")).thenReturn("742");

        // Colonia
        when(rsMock.getInt("IdColonia")).thenReturn(10);
        when(rsMock.getString("NombreColonia")).thenReturn("Centro");
        when(rsMock.getString("CodigoPostal")).thenReturn("12345");

        // Municipio
        when(rsMock.getInt("IdMunicipio")).thenReturn(20);
        when(rsMock.getString("NombreMunicipio")).thenReturn("MunicipioX");

        // Estado
        when(rsMock.getInt("IdEstado")).thenReturn(30);
        when(rsMock.getString("NombreEstado")).thenReturn("EstadoY");

        // País
        when(rsMock.getInt("IdPais")).thenReturn(40);
        when(rsMock.getString("NombrePais")).thenReturn("México");

        // Simulación del CallableStatementCallback
        when(jdbcTemplate.execute(anyString(), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    CallableStatement cs = mock(CallableStatement.class);
                    when(cs.getObject(anyInt())).thenReturn(rsMock);
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
    public void testGetAllJPA() {
        // Usuario JPA simulado
        com.Digis01.FArceProgramacionNCapas.JPA.Usuario usuarioJPA = new com.Digis01.FArceProgramacionNCapas.JPA.Usuario();
        usuarioJPA.setIdUsuario(1);
        usuarioJPA.setNombre("Juan");
        usuarioJPA.setApellidoPaterno("Pérez");
        usuarioJPA.setRol(new com.Digis01.FArceProgramacionNCapas.JPA.Rol());
        usuarioJPA.getRol().setIdRol(1);

        TypedQuery usuarioQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(com.Digis01.FArceProgramacionNCapas.JPA.Usuario.class)))
                .thenReturn(usuarioQuery);
        when(usuarioQuery.getResultList()).thenReturn(List.of(usuarioJPA));

        TypedQuery direccionQuery = mock(TypedQuery.class);
        when(entityManager.createQuery(contains("FROM Direccion"), eq(com.Digis01.FArceProgramacionNCapas.JPA.Direccion.class)))
                .thenReturn(direccionQuery);
        when(direccionQuery.setParameter(eq("IdUsuario"), anyInt())).thenReturn(direccionQuery);
        when(direccionQuery.getResultList()).thenReturn(List.of());

        Result result = usuarioDAOImplementation.GetAllJPA();
        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
        Assertions.assertEquals(1, result.objects.size());
    }

    @Test
    public void testGetAllDinamico() {
        
    }
    
    // ---> INSERT
    // Correcto
    @Test
    public void testAdd() throws ParseException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setNombre("Juan");
        usuarioDireccion.Usuario.setApellidoPaterno("Pérez");
        usuarioDireccion.Usuario.setApellidoMaterno("Gómez");
        usuarioDireccion.Usuario.setImagen("img");
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

        when(jdbcTemplate.execute(anyString(), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback callback = invocation.getArgument(1);
                    CallableStatement cs = mock(CallableStatement.class);
                    return callback.doInCallableStatement(cs);
                });

        when(passwordEncoder.encode(anyString())).thenReturn("hashed123");

        Result result = usuarioDAOImplementation.Add(usuarioDireccion);
        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    // Correcto
    @Test
    public void testAddJPA() throws ParseException {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
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
        usuarioDireccion.Usuario.setImagen("base64img");
        usuarioDireccion.Usuario.setStatus(1);
        usuarioDireccion.Usuario.Rol = new Rol();
        usuarioDireccion.Usuario.Rol.setIdRol(1);

        usuarioDireccion.Direccion = new Direccion();
        usuarioDireccion.Direccion.setCalle("Av. Principal");
        usuarioDireccion.Direccion.setNumeroExterior("100");
        usuarioDireccion.Direccion.setNumeroInterior("10A");
        usuarioDireccion.Direccion.Colonia = new Colonia();
        usuarioDireccion.Direccion.Colonia.setIdColonia(1);

        when(passwordEncoder.encode(anyString())).thenReturn("hashed123");

        doNothing().when(entityManager).persist(any());

        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);
        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    // ---> UPDATE
    /*@Test
    public void testUpdateStatus() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setStatus(1);

        when(jdbcTemplate.update(any(CallableStatementCallback.class))).thenAnswer(invocation -> {
            CallableStatementCallback<Integer> callback = invocation.getArgument(1);
            CallableStatement mockCallable = mock(CallableStatement.class);
            when(mockCallable.executeUpdate()).thenReturn(1);
            return callback.doInCallableStatement(mockCallable);
        });

        Result result = usuarioDAOImplementation.UpdateStatus(usuario);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");

    }*/

    @Test
    public void testUpdateUsuario() {
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

        when(passwordEncoder.encode("12345")).thenReturn("hashed123");

        when(jdbcTemplate.execute(anyString(), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<Integer> callback = invocation.getArgument(1);
                    CallableStatement mockCallable = mock(CallableStatement.class);
                    when(mockCallable.executeUpdate()).thenReturn(1);
                    return callback.doInCallableStatement(mockCallable);
                });

        Result result = usuarioDAOImplementation.Update(usuario);

        Assertions.assertTrue(result.correct);
    }

    @Test
    public void testUpdateJPAUsuario() {
        // Arrange
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
        when(passwordEncoder.encode(anyString())).thenReturn("hashed123");

        // Configurar la dirección
        Direccion direccion = new Direccion();
        direccion.setCalle("Av. Principal");
        direccion.setNumeroExterior("100");
        direccion.setNumeroInterior("10A");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1);
        direccion.setColonia(colonia);

        // Asignar usuario y dirección a la clase intermedia
        usuarioDireccion.setUsuario(usuario);
        usuarioDireccion.setDireccion(direccion);

        // Simular persistencia sin errores
        doNothing().when(entityManager).persist(any());

        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);

        // Assert
        Assertions.assertTrue(result.correct);
        Assertions.assertNull(result.errorMessage); // En caso de éxito, no debe haber mensaje de error

        // Verificar que se intentó persistir
        verify(entityManager, atLeastOnce()).persist(any());
    }

    // ---> DELETE
    @Test
    public void testDeleteJPA() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        usuarioDireccion.Usuario = new Usuario();
        usuarioDireccion.Usuario.setIdUsuario(1000);
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

        when(passwordEncoder.encode(anyString())).thenReturn("hashed123");

        doNothing().when(entityManager).persist(any());

        Result result = usuarioDAOImplementation.DeleteJPA(1000);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");

    }

    
    // Direccion
    
    
    // DDL PAIS, ESTADO, MUNICIPIO, COLONIA
    /*@Test
    public void testGetAllPais() throws SQLException {
        ResultSet rsMock = mock(ResultSet.class);
        when(rsMock.next()).thenReturn(true, false);

        // País
        when(rsMock.getInt("IdPais")).thenReturn(40);
        when(rsMock.getString("NombrePais")).thenReturn("México");

        // Simulación del CallableStatementCallback
        when(jdbcTemplate.execute(anyString(), any(CallableStatementCallback.class)))
                .thenAnswer(invocation -> {
                    CallableStatementCallback<?> callback = invocation.getArgument(1);
                    CallableStatement cs = mock(CallableStatement.class);
                    when(cs.getObject(anyInt())).thenReturn(rsMock);
                    return callback.doInCallableStatement(cs);
                });

        Result result = paisDAOImplementation.GetAll();

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testGetAllPaisJPA() {
        Result result = paisDAOImplementation.GetAllJPA();

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testEstadoByIdPais() {
        Result result = estadoDAOImplementation.EstadoByIdPais(1);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testEstadoByIdPaisJPA() {
        Result result = estadoDAOImplementation.EstadoByIdPaisJPA(1);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testMunicipioByIdEstado() {
        Result result = municipioDAOImplementation.MunicipioByIdEstado(1);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testMunicipioByIdEstadoJPA() {
        Result result = municipioDAOImplementation.MunicipioByIdEstadoJPA(1);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testColoniaByIdMunicipio() {
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipio(1);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testColoniaByIdMunicipioJPA() {
        Result result = coloniaDAOImplementation.ColoniaByIdMunicipioJPA(1);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }*/
}
