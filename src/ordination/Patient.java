package ordination;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Patient {

    private final ArrayList<Ordination> ordinationer = new ArrayList<Ordination>();
    private final String cprNr;
    private final String navn;
    private final double vægt;

    public Patient(String cprNr, String navn, double vægt) {
        this.cprNr = cprNr;
        this.navn = navn;
        this.vægt = vægt;
    }

    public void tilføjOrdination(Ordination ordination){
        ordinationer.add(ordination);
    }

    public double getVægt() {
        return vægt;
    }

    @Override
    public String toString() {
        return navn + "  " + cprNr;
    }
}