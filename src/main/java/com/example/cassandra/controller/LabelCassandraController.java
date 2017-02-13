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
import com.example.cassandra.entity.Label;
import com.example.cassandra.form.EmployeesJOIN;
import com.example.cassandra.repository.DepartmentsRepository;
import com.example.cassandra.repository.LabelRepository;


/**
 * http://localhost:1598/LabelCassandra
 *
 * http://localhost:1598/LabelCassandra/form
 * http://localhost:1598/LabelCassandra/find
 * http://localhost:1598/LabelCassandra/edit
 * http://localhost:1598/LabelCassandra/delete
 *
 * @author sun
 *
 */
@Controller
public class LabelCassandraController {

	@PostConstruct
	public void init(){
//		// MongoDBの場合はダミーデータ登録の必要なし
//		Employees d1 = new Employees("tanaka","memo");
//		repository.save(d1);
	}

	@Autowired
	private LabelRepository repository;

	@Autowired
	private DepartmentsRepository repositoryDep;

	@RequestMapping(value = "/LabelCassandra", method = RequestMethod.GET)
	public ModelAndView index(
			ModelAndView mav){
		mav.setViewName("index_LabelCassandra");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Label Register");
		Iterable<Label> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/LabelCassandraJOIN", method = RequestMethod.GET)
	public ModelAndView indexJOIN(
			ModelAndView mav){
		mav.setViewName("index_LabelCassandraJOIN");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Register");
		Iterable<Label> list = repository.findAll();

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

	@RequestMapping(value = "/LabelCassandra/form", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@RequestParam(" kigyou_cd")       String    kigyou_cd,
			@RequestParam(" label_id")        int       label_id,
			@RequestParam(" label_name")      String    label_name,
			@RequestParam("parent_label_id")  int       parent_label_id,
			ModelAndView mav){
		Label mydata = new Label();
		  mydata.setKigyou_cd(kigyou_cd);
		  mydata.setLabel_id(label_id);
		  mydata.setLabel_name(label_name);
		  mydata.setParent_label_id(parent_label_id);
		  Date datetest = new Date();
		  mydata.setCreat_datetime(datetest);
		  mydata.setCreat_user_id("usertest");
		  mydata.setUpdate_user_id("usertest");
		  mydata.setUpdate_datetime(datetest);
		  mydata.setUi_assemb_id("ui_assemb_id");
		  mydata.setServer_assemb_id("server_assemb_id");
		  mydata.setDelete_id("0");
		repository.save(mydata);
		return new ModelAndView("redirect:/LabelCassandra");
	}

	@RequestMapping(value = "/LabelCassandra/find", method = RequestMethod.GET)
	public ModelAndView find(
			ModelAndView mav){
		mav.setViewName("find_LabelCassandra");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Label Find");
		mav.addObject("value","");
		List<Label> list = (List<Label>) repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/LabelCassandra/find", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView search(
			@RequestParam("find") String param,
			ModelAndView mav){
		mav.setViewName("find_LabelCassandra");
		if( param == ""){
			mav = new ModelAndView("redirect:/LabelCassandra/find");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Search by word " + param);
			mav.addObject("value",param);
			List<Label> list = repository.findByLabelName(param);
			mav.addObject("datalist", list);
		}
		return mav;

	}

	@RequestMapping(value = "/LabelCassandra/editId", method = RequestMethod.POST)
	public ModelAndView editId(
			@RequestParam("kigyou_cd") String kigyouCd,
			@RequestParam("label_id") int labelId,
			ModelAndView mav){
		System.out.println("editId:POST:" + kigyouCd + labelId);
		mav.setViewName("edit_LabelCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Modify");
		Label data = repository.findByLabelId(kigyouCd,labelId);
		System.out.println("変更前ラベル名：" + data.getLabel_name());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/LabelCassandra/edit/{kigyou_cd,label_id}", method = RequestMethod.GET)
	public ModelAndView edit(
//			@ModelAttribute("formModel") MyData mydata,
			@RequestParam("kigyou_cd") String kigyouCd,
			@PathVariable int labelId,
			ModelAndView mav){
		mav.setViewName("edit_LabelCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Label Modify");
		Label data = repository.findByLabelId(kigyouCd,labelId);
		System.out.println("変更前ラベル名：" + data.getLabel_name());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/LabelCassandra/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(
			@RequestParam("kigyou_cd")       String    kigyou_cd,
			@RequestParam("label_id")        int       label_id,
			@RequestParam("label_name")      String    label_name,
			@RequestParam("parent_label_id")  int       parent_label_id,
			ModelAndView mav){
		System.out.println("変更後社員名：" + label_name);
		Label mydata = repository.findByLabelId(kigyou_cd,label_id);
		  mydata.setKigyou_cd(kigyou_cd);
		  mydata.setLabel_id(label_id);
		  mydata.setLabel_name(label_name);
		  mydata.setParent_label_id(parent_label_id);
		  Date datetest = new Date();
		  mydata.setCreat_datetime(datetest);
		  mydata.setCreat_user_id("usertest");
		  mydata.setUpdate_user_id("usertest");
		  mydata.setUpdate_datetime(datetest);
		  mydata.setUi_assemb_id("ui_assemb_id");
		  mydata.setServer_assemb_id("server_assemb_id");
		  mydata.setDelete_id("0");
		repository.save(mydata);
		mav.addObject("formModel", null);
		return new ModelAndView("redirect:/LabelCassandra");
	}

	@RequestMapping(value = "/LabelCassandra/deleteId", method = RequestMethod.POST)
	public ModelAndView deleteId(
			@RequestParam("kigyou_cd") String kigyou_cd,
			@RequestParam("label_id") int label_id,
			ModelAndView mav){
		System.out.println("deleteId:POST:" + label_id);
		System.out.println("削除予定：");
		mav.setViewName("delete_LabelCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Label Delete");
		Label data = repository.findByLabelId(kigyou_cd,label_id);
		System.out.println("削除予定ID：" + data.getLabel_id());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/LabelCassandra/delete", method = RequestMethod.GET)
	public ModelAndView delete(
			ModelAndView mav){
		mav.setViewName("delete_LabelCassandra");
		mav.addObject("title", "Delete Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Label Delete");
		mav.addObject("value","");
		List<Label> list = (List<Label>) repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/LabelCassandra/delete", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(
			@RequestParam("kigyou_cd")       String    kigyou_cd,
			@RequestParam("label_id") int label_id,
			ModelAndView mav){
		System.out.println("remove id:" + label_id);
		mav.setViewName("index_LabelCassandra");
		if( label_id == 0){
			mav = new ModelAndView("redirect:/LabelCassandra/delete");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Delete by word " + label_id);
			mav.addObject("kigyou_cd",kigyou_cd);
			mav.addObject("label_id",label_id);
			List<Label> list = repository.deleteByLabelId(kigyou_cd,label_id);
			mav.addObject("datalist", list);
			mav = new ModelAndView("redirect:/LabelCassandra");
		}
		return mav;
	}

}
