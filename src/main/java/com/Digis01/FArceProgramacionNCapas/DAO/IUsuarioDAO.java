package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Result;
import com.Digis01.FArceProgramacionNCapas.ML.Usuario;
import com.Digis01.FArceProgramacionNCapas.ML.UsuarioDireccion;

public interface IUsuarioDAO {

    Result GetAll(); //Metodo Abstracto

    Result Add(UsuarioDireccion usuarioDireccion);

    Result direccionesByIdUsuario(int IdUsario);

    Result GetById(int IdUsaurio);

    Result Update(Usuario usuario);

    Result UpdateStatus(Usuario usuario);
    
    Result GetAllDinamico(Usuario usuario);
    
    Result GetAllJPA();
    
    Result AddJPA(UsuarioDireccion usuarioDireccion);
    
    Result DireccionesByIdUsuarioJPA(int IdUsuario);
    
    Result GetByIdJPA(int IdUsuario);
    
    Result UpdateJPA(Usuario usuario);
    
    Result DeleteJPA(int IdUsuario);
}
