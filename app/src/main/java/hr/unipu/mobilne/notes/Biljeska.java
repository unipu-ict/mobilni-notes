package hr.unipu.mobilne.notes;

/**
 * Created by Siniša on 7.6.2016..
 */
public class Biljeska {

    int id;
    String naslov;
    String tekst;

    public Biljeska() {
    }
    public Biljeska(String tekst, int id, String naslov) {
        this.tekst = tekst;
        this.id = id;
        this.naslov = naslov;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }
}
