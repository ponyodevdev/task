package com.task.backend;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryManager {
	
	private Map<Integer, Category> categoryMap = new HashMap<>(); // 카테고리 ID를 키로 하는 카테고리 맵
	private Map<String, Integer> categoryNameMap = new HashMap<>(); // 카테고리명으로 검색
	private Map<Integer, Board> boardMap = new HashMap<>(); // 게시판 관리
	private Map<Integer, Set<Integer>> shareBoardMap = new HashMap<>(); // 공유 게시판 관리
	
	// 카테고리 추가 (부모 카테고리와 연결 parent_idx, child_id)
	public void addCategory(int parentId, int childId, String categoryName) {
		Category childCategory =  categoryMap.get(childId);
		if(childCategory == null) {
		   childCategory = new Category(childId, categoryName);
		   categoryMap.put(childId, childCategory);
		}
		
		// 부모가 있다면 부모 카테고리의 하위 카테고리 목록에 추가하기
		if(parentId != -1) {
			Category parentCategory = categoryMap.get(parentId);
			if(parentCategory != null) {
				parentCategory.addSubCategory(childId);
			}
		}
		
		// 카테고리명으로도 검색 가능하게 하기
		categoryNameMap.put(categoryName, childId);
	}
	
	// 게시판 추가 (각 카테고리별로 고유한 게시판)
	public void addBoard(int categoryId, int boardId, String boardName) {
		Board board = new Board(boardId, boardName); // 새로운 게시판 생성
		boardMap.put(boardId, board); // 게시판 맵에 게시판 추가
		
		Category category = categoryMap.get(categoryId); // 카테고리 맵에서 해당 카테고리 조회
		if(category != null) {
			category.addBoard(board); // 카테고리에 게시판 추가 
		}else {
			 throw new IllegalArgumentException("존재하지 않는 카테고리에 게시판을 추가할 수 없습니다.");
		}
	}
	
	// 익명게시판 추가 (공유되는 게시판은 여러 카테고리에 추가)
	public void addAnonyMousBoard(int categoryId, int boardId) {
		Set<Integer> boardCategories = shareBoardMap.get(boardId); // 중복 방지를 위해 Set 사용함
		if(boardCategories == null) {
			boardCategories = new HashSet<>();
			shareBoardMap.put(boardId, boardCategories);
		}
		
		boardCategories.add(categoryId);
		
		Category category = categoryMap.get(categoryId);
		if(category != null) {
			Board board = boardMap.get(boardId);
			if(board != null) {
				category.addBoard(board);
			}
		}
	}
	
	// 카테고리 ID로 카테고리 검색
	public Category getCategoryById(int categoryId) {
		return categoryMap.get(categoryId);
	}
	
	// 카테고리 JSON 변환
	public String getCategoryJson(int categoryId) throws JsonProcessingException{
		Category category = categoryMap.get(categoryId);
		if(category != null) {
			return toJson(category);
		}
		return "{}";
	}
	
	
	// 현재 카테고리를 JSON 문자열로 변환
    public String toJson(Category category) throws JsonProcessingException{
    	ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper를 사용하여 JSON 변환
    	return objectMapper.writeValueAsString(category); // JSON 문자열로 변환하여 반환
    }
}
