package it.customfanta.be.repository;

import it.customfanta.be.model.Personaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PersonaggiRepository extends JpaRepository<Personaggio, String> {

}
