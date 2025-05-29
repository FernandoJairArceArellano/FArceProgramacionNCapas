package com.Digis01.FArceProgramacionNCapas;

import com.Digis01.FArceProgramacionNCapas.DAO.UsuarioDAOImplementation;
import com.Digis01.FArceProgramacionNCapas.ML.Colonia;
import com.Digis01.FArceProgramacionNCapas.ML.Direccion;
import com.Digis01.FArceProgramacionNCapas.ML.Estado;
import com.Digis01.FArceProgramacionNCapas.ML.Municipio;
import com.Digis01.FArceProgramacionNCapas.ML.Pais;
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

    // ---> SELECT
    // Correcto
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

    // Correcto
    @Test
    public void testGetAllJPA() {
        Result result = new Result();
        result = usuarioDAOImplementation.GetAllJPA();

        List<UsuarioDireccion> usuarioDireccion = new ArrayList<>();

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "El result.correct viene false");
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

    // Correcto
    @Test
    public void testdireccionesByIdUsuario() {
        Result result = usuarioDAOImplementation.direccionesByIdUsuario(1);
        Assertions.assertTrue(result.correct, "Se deberian de buscar las direcciones del Usuario");
    }

    // Correcto
    @Test
    public void testDireccionesByIdUsuarioJPA() {
        Result result = usuarioDAOImplementation.DireccionesByIdUsuarioJPA(1);
        Assertions.assertTrue(result.correct, "Se deberia de buscar las direcicones del Usuario");
    }

    // Correcto
    @Test
    public void testGetById() {
        Result result = usuarioDAOImplementation.GetById(1);
        Assertions.assertTrue(result.correct, "Se deberia de buscar la informacion del Usuario");
    }

    // Correcto
    @Test
    public void testGetByIdJPA() {
        Result result = usuarioDAOImplementation.GetByIdJPA(1);
        Assertions.assertTrue(result.correct, "Se deberia de buscar la informacion del Usuario");
    }

    // ---> INSERT    
    // Correcto
    @Test
    public void testAddUsuarioAdd() {
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

        Assertions.assertTrue(result.correct, "EL usuario se deberia de haber insertado de manera exitosa");
    }

    // Correcto
    @Test
    public void testAddUsuarioAddJPA() {
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

        Assertions.assertTrue(result.correct, "EL usuario se deberia de haber insertado de manera exitosa");
    }

    // ---> UPDATE
    @Test
    public void testUpdate() {
        
    }

    // Correcto
    @Test
    public void testUpdateStatus() {
        Usuario usuario = new Usuario();

        usuario.setIdUsuario(301);
        usuario.setStatus(0);

        Result result = usuarioDAOImplementation.UpdateStatus(usuario);

        Assertions.assertTrue(result.correct, "Se actualizaria el estatus del Usuario");
    }

    // Correcto
    @Test
    public void testUpdateStatusJPA() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(306);
        usuario.setStatus(1);

        Result result = usuarioDAOImplementation.UpdateStatusJPA(usuario);

        Assertions.assertTrue(result.correct, "Se actualizaria el estatus del Usuario");
    }

    // ---> DELETE
    // Correcto
    @Test
    public void testDeleteJPA() {

        Result result = usuarioDAOImplementation.DeleteJPA(302);

        Assertions.assertNotNull(result, "El objeto result esta nulo");
        Assertions.assertNotNull(result.objects, "result.objects esta nulo");
        Assertions.assertNull(result.ex, "Se produjo una excepcion");
        Assertions.assertNull(result.errorMessage, "Se envia un mensaje de error");
        Assertions.assertNull(result.object, "result.object esta nulo");
        Assertions.assertTrue(result.correct, "La eliminacion del usuario y sus direcciones no se realizo");

    }
}
