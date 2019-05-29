package com.jokerdata.vo;

import com.jokerdata.entity.app.generator.Task;
import com.jokerdata.entity.app.generator.TaskLog;
import com.jokerdata.entity.app.generator.User;
import com.jokerdata.entity.app.generator.UserAccount;
import lombok.Data;

@Data
public class TaskVo extends TaskLog {

    private Task task;

    private UserAccount userAccount;

    private User user;
}
