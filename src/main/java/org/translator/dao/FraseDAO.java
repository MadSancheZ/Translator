package org.translator.dao;

import org.springframework.stereotype.Component;
import org.translator.model.Frase;

import java.util.List;

public interface FraseDAO {

    List<Frase> getAll();

    Frase getOne(String text);

    void add(Frase frase);
}
