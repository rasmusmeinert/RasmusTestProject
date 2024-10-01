package ordination;

import java.time.LocalDate;

public class DagligFast extends Ordination {
    private final Dosis[] doser = new Dosis[4];


    public DagligFast(LocalDate startDato, LocalDate slutDato, Lægemiddel lægemiddel) {
        super(startDato, slutDato, lægemiddel);
    }

    public void setDosis(int i, Dosis dosis) {
        doser[i] = dosis;
    }

    @Override
    public double samletDosis() {
        return antalDage() * døgnDosis();
    }


    @Override
    public double døgnDosis() {
        double døgnDosis = 0;
        for (int i = 0; i < 4; i++) {
            døgnDosis = doser[i].getAntal();
        }
        return døgnDosis;
    }

    @Override
    public String getType() {
        return "DagligFast";
    }
}
