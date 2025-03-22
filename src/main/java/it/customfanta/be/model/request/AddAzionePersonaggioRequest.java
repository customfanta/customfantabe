package it.customfanta.be.model.request;

import lombok.Data;

@Data
public class AddAzionePersonaggioRequest {

    private String chiaveAzione;
    private String chiavePersonaggio;

}
