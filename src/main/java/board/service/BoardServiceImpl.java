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
	//�Խ��� �����ֱ� ���� ���� ���� ����
//	@Override
//	public Map<String, Integer> listArticles1(Map paginMap) throws Exception{
//		Map articlesList =  (Map) boardDAO.selectMap(paginMap);
//        return articlesList;
//	}
	//��ȸ���� �����ְ� ������Ʈ �� ���� ����Ʈ�� �����ֱ� ����
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
	//���� �̹��� �߰��ϱ�
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		return boardDAO.insertNewArticle(articleMap);
	}
	*/
	 //���� �̹��� �߰��ϱ�
	
	@Override
	public int addNewArticle(Map articleMap) throws Exception{
		int articleNO = boardDAO.insertNewArticle(articleMap);
		articleMap.put("articleNO", articleNO);
		boardDAO.insertNewImage(articleMap);
		return articleNO;
	}
	
	
	//���� ���� ���̱�
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
	 //���� ���� ���̱�
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
