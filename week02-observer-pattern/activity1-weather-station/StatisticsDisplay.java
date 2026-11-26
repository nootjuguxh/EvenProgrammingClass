public class StatisticsDisplay implements Observer {

    // Note: Float.MIN_VALUE is the smallest POSITIVE float, not the most
    // negative one. It works here because all sample temperatures are
    // positive, but -Float.MAX_VALUE would be safer for sub-zero readings.
    private float maxTemp = Float.MIN_VALUE;
    private float minTemp = Float.MAX_VALUE;

    @Override
    public void update(float temperature, float humidity, float pressure) {
        maxTemp = Math.max(maxTemp, temperature);
        minTemp = Math.min(minTemp, temperature);
        System.out.println("  [Statistics] Max: " + maxTemp
                + "°C, Min: " + minTemp + "°C");
    }
}
