package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Result;
import com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion;

public interface IDireccionDAO {
    Result GetById(int IdDireccion);
    
    Result DireccionAdd(UsuarioDireccion usuarioDireccion);
    
    Result UpdateById(UsuarioDireccion usuarioDireccion);
}
