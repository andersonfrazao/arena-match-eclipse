package br.com.arenamatch.beans;

import br.com.arenamatch.client.CadastroClient;
import br.com.arenamatch.client.EnderecoClient;
import br.com.arenamatch.dto.EnderecoDTO;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import org.primefaces.PrimeFaces;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.Iterator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Component("cadastroBean")
@Scope("view")
public class CadastroBean implements Serializable {

    // Representante
    private String nomeRepresentante;
    private String cpf;
    private String email;
    private String senha;

    // Time
    private String nomeTime;
    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
    private String regiao;
    private boolean temCasa;
    private boolean somenteFora;

    // Categorias
    private List<String> categoriasSelecionadas = new ArrayList<>();

    // Agenda
    private List<AgendaLinha> agendas = new ArrayList<>();

    @Inject
    private EnderecoClient enderecoClient;

    @Inject
    private CadastroClient cadastroClient;

    public CadastroBean() {
        // RestClient apontando para a API interna
    }

    @PostConstruct
    public void init() {

        if (agendas.isEmpty()) {
            agendas.add(new AgendaLinha());
        }
    }

    public String onFlow(org.primefaces.event.FlowEvent event) {

        limparMensagens();
        // valida categorias SOMENTE quando for sair da aba cat
        if ("cat".equals(event.getOldStep()) && "agenda".equals(event.getNewStep())) {

            if (categoriasSelecionadas == null || categoriasSelecionadas.isEmpty()) {
                msgErro("Selecione pelo menos 1 categoria.");
                return event.getOldStep();
            }
            if (categoriasSelecionadas.size() > 3) {
                msgErro("Selecione no máximo 3 categorias.");
                return event.getOldStep();
            }
        }

        return event.getNewStep();
    }


    public void buscarCep() {
        try {
            String cepNumeros = (cep == null) ? "" : cep.replaceAll("\\D", "");
            EnderecoDTO dto = enderecoClient.buscarPorCep(cepNumeros);

            if (dto == null) {
                msgErro("CEP não encontrado.");
                return;
            }

            this.cep = dto.cep();
            this.logradouro = dto.logradouro();
            this.bairro = dto.bairro();
            this.cidade = dto.cidade();
            this.uf = dto.uf();
            this.regiao = dto.regiao();

            // Região fica editável (preenche se vier)
            if (dto.regiao() != null && !dto.regiao().isBlank()) {
                this.regiao = dto.regiao();
            }

            msgInfo("Endereço preenchido pelo CEP.");
        } catch (Exception e) {
            msgErro("Não foi possível localizar o CEP.");
        }
    }

    public void adicionarAgenda() {
        agendas.add(new AgendaLinha());
    }

    public void removerAgenda(AgendaLinha linha) {
        agendas.remove(linha);
        if (agendas.isEmpty()) {
            agendas.add(new AgendaLinha());
        }
    }

    public String concluirCadastro() {
        try {
            // validações mínimas de UI (se quiser manter)
            if (nomeRepresentante == null || nomeRepresentante.isBlank()) { msgErro("Informe o nome do representante."); return null; }
            if (nomeTime == null || nomeTime.isBlank()) { msgErro("Informe o nome do time."); return null; }

            // monta DTO
            var req = new br.com.arenamatch.dto.CadastroRequest(
                    new br.com.arenamatch.dto.RepresentanteCadastroRequest(nomeRepresentante, cpf, email, senha),
                    new br.com.arenamatch.dto.TimeCadastroRequest(nomeTime, cep, logradouro, bairro, cidade, uf, regiao, temCasa, somenteFora),
                    categoriasSelecionadas,
                    agendasParaDto()
            );

            cadastroClient.criarCadastro(req);

            msgInfo("Cadastro concluído com sucesso!");
            return "/login.xhtml?faces-redirect=true";

        } catch (Exception e) {
            msgErro(e.getMessage() != null ? e.getMessage() : "Erro ao concluir cadastro.");
            return null;
        }
    }

    private java.util.List<br.com.arenamatch.dto.AgendaRequest> agendasParaDto() {
        var lista = new java.util.ArrayList<br.com.arenamatch.dto.AgendaRequest>();
        var fmt = new java.text.SimpleDateFormat("HH:mm");

        if (agendas == null) return lista;

        for (AgendaLinha a : agendas) {
            if (a.getHoraInicio() == null || a.getHoraFim() == null) continue;

            lista.add(new br.com.arenamatch.dto.AgendaRequest(
                    a.getCategoria(),
                    a.getDiaSemana(),
                    fmt.format(a.getHoraInicio()),
                    fmt.format(a.getHoraFim())
            ));
        }
        return lista;
    }



    private void limparMensagens() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc == null) return;

        // remove mensagens globais (clientId null)
        Iterator<FacesMessage> itGlobal = fc.getMessages(null);
        while (itGlobal.hasNext()) {
            itGlobal.next();
            itGlobal.remove();
        }

        // remove mensagens por componentes (clientId != null)
        Iterator<String> clientIds = fc.getClientIdsWithMessages();
        while (clientIds.hasNext()) {
            String clientId = clientIds.next();
            Iterator<FacesMessage> it = fc.getMessages(clientId);
            while (it.hasNext()) {
                it.next();
                it.remove();
            }
        }
    }



    private void msgErro(String m) {
        limparMensagens();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", m));

        PrimeFaces.current().ajax().update("formCadastro:msgs", "formCadastro:growl");
    }

    private void msgInfo(String m) {
        limparMensagens();
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "OK", m));

        PrimeFaces.current().ajax().update("formCadastro:msgs", "formCadastro:growl");
    }




    // Linha da agenda (MVP)
    public static class AgendaLinha implements Serializable {

        private String categoria = "ESPORTE";
        private String diaSemana = "SAB";
        private java.util.Date horaInicio;
        private java.util.Date horaFim;

        public AgendaLinha() {
            // opcional: inicializar horários default
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public String getDiaSemana() {
            return diaSemana;
        }

        public void setDiaSemana(String diaSemana) {
            this.diaSemana = diaSemana;
        }

        public java.util.Date getHoraInicio() {
            return horaInicio;
        }

        public void setHoraInicio(java.util.Date horaInicio) {
            this.horaInicio = horaInicio;
        }

        public java.util.Date getHoraFim() {
            return horaFim;
        }

        public void setHoraFim(java.util.Date horaFim) {
            this.horaFim = horaFim;
        }
    }

    // Getters/Setters do xhtml
    public String getNomeRepresentante() {
        return nomeRepresentante;
    }

    public void setNomeRepresentante(String nomeRepresentante) {
        this.nomeRepresentante = nomeRepresentante;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNomeTime() {
        return nomeTime;
    }

    public void setNomeTime(String nomeTime) {
        this.nomeTime = nomeTime;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public String getUf() {
        return uf;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public boolean isTemCasa() {
        return temCasa;
    }

    public void setTemCasa(boolean temCasa) {
        this.temCasa = temCasa;
    }

    public boolean isSomenteFora() {
        return somenteFora;
    }

    public void setSomenteFora(boolean somenteFora) {
        this.somenteFora = somenteFora;
    }

    public List<String> getCategoriasSelecionadas() {
        return categoriasSelecionadas;
    }

    public void setCategoriasSelecionadas(List<String> categoriasSelecionadas) {
        this.categoriasSelecionadas = categoriasSelecionadas;
    }

    public List<AgendaLinha> getAgendas() {
        return agendas;
    }

    public void setAgendas(List<AgendaLinha> agendas) {
        this.agendas = agendas;
    }
}
