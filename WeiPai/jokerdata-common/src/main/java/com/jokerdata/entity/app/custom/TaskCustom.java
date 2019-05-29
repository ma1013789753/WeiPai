package com.jokerdata.entity.app.custom;

import com.jokerdata.entity.app.generator.Task;
import com.jokerdata.entity.app.generator.UserAccount;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Data
public class TaskCustom extends Task {




    private String ids;
}
