package com.Digis01.FArceProgramacionNCapas;

import com.Digis01.FArceProgramacionNCapas.DAO.ColoniaDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.DireccionDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.EstadoDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.MunicipioDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.PaisDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.ML.Colonia;
import com.Digis01.FArceProgramacionNCapas.ML.Direccion;
import com.Digis01.FArceProgramacionNCapas.ML.Rol;
import com.Digis01.FArceProgramacionNCapas.ML.Usuario;
import com.Digis01.FArceProgramacionNCapas.ML.Result;
import com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JUnitTest {

    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    // ---> SELECT
    // Usuario
    @Test
    public void testGetAll() {
        Result result = new Result();
        result = usuarioDAOImplementation.GetAll();

        List<UsuarioDireccion> usuarioDireccion = new ArrayList<>();

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testGetAllJPA() {
        Result result = new Result();
        result = usuarioDAOImplementation.GetAllJPA();

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
        Assertions.assertFalse(result.objects.isEmpty(), "Debe haber al menos un usuario");
    }

    @Test
    public void testGetAllDinamico() {
        Usuario usuario = new Usuario();
//        usuario.setNombre("");
//        usuario.setApellidoPaterno("");
//        usuario.setApellidoMaterno("");
        Rol rol = new Rol();
        rol.setIdRol(10);
        usuario.setRol(rol);

        Result result = usuarioDAOImplementation.GetAllDinamico(usuario);

//        Assertions.assertNotNull(result, "El objeto result esta nulo");
//        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
//        Assertions.assertNull(result.ex, "Se produjo una excepcion");
//        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
//        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
        Assertions.assertTrue(result.objects == null || result.objects.isEmpty(), "No se devolvio usuarios con el rol asignado");
    }

    @Test
    public void testGetAllDinamicoJPA() {
        Usuario filtro = new Usuario();
        filtro.setNombre("Fernando");
        filtro.setApellidoPaterno("Arce");
        filtro.setApellidoMaterno("");
        Rol rol = new Rol();
        rol.setIdRol(1);
        filtro.setRol(rol);
        filtro.setRol(rol);
        filtro.setStatus(0);

        Result result = usuarioDAOImplementation.GetAllDinamicoJPA(filtro);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testdireccionesByIdUsuario() {
        Result result = usuarioDAOImplementation.direccionesByIdUsuario(1);
        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testDireccionesByIdUsuarioJPA() {
        Result result = usuarioDAOImplementation.DireccionesByIdUsuarioJPA(1);
        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testGetById() {
        Result result = usuarioDAOImplementation.GetById(1);
        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testGetByIdJPA() {
        Result result = usuarioDAOImplementation.GetByIdJPA(1);
        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    // ---> INSERT    
    @Test
    public void testUsuarioAdd() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        Usuario usuario = new Usuario();
        usuario.setNombre("Paul");
        usuario.setApellidoPaterno("William");
        usuario.setApellidoMaterno("Walker IV");
        usuario.setImagen(null);
        usuario.setFNacimiento(new Date(12051973L));
        usuario.setNCelular("5512345678");
        usuario.setCURP("PEJL900101HDFRZN00");
        usuario.setUsername("juanp");
        usuario.setEmail("paul@maio.com");
        usuario.setPassword("1234");
        usuario.setSexo('M');
        usuario.setTelefono("55887766");
        usuario.setStatus(1);

        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);
        usuarioDireccion.setUsuario(usuario);

        Direccion direccion = new Direccion();
        direccion.setCalle("Insurgentes");
        direccion.setNumeroExterior("100");
        direccion.setNumeroInterior("10");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1); // Asegúrate que exista
        direccion.setColonia(colonia);
        usuarioDireccion.setDireccion(direccion);

        Result result = usuarioDAOImplementation.Add(usuarioDireccion);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testUsuarioAddJPA() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        Usuario usuario = new Usuario();
        usuario.setNombre("Paul");
        usuario.setApellidoPaterno("William");
        usuario.setApellidoMaterno("Walker IV");
        usuario.setImagen(null);
        usuario.setFNacimiento(new Date(12051973L));
        usuario.setNCelular("5512345678");
        usuario.setCURP("PEJL900101HDFRZN00");
        usuario.setUsername("PaulWW");
        usuario.setEmail("paul@gmail.com");
        usuario.setPassword("1234");
        usuario.setSexo('M');
        usuario.setTelefono("55887766");
        usuario.setStatus(1);

        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);
        usuarioDireccion.setUsuario(usuario);

        Direccion direccion = new Direccion();
        direccion.setCalle("Insurgentes");
        direccion.setNumeroExterior("100");
        direccion.setNumeroInterior("10");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1); // Asegúrate que exista
        direccion.setColonia(colonia);
        usuarioDireccion.setDireccion(direccion);

        Result result = usuarioDAOImplementation.AddJPA(usuarioDireccion);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    // ---> UPDATE
    @Test
    public void testUpdate() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(301);
        usuario.setNombre("Mark");
        usuario.setApellidoPaterno("Sinclair");
        usuario.setApellidoMaterno("Vincent");
        usuario.setImagen(null);
        usuario.setUsername("charger 1970");
        usuario.setEmail("vincent@mail.com");
        usuario.setPassword("1234");
        usuario.setFNacimiento(new Date(19800530L));
        usuario.setSexo('M');
        usuario.setNCelular("5512345678");
        usuario.setTelefono("5588776655");
        usuario.setCURP("PEJL900101HDFRZN00");

        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);

        Result result = usuarioDAOImplementation.UpdateJPA(usuario);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testUpdateJPA() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(306);
        usuario.setNombre("Paul");
        usuario.setApellidoPaterno("William");
        usuario.setApellidoMaterno("Walker IV");
        usuario.setImagen(null);
        usuario.setUsername("pepepepepeepep");
        usuario.setEmail("walkerIV@mail.com");
        usuario.setPassword("1234");
        usuario.setFNacimiento(new Date(20020530L));
        usuario.setSexo('M');
        usuario.setNCelular("5512345678");
        usuario.setTelefono("5588776655");
        usuario.setCURP("PEJL900101HDFRZN00");

        Rol rol = new Rol();
        rol.setIdRol(1);
        usuario.setRol(rol);

        Result result = usuarioDAOImplementation.UpdateJPA(usuario);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testUpdateStatus() {
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(301);
        usuario.setStatus(0);

        Result result = usuarioDAOImplementation.UpdateStatus(usuario);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    @Test
    public void testUpdateStatusJPA() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(306);
        usuario.setStatus(1);

        Result result = usuarioDAOImplementation.UpdateStatusJPA(usuario);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
    }

    // ---> DELETE
    @Test
    public void testDeleteJPA() {

        Result result = usuarioDAOImplementation.DeleteJPA(302);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");

    }

    // Direccion
    // ---> INSERT    
    @Test
    public void testDireccionAdd() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        usuarioDireccion.setUsuario(usuario);

        Direccion direccion = new Direccion();
        direccion.setCalle("Insurgentes");
        direccion.setNumeroExterior("100");
        direccion.setNumeroInterior("10");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1); // Asegúrate que exista
        direccion.setColonia(colonia);
        usuarioDireccion.setDireccion(direccion);

        Result result = direccionDAOImplementation.DireccionAdd(usuarioDireccion);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testDireccionAddJPA() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        usuarioDireccion.setUsuario(usuario);

        Direccion direccion = new Direccion();
        direccion.setCalle("Insurgentes");
        direccion.setNumeroExterior("100");
        direccion.setNumeroInterior("10");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1); // Asegúrate que exista
        direccion.setColonia(colonia);
        usuarioDireccion.setDireccion(direccion);

        Result result = direccionDAOImplementation.DireccionAddJPA(usuarioDireccion);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    // ---> SELECT
    @Test
    public void testGetByIdDireccion() {
        Result result = direccionDAOImplementation.GetById(2);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testGetByIdJPADireccion() {
        Result result = direccionDAOImplementation.GetByIdJPA(2);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    // ---> UPDATE
    @Test
    public void testUpdateByIdDireccion() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(1);

        usuarioDireccion.setUsuario(usuario);

        Direccion direccion = new Direccion();
        direccion.setIdDireccion(256);
        direccion.setCalle("Insurgentes");
        direccion.setNumeroExterior("1");
        direccion.setNumeroInterior("1");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1); // Asegúrate que exista
        direccion.setColonia(colonia);
        usuarioDireccion.setDireccion(direccion);

        Result result = direccionDAOImplementation.UpdateById(usuarioDireccion);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    @Test
    public void testUpdateByIdDireccionJPA() {
        UsuarioDireccion usuarioDireccion = new UsuarioDireccion();
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(2);

        usuarioDireccion.setUsuario(usuario);

        Direccion direccion = new Direccion();
        direccion.setIdDireccion(244);
        direccion.setCalle("Insurgentes");
        direccion.setNumeroExterior("1");
        direccion.setNumeroInterior("1");

        Colonia colonia = new Colonia();
        colonia.setIdColonia(1); // Asegúrate que exista
        direccion.setColonia(colonia);
        usuarioDireccion.setDireccion(direccion);

        Result result = direccionDAOImplementation.UpdateByIdJPA(usuarioDireccion);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    // ---> DELETE
    @Test
    public void testDeleteDIreccionJPA() {
        Result result = direccionDAOImplementation.DeleteDireccionJPA(258);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");
    }

    // DDL PAIS, ESTADO, MUNICIPIO, COLONIA
    @Test
    public void testGetAllPais() {
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
    }

}
