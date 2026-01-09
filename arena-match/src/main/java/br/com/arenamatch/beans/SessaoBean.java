package br.com.arenamatch.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

//@Named
//@SessionScoped
@Component("sessaoBean")
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SessaoBean implements Serializable {

    private boolean autenticado;

    private Long representanteId;
    private Long timeId;
    private String nomeTime;
    private String nomeRepresentante;

    private LocalDate dataFimTrial;

    public boolean isAutenticado() {
        return autenticado;
    }

    public void autenticar(Long representanteId, Long timeId, String nomeTime, LocalDate dataFimTrial) {
        this.autenticado = true;
        this.representanteId = representanteId;
        this.timeId = timeId;
        this.nomeTime = nomeTime;
        this.dataFimTrial = dataFimTrial;
    }

    public void sair() {
        this.autenticado = false;
        this.representanteId = null;
        this.timeId = null;
        this.nomeTime = null;
        this.dataFimTrial = null;
    }
    public boolean isLogado() {
        return representanteId != null;
    }

    public void iniciarSessao(Long representanteId, Long timeId, String nomeRepresentante) {
        this.representanteId = representanteId;
        this.timeId = timeId;
        this.nomeRepresentante = nomeRepresentante;
    }

    public long getDiasRestantesTrial() {
        if (dataFimTrial == null) return 0;
        long dias = ChronoUnit.DAYS.between(LocalDate.now(), dataFimTrial);
        return Math.max(dias, 0);
    }

    public boolean isTrialExpirado() {
        return dataFimTrial != null && LocalDate.now().isAfter(dataFimTrial);
    }

    public void encerrarSessao() {
        this.representanteId = null;
        this.timeId = null;
        this.nomeRepresentante = null;
    }

    public Long getRepresentanteId() { return representanteId; }
    public Long getTimeId() { return timeId; }
    public String getNomeRepresentante() { return nomeRepresentante; }
}
