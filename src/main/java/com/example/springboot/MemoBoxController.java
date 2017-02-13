package com.example.springboot;



import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.springboot.repositories.MemoBoxRepository;

/**
 * http://localhost:1598/MemoBox
 *
 * http://localhost:1598/MemoBox/form
 * http://localhost:1598/MemoBox/find
 * http://localhost:1598/MemoBox/edit
 * http://localhost:1598/MemoBox/delete
 *
 * @author maekawa
 *
 */
@Controller
public class MemoBoxController {

	@PostConstruct
	public void init(){
//		// MongoDBの場合はダミーデータ登録の必要なし
//		MemoBox d1 = new MemoBox("tanaka","memo");
//		repository.save(d1);
	}

	@Autowired
	MemoBoxRepository repository;

	@RequestMapping(value = "/MemoBox", method = RequestMethod.GET)
	public ModelAndView index(
			ModelAndView mav){
		mav.setViewName("index_MemoBox");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model MemoBox Register");
		Iterable<MemoBox> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/MemoBox/form", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@RequestParam("name") String name,
			@RequestParam("memo") String memo,
			ModelAndView mav){
		MemoBox mydata = new MemoBox(name,memo);
		repository.save(mydata);
		return new ModelAndView("redirect:/MemoBox");
	}


//	@RequestMapping(value = "/LabelCassandra", method = RequestMethod.GET)
//	public ModelAndView index1(
//			ModelAndView mav){
//		mav.setViewName("index_MemoBox");
//		mav.addObject("title", "Find Page");
//		mav.addObject("msg", "This Is Sample Spring Boot JPA Model MemoBox Register");
//		Iterable<MemoBox> list = repository.findAll();
//		mav.addObject("datalist", list);
//		return mav;
//	}
	@RequestMapping(value = "/MemoBox/find", method = RequestMethod.GET)
	public ModelAndView find(
			ModelAndView mav){
		mav.setViewName("find_MemoBox");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model MemoBox Find");
		mav.addObject("value","");
		List<MemoBox> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/MemoBox/find", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView search(
			@RequestParam("find") String param,
			ModelAndView mav){
		mav.setViewName("find_MemoBox");
		if( param == ""){
			mav = new ModelAndView("redirect:/MemoBox/find");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Search by word " + param);
			mav.addObject("value",param);
			List<MemoBox> list = repository.findByName(param);
			mav.addObject("datalist", list);
		}
		return mav;

	}

	@RequestMapping(value = "/MemoBox/editId", method = RequestMethod.POST)
	public ModelAndView editId(
			@RequestParam("id") String id,
			ModelAndView mav){
		System.out.println("editId:POST:" + id);
		mav.setViewName("edit_MemoBox");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model MemoBox Modify");
		MemoBox data = repository.findById(id);
		System.out.println("変更前メモ：" + data.getMemo());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/MemoBox/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(
//			@ModelAttribute("formModel") MyData mydata,
			@PathVariable String id,
			ModelAndView mav){
		mav.setViewName("edit_MemoBox");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model MemoBox Modify");
		MemoBox data = repository.findById(id);
		System.out.println("変更前メモ：" + data.getMemo());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/MemoBox/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(
			@RequestParam("id") String id,
			@RequestParam("name") String name,
			@RequestParam("memo") String memo,
			ModelAndView mav){
		System.out.println("変更後メモ：" + memo);
		MemoBox mydata = repository.findById(id);
		mydata.setName(name);
		mydata.setMemo(memo);
		mydata.setDate(new Date());
		System.out.println("aaa：");
		repository.save(mydata);
		System.out.println("bbb：");
		mav.addObject("formModel", null);
		return new ModelAndView("redirect:/MemoBox");
	}

	@RequestMapping(value = "/MemoBox/deleteId", method = RequestMethod.POST)
	public ModelAndView deleteId(
			@RequestParam("id") String id,
			ModelAndView mav){
		System.out.println("deleteId:POST:" + id);
		System.out.println("削除予定：");
		mav.setViewName("delete_MemoBox");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model MemoBox Delete");
		MemoBox data = repository.findById(id);
		System.out.println("削除予定ID：" + data.getId());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/MemoBox/delete", method = RequestMethod.GET)
	public ModelAndView delete(
			ModelAndView mav){
		mav.setViewName("delete_MemoBox");
		mav.addObject("title", "Delete Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model MemoBox Delete");
		mav.addObject("value","");
		List<MemoBox> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/MemoBox/delete", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(
			@RequestParam("id") String id,
			ModelAndView mav){
		System.out.println("remove id:" + id);
		mav.setViewName("index_MemoBox");
		if( id == ""){
			mav = new ModelAndView("redirect:/MemoBox/delete");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Delete by word " + id);
//			mav.addObject("value",id);
			List<MemoBox> list = repository.deleteById(id);
//			mav.addObject("datalist", list);
			mav = new ModelAndView("redirect:/MemoBox");
		}
		return mav;
	}

}
