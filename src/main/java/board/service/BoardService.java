package board.service;

import java.util.List;
import java.util.Map;

import board.vo.ArticleVO;

public interface BoardService {
	public List<ArticleVO> listArticles() throws Exception;
	public int addNewArticle(Map articleMap) throws Exception;
	//public ArticleVO viewArticle(int articleNO) throws Exception;
	public Map viewArticle(int articleNO) throws Exception;
	public void modArticle(Map articleMap) throws Exception;
	public void removeArticle(int articleNO) throws Exception;
	public void updateCount(int articleNO) throws Exception;
	public Map selectarticle(Map paginMap) throws Exception;
	//Map<String, Integer> listArticles1(Map paginMap) throws Exception;
	public void modimage(Map articleMap) throws Exception;
}
