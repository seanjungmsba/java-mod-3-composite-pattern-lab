import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class HotelManager {

    private final static int NUMBER_OF_FLOORS = 3;
    private final static int ROOMS_PER_FLOOR = 4;
    private static int totalNumberOfFloors = NUMBER_OF_FLOORS * ROOMS_PER_FLOOR;
    private static List<HotelRoomInterface> hotelRooms = null;
    private static List<HotelFloor> hotelFloors = null;

    public static void main(String[] args) {

        Logger.getInstance().log("Managing hotel...");
        
        // 1. create hotel rooms
        hotelRooms = createHotelRooms(totalNumberOfFloors);

        // 2. create hotel floors
        hotelFloors = createHotelFloors(NUMBER_OF_FLOORS);

        // 3. add hotel rooms to hotel floors
        for (int i = 0; i < (NUMBER_OF_FLOORS); i++) {
            for (int j = 0; j < (ROOMS_PER_FLOOR); j++) {
                int rooms = j+1;
                int floor = i+1;
                System.out.println("(Adding " + rooms + " rooms in " + floor + " floors...)");
                hotelFloors.get(i % NUMBER_OF_FLOORS).addHotelRoom(hotelRooms.get(i));
            }
        }
        System.out.println("==========================================");

        // 4. take actions on rooms and floors and examine your output to ensure you implemented the desired behaviors
        System.out.println("======== Cleaning an entire floor ========");
        hotelFloors.forEach(HotelFloor::clean);
        System.out.println("==========================================");

        System.out.println("==== Booking an entire floor for Sean ====");
        hotelFloors.forEach(floor -> floor.book("Sean"));
        System.out.println("==========================================");

        System.out.println("======== Cleaning an individual room ========");
        int numberOfRoomsToClean = 5;
        for (int i = 0; i < numberOfRoomsToClean; i++) {
            hotelRooms.get(i).clean();
        }
        System.out.println("==========================================");

        System.out.println("======== Booking an individual room ========");
        int numberofPeopleToBook = 4;
        String[] peopleToBook = {"Abigail", "Beth", "Chris", "Drew"};
        for (int i = 0; i < numberofPeopleToBook; i++) {
            hotelRooms.get(i).book(peopleToBook[i]);
        }
        System.out.println("==========================================");
    }

    public static List<HotelRoomInterface> createHotelRooms(int totalHotelRooms) {
        // rang(): https://www.geeksforgeeks.org/intstream-range-java/
        // mapToObj(): https://www.geeksforgeeks.org/intstream-maptoobj-java/
        // collect(): https://stackoverflow.com/questions/23674624/how-do-i-convert-a-java-8-intstream-to-a-list
        List<HotelRoomInterface> hotelRooms = IntStream
                .range(0, totalHotelRooms)
                .mapToObj(i -> new HotelRoom())
                .collect(Collectors.toList());
        System.out.println("STATUS: Hotel rooms are created!");
        return hotelRooms;
    }

    public static HotelRoomInterface returnHotelRoom(List<HotelRoomInterface> hotelRooms, int index) {
        return hotelRooms.get(index);
    }

    public static List<HotelFloor> createHotelFloors(int numberOfFloors) {
        // rang(): https://www.geeksforgeeks.org/intstream-range-java/
        // mapToObj(): https://www.geeksforgeeks.org/intstream-maptoobj-java/
        // collect(): https://stackoverflow.com/questions/23674624/how-do-i-convert-a-java-8-intstream-to-a-list
        List<HotelFloor> hotelFloors = IntStream
                .range(0, numberOfFloors)
                .mapToObj(i -> new HotelFloor())
                .collect(Collectors.toList());
        System.out.println("STATUS: Hotel floors are created!");
        return hotelFloors;
    }

    public static HotelFloor returnHotelFloor(List<HotelFloor> hotelFloors, int index) {
        return hotelFloors.get(index);
    }

}

interface HotelRoomInterface {
    void book(String guestName);
    void clean();
}

class HotelRoom implements HotelRoomInterface {
    public void book(String guestName) {
        Logger.getInstance().log("Booked a room for " + guestName);
    }

    public void clean() {
        Logger.getInstance().log("Cleaned room");
    }
}

class HotelFloor implements HotelRoomInterface {
    private List<HotelRoomInterface> hotelRooms = new ArrayList<HotelRoomInterface>();
    public void book(String guestName) {
        hotelRooms.forEach(child -> {
            child.book(guestName);
        });
    }

    public void clean() {
        hotelRooms.forEach(child -> child.clean());
    }

    public void addHotelRoom(HotelRoomInterface hotelRoom) {
        hotelRooms.add(hotelRoom);
    }

    public void removeHotelRoom(HotelRoomInterface hotelRoom) {
        hotelRooms.remove(hotelRoom);
    }
}

class Logger {
    private int currentLine = 0;
    private static Logger logger = null;

    private Logger() {
    }

    public static Logger getInstance() {
        if (logger == null) {
            logger = new Logger();
        }

        return logger;
    }

    public void log(String message) {
        currentLine++;
        System.out.println(currentLine + "::" + message);
    }
}