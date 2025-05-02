package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Result;

public interface IMunicipioDAO {

    Result MunicipioByIdEstado(int IdEstado);

    Result MunicipioByIdEstadoJPA(int IdEstado);
}
