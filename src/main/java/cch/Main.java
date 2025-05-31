package cch;


import cch.utils.FontManager;
import cch.view.frames.CriptoFrame;

public class Main {

    private Main() {
    }

    public static void main(String[] args) {

        FontManager.carregarFonte();

        try {
            new CriptoFrame();
        } catch (InterruptedException _) {
            System.out.println("LOL");
            Thread.currentThread().interrupt();
        }
    }
}