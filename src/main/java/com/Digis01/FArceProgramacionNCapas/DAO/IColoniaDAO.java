package com.Digis01.FArceProgramacionNCapas.DAO;

import com.Digis01.FArceProgramacionNCapas.ML.Result;

public interface IColoniaDAO {

    Result ColoniaByIdMunicipio(int IdMunicipio);

    Result ColoniaByIdMunicipioJPA(int IdMunicipio);
}
