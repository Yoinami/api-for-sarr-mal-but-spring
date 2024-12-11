package com.yoinami.sarr_mal_api.service;


import com.yoinami.sarr_mal_api.model.User;
import com.yoinami.sarr_mal_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String getUserIdByUsername(String username) {
        try {
            User user = userRepository.findItemByName(username);

            return user.getId();
        } catch (Exception e) {
            return null;
        }
    }

    public User updateUserInfo(User olduser, User newuser) {
        olduser.setEmail(newuser.getEmail());
        olduser.setAge(newuser.getAge());
        olduser.setAllergies(newuser.getAllergies());
        olduser.setDiseases(newuser.getDiseases());
        olduser.setWeight(newuser.getWeight());
        olduser.setHeight(newuser.getHeight());
        olduser.setPreferredFood(newuser.getPreferredFood());
        olduser.setExercise(newuser.getExercise());
        userRepository.save(olduser);
        return olduser;
    }
}
