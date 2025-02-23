package com.task.backend;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class CategoryManagerTest {
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
    void testAddCategory() {
        // 남자 카테고리에 하위 카테고리 확인
        Category maleCategory = categoryManager.getCategoryById(1);
        assertNotNull(maleCategory);
        assertTrue(maleCategory.getSubCategoryIds().contains(2)); // 엑소
        assertTrue(maleCategory.getSubCategoryIds().contains(3)); // 방탄소년단
        
        // 여자 카테고리에 하위 카테고리 확인
        Category femaleCategory = categoryManager.getCategoryById(4);
        assertNotNull(femaleCategory);
        assertTrue(femaleCategory.getSubCategoryIds().contains(5));  // 블랙핑크
    }
    
    @Test
    void testCategoryAndBoard() {
        // 엑소 카테고리 내 게시판 확인
        Category exo = categoryManager.getCategoryById(2);
        assertNotNull(exo);
        assertTrue(exo.getBoards().stream().anyMatch(board -> board.getBoardId() == 1)); // 공지사항
        assertTrue(exo.getBoards().stream().anyMatch(board -> board.getBoardId() == 2)); // 첸
        assertTrue(exo.getBoards().stream().anyMatch(board -> board.getBoardId() == 3)); // 백현
        assertTrue(exo.getBoards().stream().anyMatch(board -> board.getBoardId() == 4)); // 시우민
        
        // 방탄소년단 카테고리 내 게시판 확인
        Category bts = categoryManager.getCategoryById(3);
        assertNotNull(bts);
        assertTrue(bts.getBoards().stream().anyMatch(board -> board.getBoardId() == 5)); // 공지사항
        assertTrue(bts.getBoards().stream().anyMatch(board -> board.getBoardId() == 6)); // 익명게시판
        assertTrue(bts.getBoards().stream().anyMatch(board -> board.getBoardId() == 7)); // 뷔

        // 블랙핑크 카테고리 내 게시판 확인
        Category blackpink = categoryManager.getCategoryById(5);
        assertNotNull(blackpink);
        assertTrue(blackpink.getBoards().stream().anyMatch(board -> board.getBoardId() == 8)); // 공지사항
        assertTrue(blackpink.getBoards().stream().anyMatch(board -> board.getBoardId() == 6)); // 익명게시판
        assertTrue(blackpink.getBoards().stream().anyMatch(board -> board.getBoardId() == 9)); // 로제
    }
    
    @Test
    void testAnonymousBoard() {
        // 익명게시판이 여러 카테고리에서 공유되고 있는지 확인
        Category bts= categoryManager.getCategoryById(3); // 방탄소년단 카테고리
        assertNotNull(bts, "카테고리가 null 입니다.");
        assertTrue(bts.containsBoard(6)); // 익명게시판이 있는지 확인
        
        Category blackpink = categoryManager.getCategoryById(5); // 블랙핑크 카테고리
        assertNotNull(blackpink, "카테고리가 null 입니다.");
        assertTrue(blackpink.containsBoard(6)); // 익명게시판이 있는지 확인
    }
    
    @Test
    void testJson() throws JsonProcessingException {
        // 카테고리 JSON 변환 테스트
        String json = categoryManager.getCategoryJson(1); // 남자 카테고리
        assertNotNull(json);
        assertTrue(json.contains("남자"));
    }
}
