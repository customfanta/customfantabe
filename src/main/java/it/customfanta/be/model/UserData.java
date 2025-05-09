package it.customfanta.be.model;

import it.customfanta.be.model.annotations.SingleRequestScope;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
@SingleRequestScope
public class UserData {

	private String username;
	private String nome;
	private String mail;
	private boolean mailCertificata;
	private boolean logged;

}
