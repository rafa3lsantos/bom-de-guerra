package jogo;

public class AcaoCombate {
    private String tipoAcao; //atacar ou defender
    private int vidaInimigoAntes;
    private int escudoPlayerAntes;

    public AcaoCombate (String tipoAcao, int vidaInimigoAntes, int escudoPlayerAntes) {
        this.tipoAcao = tipoAcao;
        this.vidaInimigoAntes = vidaInimigoAntes;
        this.escudoPlayerAntes = escudoPlayerAntes;
    }

    public String getTipoAcao () {
        return tipoAcao;
    }

    public int getVidaInimigoAntes () {
        return vidaInimigoAntes;
    }

    public int getEscudoPlayerAntes () {
        return escudoPlayerAntes;
    }
}
