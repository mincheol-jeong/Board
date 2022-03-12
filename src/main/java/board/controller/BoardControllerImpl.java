package board.controller;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.service.BoardService;
import board.vo.ArticleVO;
import board.vo.ImageVO;
import member.vo.MemberVO;


@Controller("boardController")
public class BoardControllerImpl  implements BoardController{
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
	@Autowired
	private BoardService boardService;
	@Autowired
	private ArticleVO articleVO;
	
	@Override
	@RequestMapping(value= "/board/listArticles.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView listArticles(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		/* ����Ʈ ���� �ܿ� �������� �������ֱ� ���� ���� jsp�ܿ� �ֱ� ���� section �� pageNum�� ������ map�� �ְ� ȭ��ܿ� �־���. */
		String _section = request.getParameter("section");
		String _pageNum = request.getParameter("pageNum");
		int section = Integer.parseInt(((_section == null)?"1":_section));
		int pageNum = Integer.parseInt(((_pageNum == null)?"1":_pageNum));
		//��¥ ������ ���߱� ���ؼ� �����Ͽ� ȭ��ܿ� ��
		DateFormat now = new SimpleDateFormat("yyyy-MM-dd");
		String date = now.format(new Date());
		request.setAttribute("now", date);
		System.out.println(date);
		Map paginMap1 = new HashMap();
		paginMap1.put("section", section);
		paginMap1.put("pageNum",pageNum);
		Map<String,Integer> paginMap = boardService.selectarticle(paginMap1);
		paginMap.put("section", section);
		paginMap.put("pageNum", pageNum);
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("paginMap", paginMap);
		return mav;
		
	}
	/*
	 //�� �� �̹��� �۾���
	@Override
	@RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity addNewArticle(MultipartHttpServletRequest multipartRequest, 
	HttpServletResponse response) throws Exception {
		multipartRequest.setCharacterEncoding("utf-8");
		Map<String,Object> articleMap = new HashMap<String, Object>();
		Enumeration enu=multipartRequest.getParameterNames();
		while(enu.hasMoreElements()){
			String name=(String)enu.nextElement();
			String value=multipartRequest.getParameter(name);
			articleMap.put(name,value);
		}
		
		String imageFileName= upload(multipartRequest);
		HttpSession session = multipartRequest.getSession();
		MemberVO memberVO = (MemberVO) session.getAttribute("member");
		String articleNOn = multipartRequest.getParameter("check");
		String id = memberVO.getId();
		System.out.println(articleNOn);
		articleMap.put("parentNO", 0);
		articleMap.put("id", id);
		articleMap.put("imageFileName", imageFileName);
		if(articleNOn != null) {
		articleMap.put("articleNOn",articleNOn);
		}
		else
			articleMap.put("articleNOn", 0);
		
		String message;
		ResponseEntity resEnt=null;
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add("Content-Type", "text/html; charset=utf-8");
		try {
			int articleNO = boardService.addNewArticle(articleMap);
			if(imageFileName!=null && imageFileName.length()!=0) {
				File srcFile = new 
				File(ARTICLE_IMAGE_REPO+ "\\" + "temp"+ "\\" + imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				FileUtils.moveFileToDirectory(srcFile, destDir,true);
			}
	
			message = "<script>";
			message += " alert('������ �߰��߽��ϴ�.');";
			message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
			message +=" </script>";
		    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		}catch(Exception e) {
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
			srcFile.delete();
			
			message = " <script>";
			message +=" alert('������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');');";
			message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
			message +=" </script>";
			resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
			e.printStackTrace();
		}
		return resEnt;
	}
	*/
	
	/*
	//�Ѱ��� �̹��� �����ֱ�
	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
                                    HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		articleVO=boardService.viewArticle(articleNO);
		boardService.updateCount(articleNO);
		System.out.println(articleNO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("article", articleVO);
		return mav;
	*/
	
	//���� �̹��� �����ֱ�
	@RequestMapping(value="/board/viewArticle.do" ,method = RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("articleNO") int articleNO,
			  HttpServletRequest request, HttpServletResponse response) throws Exception{
		String viewName = (String)request.getAttribute("viewName");
		Map articleMap=boardService.viewArticle(articleNO);
		boardService.updateCount(articleNO);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		mav.addObject("articleMap", articleMap);
		return mav;
	}
   
	

	
  //�� �� �̹��� ���� ���
  @RequestMapping(value="/board/modArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity modArticle(MultipartHttpServletRequest multipartRequest,  
    HttpServletResponse response) throws Exception{
    multipartRequest.setCharacterEncoding("utf-8");
	Map<String,Object> articleMap = new HashMap<String, Object>();
	Enumeration enu=multipartRequest.getParameterNames();
	String imageFileNO = multipartRequest.getParameter("imageFileNO");
	while(enu.hasMoreElements()){
		String name=(String)enu.nextElement();
		String value=multipartRequest.getParameter(name);
		articleMap.put(name,value);
		System.out.println(name+value);
	}
	
	String imageFileName= upload(multipartRequest);
	articleMap.put("imageFileName", imageFileName);
	articleMap.put("imageFileNO", imageFileNO);
	System.out.println(imageFileNO);
	String articleNO=(String)articleMap.get("articleNO");
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	
    try {
    	
    	boardService.modimage(articleMap);
    
       boardService.modArticle(articleMap);
   	System.out.println(imageFileName+imageFileName.length());
       if(imageFileName!=null && imageFileName.length()!=0) {
         File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
         File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
       
         FileUtils.moveFileToDirectory(srcFile, destDir, true);
         System.out.println("asss");
         String originalFileName = (String)articleMap.get("originalFileName");
         File oldFile = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO+"\\"+originalFileName);
         oldFile.delete();
       }	
       message = "<script>";
	   message += " alert('���� �����߽��ϴ�.');";
	   message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	   message +=" </script>";
       resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }catch(Exception e) {
    	e.printStackTrace();
      File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
      srcFile.delete();
      message = "<script>";
	  message += " alert('������ �߻��߽��ϴ�.�ٽ� �������ּ���');";
	  message += " location.href='"+multipartRequest.getContextPath()+"/board/viewArticle.do?articleNO="+articleNO+"';";
	  message +=" </script>";
      resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
    }
  
    return resEnt;
  }
  
  @Override
  @RequestMapping(value="/board/removeArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  removeArticle(@RequestParam("articleNO") int articleNO,
                              HttpServletRequest request, HttpServletResponse response) throws Exception{
	response.setContentType("text/html; charset=UTF-8");
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
	responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	try {
		boardService.removeArticle(articleNO);
		File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
		FileUtils.deleteDirectory(destDir);
		
		message = "<script>";
		message += " alert('���� �����߽��ϴ�.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	       
	}catch(Exception e) {
		message = "<script>";
		message += " alert('�۾��� ������ �߻��߽��ϴ�.�ٽ� �õ��� �ּ���.');";
		message += " location.href='"+request.getContextPath()+"/board/listArticles.do';";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    e.printStackTrace();
	}
	return resEnt;
  }  
  

  //���� �̹��� �� �߰��ϱ�
  @Override
  @RequestMapping(value="/board/addNewArticle.do" ,method = RequestMethod.POST)
  @ResponseBody
  public ResponseEntity  addNewArticle(MultipartHttpServletRequest multipartRequest, HttpServletResponse response) throws Exception {
	multipartRequest.setCharacterEncoding("utf-8");
	String imageFileName=null;
	
	Map articleMap = new HashMap();
	Enumeration enu=multipartRequest.getParameterNames();
	while(enu.hasMoreElements()){
		String name=(String)enu.nextElement();
		String value=multipartRequest.getParameter(name);
		articleMap.put(name,value);
		System.out.println(name+value);
	}
	
	//�α��� �� ���ǿ� ����� ȸ�� �������� �۾��� ���̵� ���ͼ� Map�� �����մϴ�.
	HttpSession session = multipartRequest.getSession();
	MemberVO memberVO = (MemberVO) session.getAttribute("member");
	//�������׿� ���� ��Ʈ�ѷ��� ����
	String articleNOn = multipartRequest.getParameter("check");
	String id = memberVO.getId();
	articleMap.put("id",id);
	articleMap.put("parentNO", 0);
	//ȭ��ܿ��� ���������� üũ �� ���� �� �� ���� �������� ��ܿ� ��ġ�ϰ� �������.
	if(articleNOn != null) {
		articleMap.put("articleNOn", articleNOn);
	}
	else {
		articleMap.put("articleNOn", 0);
	}
	
	List<String> fileList =upload1(multipartRequest);
	List<ImageVO> imageFileList = new ArrayList<ImageVO>();
	if(fileList!= null && fileList.size()!=0) {
		for(String fileName : fileList) {
			ImageVO imageVO = new ImageVO();
			imageVO.setImageFileName(fileName);
			imageFileList.add(imageVO);
		}
		articleMap.put("imageFileList", imageFileList);
	}
	String message;
	ResponseEntity resEnt=null;
	HttpHeaders responseHeaders = new HttpHeaders();
    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
    System.out.println("asss");
	try {
		int articleNO = boardService.addNewArticle(articleMap);
		System.out.println(articleNO);
		if(imageFileList!=null && imageFileList.size()!=0) {
			System.out.println(articleNO);
			for(ImageVO  imageVO : imageFileList) {
				System.out.println(articleNO);
				imageFileName = imageVO.getImageFileName();
				File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
				File destDir = new File(ARTICLE_IMAGE_REPO+"\\"+articleNO);
				//destDir.mkdirs();
				System.out.println(imageFileName);
				if(imageFileName != null) {
				FileUtils.moveFileToDirectory(srcFile, destDir,true);
				}
					
			
			}
		}
		    
		message = "<script>";
		message += " alert('������ �߰��߽��ϴ�.');";
		message += " location.href='"+multipartRequest.getContextPath()+"/board/listArticles.do'; ";
		message +=" </script>";
	    resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
	    
		 
	}catch(Exception e) {
		if(imageFileList!=null && imageFileList.size()!=0) {
		  for(ImageVO  imageVO:imageFileList) {
		  	imageFileName = imageVO.getImageFileName();
			File srcFile = new File(ARTICLE_IMAGE_REPO+"\\"+"temp"+"\\"+imageFileName);
		 	srcFile.delete();
		  }
		}

		
		message = " <script>";
		message +=" alert('������ �߻��߽��ϴ�. �ٽ� �õ��� �ּ���');');";
		message +=" location.href='"+multipartRequest.getContextPath()+"/board/articleForm.do'; ";
		message +=" </script>";
		resEnt = new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		e.printStackTrace();
	}
	return resEnt;
  }
	


	

	@RequestMapping(value = "/board/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = (String)request.getAttribute("viewName");
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	//�Ѱ� �̹��� ���ε��ϱ�
	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception{
		String imageFileName= null;
		Iterator<String> fileNames = multipartRequest.getFileNames();
		
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName=mFile.getOriginalFilename();
			File file = new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\" + fileName);
			if(mFile.getSize()!=0){ //File Null Check
				if(!file.exists()){ //��λ� ������ �������� ���� ���
					file.getParentFile().mkdirs();  //��ο� �ش��ϴ� ���丮���� ����
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+imageFileName)); //�ӽ÷� ����� multipartFile�� ���� ���Ϸ� ����
				}
			}
			
		}
		return imageFileName;
	}
	
	
	//���� �̹��� ���ε��ϱ�
	private List<String> upload1(MultipartHttpServletRequest multipartRequest) throws Exception{
		List<String> fileList= new ArrayList<String>();
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while(fileNames.hasNext()){
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			String originalFileName=mFile.getOriginalFilename();
			fileList.add(originalFileName);
			File file = new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+"\\" + fileName);
			if(mFile.getSize()!=0){ //File Null Check
				if(!file.exists()){ //��λ� ������ �������� ���� ���
					file.getParentFile().mkdirs();  //��ο� �ش��ϴ� ���丮���� ����
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO +"\\"+"temp"+ "\\"+originalFileName)); //�ӽ÷� ����� multipartFile�� ���� ���Ϸ� ����
				}
			}
		}
		return fileList;
	}

	
}
