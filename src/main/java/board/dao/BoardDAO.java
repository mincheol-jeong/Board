package board.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import board.vo.ArticleVO;


public interface BoardDAO {
	public List selectAllArticlesList() throws DataAccessException;
	public int insertNewArticle(Map articleMap) throws DataAccessException;
	public void insertNewImage(Map articleMap) throws DataAccessException;
	
	public ArticleVO selectArticle(int articleNO) throws DataAccessException;
	public void updateArticle(Map articleMap) throws DataAccessException;
	public void deleteArticle(int articleNO) throws DataAccessException;
	public List selectImageFileList(int articleNO) throws DataAccessException;
	public void updateCount(int articleNO) throws DataAccessException;
	public List selectMap(Map paginMap) throws DataAccessException;
	public int select() throws DataAccessException;
	//public List selectAllArticlesList1(Map paginMap) throws DataAccessException;
	public void updateimage(Map articleMap) throws DataAccessException;
	
	
}
