package br.com.arenamatch.beans;

import jakarta.faces.annotation.View;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component("welcomeBean")
@Scope("view")
public class WelcomeBean {

    private String nome;

    public void enviarMensagem() {
        String mensagem = "Seja bem-vindo, " + nome + "!";
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", mensagem));
    }

    // Getter e Setter (Obrigatórios para o JSF conseguir ler o campo)
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
}
