package com.toedter.workshops.springrest.lab2.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class UserTestDataLoader {
    private final Logger logger = LoggerFactory.getLogger(UserTestDataLoader.class);

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void loadData() {
        logger.info("init test data");
    }
}