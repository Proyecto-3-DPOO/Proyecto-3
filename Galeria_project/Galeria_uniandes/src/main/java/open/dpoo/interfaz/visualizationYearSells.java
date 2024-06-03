package main.java.open.dpoo.interfaz;

    import javax.swing.*;
    import java.awt.*;
    import java.util.Random;
    import java.util.Calendar;
    import java.util.GregorianCalendar;
    
    public class visualizationYearSells extends JPanel {
        private final int[][] salesData;

    public visualizationYearSells(int[][] salesData) {
        this.salesData = salesData;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = 15;
        int padding = 50;
        int maxSales = findMaxSales(salesData);

        // Etiquetas de los días de la semana
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int day = 0; day < daysOfWeek.length; day++) {
            g.drawString(daysOfWeek[day], 10, padding + (day + 1) * cellSize);
        }

        // Etiquetas de los meses
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        int weeksInMonth = 4; // Aproximación para el número de semanas por mes
        for (int month = 0; month < months.length; month++) {
            g.drawString(months[month], padding + month * weeksInMonth * cellSize, 40);
        }

        // Dibujar la cuadrícula de ventas
        for (int week = 0; week < salesData.length; week++) {
            for (int day = 0; day < salesData[week].length; day++) {
                int sales = salesData[week][day];
                float intensity = (float) sales / maxSales;
                g.setColor(new Color(0, 0, 0, intensity));
                int x = padding + week * cellSize;
                int y = padding + (day + 1) * cellSize;
                g.fillRect(x, y, cellSize, cellSize);
            }
        }
    }

    private int findMaxSales(int[][] salesData) {
        int max = 0;
        for (int[] week : salesData) {
            for (int sales : week) {
                if (sales > max) {
                    max = sales;
                }
            }
        }
        return max;
    }

    public static int[][] generateRandomSalesData() {
        int weeksInYear = 52;
        int daysInWeek = 7;
        Random rand = new Random();
        int[][] salesData = new int[weeksInYear][daysInWeek];
        for (int week = 0; week < weeksInYear; week++) {
            for (int day = 0; day < daysInWeek; day++) {
                salesData[week][day] = rand.nextInt(100);
            }
        }
        return salesData;
    }

    public static void main(String[] args) {
        int[][] salesData = generateRandomSalesData();
        JFrame frame = new JFrame("Sales Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(820, 400);
        frame.add(new visualizationYearSells(salesData));
        frame.setVisible(true);
    }
}