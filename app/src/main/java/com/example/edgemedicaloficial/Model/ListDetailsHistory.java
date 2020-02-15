package com.example.edgemedicaloficial.Model;

import java.util.ArrayList;

public class ListDetailsHistory
{
    public static ArrayList<History> getListHistory()
    {
        ArrayList<History> historyList = new ArrayList<>();
        historyList.add(new History("Cita con el Dr Gustavo Gomez","22/01/2020","Dr. Gustavo Gomez",2));
        historyList.add(new History("Cita con el Dr Gustavo Gomez","25/01/2020","Dr. Gustavo Gomez",1));
        historyList.add(new History("Cita con la Dra Karla Lissette","27/01/2020","Dra. Karla Lissette",1));

        return historyList;
    }
}
