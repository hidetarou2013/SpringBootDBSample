package com.example.cassandra.controller;



import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.cassandra.entity.Departments;
import com.example.cassandra.entity.Tag;
import com.example.cassandra.form.EmployeesJOIN;
import com.example.cassandra.repository.DepartmentsRepository;
import com.example.cassandra.repository.TagRepository;


/**
 * http://localhost:1598/TagCassandra
 *
 * http://localhost:1598/TagCassandra/form
 * http://localhost:1598/TagCassandra/find
 * http://localhost:1598/TagCassandra/edit
 * http://localhost:1598/TagCassandra/delete
 *
 * @author sun
 *
 */
@Controller
public class TagCassandraController {

	@PostConstruct
	public void init(){
//		// MongoDBの場合はダミーデータ登録の必要なし
//		Employees d1 = new Employees("tanaka","memo");
//		repository.save(d1);
	}

	@Autowired
	private TagRepository repository;

	@Autowired
	private DepartmentsRepository repositoryDep;

	@RequestMapping(value = "/TagCassandra", method = RequestMethod.GET)
	public ModelAndView index(
			ModelAndView mav){
		mav.setViewName("index_TagCassandra");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Tag Register");
		Iterable<Tag> list = repository.findAll();
		mav.addObject("datalist", list);

		return mav;
	}

	@RequestMapping(value = "/TagCassandraJOIN", method = RequestMethod.GET)
	public ModelAndView indexJOIN(
			ModelAndView mav){
		mav.setViewName("index_TagCassandraJOIN");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Register");
		Iterable<Tag> list = repository.findAll();

		//TODO
		Iterable<Departments> listDep = repositoryDep.findAll();

		List<EmployeesJOIN> listJOIN = new ArrayList<EmployeesJOIN>();
		list.forEach(
			(k) -> {
				EmployeesJOIN e = new EmployeesJOIN();
				BeanUtils.copyProperties(k, e);
				String name = "";
				Iterator<Departments> ite = listDep.iterator();
				while(ite.hasNext()){
					Departments dep = ite.next();
					if(dep.getDepartment_id() == e.getDepartment_id()){
						name = dep.getDepartment_name();
						break;
					}
				}
				e.setDepartment_name(name);
				listJOIN.add(e);
			}
		);

		mav.addObject("datalist", listJOIN);
		return mav;
	}

	@RequestMapping(value = "/TagCassandra/form", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@RequestParam("kigyou_cd")     String kigyouCd,
			//案01－02の対応
			@RequestParam("attibute_id")   String attibuteId,
			@RequestParam("tag_id")        int    tag_id,
			@RequestParam("tag_name")      String tag_name,
			@RequestParam("label_id")      int    label_id,
			@RequestParam("target_id")     String target_id,
			ModelAndView mav){
		Tag mydata = new Tag();
		  mydata.setKigyou_cd(kigyouCd);
		  mydata.setAttibute_id(attibuteId);
		  mydata.setTag_id(tag_id);
		  mydata.setTag_name(tag_name);
		  mydata.setLabel_id(label_id);
		  mydata.setTarget_id(target_id);
		  Date datetest = new Date();
		  mydata.setCreat_datetime(datetest);
		  mydata.setCreat_user_id("usertest");
		  mydata.setUpdate_user_id("usertest");
		  mydata.setUpdate_datetime(datetest);
		  mydata.setUi_assemb_id("ui_assemb_id");
		  mydata.setServer_assemb_id("server_assemb_id");
		  mydata.setDelete_id("0");
		repository.save(mydata);

		return new ModelAndView("redirect:/TagCassandra");
	}

	@RequestMapping(value = "/TagCassandra/find", method = RequestMethod.GET)
	public ModelAndView find(
			ModelAndView mav){
		mav.setViewName("find_TagCassandra");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Tag Find");
		mav.addObject("value","");
		List<Tag> list = (List<Tag>) repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/TagCassandra/find", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView search(
			@RequestParam("find") String param,
			ModelAndView mav){
		mav.setViewName("find_TagCassandra");
		if( param == ""){
			mav = new ModelAndView("redirect:/TagCassandra/find");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Search by word " + param);
			mav.addObject("value",param);
			List<Tag> list = repository.findByTagName(param);
			mav.addObject("datalist", list);
		}
		return mav;

	}

	@RequestMapping(value = "/TagCassandra/editId", method = RequestMethod.POST)
	public ModelAndView editId(
			@RequestParam("kigyou_cd") String kigyou_cd,
			//案01－02の対応
			@RequestParam("attibute_id") String attibuteId,
			@RequestParam("tag_id") int tagId,
			ModelAndView mav){
		System.out.println("editId:POST:" + kigyou_cd + attibuteId + tagId);
		mav.setViewName("edit_TagCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model 案１-2 Modify");
		//Tag data = repository.findByTagId(kigyouCd,labelId);
		//案1－02対応
		Tag data = repository.findByKeys(kigyou_cd,attibuteId,tagId);

		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/TagCassandra/edit/{kigyou_cd,label_id}", method = RequestMethod.GET)
	public ModelAndView edit(
//			@ModelAttribute("formModel") MyData mydata,
			@RequestParam("kigyou_cd") String kigyouCd,
			//案01－02の対応
			@RequestParam("attibute_id") String attibuteId,
			@PathVariable int labelId,
			ModelAndView mav){
		mav.setViewName("edit_TagCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Tag Modify");
		Tag data = repository.findByKeys(kigyouCd,attibuteId,labelId);
		System.out.println("変更前ラベル名：" + data.getTag_name());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/TagCassandra/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(
			@RequestParam("kigyou_cd")       String    kigyouCd,
			//案01－02の対応
			@RequestParam("attibute_id")     String    attibuteId,
			@RequestParam("tag_id")          int       tagId,
			@RequestParam("tag_name")        String    tag_name,
			@RequestParam("label_id")        int       labelId,
			@RequestParam("target_id")       String    target_id,
			ModelAndView mav){

		//Tag mydata = repository.findByTagId(kigyou_cd,label_id);
		Tag mydata = new Tag();
		  mydata.setKigyou_cd(kigyouCd);
		  mydata.setAttibute_id(attibuteId);
		  mydata.setTag_id(tagId);
		  mydata.setTag_name(tag_name);
		  mydata.setLabel_id(labelId);
		  mydata.setTarget_id(target_id);
		  Date datetest = new Date();
		  mydata.setCreat_datetime(datetest);
		  mydata.setCreat_user_id("usertest");
		  mydata.setUpdate_user_id("usertest");
		  mydata.setUpdate_datetime(datetest);
		  mydata.setUi_assemb_id("ui_assemb_id");
		  mydata.setServer_assemb_id("server_assemb_id");
		  mydata.setDelete_id("0");
		repository.save(mydata);
		 mydata = repository.findByKeys(kigyouCd,attibuteId,labelId);
		mav.addObject("formModel", mydata);
		Iterable<Tag> datalist = repository.findTagsByTag(labelId);
		mav.addObject("datalist", datalist);

		return new ModelAndView("redirect:/TagCassandra");
	}

	@RequestMapping(value = "/TagCassandra/deleteId", method = RequestMethod.POST)
	public ModelAndView deleteId(
			@RequestParam("kigyou_cd") String kigyou_cd,
			//案01－02の対応
			@RequestParam("attibute_id") String attibuteId,
			@RequestParam("tag_id") int tag_id,
			ModelAndView mav){
		System.out.println("deleteId:POST:" + tag_id);
		System.out.println("削除予定：");
		mav.setViewName("delete_TagCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Tag Delete");
		//Label data = repository.findByLabelId(kigyou_cd,label_id);
		/* 案1－02対応:*/
		Tag mydata = repository.findByKeys(kigyou_cd,attibuteId,tag_id);
		System.out.println("削除予定ID：" + mydata.getTag_id());
		mav.addObject("formModel", mydata);
		return mav;
	}

	@RequestMapping(value = "/TagCassandra/delete", method = RequestMethod.GET)
	public ModelAndView delete(
			ModelAndView mav){
		mav.setViewName("delete_TagCassandra");
		mav.addObject("title", "Delete Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Tag Delete");
		mav.addObject("value","");
		List<Tag> list = (List<Tag>) repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/TagCassandra/delete", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(
			@RequestParam("kigyou_cd")   String  kigyou_cd,
			@RequestParam("attibute_id") String attibuteId,
			@RequestParam("tag_id") int tag_id,
			ModelAndView mav){
		System.out.println("remove id:" + tag_id);
		mav.setViewName("index_TagCassandra");
		if( tag_id == 0){
			mav = new ModelAndView("redirect:/TagCassandra/delete");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Delete by word " + tag_id);
			mav.addObject("kigyou_cd",kigyou_cd);
			mav.addObject("attibute_id",attibuteId);
			mav.addObject("tag_id",tag_id);
			List<Tag> list = repository.deleteByTagId(kigyou_cd,attibuteId,tag_id);
			mav.addObject("datalist", list);
			mav = new ModelAndView("redirect:/TagCassandra");
		}
		return mav;
	}

}
