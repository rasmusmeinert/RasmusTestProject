package ordination;

import java.time.LocalDate;
import java.time.LocalTime;

public class DagligFast extends Ordination {
    private final Dosis[] doser = new Dosis[4];


    public DagligFast(LocalDate startDato, LocalDate slutDato, Lægemiddel lægemiddel) {
        super(startDato, slutDato, lægemiddel);
    }

    public void opretDosis(int i, LocalTime tidspunkt, double antal){
        doser[i] = new Dosis(tidspunkt,antal);
    }

    public Dosis[] getDoser() {
        return doser;
    }

    @Override
    public double samletDosis() {
        return antalDage() * døgnDosis();
    }


    @Override
    public double døgnDosis() {
        double døgnDosis = 0;
        for (int i = 0; i < 4; i++) {
            if (doser[i] != null){
                døgnDosis += doser[i].getAntal();
            }
        }
        return døgnDosis;
    }

    @Override
    public String getType() {
        return "DagligFast";
    }
}
