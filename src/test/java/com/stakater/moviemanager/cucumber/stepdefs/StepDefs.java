package com.stakater.moviemanager.cucumber.stepdefs;

import com.stakater.moviemanager.MoviemanagerApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = MoviemanagerApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
