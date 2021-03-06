package Algorithme;

import Interface.Other.Listes;


/**
 * Created by ibtissem on 21/03/2017.
 */

public class Priority extends Comparator {


    /**
     * Methodes
     **/

    //methode Priority
    public void runPriorityMethode() {

        float tempWaitingTime = 0;
        float tempTurnAroundTime = 0;

        //trie la liste selon la priority prioritaire
        Listes.getListProcessusesPriority().sort(comparatorPriority);

        //pour le premier le waiting time est initaliser a 0
        Listes.getListProcessusesPriority().get(0).setWaitTime(0);

        //boucle pour le calcule pour chaque processus
        for (int i = 1; i < Listes.getListProcessusesPriority().size(); i++) {
            // waiting time : temps CPU precedent + waiting time precedent
            Listes.getListProcessusesPriority().get(i).setWaitTime
                    (Listes.getListProcessusesPriority().get(i - 1).getCpuTime() +
                            Listes.getListProcessusesPriority().get(i - 1).getWaitTime());

            // incrementer le temp  par le resulta de la somme ci dessous
            tempWaitingTime += Listes.getListProcessusesPriority().get(i).getWaitTime();
        }

        //boucle pour le calcule pour chaque processus
        for (int i = 0; i < Listes.getListProcessusesPriority().size(); i++) {
            // turn arroud time :  temp CPU + waiting time
            Listes.getListProcessusesPriority().get(i).setTurnAroundTime
                    (Listes.getListProcessusesPriority().get(i).getCpuTime() +
                            Listes.getListProcessusesPriority().get(i).getWaitTime());

            // incrementer le temp  par le resulta de cette somme ci dessous
            tempTurnAroundTime += Listes.getListProcessusesPriority().get(i).getTurnAroundTime();
        }

        //remetre l'ordre normal de la liste selon le nom du processus
        //Listes.getListProcessusesPriority().sort(comparatorNum);

        //save les moyenne dans le tab avg
        Listes.getAvg().add(tempWaitingTime / Listes.getListProcessusesPriority().size());
        Listes.getAvg().add(tempTurnAroundTime / Listes.getListProcessusesPriority().size());

    }


    public void runPriorityMethode2() {

        float tempWaitingTime = 0;
        float tempTurnAroundTime = 0;
        float horloge;

        //trier par arrive et priority
        Listes.getListProcessusesPriority().sort(comparatorArrivePriority);

        //initialiser l'horloge au temp d'arivé du 1er
        horloge = Listes.getListProcessusesPriority().get(0).getArrive();


        // boucle pour calculer le waiting time
        //waitingTime[i] = Horloge - arrivalTime[i];
        // horloge is actually current time.
        for (int i = 0; i < Listes.getListProcessusesPriority().size(); i++) {

            if (horloge - Listes.getListProcessusesPriority().get(i).getArrive() < 0) {
                horloge = Listes.getListProcessusesPriority().get(i).getArrive();
            }

            Listes.getListProcessusesPriority().get(i).setWaitTime(horloge - Listes.getListProcessusesPriority().get(i).getArrive());

            horloge += Listes.getListProcessusesPriority().get(i).getCpuTime();
            tempWaitingTime += Listes.getListProcessusesPriority().get(i).getWaitTime();   // incrementer le temp total du wainting time
        }


        //boucle pour le calcule pour chaque processus
        for (int i = 0; i < Listes.getListProcessusesPriority().size(); i++) {
            // turn arroud time :  temp CPU + waiting time
            Listes.getListProcessusesPriority().get(i).setTurnAroundTime
                    (Listes.getListProcessusesPriority().get(i).getCpuTime() +
                            Listes.getListProcessusesPriority().get(i).getWaitTime());

            tempTurnAroundTime += Listes.getListProcessusesPriority().get(i).getTurnAroundTime(); // incrementer le temp  par le resulta de cette somme ci dessous
        }


        //trier par numero
        Listes.getListProcessusesPriority().sort(comparatorNum);

        //save les moyenne dans le tab avg
        Listes.getAvg().add(tempWaitingTime / Listes.getListProcessusesPriority().size());
        Listes.getAvg().add(tempTurnAroundTime / Listes.getListProcessusesPriority().size());


    }


}
