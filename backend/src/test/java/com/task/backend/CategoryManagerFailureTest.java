package com.task.backend;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryManagerFailureTest {
	
	private CategoryManager categoryManager;

	@BeforeEach
	void setUp() {
	    categoryManager = new CategoryManager();
	        
		// 카테고리 추가
		categoryManager.addCategory(-1, 1, "남자");
		categoryManager.addCategory(1, 2, "엑소");
		categoryManager.addCategory(1, 3, "방탄소년단");
		
		categoryManager.addCategory(-1, 4, "여자");
		categoryManager.addCategory(4, 5, "블랙핑크");
		
		// 게시판 추가
		categoryManager.addBoard(2, 1, "공지사항");
		categoryManager.addBoard(2, 2, "첸");
		categoryManager.addBoard(2, 3, "백현");
		categoryManager.addBoard(2, 4, "시우민");
		
		categoryManager.addBoard(3, 5, "공지사항");
		categoryManager.addBoard(3, 6, "익명게시판");
		categoryManager.addAnonyMousBoard(3, 6);  // 익명게시판
		categoryManager.addBoard(3, 7, "뷔");
		
		categoryManager.addBoard(5, 8, "공지사항");
		categoryManager.addBoard(5, 6, "익명게시판");
		categoryManager.addAnonyMousBoard(5, 6);  // 익명게시판
		categoryManager.addBoard(5, 9, "로제");
}

	@Test
	void testCategoryNotFound() {
		// 999번 ID로 카테고리를 조회하고, 해당 카테고리가 존재하지 않으므로 null이 반환되는지 확인
	    Category nonExistentCategory = categoryManager.getCategoryById(999);
	    assertNull(nonExistentCategory);  // 존재하지 않으면 null 반환
	}
	
	@Test
	void testBoardNotInCategory() {
	    // 1번 카테고리를 조회한 후, 해당 카테고리의 게시판 목록에 999번 게시판이 포함되지 않은지 확인
	    Category maleCategory = categoryManager.getCategoryById(1);
	    assertNotNull(maleCategory);   // 카테고리가 존재하는지 확인
	    // 존재하지 않는 게시판 ID 999번이 해당 카테고리에 포함되어 있지 않은지 확인
	    assertFalse(maleCategory.getBoards().stream().anyMatch(board -> board.getBoardId() == 999));
	}
	
	@Test
	void testAddBoardToMissingCategory() {
		// 존재하지 않는 카테고리 ID (999)와 게시판을 추가하려고 시도할 때 IllegalArgumentException 예외가 발생하는지 확인
	    assertThrows(IllegalArgumentException.class, () -> categoryManager.addBoard(999, 100, "새 게시판"));
	}
	
	@Test
	void testBoardNotInCategory2() {
		 // 2번 카테고리를 조회하고, 해당 카테고리에서 존재하지 않는 게시판 ID (99번)과의 관계가 없는지 확인
	    Category exo = categoryManager.getCategoryById(2);
	    assertNotNull(exo); // 카테고리가 존재하는지 확인
	    // 99번 게시판이 해당 카테고리에 포함되어 있지 않은지 확인
	    assertFalse(exo.getBoards().stream().anyMatch(board -> board.getBoardId() == 99));  // 존재하지 않는 게시판 ID
	}
}
