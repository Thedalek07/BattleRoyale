package me.dalek.battleroyale.statistiques;

public class PlayerStats {

    private String pseudo;
    private int kills;
    private float degatsInfliges;
    private float distance;
    private String tempsSurvie;
    private int tradeGerard;
    private int totalSurvieEnSecondes;

    public PlayerStats(String pseudo, int kills, float degatsInfliges, float distance, String tempsSurvie, int tradeGerard, int totalSurvieEnSecondes) {
        this.pseudo = pseudo;
        this.kills = kills;
        this.degatsInfliges = degatsInfliges;
        this.distance = distance;
        this.tempsSurvie = tempsSurvie;
        this.tradeGerard = tradeGerard;
        this.totalSurvieEnSecondes = totalSurvieEnSecondes;
    }

    public int getTotalSurvieEnSecondes() {
        return totalSurvieEnSecondes;
    }

    public String getPseudo() {
        return pseudo;
    }

    public int getKills() {
        return kills;
    }

    public float getDegatsInfliges() {
        return degatsInfliges;
    }

    public float getDistance() {
        return distance;
    }

    public String getTempsSurvie() {
        return tempsSurvie;
    }

    public int getTradeGerard() {
        return tradeGerard;
    }
}
