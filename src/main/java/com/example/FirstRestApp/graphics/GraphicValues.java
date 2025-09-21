package com.example.FirstRestApp.graphics;

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.IntStream;


public class GraphicValues {
    public static void main(String[] args) {
        List<Integer> temperatures = getTemperaturesFromServer();

        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Temperature Chart")
                .xAxisTitle("Measurement Number")
                .yAxisTitle("Temperature (°C)")
                .build();

        chart.addSeries("Temperatures",
                IntStream.range(1, temperatures.size() + 1).toArray()
        );

        new SwingWrapper<>(chart).displayChart();
    }

    private static List<Integer> getTemperaturesFromServer() {
        String url = "http://localhost:7070/measurements";
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, List.class);
    }
}
