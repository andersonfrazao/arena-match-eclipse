package br.com.arenamatch.beans;

import br.com.arenamatch.client.AutenticacaoClient;
import br.com.arenamatch.dto.AutenticacaoResponse;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("autenticacaoBean")
@Scope("view")
public class AutenticacaoBean implements Serializable {

    @Inject
    private AutenticacaoClient autenticacaoClient;
    @Inject
    private SessaoBean sessaoBean;

    private String login;
    private String senha;

    public AutenticacaoBean() {}

    public AutenticacaoBean(AutenticacaoClient autenticacaoClient, SessaoBean sessaoBean) {
        this.autenticacaoClient = autenticacaoClient;
        this.sessaoBean = sessaoBean;
    }

    public String autenticar() {
        try {
            AutenticacaoResponse resp = autenticacaoClient.autenticar(login, senha);

            sessaoBean.iniciarSessao(
                    resp.representanteId(),
                    resp.timeId(),
                    resp.nomeRepresentante()
            );

            return "/dashboard.xhtml?faces-redirect=true";

        } catch (Exception e) {
            msgErro(e.getMessage());
            return null;
        }
    }

    private void msgErro(String m) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", m));
    }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
