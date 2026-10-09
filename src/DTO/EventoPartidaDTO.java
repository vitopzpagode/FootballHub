
package DTO;

public class EventoPartidaDTO {
    private int id;
    private int partidaId;
    private int clubeId;
    private Integer jogadorId;
    private Integer jogadorSubstituidoId;
    private int minuto;
    private String tipo;
    private String descricao;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getPartidaId() { return partidaId; }
    public void setPartidaId(int partidaId) { this.partidaId = partidaId; }
    public int getClubeId() { return clubeId; }
    public void setClubeId(int clubeId) { this.clubeId = clubeId; }
    public Integer getJogadorId() { return jogadorId; }
    public void setJogadorId(Integer jogadorId) { this.jogadorId = jogadorId; }
    public Integer getJogadorSubstituidoId() { return jogadorSubstituidoId; }
    public void setJogadorSubstituidoId(Integer jogadorSubstituidoId) { this.jogadorSubstituidoId = jogadorSubstituidoId; }
    public int getMinuto() { return minuto; }
    public void setMinuto(int minuto) { this.minuto = minuto; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
