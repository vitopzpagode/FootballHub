
package DTO;

import java.sql.Date;

public class TemporadaDTO {
    private int id;
    private int competicaoId;
    private String nome;
    private Date dataInicio;
    private Date dataFim;
    private String status;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getCompeticaoId() { return competicaoId; }
    public void setCompeticaoId(int competicaoId) { this.competicaoId = competicaoId; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
