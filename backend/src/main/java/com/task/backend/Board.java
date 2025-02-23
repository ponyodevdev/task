package com.task.backend;

public class Board {
   
	private int boardId;
    private String name;

    public Board(int boardId, String name) {
        this.boardId = boardId;
        this.name = name;
    }

    public int getBoardId() {
        return boardId; 
    }

    public String getName() {
        return name;
    }
}
