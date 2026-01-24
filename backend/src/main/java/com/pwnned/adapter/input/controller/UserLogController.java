package com.pwnned.adapter.input.controller;

import com.pwnned.domain.model.UserLog;
import com.pwnned.port.input.UserLogServicePort;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/v1/logs")
public class UserLogController {
    private final UserLogServicePort servicePort;

    public UserLogController(UserLogServicePort servicePort) {
        this.servicePort = servicePort;
    }

    @GetMapping("/{userId}")
    public List<UserLog> getLogs(@PathVariable String userId) {
        return servicePort.getUserLogs(userId);
    }
}