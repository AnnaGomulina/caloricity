package com.example.demo;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;

/**
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 */
@PWA(name = "Caloricity", shortName = "Caloricity")
//@Theme(variant = Lumo.DARK)
public class AppShell implements AppShellConfigurator {
}
