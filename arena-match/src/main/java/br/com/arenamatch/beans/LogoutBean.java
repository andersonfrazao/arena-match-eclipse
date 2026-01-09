package br.com.arenamatch.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

//@Named
//@RequestScoped
@Component("logoutBean")
@Scope("request")
public class LogoutBean {

    @Inject
    private SessaoBean sessaoBean;

    public void sair() {
        try {
            // limpa dados da sessão lógica
            sessaoBean.sair();

            // invalida a sessão HTTP
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.invalidateSession();

            // redireciona para login
            ec.redirect(ec.getRequestContextPath() + "/login.xhtml");

        } catch (IOException e) {
            throw new RuntimeException("Erro ao realizar logout", e);
        }
    }
}
