package it.customfanta.be.service;

import com.google.firebase.FirebaseApp;
import it.customfanta.be.model.SquadraPersonaggio;
import it.customfanta.be.repository.SquadrePersonaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SquadrePersonaggiService extends BaseService {

    @Autowired
    private SquadrePersonaggiRepository squadrePersonaggiRepository;

    public SquadrePersonaggiService(FirebaseApp firebaseApp) {
        super(firebaseApp);
    }

    public SquadraPersonaggio saveSquadraPersonaggio(SquadraPersonaggio squadraPersonaggio) {
        return squadrePersonaggiRepository.save(squadraPersonaggio);
    }

    public List<SquadraPersonaggio> readByChiaveSquadra(String chiaveSquadra) {
        return squadrePersonaggiRepository.findByChiaveSquadra(chiaveSquadra).orElse(new ArrayList<>());
    }

    public void deleteSquadraPersonaggioById(String chiave) {
        squadrePersonaggiRepository.deleteById(chiave);
    }

}
