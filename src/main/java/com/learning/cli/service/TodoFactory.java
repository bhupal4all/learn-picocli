package com.learning.cli.service;

import com.learning.cli.service.impl.TodoServiceImpl;

public class TodoFactory {
    public static TodoService getService(){
        return new TodoServiceImpl();
    }
}
