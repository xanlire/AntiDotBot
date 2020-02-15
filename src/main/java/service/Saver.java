package service;

import java.sql.SQLIntegrityConstraintViolationException;

import dao.MessageDao;
import dao.UserDao;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import utils.Cache;

public class Saver {

    MessageDao messageDao = new MessageDao();
    UserDao userDao = new UserDao();

    public void saveUpdateInfo(Update update){

        User user = update.getMessage().getFrom();

        if(Cache.isEmpty()){
            Cache.updateCacheOnStart();
        }
        if(!Cache.isUserActive(user)){
            Cache.addActiveUser(user);
            try {
                userDao.save(user);
            } catch (SQLIntegrityConstraintViolationException e) {
                userDao.update(user);
            }
        }
        Message message = update.getMessage();
        if(message.hasText() && !message.getText().startsWith("/top10")){
            messageDao.save(update.getMessage());
        }
    }
}
