package com.task.backend;

import java.util.ArrayList;
import java.util.List;

public class Category {
	
	private int id;  // 카테고리 ID
	private String name;  // 카테고리 이름
	private List<Integer> subCategoryIds;  // 하위 카테고리 목록
	private List<Board> boards;  // 카테고리에 속한 게시판 목록
	
	// 카테고리 생성자
	public Category(int id, String name) {
	    this.id = id;
	    this.name = name;
	    this.subCategoryIds = new ArrayList<>();  // 하위 카테고리는 빈 리스트로 초기화
	    this.boards = new ArrayList<>();  // 게시판은 빈 리스트로 초기화
	}
	
	public int getId() {
	    return id;
	}
	
	public String getName() {
	    return name;
	}
	
	// 하위 카테고리 추가
	public void addSubCategory(int subCategoryId) {
	    this.subCategoryIds.add(subCategoryId);  
	}
	
	public List<Integer> getSubCategoryIds() {
	    return subCategoryIds;
	}
	
	// 게시판 추가
	public void addBoard(Board board) {
	    this.boards.add(board);  // 주어진 게시판을 게시판 목록에 추가
	}
	
	public List<Board> getBoards() {
	    return boards;
	}
	
	// 게시판 ID로 확인하는 메서드 추가
    public boolean containsBoard(int boardId) {
        return boards.stream().anyMatch(board -> board.getBoardId() == boardId);
    }
}
