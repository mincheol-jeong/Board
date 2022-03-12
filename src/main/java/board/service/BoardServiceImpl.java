package board.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import board.dao.BoardDAO;
import board.vo.ArticleVO;
import board.vo.ImageVO;


@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl  implements BoardService{
	@Autowired
	BoardDAO boardDAO;
	
	public List<ArticleVO> listArticles() throws Exception{
		List<ArticleVO> articlesList =  boardDAO.selectAllArticlesList();
        return articlesList;
	}
	//게시판 보여주기 위해 만든 서비스 구현
//	@Override
//	public Map<String, Integer> listArticles1(Map paginMap) throws Exception{
//		Map articlesList =  (Map) boardDAO.selectMap(paginMap);
//        return articlesList;
//	}
	//조회수를 보여주고 업데이트 한 것을 리스트에 보여주기 위함
	@Override
	public Map selectarticle( Map paginMap) throws Exception{
		Map articleMap = new HashMap();
		List<ArticleVO> articleList = boardDAO.selectMap(paginMap);
		
		int totArticles = boardDAO.select();
		articleMap.put("articleList", articleList);
		articleMap.put("totArticles", totArticles);
		
		return articleMap;
		
	}

	@Override
	public void updateCount(int articleNO) throws Exception{
		 boardDAO.updateCount(articleNO);
	}
	/*
	//단일 이미지 추가하기
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		return boardDAO.insertNewArticle(articleMap);
	}
	*/
	 //다중 이미지 추가하기
	
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		int articleNO = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNO", articleNO);
		boardDAO.insertNewImage(articleMap);
		return articleNO;
	}
	
	
	//다중 파일 보이기
	@Override
	public Map viewArticle(int articleNO) throws Exception {
		Map articleMap = new HashMap();
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		List<ImageVO> imageFileList = boardDAO.selectImageFileList(articleNO);
		articleMap.put("article", articleVO);
		articleMap.put("imageFileList", imageFileList);
		return articleMap;
	}
   
	
	/*
	 //단일 파일 보이기
	@Override
	public ArticleVO viewArticle(int articleNO) throws Exception {
		ArticleVO articleVO = boardDAO.selectArticle(articleNO);
		return articleVO;
	}
	
	*/
	@Override
	public void modArticle(Map articleMap) throws Exception {
		boardDAO.updateArticle(articleMap);
	}
	@Override
	public void modimage(Map articleMap) throws Exception{
		boardDAO.updateimage(articleMap);
	}
	@Override
	public void removeArticle(int articleNO) throws Exception {
		boardDAO.deleteArticle(articleNO);
	}
	

	
}
