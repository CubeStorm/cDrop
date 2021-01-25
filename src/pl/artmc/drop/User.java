package pl.artmc.drop;

public class User {
    /**
     * Change drop state of specific Mineral
     * @param name Player's name, key to HashMap
     * @param id Id of object in list
     */
    public User(String name, int id) {
        boolean[] list = Main.players.get(name);

        list[id] = !list[id];
    }
    public User() {}

    /**
     * Return drop state of specific Mineral
     * @param name Player's name, key to HashMap
     * @param id Id of object in list
     * @return String to /drop command
     */
    public String getState(String name, int id) {
        boolean[] list = Main.players.get(name);

        return list[id] ? "&aOn" : "&cOff";
    }
}
