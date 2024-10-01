package gui;

import controller.Controller;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import ordination.*;

public class VisOrdinationPane extends GridPane {
    private final ListView<Patient> lstPatient = new ListView<>();
    private final ListView<Ordination> lstOrdination = new ListView<>();
    private final OrdinationDetailsPane ordinationDetailsPane = new OrdinationDetailsPane();

    public VisOrdinationPane() {
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(10);
        this.setGridLinesVisible(false);

        this.add(new Label("Vælg patient"), 0, 0);
        this.add(lstPatient, 0, 1);
        lstPatient.getItems().setAll(Controller.getAllPatienter());
        lstPatient.getSelectionModel().selectedIndexProperty().addListener(observable ->
                lstOrdination.getItems().setAll(
                        lstPatient.getSelectionModel().getSelectedItem().getOrdinationer())
        );
        lstPatient.getSelectionModel().selectFirst();

        this.add(new Label("Vælg ordination"), 1, 0);
        this.add(lstOrdination, 1, 1);
        lstOrdination.getSelectionModel().selectedItemProperty().addListener(observable -> updateDetails());

        this.add(new Label("Ordinationsdetaljer"), 2, 0);
        this.add(ordinationDetailsPane, 2, 1);
    }

    public void updateDetails() {
        Ordination ordination = lstOrdination.getSelectionModel().getSelectedItem();
        ordinationDetailsPane.clear();
        if (ordination != null) {
            ordinationDetailsPane.clear();
            ordinationDetailsPane.setOrdination(ordination);
            if (ordination instanceof DagligFast dagligFast) {
                ordinationDetailsPane.setFast(dagligFast.getDoser()[0],
                        dagligFast.getDoser()[1], dagligFast.getDoser()[2],
                        dagligFast.getDoser()[3]);
            } else if (ordination instanceof DagligSkæv) {
                ordinationDetailsPane.setSkæv((DagligSkæv) ordination);
            } else if (ordination instanceof PN) {
                ordinationDetailsPane.setPN((PN) ordination);
            }
        }
    }

    public void updateControls() {
        int selected = 0;
        if (lstOrdination.getSelectionModel().getSelectedItem() != null) {
            selected = lstOrdination.getSelectionModel().getSelectedIndex();
        }
        lstOrdination.getItems().setAll(lstPatient.getSelectionModel().getSelectedItem().getOrdinationer());
        lstOrdination.getSelectionModel().select(selected);
    }
}
