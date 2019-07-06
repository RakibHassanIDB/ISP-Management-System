package main;

import login.login;

public class main {

    public static void main(String[] args) {
        splashscreen sc = new splashscreen();
        login bd = new login();
        sc.setVisible(true);
        try {
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(125);
                sc.lod.setText(Integer.toString(i) + "%");
                sc.progress.setValue(i);
                if (i == 100) {

                    sc.setVisible(false);
                    bd.setVisible(true);
                }

            }

        } catch (Exception e) {
        }
    }

}
