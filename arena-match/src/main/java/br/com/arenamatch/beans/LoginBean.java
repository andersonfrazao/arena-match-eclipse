package br.com.arenamatch.beans;

import br.com.arenamatch.client.AutenticacaoClient;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

//@Named
//@RequestScoped
@Component("loginBean")
@Scope("request")
public class LoginBean {

    @Inject
    private SessaoBean sessaoBean;

    private String login;
    private String senha;

    @Inject
    private AutenticacaoClient autenticacaoClient;


    @Inject
    private SessaoBean sessao;

    public String entrar() {
        try {
            var response = autenticacaoClient.autenticar(login, senha);

            // inicia sessão
            sessaoBean.iniciarSessao(
                    response.representanteId(),
                    response.timeId(),
                    response.nomeRepresentante()
            );

            return "/dashboard.xhtml?faces-redirect=true";

        } catch (Exception e) {
            erro(e.getMessage());
            return null;
        }
    }

    private void erro(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", msg));
    }

    // DTOs do client
    public record LoginRequest(String login, String senha) {}
    public record LoginResponse(Long representanteId, Long timeId, String nomeTime, LocalDate dataFimTrial) {}

    // getters/setters
    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
