package utils;

import java.util.HashSet;
import java.util.Set;

import dao.UserDao;
import org.telegram.telegrambots.api.objects.User;

public class Cache {

    private static Set<User> activeUsers = new HashSet<>();

    public static boolean isUserActive(User user){
        return activeUsers.stream().anyMatch(looser -> looser.getId().equals(user.getId()) && looser.getFirstName().equals(user.getFirstName()));
    }

    public static void addActiveUser(User user){
        activeUsers.add(user);
        System.out.println("User added: " + user);
    }

    public static void updateCacheOnStart(){
        UserDao userDao = new UserDao();
    }

    public static boolean isEmpty() {
        return activeUsers.isEmpty();
    }
}
