package Interface.AlgorithmeMenu;

import Algorithme.Priority;

import Interface.Model.Processus;
import Interface.Other.Aleatoire;
import Interface.Other.ChangeMenu;
import Interface.Principal.ControlerMenuCSV;
import Interface.Principal.ControlerMenuPrincipal;
import Interface.Resultat.ControlerMenuResultat;
import Interface.Other.DetectionErreur;
import Interface.Other.Listes;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by chawki on 27/03/2017.
 */
public class ControlerMenuPriority implements Initializable {


    /**
     * Attribut
     **/

    @FXML
    private TableView<Processus> tableProcessusPriority;
    @FXML
    private TableColumn<Processus, String> nomProcessusColumn;
    @FXML
    private TableColumn<Processus, Number> arriveProcessusColumn;
    @FXML
    private TableColumn<Processus, Number> cpuTimeProcessusColumn;
    @FXML
    private TableColumn<Processus, Number> priorityProcessusColumn;

    @FXML
    private TextField arriveTimeField;
    @FXML
    private TextField cpuTimeField;
    @FXML
    private TextField priorityField;


    //variable pour compter le nombre de processus
    private int compteurProcessus;

    private Priority priority = new Priority();


    /**
     * Methode
     **/

    //methode pour ajouter les processus dans le tab en saisi
    private void add() {
        Listes.getListProcessusesPriority().add(new Processus((compteurProcessus + 1), Integer.parseInt(arriveTimeField.getText()), Integer.parseInt(cpuTimeField.getText()), Integer.parseInt(priorityField.getText())));
        compteurProcessus++;
        arriveTimeField.setText("");
        cpuTimeField.setText("");
        priorityField.setText("");
    }


    //button ajouter processus
    @FXML
    private void ajouterProcessus(ActionEvent event) {
        if (DetectionErreur.isInputValid(arriveTimeField, "Arrive") && DetectionErreur.isInputValid(cpuTimeField, "CPUtime") && DetectionErreur.isInputValid(priorityField, "Priority")) {
            add();
            tableProcessusPriority.setItems(Listes.getListProcessusesPriority());
        }
    }


    @FXML
    //button calculer dans le menu priority
    private void calculerPriority(ActionEvent event) throws IOException {
        if (!Listes.getListProcessusesPriority().isEmpty()) {
            priority.runPriorityMethode2();
            ControlerMenuResultat.setChoix("Priority");
            ChangeMenu.afficheMenuResultat(event);

        }
    }

    //methode d'ajout aleatoirement
    private void addProcessusAleatoirement() {
        Aleatoire aleatoire = new Aleatoire();
        int nbr = aleatoire.getNbrProcessus();
        for (int i = 0; i < nbr; i++) {
            Listes.getListProcessusesPriority().add(new Processus((i + 1), aleatoire.getArrive(), aleatoire.getCpuTime(), aleatoire.getPriority()));
            compteurProcessus++;
            aleatoire = new Aleatoire();
        }
        tableProcessusPriority.setItems(Listes.getListProcessusesPriority());
    }

    //methode ajout processus par csv
    private void addProcessusCSV() throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(ControlerMenuCSV.getPath2()));
        String line = null;

        while ((line = bufferedReader.readLine()) != null) {

            //suprimer les espace
            line = line.trim();

            // On saute les lignes vides
            if (line.length() == 0) {
                continue;
            }

            // On saute les lignes de commentaire
            if (line.startsWith("#")) {
                continue;
            }

            // Retourner la ligne dans un tableau
            String[] data = line.split(",");
            Listes.getListProcessusesPriority().add(new Processus(compteurProcessus + 1, Integer.parseInt(data[0]), Integer.parseInt(data[1]), Integer.parseInt(data[2])));
            compteurProcessus++;

        }
        bufferedReader.close();
        tableProcessusPriority.setItems(Listes.getListProcessusesPriority());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomProcessusColumn.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
        arriveProcessusColumn.setCellValueFactory(cellData -> cellData.getValue().arriveProperty());
        cpuTimeProcessusColumn.setCellValueFactory(cellData -> cellData.getValue().cpuTimeProperty());
        priorityProcessusColumn.setCellValueFactory(cellData -> cellData.getValue().priorityProperty());
        compteurProcessus = 0;

        arriveTimeField.setText("");
        cpuTimeField.setText("");
        priorityField.setText("");

        if (ControlerMenuPrincipal.getChoix().equals("Aleatoire")) {
            addProcessusAleatoirement();
        }
        if (ControlerMenuPrincipal.getChoix().equals("csv")) {
            try {
                addProcessusCSV();
            } catch (Exception e) {
                DetectionErreur.errorFichierCSV();
            }
        }
    }


}
