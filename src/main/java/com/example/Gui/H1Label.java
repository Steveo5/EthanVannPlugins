package com.example.Gui;

import javax.swing.*;
import java.awt.*;

public class H1Label extends JLabel {
    public H1Label(String text) {
        Font font = new Font(this.getFont().getFontName(), this.getFont().getStyle(), 16);
        this.setFont(font);
        this.setText(text);
    }
}
