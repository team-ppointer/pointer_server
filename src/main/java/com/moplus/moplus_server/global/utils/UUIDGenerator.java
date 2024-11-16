package com.moplus.moplus_server.global.utils;

import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class UUIDGenerator {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }
}
