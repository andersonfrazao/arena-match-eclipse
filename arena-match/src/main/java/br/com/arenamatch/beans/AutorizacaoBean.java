package br.com.arenamatch.beans;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component("autorizacaoBean")
@Scope("view")
public class AutorizacaoBean {

    @Inject
    private SessaoBean sessaoBean;

    @PostConstruct
    public void verificar() {
        if (!sessaoBean.isLogado()) {
            try {
                FacesContext.getCurrentInstance()
                        .getExternalContext()
                        .redirect("login.xhtml");
            } catch (IOException ignored) {}
        }
    }
}
