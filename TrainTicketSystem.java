import java.util.HashMap;
import java.util.Map;

public class TrainTicketSystem {
    private Map<String, Ticket> tickets;
    private Map<String, String> seatAllocations;

    public TrainTicketSystem() {
        this.tickets = new HashMap<>();
        this.seatAllocations = new HashMap<>();
    }

    public String purchaseTicket(String from, String to, String firstName, String lastName, String email, double price) {
        String userId = generateUserId(firstName, lastName, email);
        Ticket ticket = new Ticket(from, to, userId, price);
        tickets.put(userId, ticket);
        allocateSeat(userId);
        return userId;
    }

    public Ticket getReceipt(String userId) {
        return tickets.get(userId);
    }

    public Map<String, String> getUsersBySection(String section) {
        Map<String, String> usersInSection = new HashMap<>();
        for (Map.Entry<String, String> entry : seatAllocations.entrySet()) {
            if (entry.getValue().equals(section)) {
                usersInSection.put(entry.getKey(), section);
            }
        }
        return usersInSection;
    }

    public void removeUser(String userId) {
        if (tickets.containsKey(userId)) {
            seatAllocations.remove(userId);
            tickets.remove(userId);
        }
    }

    public void modifySeat(String userId, String newSection) {
        if (seatAllocations.containsKey(userId)) {
            seatAllocations.put(userId, newSection);
        }
    }

    private void allocateSeat(String userId) {
        // Simple allocation: alternating between sections A and B
        String section = seatAllocations.size() % 2 == 0 ? "Section A" : "Section B";
        seatAllocations.put(userId, section);
    }

    private String generateUserId(String firstName, String lastName, String email) {
        // Simple method to generate a user ID
        return firstName.substring(0, 1) + lastName.substring(0, 1) + email.hashCode();
    }

    public static void main(String[] args) {
        TrainTicketSystem ticketSystem = new TrainTicketSystem();

        // Example usage
        String userId = ticketSystem.purchaseTicket("London", "France", "John", "Doe", "john.doe@example.com", 20.0);
        Ticket receipt = ticketSystem.getReceipt(userId);
        System.out.println("Receipt:");
        System.out.println(receipt);

        System.out.println("\nUsers in Section A:");
        Map<String, String> usersInSectionA = ticketSystem.getUsersBySection("Section A");
        usersInSectionA.forEach((user, section) -> System.out.println(user + " - " + section));

        // Example of removing a user
        ticketSystem.removeUser(userId);

        // Example of modifying a user's seat
        ticketSystem.modifySeat(userId, "Section B");
    }
}

class Ticket {
    private String from;
    private String to;
    private String userId;
    private double price;

    public Ticket(String from, String to, String userId, double price) {
        this.from = from;
        this.to = to;
        this.userId = userId;
        this.price = price;
    }

    @Override
    public String toString() {
        return "From: " + from + ", To: " + to + ", User: " + userId + ", Price Paid: $" + price;
    }
}
