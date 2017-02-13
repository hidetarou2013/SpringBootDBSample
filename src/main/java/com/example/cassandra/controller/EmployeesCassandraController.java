package com.example.cassandra.controller;



import java.util.ArrayList;
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
import com.example.cassandra.entity.Employees;
import com.example.cassandra.form.EmployeesJOIN;
import com.example.cassandra.repository.DepartmentsRepository;
import com.example.cassandra.repository.EmployeesRepository;


/**
 * http://localhost:1598/EmployeesCassandra
 *
 * http://localhost:1598/EmployeesCassandra/form
 * http://localhost:1598/EmployeesCassandra/find
 * http://localhost:1598/EmployeesCassandra/edit
 * http://localhost:1598/EmployeesCassandra/delete
 *
 * @author maekawa
 *
 */
@Controller
public class EmployeesCassandraController {

	@PostConstruct
	public void init(){
//		// MongoDBの場合はダミーデータ登録の必要なし
//		Employees d1 = new Employees("tanaka","memo");
//		repository.save(d1);
	}

	@Autowired
	private EmployeesRepository repository;

	@Autowired
	private DepartmentsRepository repositoryDep;

	@RequestMapping(value = "/EmployeesCassandra", method = RequestMethod.GET)
	public ModelAndView index(
			ModelAndView mav){
		mav.setViewName("index_EmployeesCassandra");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Register");
		Iterable<Employees> list = repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/EmployeesCassandraJOIN", method = RequestMethod.GET)
	public ModelAndView indexJOIN(
			ModelAndView mav){
		mav.setViewName("index_EmployeesCassandraJOIN");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Register");
		Iterable<Employees> list = repository.findAll();
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

	@RequestMapping(value = "/EmployeesCassandra/form", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView form(
			@RequestParam("employee_id") int employee_id,
			@RequestParam("employee_name") String employee_name,
			@RequestParam("employee_phone") String employee_phone,
			@RequestParam("employee_email") String employee_email,
			ModelAndView mav){
		Employees mydata = new Employees();
		mydata.setEmployee_id(employee_id);
		mydata.setEmployee_name(employee_name);
		mydata.setEmployee_phone(employee_phone);
		mydata.setEmployee_email(employee_email);
		repository.save(mydata);
		return new ModelAndView("redirect:/EmployeesCassandra");
	}

	@RequestMapping(value = "/EmployeesCassandra/find", method = RequestMethod.GET)
	public ModelAndView find(
			ModelAndView mav){
		mav.setViewName("find_EmployeesCassandra");
		mav.addObject("title", "Find Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Find");
		mav.addObject("value","");
		List<Employees> list = (List<Employees>) repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/EmployeesCassandra/find", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView search(
			@RequestParam("find") String param,
			ModelAndView mav){
		mav.setViewName("find_EmployeesCassandra");
		if( param == ""){
			mav = new ModelAndView("redirect:/EmployeesCassandra/find");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Search by word " + param);
			mav.addObject("value",param);
			List<Employees> list = repository.findByEmployeeName(param);
			mav.addObject("datalist", list);
		}
		return mav;

	}

	@RequestMapping(value = "/EmployeesCassandra/editId", method = RequestMethod.POST)
	public ModelAndView editId(
			@RequestParam("employee_id") int id,
			ModelAndView mav){
		System.out.println("editId:POST:" + id);
		mav.setViewName("edit_EmployeesCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Modify");
		Employees data = repository.findByEmployeeId(id);
		System.out.println("変更前社員名：" + data.getEmployee_name());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/EmployeesCassandra/edit/{employee_id}", method = RequestMethod.GET)
	public ModelAndView edit(
//			@ModelAttribute("formModel") MyData mydata,
			@PathVariable int employee_id,
			ModelAndView mav){
		mav.setViewName("edit_EmployeesCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Modify");
		Employees data = repository.findByEmployeeId(employee_id);
		System.out.println("変更前社員名：" + data.getEmployee_name());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/EmployeesCassandra/edit", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView update(
			@RequestParam("employee_id") int employee_id,
			@RequestParam("employee_name") String employee_name,
			@RequestParam("employee_phone") String employee_phone,
			@RequestParam("employee_email") String employee_email,
			ModelAndView mav){
		System.out.println("変更後社員名：" + employee_name);
		Employees mydata = repository.findByEmployeeId(employee_id);
		mydata.setEmployee_name(employee_name);
		mydata.setEmployee_id(employee_id);
		mydata.setEmployee_phone(employee_phone);
		mydata.setEmployee_email(employee_email);
		repository.save(mydata);
		mav.addObject("formModel", null);
		return new ModelAndView("redirect:/EmployeesCassandra");
	}

	@RequestMapping(value = "/EmployeesCassandra/deleteId", method = RequestMethod.POST)
	public ModelAndView deleteId(
			@RequestParam("employee_id") int employee_id,
			ModelAndView mav){
		System.out.println("deleteId:POST:" + employee_id);
		System.out.println("削除予定：");
		mav.setViewName("delete_EmployeesCassandra");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Delete");
		Employees data = repository.findByEmployeeId(employee_id);
		System.out.println("削除予定ID：" + data.getEmployee_id());
		mav.addObject("formModel", data);
		return mav;
	}

	@RequestMapping(value = "/EmployeesCassandra/delete", method = RequestMethod.GET)
	public ModelAndView delete(
			ModelAndView mav){
		mav.setViewName("delete_EmployeesCassandra");
		mav.addObject("title", "Delete Page");
		mav.addObject("msg", "This Is Sample Spring Boot JPA Model Employees Delete");
		mav.addObject("value","");
		List<Employees> list = (List<Employees>) repository.findAll();
		mav.addObject("datalist", list);
		return mav;
	}

	@RequestMapping(value = "/EmployeesCassandra/delete", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView remove(
			@RequestParam("employee_id") int employee_id,
			ModelAndView mav){
		System.out.println("remove id:" + employee_id);
		mav.setViewName("index_EmployeesCassandra");
		if( employee_id == 0){
			mav = new ModelAndView("redirect:/EmployeesCassandra/delete");
		}else{
			mav.addObject("title", "Find Result");
			mav.addObject("msg", "This Is Result of Delete by word " + employee_id);
//			mav.addObject("value",id);
			List<Employees> list = repository.deleteByEmployeeId(employee_id);
//			mav.addObject("datalist", list);
			mav = new ModelAndView("redirect:/EmployeesCassandra");
		}
		return mav;
	}

}
