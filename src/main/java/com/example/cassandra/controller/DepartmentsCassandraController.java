package com.example.cassandra.controller;



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

import com.example.cassandra.entity.Departments;
import com.example.cassandra.repository.DepartmentsRepository;


/**
 * http://localhost:1598/DepartmentsCassandra
 *
 * http://localhost:1598/DepartmentsCassandra/form
 * http://localhost:1598/DepartmentsCassandra/find
 * http://localhost:1598/DepartmentsCassandra/edit
 * http://localhost:1598/DepartmentsCassandra/delete
 *
 * @author maekawa
 *
 */
@Controller
public class DepartmentsCassandraController {

	@PostConstruct
	public void init(){
//		// MongoDBの場合はダミーデータ登録の必要なし
//		Departments d1 = new Departments("tanaka","memo");
//		repository.save(d1);
	}

	@Autowired
	private DepartmentsRepository repository;

	@RequestMapping(value = "/DepartmentsCassandra", method = RequestMethod.GET)
	public ModelAndView index(
			ModelAndView mav){
		mav.setViewName("index_DepartmentsCassandra");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Departments Register");
		Iterable<Departments> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/DepartmentsCassandra/form", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@RequestParam("department_id") int department_id,
			@RequestParam("department_name") String department_name,
			ModelAndView mav){
		Departments mydata = new Departments(department_id,department_name);
		repository.save(mydata);
		return new ModelAndView("redirect:/DepartmentsCassandra");
	}

	@RequestMapping(value = "/DepartmentsCassandra/find", method = RequestMethod.GET)
	public ModelAndView find(
			ModelAndView mav){
		mav.setViewName("find_DepartmentsCassandra");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Departments Find");
		mav.addObject("value","");
		List<Departments> list = (List<Departments>) repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/DepartmentsCassandra/find", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView search(
			@RequestParam("find") String param,
			ModelAndView mav){
		mav.setViewName("find_DepartmentsCassandra");
		if( param == ""){
			mav = new ModelAndView("redirect:/DepartmentsCassandra/find");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Search by word " + param);
			mav.addObject("value",param);
			List<Departments> list = repository.findByDepartmentName(param);
			mav.addObject("datalist", list);
		}
		return mav;

	}

	@RequestMapping(value = "/DepartmentsCassandra/editId", method = RequestMethod.POST)
	public ModelAndView editId(
			@RequestParam("department_id") int id,
			ModelAndView mav){
		System.out.println("editId:POST:" + id);
		mav.setViewName("edit_DepartmentsCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Departments Modify");
		Departments data = repository.findByDepartmentId(id);
		System.out.println("変更前部署名：" + data.getDepartment_name());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/DepartmentsCassandra/edit/{department_id}", method = RequestMethod.GET)
	public ModelAndView edit(
//			@ModelAttribute("formModel") MyData mydata,
			@PathVariable int department_id,
			ModelAndView mav){
		mav.setViewName("edit_DepartmentsCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Departments Modify");
		Departments data = repository.findByDepartmentId(department_id);
		System.out.println("変更前部署名：" + data.getDepartment_name());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/DepartmentsCassandra/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(
			@RequestParam("department_id") int department_id,
			@RequestParam("department_name") String department_name,
			ModelAndView mav){
		System.out.println("変更後部署名：" + department_name);
		Departments mydata = repository.findByDepartmentId(department_id);
		mydata.setDepartment_name(department_name);
		mydata.setDepartment_id(department_id);
		repository.save(mydata);
		System.out.println("bbb：");
		mav.addObject("formModel", null);
		return new ModelAndView("redirect:/DepartmentsCassandra");
	}

	@RequestMapping(value = "/DepartmentsCassandra/deleteId", method = RequestMethod.POST)
	public ModelAndView deleteId(
			@RequestParam("department_id") int department_id,
			ModelAndView mav){
		System.out.println("deleteId:POST:" + department_id);
		System.out.println("削除予定：");
		mav.setViewName("delete_DepartmentsCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Departments Delete");
		Departments data = repository.findByDepartmentId(department_id);
		System.out.println("削除予定ID：" + data.getDepartment_id());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/DepartmentsCassandra/delete", method = RequestMethod.GET)
	public ModelAndView delete(
			ModelAndView mav){
		mav.setViewName("delete_DepartmentsCassandra");
		mav.addObject("title", "Delete Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Departments Delete");
		mav.addObject("value","");
		List<Departments> list = (List<Departments>) repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/DepartmentsCassandra/delete", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(
			@RequestParam("department_id") int department_id,
			ModelAndView mav){
		System.out.println("remove id:" + department_id);
		mav.setViewName("index_DepartmentsCassandra");
		if( department_id == 0){
			mav = new ModelAndView("redirect:/DepartmentsCassandra/delete");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Delete by word " + department_id);
//			mav.addObject("value",id);
			List<Departments> list = repository.deleteByDepartmentId(department_id);
//			mav.addObject("datalist", list);
			mav = new ModelAndView("redirect:/DepartmentsCassandra");
		}
		return mav;
	}

}
