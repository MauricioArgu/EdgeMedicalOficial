package com.example.edgemedicaloficial.Model;

import java.util.ArrayList;

public class ListDetailsHome {

    public static ArrayList<Home> getList(){

        ArrayList<Home> especialidadesList = new ArrayList<>();
        especialidadesList.add(new Home("Alergiologia"));
        especialidadesList.add(new Home("Anesteciologia"));
        especialidadesList.add(new Home("Cardiologia"));
        especialidadesList.add(new Home("Gatroenterologia"));
        especialidadesList.add(new Home("Endocrinologia"));
        especialidadesList.add(new Home("Hematologia"));
        especialidadesList.add(new Home("Pediatria"));
        especialidadesList.add(new Home("Oncologia"));
        especialidadesList.add(new Home("Medicina  General"));

        return especialidadesList;
    }
}