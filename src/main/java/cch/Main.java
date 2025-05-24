package cch;


import cch.utils.FontManager;
import cch.view.frames.CriptoFrame;

public class Main {

    private Main() {
    }

    public static void main(String[] args) {

        FontManager.carregarFonte();
        new CriptoFrame();
    }


}