package it.customfanta.be.controller;

import it.customfanta.be.model.UserData;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BaseController {

    @Autowired
    protected UserData userData;

}
