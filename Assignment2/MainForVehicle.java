import java.time.Year;
public class MainForVehicle{
    public static void main(String[] a){
        Vehicle v = new Vehicle();
        Vehicle v1 = new Vehicle("Mahindra", "Thar", Year.of(2022), "White", 1500000.50, 4);
        v1.setMfgCode("THR2878822");
        v1.setNoOfServices(2);
        v1.fuelType = 'D';
        Vehicle v2 = new Vehicle(7, 2000000.00, 'D', "MND2022THAR", 3);
        v2.brand = "Mahindra";
        v2.model = "Thar";
        v2.yearofMfg = Year.of(2022);
        v2.color = "Red";
        Vehicle[] garage = new Vehicle[3]; 
        garage[0] = v;
        garage[1] = v1; garage[2] = v2;
        System.out.println("Vehicle Details:");
        System.out.println("Brand        Model      Year   Color      Fuel   Seats    Price           Mileage    MfgCode         Services");
        for(int i = 0; i < garage.length; i++) {
            if(garage[i].fuelType == 'D') {
                float m = garage[i].calcMileage(50, 500);
                printTabular(garage[i], m );
            }
            else if (garage[i].fuelType == 'P' || garage[i].fuelType == 'C') {
                float m = garage[i].calcMileage(40, 500);
                printTabular(garage[i], m );
            }
            else {
                float m = 0; printTabular(garage[i], m );
            }
        }

        for(int i = 0; i < garage.length; i++) {
            System.out.println("Vehicle "+(i+1)+":");
            garage[i].start();
            garage[i].drive();
            garage[i].stop();
        }
    }
    
    public static void printTabular(Vehicle v, float m) {
        String format = "%-12s %-10s %-6d %-10s %-6c %-8d %-15.2f %-10.1f %-15s %-10d%n";
        System.out.printf(format, v.brand, v.model, v.yearofMfg.getValue(), v.color, v.fuelType, v.seats, v.price, m, (v.getMfgCode() == null ? "null" : v.getMfgCode()), v.getNoOfServices());
    }
}
