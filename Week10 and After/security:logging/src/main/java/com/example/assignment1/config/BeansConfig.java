package com.example.assignment1.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for defining beans.
 */

@Configuration
public class BeansConfig {


    /**
     * Provides a greeting message.
     *
     * @return The greeting message.
     */
    @Bean
    public String greetText() {
        return "Hello all, Welcome!";
    }

    /**
     * Provides a farewell message.
     *
     * @return The farewell message.
     */
    @Bean
    public String byeText() {
        return "Farewell, my friend!";
    }

    /**
     * Provides a ModelMapper instance.
     *
     * @return A ModelMapper instance.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
