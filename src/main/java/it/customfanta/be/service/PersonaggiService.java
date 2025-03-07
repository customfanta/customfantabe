package it.customfanta.be.service;

import it.customfanta.be.model.Personaggio;
import it.customfanta.be.repository.PersonaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonaggiService {

    @Autowired
    private PersonaggiRepository personaggiRepository;

    public Personaggio savePersonaggio(Personaggio personaggio) {
        return personaggiRepository.save(personaggio);
    }

    public List<Personaggio> readPersonaggi() {
        return personaggiRepository.findAll();
    }

}
