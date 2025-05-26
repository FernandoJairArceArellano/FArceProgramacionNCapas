package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Result;
import com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion;

public interface IDireccionDAO {
    // JdbcTemplate
    Result GetById(int IdDireccion);
    
    Result DireccionAdd(UsuarioDireccion usuarioDireccion);
    
    Result UpdateById(UsuarioDireccion usuarioDireccion);
    
    // EntityManager
    Result GetByIdJPA(int IdDireccion);
    
//    Result GetUsuarioIdByDireccionId(int idDireccion);
    
    Result DireccionAddJPA(UsuarioDireccion usuarioDireccion);
    
    Result UpdateByIdJPA(UsuarioDireccion usuarioDireccion);
    
    Result DeleteDireccionJPA(int IdDireccion);
}
