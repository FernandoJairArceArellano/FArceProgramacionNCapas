package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Result;

public interface IEstadoDAO {

    Result EstadoByIdPais(int IdPais);

    Result EstadoByIdPaisJPA(int IdPais);
}
