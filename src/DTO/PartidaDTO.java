
package DTO;

import java.sql.Timestamp;

public class PartidaDTO {
    private int id;
    private int temporadaId;
    private int clubeMandanteId;
    private int clubeVisitanteId;
    private Timestamp dataHora;
    private String estadio;
    private int golsMandante;
    private int golsVisitante;
    private String status;
    private Integer rodada;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTemporadaId() { return temporadaId; }
    public void setTemporadaId(int temporadaId) { this.temporadaId = temporadaId; }
    public int getClubeMandanteId() { return clubeMandanteId; }
    public void setClubeMandanteId(int clubeMandanteId) { this.clubeMandanteId = clubeMandanteId; }
    public int getClubeVisitanteId() { return clubeVisitanteId; }
    public void setClubeVisitanteId(int clubeVisitanteId) { this.clubeVisitanteId = clubeVisitanteId; }
    public Timestamp getDataHora() { return dataHora; }
    public void setDataHora(Timestamp dataHora) { this.dataHora = dataHora; }
    public String getEstadio() { return estadio; }
    public void setEstadio(String estadio) { this.estadio = estadio; }
    public int getGolsMandante() { return golsMandante; }
    public void setGolsMandante(int golsMandante) { this.golsMandante = golsMandante; }
    public int getGolsVisitante() { return golsVisitante; }
    public void setGolsVisitante(int golsVisitante) { this.golsVisitante = golsVisitante; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getRodada() { return rodada; }
    public void setRodada(Integer rodada) { this.rodada = rodada; }
}