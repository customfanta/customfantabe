package it.customfanta.be.service;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseService {

    @Autowired
    protected EntityManager entityManager;

}
